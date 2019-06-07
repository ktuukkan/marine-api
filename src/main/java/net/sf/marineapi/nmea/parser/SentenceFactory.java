/*
 * SentenceFactory.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 *
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Factory for creating sentence parsers.
 * <p>
 * Custom parsers may be implemented and registered in the factory at runtime
 * by following these steps:
 * <ol>
 * <li>Define a sentence interface by extending the {@link Sentence} interface
 * (e.g. {@code com.acme.XYZSentence}).</li>
 * <li>Implement the interface in a class that extends {@link SentenceParser},
 * (e.g. {@code com.acme.XYZParser}).</li>
 * <li>Use the protected getters and setters in {@code SentenceParser} to
 * read and write sentence data.</li>
 * <li>Add a constructor in {@code XYZParser} with {@code String}
 * parameter, i.e. the sentence to be parsed. Pass this parameter to
 * {@link SentenceParser#SentenceParser(String, String)} with expected sentence
 * type (e.g. {@code "XYZ"}).</li>
 * <li>Add another constructor with {@link TalkerId} parameter. Pass this
 * parameter to {@link SentenceParser#SentenceParser(TalkerId, String, int)}
 * with sentence type and the expected number of data fields.</li>
 * <li>Register {@code XYZParser} in {@code SentenceFactory} by using
 * the {@link #registerParser(String, Class)} method.</li>
 * <li>Use {@link SentenceFactory#createParser(String)} or
 * {@link SentenceFactory#createParser(TalkerId, String)} to obtain an instance
 * of your parser. In addition, {@link net.sf.marineapi.nmea.io.SentenceReader}
 * will now dispatch instances of {@code XYZSentence} when "XYZ" sentences
 * are read from the data source.</li>
 * </ol>
 * <p>
 * Notice that there is no need to compile the whole library and the added
 * parser source code may be located in your own codebase. Additionally, it is
 * also possible to override any existing parsers of the library as needed.
 * </p>
 *
 * @author Kimmo Tuukkanen
 */
public final class SentenceFactory {

	// map that holds registered sentence types and parser classes
	private static Map<String, Class<? extends SentenceParser>> parsers;

	// singleton factory instance
	private static final SentenceFactory INSTANCE = new SentenceFactory();

	/**
	 * Constructor.
	 */
	private SentenceFactory() {
		reset();
	}

	/**
	 * Creates a parser for specified NMEA 0183 sentence String. The parser
	 * implementation is selected from registered parsers according to sentence
	 * type. The returned instance must be cast in to correct sentence
	 * interface, for which the type should first be checked by using the
	 * {@link Sentence#getSentenceId()} method.
	 *
	 * @param nmea NMEA 0183 sentence String
	 * @return Sentence parser instance for specified sentence
	 * @throws IllegalArgumentException If there is no parser registered for the
	 *             given sentence type
	 * @throws IllegalStateException If parser is found, but it does not
	 *             implement expected constructors or is otherwise unusable.
	 */
	public Sentence createParser(String nmea) {
		String sid = SentenceId.parseStr(nmea);
		return createParserImpl(sid, nmea);
	}

	/**
 	 * Creates a parser for specified talker and sentence type. The returned
 	 * instance needs to be cast to corresponding sentence interface.
 	 *
	 * @param talker Sentence talker id
	 * @param type Sentence type
	 * @return Sentence parser of requested type.
	 * @throws IllegalArgumentException If talker id is null or if there is no
	 *             parser registered for given sentence type.
	 * @throws IllegalStateException If parser instantiation fails.
	 */
	public Sentence createParser(TalkerId talker, SentenceId type) {
		return createParser(talker, type.toString());
	}

	/**
	 * Creates a parser for specified talker and sentence type. This method is
	 * mainly intended to be used when custom parsers have been registered in
	 * the factory. The returned instance needs to be cast to corresponding
	 * sentence interface.
	 *
	 * @param talker Talker ID to use in parser
	 * @param type Type of the parser to create
	 * @return Sentence parser for requested type
	 * @throws IllegalArgumentException If talker id is null or if there is no
	 *             parser registered for given sentence type.
	 * @throws IllegalStateException If parser is found, but it does not
	 *             implement expected constructors or is otherwise unusable.
	 */
	public Sentence createParser(TalkerId talker, String type) {

		if (talker == null) {
			throw new IllegalArgumentException("TalkerId cannot be null");
		}

		return createParserImpl(type, talker);
	}

	/**
	 * Tells if the factory is able to create parser for specified sentence
	 * type. All {@link SentenceId} enum values should result returning
	 * {@code true} at all times.
	 *
	 * @param type Sentence type id, e.g. "GLL" or "GGA".
	 * @return true if type is supported, otherwise false.
	 */
	public boolean hasParser(String type) {
		return parsers.containsKey(type);
	}

	/**
	 * Returns a list of currently parseable sentence types.
	 *
	 * @return List of sentence ids
	 */
	public List<String> listParsers() {
		Set<String> keys = parsers.keySet();
		return Arrays.asList(keys.toArray(new String[parsers.size()]));
	}

	/**
	 * Registers a sentence parser to the factory. After registration,
	 * {@link #createParser(String)} method can be used to obtain instances of
	 * registered parser.
	 * <p>
	 * Sentences supported by the library are registered automatically, but they
	 * can be overridden simply be registering a new parser implementation for
	 * chosen sentence type. That is, each sentence type can have only one
	 * parser registered at a time.
	 *
	 * @param type Sentence type id, e.g. "GGA" or "GLL".
	 * @param parser Class of parser implementation for given {@code type}.
	 */
	public void registerParser(String type,
		Class<? extends SentenceParser> parser) {
		registerParser(parsers, type, parser);
	}

	/**
	 * Registers a sentence parser to the given factory. After registration,
	 * {@link #createParser(String)} method can be used to obtain instances of
	 * registered parser.
	 * <p>
	 * Sentences supported by the library are registered automatically, but they
	 * can be overridden simply be registering a new parser implementation for
	 * chosen sentence type. That is, each sentence type can have only one
	 * parser registered at a time.
	 *
	 * @param parsers The provided factory to register the sentence parsers to.
	 * @param type Sentence type id, e.g. "GGA" or "GLL".
	 * @param parser Class of parser implementation for given {@code type}.
	 */
	private void registerParser(
			Map<String, Class<? extends SentenceParser>> parsers, String type,
			Class<? extends SentenceParser> parser) {

		try {
			parser.getConstructor(new Class[] { String.class });
			parser.getConstructor(new Class[] { TalkerId.class });
			parsers.put(type, parser);
		} catch (SecurityException e) {
			String msg = "Unable to register parser due security violation";
			throw new IllegalArgumentException(msg, e);
		} catch (NoSuchMethodException e) {
			String msg = "Required constructors not found; SentenceParser(String),"
					+ " SentenceParser(TalkerId)";
			throw new IllegalArgumentException(msg, e);
		}
	}

	/**
	 * Unregisters a parser class, regardless of sentence type(s) it is
	 * registered for.
	 *
	 * @param parser Parser implementation class for {@code type}.
	 * @see #registerParser(String, Class)
	 */
	public void unregisterParser(Class<? extends SentenceParser> parser) {

		for (String key : parsers.keySet()) {

			if (parsers.get(key) == parser) {
				parsers.remove(key);
				break;
			}
		}
	}

	/**
	 * Creates a new parser instance with specified parameters.
	 *
	 * @param sid Sentence/parser type ID, e.g. "GGA" or "GLL"
	 * @param param Object to pass as parameter to parser constructor
	 * @return Sentence parser
	 */
	private Sentence createParserImpl(String sid, Object param) {

		if (!hasParser(sid)) {
			String msg = String.format("Parser for type '%s' not found", sid);
			throw new UnsupportedSentenceException(msg);
		}

		Sentence parser = null;
		Class<?> klass = param.getClass();

		try {
			Class<? extends SentenceParser> c = parsers.get(sid);
			Constructor<? extends SentenceParser> co = c.getConstructor(klass);
			parser = co.newInstance(param);
		} catch (NoSuchMethodException e) {
			String name = klass.getName();
			String msg = "Constructor with %s parameter not found";
			throw new IllegalStateException(String.format(msg, name), e);
		} catch (InstantiationException e) {
			throw new IllegalStateException("Unable to instantiate parser", e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Unable to access parser", e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException(
				"Unable to invoke parser constructor", e);
		}

		return parser;
	}

	/**
	 * Returns the singleton instance of {@code SentenceFactory}.
	 *
	 * @return SentenceFactory instance
	 */
	public static SentenceFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Resets the factory in it's initial state, i.e. restores and removes all
	 * parsers the have been either removed or added.
	 * 
	 * Add new parsers for Boreal GasFinder DTA and DTB
     * @author Bob Schwarz
	 * @see <a href="https://github.com/LoadBalanced/marine-api">marina-api fork</a>
	 */
	public void reset() {
		Map<String, Class<? extends SentenceParser>> tempParsers = new ConcurrentHashMap<>();
		registerParser(tempParsers, "APB", APBParser.class);
		registerParser(tempParsers, "ALK", STALKParser.class);
		registerParser(tempParsers, "BOD", BODParser.class);
		registerParser(tempParsers, "CUR", CURParser.class);
		registerParser(tempParsers, "DBT", DBTParser.class);
		registerParser(tempParsers, "DPT", DPTParser.class);
		registerParser(tempParsers, "DTM", DTMParser.class);
		registerParser(tempParsers, "GBS", GBSParser.class);
		registerParser(tempParsers, "GGA", GGAParser.class);
		registerParser(tempParsers, "GLL", GLLParser.class);
		registerParser(tempParsers, "GNS", GNSParser.class);
		registerParser(tempParsers, "GSA", GSAParser.class);
		registerParser(tempParsers, "GST", GSTParser.class);
		registerParser(tempParsers, "GSV", GSVParser.class);
		registerParser(tempParsers, "HDG", HDGParser.class);
		registerParser(tempParsers, "HDM", HDMParser.class);
		registerParser(tempParsers, "HDT", HDTParser.class);
		registerParser(tempParsers, "HTC", HTCParser.class);
		registerParser(tempParsers, "HTD", HTDParser.class);
		registerParser(tempParsers, "MHU", MHUParser.class);
		registerParser(tempParsers, "MMB", MMBParser.class);
		registerParser(tempParsers, "MTA", MTAParser.class);
		registerParser(tempParsers, "MTW", MTWParser.class);
		registerParser(tempParsers, "MWV", MWVParser.class);
		registerParser(tempParsers, "RMB", RMBParser.class);
		registerParser(tempParsers, "RMC", RMCParser.class);
		registerParser(tempParsers, "RPM", RPMParser.class);
		registerParser(tempParsers, "ROT", ROTParser.class);
		registerParser(tempParsers, "RTE", RTEParser.class);
		registerParser(tempParsers, "RSA", RSAParser.class);
		registerParser(tempParsers, "TTM", TTMParser.class);
		registerParser(tempParsers, "TXT", TXTParser.class);
		registerParser(tempParsers, "VBW", VBWParser.class);
		registerParser(tempParsers, "VDM", VDMParser.class);
		registerParser(tempParsers, "VDO", VDOParser.class);
		registerParser(tempParsers, "VDR", VDRParser.class);
		registerParser(tempParsers, "VHW", VHWParser.class);
		registerParser(tempParsers, "VLW", VLWParser.class);
		registerParser(tempParsers, "VTG", VTGParser.class);
		registerParser(tempParsers, "VWR", VWRParser.class);
		registerParser(tempParsers, "VWT", VWTParser.class);
		registerParser(tempParsers, "WPL", WPLParser.class);
		registerParser(tempParsers, "XTE", XTEParser.class);
		registerParser(tempParsers, "XDR", XDRParser.class);
		registerParser(tempParsers, "ZDA", ZDAParser.class);
		registerParser(tempParsers, "MDA", MDAParser.class);
		registerParser(tempParsers, "MWD", MWDParser.class);
		registerParser(tempParsers, "DTA", DTAParser.class);
		registerParser(tempParsers, "DTB", DTBParser.class);
		parsers = tempParsers;
	}
}

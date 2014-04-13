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
import java.util.HashMap;
import java.util.Map;

import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Factory for creating sentence parsers.
 * <p>
 * Custom parsers may be implemented and registered in factory as follows:
 * <ol>
 * <li>Define a sentence interface by extending the {@link Sentence} interface
 * (e.g. <code>com.acme.XYZSentence</code>).</li>
 * <li>Implement the interface in a class that extends {@link SentenceParser},
 * (e.g. <code>com.acme.XYZParser</code>).</li>
 * <li>Use the protected getters and setters in <code>SentenceParser</code> to
 * read and write sentence data.</li>
 * <li>Add a constructor in <code>XYZParser</code> with <code>String</code>
 * parameter, i.e. the sentence to be parsed. Pass this parameter to
 * {@link SentenceParser#SentenceParser(String, String)} with expected sentence
 * type (e.g. <code>"XYZ"</code>).</li>
 * <li>Add another constructor with {@link TalkerId} parameter. Pass this
 * parameter to {@link SentenceParser#SentenceParser(TalkerId, String, int)}
 * with sentence type and number of data fields.</li>
 * <li>Register <code>XYZParser</code> in <code>SentenceFactory</code> by using
 * the {@link #registerParser(String, Class)} method.</li>
 * <li>Use {@link SentenceFactory#createParser(String)} or
 * {@link SentenceFactory#createParser(TalkerId, String)} to obtain an instance
 * of your parser. In addition, {@link net.sf.marineapi.nmea.io.SentenceReader}
 * will dispatch instances of <code>XYZSentence</code> when "XYZ" sentences are
 * read from data source.</li>
 * </ol>
 * 
 * @author Kimmo Tuukkanen
 */
public final class SentenceFactory {

	// map that holds registered sentence types and parser classes
	private static Map<String, Class<? extends SentenceParser>> parsers = new HashMap<String, Class<? extends SentenceParser>>();

	// singleton factory instance
	private static volatile SentenceFactory instance;

	/**
	 * Constructor.
	 */
	private SentenceFactory() {
		registerParser("BOD", BODParser.class);
		registerParser("DBT", DBTParser.class);
		registerParser("DPT", DPTParser.class);
		registerParser("GGA", GGAParser.class);
		registerParser("GLL", GLLParser.class);
		registerParser("GSA", GSAParser.class);
		registerParser("GSV", GSVParser.class);
		registerParser("HDG", HDGParser.class);
		registerParser("HDM", HDMParser.class);
		registerParser("HDT", HDTParser.class);
		registerParser("MTA", MTAParser.class);
		registerParser("MTW", MTWParser.class);
		registerParser("MWV", MWVParser.class);
		registerParser("RMB", RMBParser.class);
		registerParser("RMC", RMCParser.class);
		registerParser("ROT", ROTParser.class);
		registerParser("RTE", RTEParser.class);
		registerParser("RSA", RSAParser.class);
		registerParser("VDR", VDRParser.class);
		registerParser("VHW", VHWParser.class);
		registerParser("VTG", VTGParser.class);
		registerParser("WPL", WPLParser.class);
		registerParser("XTE", XTEParser.class);
		registerParser("XDR", XDRParser.class);
		registerParser("ZDA", ZDAParser.class);
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
	 * <code>true</code> at all times.
	 * 
	 * @param type Sentence type id, e.g. "GLL" or "GGA".
	 * @return true if type is supported, otherwise false.
	 */
	public boolean hasParser(String type) {
		return parsers.containsKey(type);
	}

	/**
	 * Register a sentence parser to factory. After registration,
	 * {@link #createParser(String)} method can be used to obtain instances of
	 * registered parser.
	 * <p>
	 * Sentences supported by the library are registered automatically, but they
	 * can be overridden simply be registering a new parser implementation for
	 * chosen sentence type. That is, each sentence type can have only one
	 * parser registered at a time.
	 * 
	 * @param type Sentence type id, e.g. "GGA" or "GLL".
	 * @param parser Class of parser implementation for given <code>type</code>.
	 */
	public void registerParser(String type,
		Class<? extends SentenceParser> parser) {

		try {
			parser.getConstructor(String.class);
			parser.getConstructor(TalkerId.class);
			parsers.put(type, parser);
		} catch (SecurityException e) {
			String msg = "Unable to register parser due security violation";
			throw new IllegalArgumentException(msg, e);
		} catch (NoSuchMethodException e) {
			String msg = "Required constructors not found; SentenceParser(String), SentenceParser(TalkerId)";
			throw new IllegalArgumentException(msg, e);
		}
	}

	/**
	 * Unregisters a parser class, regardless of sentence type(s) it is
	 * registered for.
	 * 
	 * @param parser Parser implementation class for <code>type</code>.
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
			throw new IllegalArgumentException(msg);
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
	 * Returns the singleton instance of <code>SentenceFactory</code>.
	 * 
	 * @return SentenceFactory instance
	 */
	public static SentenceFactory getInstance() {
		if (instance == null) {
			instance = new SentenceFactory();
		}
		return instance;
	}
}

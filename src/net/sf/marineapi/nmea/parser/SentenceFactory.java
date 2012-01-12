/* 
 * SentenceFactory.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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

import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Factory for creating sentence parsers.
 * <p>
 * To create and register a custom parser:
 * <ol>
 * <li>Define a sentence interface by extending the {@link Sentence} interface
 * (e.g. <code>XYZSentence</code>).</li>
 * <li>Implement the interface in a class that extends {@link SentenceParser},
 * (e.g. <code>XYZParser</code>).</li>
 * <li>Use the protected getters and setters in <code>SentenceParser</code> to
 * read and write sentence data.</li>
 * <li>Add a constructor with <code>String</code> parameter, i.e. the sentence
 * to be parsed by the parser. Pass this parameter to
 * {@link SentenceParser#SentenceParser(String, String)} together with expected
 * sentence id (e.g. <code>"XYZ"</code>).</li>
 * <li>Add another constructor with {@link TalkerId} parameter. Pass this
 * parameter to {@link SentenceParser#SentenceParser(TalkerId, String, int)}
 * along with parser type and number of sentence fields.</li>
 * <li>Register your parser class in <code>SentenceFactory</code> using the
 * {@link #registerParser(String, Class)} method.</li>
 * <li>Use {@link SentenceFactory#createParser(String)} or
 * {@link SentenceFactory#createParser(TalkerId, String)} to obtain an instance
 * of your parser. Also, {@link SentenceReader} will now dispatch instances of
 * it whenever the corresponding sentence is read from input stream.</li>
 * </ol>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
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
        registerParser("HDT", HDMParser.class);
        registerParser("MTW", MTWParser.class);
        registerParser("MWV", MWVParser.class);
        registerParser("RMB", RMBParser.class);
        registerParser("RMC", RMCParser.class);
        registerParser("RTE", RTEParser.class);
        registerParser("VHW", VHWParser.class);
        registerParser("VTG", VTGParser.class);
        registerParser("WPL", WPLParser.class);
        registerParser("ZDA", ZDAParser.class);
    }

    /**
     * Creates a parser for specified NMEA sentence String. The parser
     * implementation is selected by the sentence id
     * 
     * @param nmea NMEA 0183 sentence String
     * @return Sentence parser instance for specified sentence
     * @throws IllegalArgumentException If there is no parser registered for the
     *             given sentence type
     * @throws IllegalStateException If parser is found, but it does not
     *             implement expected constructor with single String parameter.
     * @throws RuntimeException If unable to find or access the parser.
     */
    public Sentence createParser(String nmea) {
        String sid = SentenceId.parseStr(nmea);
        return createParserImpl(sid, String.class, nmea);
    }

    /**
     * Creates a sentence parser for specified sentence type with given talker
     * ID.
     * 
     * @param talker Talker ID to use in parser
     * @param type Type of the parser to create
     * @return Sentence instance
     * @throws IllegalStateException If parser is found, but it does not
     *             implement expected constructor with single String parameter.
     */
    public Sentence createParser(TalkerId talker, String type) {
        if (talker == null) {
            throw new IllegalArgumentException("TalkerId cannot be null");
        }
        return createParserImpl(type, TalkerId.class, talker);
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
     * {@link #createParser(String)} method can be used to obtain parsers for
     * registered type. All sentences supported by the library are registered by
     * default, and this method is provided mainly for registering custom
     * parsers that can be created by extending the {@link SentenceParser}
     * class.
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
            String msg = "Parser must implement two constructors with String and TalkerId parameters";
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
     * @param paramClass Class of the constructor parameter, String.class or
     *            TalkerId.class
     * @param param Object to pass in parser constructor
     * @return Parser instance
     */
    private Sentence createParserImpl(String sid, Class<?> paramClass,
            Object param) {

        if (!hasParser(sid.toString())) {
            String msg = String.format("Parser for type '%s' not found", sid);
            throw new IllegalArgumentException(msg);
        }

        Sentence parser = null;

        try {
            Class<? extends SentenceParser> c = parsers.get(sid);
            Constructor<? extends SentenceParser> co = c
                    .getConstructor(paramClass);
            parser = co.newInstance(param);

        } catch (NoSuchMethodException e) {
            String name = paramClass.getName();
            String msg = "Constructor with %s parameter not found";
            throw new IllegalStateException(String.format(msg, name), e);
        } catch (InstantiationException e) {
            throw new IllegalStateException("Unable to instantiate parser", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Parser is unaccessible", e);
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

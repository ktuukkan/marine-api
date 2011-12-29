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

import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;

/**
 * Factory for creating sentence parsers. Custom parsers may be registered using
 * the {@link #registerParser(String, Class)} method.
 * <p>
 * To create and register a custom parser:
 * <ol>
 * <li>Define the sentence interface by extending the {@link Sentence} interface
 * (e.g. <code>XYZSentence</code>).
 * <li>Implement the interface in a class that extends {@link SentenceParser},
 * (e.g. <code>XYZParser</code>).
 * <li>Use the protected getters and setters in <code>SentenceParser</code> to
 * read and write sentence data.
 * <li>Write a constructor with single String parameter, i.e. the sentence to be
 * parsed by the parser. Pass this parameter to
 * {@link SentenceParser#SentenceParser(String, String)} together with expected
 * sentence id (e.g. <code>"XYZ"</code>).
 * <li>Register your parser class in <code>SentenceFactory</code> using the
 * {@link #registerParser(String, Class)} method.
 * <li>Finally, feed the {@link SentenceFactory#createParser(String)} with
 * <code>XYZ</code> sentence String and you should get an instance of your
 * parser in form of <code>XYZSentence</code> interface.
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

        Sentence parser = null;
        String sid = SentenceId.parseStr(nmea);

        if (!hasParser(sid.toString())) {
            String msg = String.format("Parser for type '%s' not found", sid);
            throw new IllegalArgumentException(msg);
        }

        try {
            Class<? extends SentenceParser> c = parsers.get(sid);
            Constructor<? extends SentenceParser> co = c
                    .getConstructor(String.class);
            parser = co.newInstance(nmea);

        } catch (NoSuchMethodException e) {
            String msg = "Constructor with single String parameter not found";
            throw new IllegalStateException(msg, e);

        } catch (InstantiationException e) {
            throw new RuntimeException(
                    "Parser found, but unable to instantiate", e);

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Parser found, but unaccessible", e);

        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Parser found, but unable to invoke constructor", e);
        }

        return parser;
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
     * @param parser Parser implementation class for <code>type</code>.
     */
    public void registerParser(String type,
            Class<? extends SentenceParser> parser) {

        try {
            parser.getConstructor(String.class);
        } catch (SecurityException e) {
            String msg = "Unable to register parser due security violation";
            throw new IllegalArgumentException(msg, e);
        } catch (NoSuchMethodException e) {
            String msg = "Parser must implement constructor with String param";
            throw new IllegalArgumentException(msg, e);
        }

        parsers.put(type, parser);
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

/* 
 * SentenceReader.java
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
package net.sf.marineapi.nmea.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 * Sentence provider reads NMEA sentences from the specified InputStream and
 * dispatches them to listeners as SentenceEvents.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceReader {

    // hash map key for listeners that listen for any kind of sentences, type
    // specific listeners are registered with sentence type String
    private static final String DISPATCH_ALL = "DISPATCH_ALL";

    // map of sentence listeners
    private ConcurrentMap<String, List<SentenceListener>> listeners = new ConcurrentHashMap<String, List<SentenceListener>>();

    // worker that reads the input stream
    private StreamReader streamReader;

    /**
     * Creates a new instance of SentenceReader.
     * 
     * @param source Stream from which to read NMEA data
     */
    public SentenceReader(InputStream source) {
        streamReader = new StreamReader(new BufferedReader(
                new InputStreamReader(source)));
        new Thread(streamReader).start();
    }

    /**
     * Adds a <code>SentenceListener</code> that wants to receive all sentences
     * read by the reader.
     * 
     * @param listener <code>SentenceListener</code> to be registered.
     * @see net.sf.marineapi.nmea.event.SentenceListener
     */
    public void addSentenceListener(SentenceListener listener) {
        registerListener(DISPATCH_ALL, listener);
    }

    /**
     * Adds a <code>SentenceListener</code> that is interested in receiving only
     * sentences of certain type.
     * 
     * @param sl SentenceListener to add
     * @param type Sentence type for which the listener is registered.
     * @see net.sf.marineapi.nmea.event.SentenceListener
     */
    public void addSentenceListener(SentenceListener sl, SentenceId type) {
        registerListener(type.toString(), sl);
    }

    /**
     * Registers a SentenceListener to hash map with given key.
     * 
     * @param type Sentence type to register for
     * @param sl SentenceListener to register
     */
    private void registerListener(String type, SentenceListener sl) {
        if (listeners.get(type) == null) {
            List<SentenceListener> list = new Vector<SentenceListener>();
            list.add(sl);
            listeners.put(type, list);
        } else {
            listeners.get(type).add(sl);
        }
    }

    /**
     * Remove a listener from reader. When removed, listener will not receive
     * any events from the reader.
     * 
     * @param sl <code>SentenceListener</code> to be removed.
     */
    public void removeSentenceListener(SentenceListener sl) {
        for (List<SentenceListener> list : listeners.values()) {
            if (list.contains(sl)) {
                listeners.remove(sl);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        streamReader.stop();
        super.finalize();
    }

    /**
     * Dispatch data to all listeners.
     * 
     * @param nmea sentence string.
     */
    private void fireSentenceEvent(Sentence sentence) {

        SentenceEvent se = new SentenceEvent(this, sentence);

        List<SentenceListener> list = new ArrayList<SentenceListener>();
        List<SentenceListener> all = listeners.get(DISPATCH_ALL);
        List<SentenceListener> forType = listeners.get(sentence.getSentenceId()
                .toString());

        if (all != null) {
            list.addAll(all);
        }

        if (forType != null) {
            list.addAll(forType);
        }

        for (SentenceListener sl : list) {
            try {
                sl.sentenceRead(se);
            } catch (Exception e) {
                // ignore listener failures
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads the input stream and fires sentence events.
     */
    private class StreamReader implements Runnable {

        private boolean running;
        private BufferedReader input;

        /**
         * Constructor
         * 
         * @param reader BufferedReader for data input
         */
        public StreamReader(BufferedReader reader) {
            input = reader;
            running = true;
        }

        /**
         * Reads the input stream and fires SentenceEvents
         */
        public void run() {

            String data = null;
            SentenceFactory factory = SentenceFactory.getInstance();

            while (running) {
                try {
                    if (input.ready()) {
                        data = input.readLine();
                        if (SentenceValidator.isValid(data)) {
                            Sentence s = factory.createParser(data);
                            fireSentenceEvent(s);
                        }
                    }
                    Thread.sleep(75);
                } catch (Exception e) {
                    // nevermind unsupported or invalid data
                    // e.printStackTrace();
                }
            }
        }

        /**
         * Stops the run loop.
         */
        public void stop() {
            this.running = false;
        }
    }
}

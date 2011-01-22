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
 * @see SentenceListener
 * @see SentenceEvent
 */
public class SentenceReader {

    // Hash map key for listeners that listen any kind of sentences, type
    // specific listeners are registered with sentence type String
    private static final String DISPATCH_ALL = "DISPATCH_ALL";

    // Thread for running the worker
    private Thread thread;
    // worker that reads the input stream
    private StreamReader reader;
    // map of sentence listeners
    private ConcurrentMap<String, List<SentenceListener>> listeners = new ConcurrentHashMap<String, List<SentenceListener>>();

    /**
     * Creates a new instance of SentenceReader.
     * 
     * @param source Stream from which to read NMEA data
     */
    public SentenceReader(InputStream source) {
        reader = new StreamReader(source);
    }

    /**
     * Adds a {@link SentenceListener} that wants to receive all sentences read
     * by the reader.
     * 
     * @param listener {@link SentenceListener} to be registered.
     * @see net.sf.marineapi.nmea.event.SentenceListener
     */
    public void addSentenceListener(SentenceListener listener) {
        registerListener(DISPATCH_ALL, listener);
    }

    /**
     * Adds a {@link SentenceListener} that is interested in receiving only
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
     * Remove a listener from reader. When removed, listener will not receive
     * any events from the reader.
     * 
     * @param sl {@link SentenceListener} to be removed.
     */
    public void removeSentenceListener(SentenceListener sl) {
        for (List<SentenceListener> list : listeners.values()) {
            if (list.contains(sl)) {
                list.remove(sl);
            }
        }
    }

    /**
     * Sets the input stream from which to read NMEA data. If reader is running,
     * it is first stopped and you must call {@link #start()} to resume reading.
     * 
     * @param stream New input stream to set.
     */
    public void setInputStream(InputStream stream) {
        if (reader.isRunning()) {
            stop();
        }
        reader = new StreamReader(stream);
    }

    /**
     * Starts reading the input stream and dispatching events.
     */
    public void start() {
        if (thread != null && thread.isAlive() && reader != null
                && reader.isRunning()) {
            throw new IllegalStateException("Reader is already running");
        }
        thread = new Thread(reader);
        thread.start();
    }

    /**
     * Stops the reader and event dispatching.
     */
    public void stop() {
        if (reader != null && reader.isRunning()) {
            reader.stop();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        reader.stop();
        super.finalize();
    }

    /**
     * Dispatch data to all listeners.
     * 
     * @param sentence sentence string.
     */
    private void fireSentenceEvent(Sentence sentence) {

        String type = sentence.getSentenceId().toString();
        List<SentenceListener> list = new ArrayList<SentenceListener>();

        if (listeners.containsKey(type)) {
            list.addAll(listeners.get(type));
        }
        if (listeners.containsKey(DISPATCH_ALL)) {
            list.addAll(listeners.get(DISPATCH_ALL));
        }

        SentenceEvent se = new SentenceEvent(this, sentence);
        for (SentenceListener sl : list) {
            try {
                sl.sentenceRead(se);
            } catch (Exception e) {
                // ignore listener failures
            }
        }
    }

    /**
     * Registers a SentenceListener to hash map with given key.
     * 
     * @param type Sentence type to register for
     * @param sl SentenceListener to register
     */
    private void registerListener(String type, SentenceListener sl) {
        if (listeners.containsKey(type)) {
            listeners.get(type).add(sl);
        } else {
            List<SentenceListener> list = new Vector<SentenceListener>();
            list.add(sl);
            listeners.put(type, list);
        }
    }

    /**
     * Worker that reads the input stream and fires sentence events.
     */
    private class StreamReader implements Runnable {

        private BufferedReader input;
        private volatile boolean isRunning = false;

        /**
         * Creates a new instance of StreamReader.
         * 
         * @param source InputStream from where to read data.
         */
        public StreamReader(InputStream source) {
            InputStreamReader isr = new InputStreamReader(source);
            input = new BufferedReader(isr);
        }

        /**
         * Tells if the reader is currently running, i.e. actively scanning the
         * input stream for new data.
         * 
         * @return <code>true</code> if running, otherwise <code>false</code>.
         */
        public boolean isRunning() {
            return isRunning;
        }

        /**
         * Reads the input stream and fires SentenceEvents
         */
        public void run() {

            this.isRunning = true;
            SentenceFactory factory = SentenceFactory.getInstance();

            while (isRunning) {
                String data;
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
                }
            }

            isRunning = false;
        }

        /**
         * Stops the run loop.
         */
        public void stop() {
            this.isRunning = false;
        }
    }

}

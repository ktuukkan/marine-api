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
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.parser.Sentence;

/**
 * Sentence provider reads NMEA sentences from the specified InputStream and
 * dispatches them to listeners as SentenceEvents.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceReader {

    // Vector (synchronized) for SentenceListeners
    private final List<SentenceListener> listeners = new Vector<SentenceListener>();
    private Worker worker;

    /**
     * Constructor.
     */
    public SentenceReader(InputStream source) {
        worker = new Worker(new BufferedReader(new InputStreamReader(source)));
        new Thread(worker).start();
    }

    /**
     * Adds a SentenceListener to this reader. If the listener is already
     * registered in this reader, it won't be added twice.
     * 
     * @param sl <code>SentenceListener</code> to be registered.
     * @see net.sf.marineapi.nmea.event.SentenceListener
     */
    public void addSentenceListener(SentenceListener sl) {
        if (!listeners.contains(sl)) {
            listeners.add(sl);
        }
    }

    /**
     * Returns an unmodifiable list of <code>SentenceListeners</code> assigned
     * to this reader.
     * 
     * @return List of SentenceListeners.
     */
    public List<SentenceListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    /**
     * Remove a listener from reader.
     * 
     * @param sl <code>SentenceListener</code> to be removed.
     */
    public void removeSentenceListener(SentenceListener sl) {
        if (listeners.contains(sl)) {
            listeners.remove(sl);
        }
    }

    /**
     * Dispatch data to all listeners.
     * 
     * @param nmea sentence string.
     */
    private void fireSentenceRead(Sentence s) {
        SentenceEvent se = new SentenceEvent(this, s);
        for (SentenceListener sl : getListeners()) {
            try {
                sl.sentenceRead(se);
            } catch (Exception e) {
                // nevermind listener failures
            }
        }
    }

    /**
     * Reads the input stream and fires sentence events.
     */
    private class Worker implements Runnable {

        private boolean running;
        private BufferedReader in;

        /**
         * Constructor
         */
        public Worker(BufferedReader buf) {
            in = buf;
            running = true;
        }

        /**
         * Reads the input stream and fires SentenceEvents
         */
        public void run() {
            String data = null;
            while (running) {
                try {
                    if (in.ready()) {
                        data = in.readLine();
                        if (Sentence.isValid(data)) {
                            fireSentenceRead(new Sentence(data));
                        }
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Stop the run loop.
         */
        public void stop() {
            this.running = false;
        }
    }
}

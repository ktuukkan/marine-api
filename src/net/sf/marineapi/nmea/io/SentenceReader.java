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
    private DataReader reader;
    // map of sentence listeners
    private ConcurrentMap<String, List<SentenceListener>> listeners = new ConcurrentHashMap<String, List<SentenceListener>>();
    // time of latest sentence event
    private volatile long lastFired = -1;
    // timeout for "reading paused" in ms
    private volatile int pauseTimeout = 5000;

    /**
     * Creates a new instance of SentenceReader.
     * 
     * @param source Stream from which to read NMEA data
     */
    public SentenceReader(InputStream source) {
        reader = new DataReader(source);
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
    public void addSentenceListener(SentenceListener sl, String type) {
        registerListener(type, sl);
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
        reader = new DataReader(stream);
    }
    
    /**
     * Set timeout time for reading paused events. Default is 5000 ms.
     * 
     * @param millis Timeout in milliseconds.
     */
    public void setPauseTimeout(int millis) {
    	this.pauseTimeout = millis;
    }
    
    /**
     * Returns the current reading paused timeout.
     * 
     * @return Timeout limit in milliseconds.
     * @see #setPauseTimeout(int)
     */
    public int getPauseTimeout() {
    	return this.pauseTimeout;
    }

    /**
     * Starts reading the input stream and dispatching events.
     */
    public void start() {
        if (thread != null && thread.isAlive() && reader != null
                && reader.isRunning()) {
            throw new IllegalStateException("Reader is already running");
        }
        lastFired = -1;
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
     * Notifies all listeners that data reading is about to start.
     */
    private void fireReadingStarted() {
        for (String key : listeners.keySet()) {
            for (SentenceListener listener : listeners.get(key)) {
                try {
                    listener.readingStarted();
                } catch (Exception e) {
                    // nevermind
                }
            }
        }
    }
    
    /**
     * Notifies all listeners that data reading has stopped.
     */
    private void fireReadingPaused() {
    	for (String key : listeners.keySet()) {
            for (SentenceListener listener : listeners.get(key)) {
                try {
                    listener.readingPaused();
                } catch (Exception e) {
                    // nevermind
                }
            }
        }
    }

    /**
     * Notifies all listeners that data reading has stopped.
     */
    private void fireReadingStopped() {
        for (String key : listeners.keySet()) {
            for (SentenceListener listener : listeners.get(key)) {
                try {
                    listener.readingStopped();
                } catch (Exception e) {
                    // nevermind
                }
            }
        }
    }

    /**
     * Dispatch data to all listeners.
     * 
     * @param sentence sentence string.
     */
    private void fireSentenceEvent(Sentence sentence) {

        if (lastFired < 0) {
            fireReadingStarted();
        }
    	
        String type = sentence.getSentenceId();
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
        lastFired = System.currentTimeMillis();
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
    private class DataReader implements Runnable {

        private PauseMonitor monitor;
        private Thread monitorThread;
        private BufferedReader input;
        private volatile boolean isRunning = true;

        /**
         * Creates a new instance of StreamReader.
         * 
         * @param source InputStream from where to read data.
         */
        public DataReader(InputStream source) {
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

            monitor = new PauseMonitor(DataReader.this);
            monitorThread = new Thread(monitor);
            monitorThread.start();
            
            SentenceFactory factory = SentenceFactory.getInstance();

            while (isRunning) {
                try {
                    if (!input.ready()) {
                    	continue;
                    }
                    
                    String data = input.readLine();
                    if (SentenceValidator.isValid(data)) {
                        Sentence s = factory.createParser(data);
                        fireSentenceEvent(s);
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    // nevermind, keep trying..
                }
            }
            fireReadingStopped();
        }

        /**
         * Stops the run loop.
         */
        public void stop() {
            isRunning = false;
            monitorThread.interrupt();
        }
    }

    /**
     * Watch dog for sending start/paused notifications.
     */
    private class PauseMonitor implements Runnable {
    	
    	private DataReader parent;
    	
    	public PauseMonitor(DataReader parent) {
    		this.parent = parent;
    	}
		
        public void run() {
            while (parent.isRunning()) {
                try {
                    int min = pauseTimeout;
                    int max = pauseTimeout + 1000;
                    long elapsed = System.currentTimeMillis() - lastFired;
                    
                    if (elapsed > min && elapsed < max) {
                        lastFired = -1;
                        fireReadingPaused();
                    }
                    
                	int sleep = (int) Math.round(pauseTimeout / 4);
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    // nevermind
                }
            }
        }
    }
}

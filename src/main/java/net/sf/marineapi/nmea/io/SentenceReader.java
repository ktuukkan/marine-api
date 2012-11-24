/* 
 * SentenceReader.java
 * Copyright (C) 2010-2012 Kimmo Tuukkanen
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

import java.io.InputStream;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;

/**
 * Sentence reader detects supported NMEA 0183 sentences from the specified
 * <code>InputStream</code> and dispatches them to registered listeners as
 * sentence events. Each event contains a parser for the read sentence.
 * <p>
 * Parsers dispatched by reader are created using {@link SentenceFactory} class,
 * where you can also register your own custom parsers.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see SentenceListener
 * @see SentenceEvent
 * @see SentenceFactory
 */
public class SentenceReader {

	// Map key for listeners that listen any kind of sentences, type
	// specific listeners are registered with sentence type String
	private static final String DISPATCH_ALL = "DISPATCH_ALL";

	// Thread for running the worker
	private Thread thread;
	// worker that reads the input stream
	private DataReader reader;
	// map of sentence listeners
	private ConcurrentMap<String, List<SentenceListener>> listeners = new ConcurrentHashMap<String, List<SentenceListener>>();
	// timeout for "reading paused" in ms
	private volatile int pauseTimeout = 5000;

	/**
	 * Creates a new instance of SentenceReader.
	 * 
	 * @param source Stream from which to read NMEA data
	 */
	public SentenceReader(InputStream source) {
		reader = new DefaultDataReader(source, this);
	}

	/**
	 * Creates a SentenceReader for UDP/DatagramSocket.
	 * 
	 * @param source Socket from which to read NMEA data
	 */
	public SentenceReader(DatagramSocket source) {
		reader = new UDPDataReader(source, this);
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
	 * Returns the current reading paused timeout.
	 * 
	 * @return Timeout limit in milliseconds.
	 * @see #setPauseTimeout(int)
	 */
	public int getPauseTimeout() {
		return this.pauseTimeout;
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
		reader = new DefaultDataReader(stream, this);
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
	 * Starts reading the input stream and dispatching events.
	 * 
	 * @throws IllegalStateException If reader is already running.
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

	/**
	 * Notifies all listeners that reader has paused due to timeout.
	 */
	void fireReadingPaused() {
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
	 * Notifies all listeners that NMEA data has been detected in the stream and
	 * events will be dispatched until stopped or timeout occurs.
	 */
	void fireReadingStarted() {
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
	void fireReadingStopped() {
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
	void fireSentenceEvent(Sentence sentence) {

		String type = sentence.getSentenceId();
		Set<SentenceListener> list = new HashSet<SentenceListener>();

		if (listeners.containsKey(type)) {
			list.addAll(listeners.get(type));
		}
		if (listeners.containsKey(DISPATCH_ALL)) {
			list.addAll(listeners.get(DISPATCH_ALL));
		}

		for (SentenceListener sl : list) {
			try {
				SentenceEvent se = new SentenceEvent(this, sentence);
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

}

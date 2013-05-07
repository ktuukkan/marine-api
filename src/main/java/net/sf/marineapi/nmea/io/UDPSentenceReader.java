/* 
 * UDPSentenceReader.java
 * Copyright (C) 2010-2012 Kimmo Tuukkanen, Ludovic Drouineau
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
package net.sf.marineapi.nmea.io;

import java.net.DatagramPacket;
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
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 * UPDSentence reader detects supported NMEA 0183 sentences from the specified
 * <code>DatagramSocket</code> and dispatches them to registered listeners as
 * sentence events. Each event contains a parser for the read sentence.
 * <p>
 * Parsers dispatched by reader are created using {@link SentenceFactory} class,
 * where you can also register your own custom parsers.
 * 
 * @author Kimmo Tuukkanen, Ludovic Drouineau
 * @version $Revision$
 * @see SentenceListener
 * @see SentenceEvent
 * @see SentenceFactory
 */
@Deprecated
public class UDPSentenceReader {

	// Map key for listeners that listen any kind of sentences, type
	// specific listeners are registered with sentence type String
	private static final String DISPATCH_ALL = "DISPATCH_ALL";

	// Thread for running the worker
	private Thread thread;
	// worker that reads the input stream
	private UDPDataReader reader;
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
	public UDPSentenceReader(DatagramSocket source) {
		reader = new UDPDataReader(source);
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

	/**
	 * Notifies all listeners that reader has paused due to timeout.
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
	 * Notifies all listeners that NMEA data has been detected in the stream and
	 * events will be dispatched until stopped or timeout occurs.
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
	private class UDPDataReader implements Runnable {

		private PauseMonitor monitor;
		private Thread monitorThread;
		DatagramSocket input;
		private volatile boolean isRunning = true;
		byte[] rcvData = new byte[1024];

		/**
		 * Creates a new instance of StreamReader.
		 * 
		 * @param source InputStream from where to read data.
		 */
		public UDPDataReader(DatagramSocket source) {
			input = source;
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

			monitor = new PauseMonitor(UDPDataReader.this);
			monitorThread = new Thread(monitor);
			monitorThread.start();

			SentenceFactory factory = SentenceFactory.getInstance();

			while (isRunning) {
				try {

					DatagramPacket dataPakt = new DatagramPacket(rcvData,
							rcvData.length);
					input.receive(dataPakt);
					String data = new String(dataPakt.getData(), 0, dataPakt
							.getLength());
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

		private UDPDataReader parent;

		public PauseMonitor(UDPDataReader parent) {
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

					int sleep = Math.round(pauseTimeout / 4);
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// nevermind
				}
			}
		}
	}
}

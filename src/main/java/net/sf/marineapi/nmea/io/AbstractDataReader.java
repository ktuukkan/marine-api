/*
 * AbstractDataReader.java
 * Copyright (C) 2014-2018 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.parser.UnsupportedSentenceException;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

import java.io.InputStream;
import java.net.DatagramSocket;
import java.util.logging.Logger;

/**
 * Abstract base class for data readers, with common methods and run loop
 * for firing events to listeners managed by the parent {@link SentenceReader}.
 * <p>
 * Extend this class to implement custom readers, for example when NMEA data
 * is delivered embedded in a proprietary format. Otherwise, it is recommended
 * to use <code>SentenceReader</code> directly with <code>InputStream</code>
 * or <code>DatagramSocket</code>.
 * </p>
 * 
 * @author Kimmo Tuukkanen
 * @see SentenceReader#SentenceReader(InputStream)
 * @see SentenceReader#SentenceReader(DatagramSocket)
 * @see SentenceReader#SentenceReader(AbstractDataReader)
 */
public abstract class AbstractDataReader implements Runnable {

	// Sleep time between failed read attempts to prevent busy-looping
	private static final int SLEEP_TIME = 100;
	private static final Logger LOGGER = Logger.getLogger(AbstractDataReader.class.getName());

	private SentenceReader parent;
	private volatile boolean isRunning = true;

	/**
	 * Default constructor.
	 */
	protected AbstractDataReader() {
	}

	/**
	 * Creates a new instance with parent, mainly for internal use.
	 *
	 * @param parent {@link SentenceReader} that owns this reader
	 */
	AbstractDataReader(SentenceReader parent) {
		setParent(parent);
	}

	/**
	 * Returns the parent <code>SentenceReader</code>.
	 */
	SentenceReader getParent() {
		return this.parent;
	}

	/**
	 * Sets the parent <code>SentenceReader</code>.
	 *
	 * @param reader <code>SentenceReader</code> to set.
	 * @throws IllegalArgumentException If given <code>reader</code> is <code>null</code>.
	 */
	void setParent(SentenceReader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("Parent SentenceReader cannot be set null");
		}
		this.parent = reader;
	}

	/**
	 * Tells if the reader is running and actively scanning the data source for
	 * new data.
	 *
	 * @return <code>true</code> if running, otherwise <code>false</code>.
	 */
	boolean isRunning() {
		return isRunning;
	}

	/**
	 * Read one NMEA-0183 sentence and return it.
	 *
	 * @return Sentence String or <code>null</code> if nothing was read.
	 */
	public abstract String read() throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		ActivityMonitor monitor = new ActivityMonitor(parent);
		SentenceFactory factory = SentenceFactory.getInstance();

		while (isRunning) {
			try {
				String data = read();
				if (data == null) {
					Thread.sleep(SLEEP_TIME);
				} else if (SentenceValidator.isValid(data)) {
					monitor.refresh();
					Sentence s = factory.createParser(data);
					parent.fireSentenceEvent(s);
				} else if (!SentenceValidator.isSentence(data)) {
					parent.fireDataEvent(data);
				}
			} catch (UnsupportedSentenceException use) {
				LOGGER.warning(use.getMessage());
			} catch (Exception e) {
				parent.handleException("Data read failed", e);
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException interruptException) {}
			} finally {
				monitor.tick();
			}
		}
		monitor.reset();
		parent.fireReadingStopped();
	}

	/**
	 * Stops the reader permanently.
	 */
	public void stop() {
		isRunning = false;
	}
}

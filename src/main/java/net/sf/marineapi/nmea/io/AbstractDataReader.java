/*
 * AbstractDataReader.java
 * Copyright (C) 2014 Kimmo Tuukkanen
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
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 * Base class for data readers; common methods and run-loop.
 * 
 * @author Kimmo Tuukkanen
 */
abstract class AbstractDataReader implements DataReader {

	// Sleep time between failed read attempts to prevent busy-looping
	private static final int SLEEP_TIME = 100;

	private final SentenceReader parent;
	private volatile boolean isRunning = true;

	/**
	 * Creates a new instance.
	 * 
	 * @param parent {@link SentenceReader} that owns this reader
	 */
	public AbstractDataReader(SentenceReader parent) {
		this.parent = parent;
	}

	/**
	 * Returns the parent SentenceReader.
	 */
	protected SentenceReader getParent() {
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.io.DataReader#isRunning()
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Read one line from the data source.
	 * 
	 * @return String or <code>null</code> if nothing was read.
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
    				        if (isRunning()) {
    				                Thread.sleep(SLEEP_TIME);
    				        }
				} else if (SentenceValidator.isValid(data)) {
					monitor.refresh();
					Sentence s = factory.createParser(data);
					parent.fireSentenceEvent(s);
				} else if (!SentenceValidator.isSentence(data)) {
					parent.fireDataEvent(data);
				}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.io.DataReader#stop()
	 */
	public void stop() {
		isRunning = false;
	}
}

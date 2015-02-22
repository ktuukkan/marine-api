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

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VDMSentence;

/**
 * Base class for data readers; common methods and run-loop.
 * 
 * @author Kimmo Tuukkanen
 */
abstract class AbstractDataReader implements DataReader {

	private static final Logger LOG =
		Logger.getLogger(AbstractDataReader.class.getName());

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.io.DataReader#isRunning()
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.io.DataReader#stop()
	 */
	public void stop() {
		isRunning = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		ActivityMonitor monitor = new ActivityMonitor(parent);
		SentenceFactory factory = SentenceFactory.getInstance();
		VDMSentence previous = null;

		while (isRunning) {
			try {
				String data = read();
				if (SentenceValidator.isValid(data)) {
					monitor.refresh();
					Sentence s = factory.createParser(data);
					
					/****** TODO move outside reader
					// AIS sentences are coming in fragments (multiple lines)
					// which should be concatenated.
					if (s.getTalkerId() == TalkerId.AI) {
						VDMSentence vdm = (VDMSentence)s;
						if (previous == null) {
							// Check if message is a new one or incorrectly a continuation
							// of a previous one (because of channel multiplexing)
							if (vdm.getFragmentNumber() == 1)
								previous = vdm;
						}
						else {
							if (previous.isPartOfMessage(vdm)) {
								// Continuation line (part of the previous fragment)
								previous.add(vdm);
							}
							else {
								// Out of sequence message (possibly a new start
								// of message caused by channel multiplexing),
								// so we throw the previous away and start a new one
								previous = vdm;
							}
						}
						if (previous != null && previous.isLastFragment()) {
							// This is the last fragment, now we can interpret the message
							try {
								parent.fireSentenceEvent(previous);
							}
							catch (Exception e) {
								LOG.log(Level.WARNING, "AIS decode failed", e);
							}
							previous = null;
						}
					}
					else
					*********/
						parent.fireSentenceEvent(s);
				} else if (!SentenceValidator.isSentence(data)) {
					parent.fireDataEvent(data);
				}
				monitor.tick();
				Thread.sleep(50);
			} catch (Exception e) {
				LOG.log(Level.WARNING, "Data read failed", e);
			}
		}
		monitor.reset();
		parent.fireReadingStopped();
	}

	/**
	 * Read one line from the data source.
	 * 
	 * @return String or <code>null</code> if nothing was read.
	 */
	public abstract String read();

	/**
	 * Returns the parent SentenceReader.
	 */
	protected SentenceReader getParent() {
		return this.parent;
	}
}

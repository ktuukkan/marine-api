/*
 * DefaultDataReader.java
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 * The default data reader implementation using InputStream as data source.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class DefaultDataReader implements DataReader {

	private SentenceReader parent;
	private ActivityMonitor monitor;
	private BufferedReader input;
	private volatile boolean isRunning = true;

	/**
	 * Creates a new instance of DefaultDataReader.
	 * 
	 * @param source InputStream to be used as data source.
	 * @param parent SentenceReader dispatching events for this reader.
	 */
	public DefaultDataReader(InputStream source, SentenceReader parent) {
		InputStreamReader isr = new InputStreamReader(source);
		this.input = new BufferedReader(isr);
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.io.DataReader#isRunning()
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.io.DataReader#stop()
	 */
	public void stop() {
		isRunning = false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		monitor = new ActivityMonitor(parent);
		SentenceFactory factory = SentenceFactory.getInstance();

		while (isRunning) {
			try {
				if (input.ready()) {
					String data = input.readLine();
					if (SentenceValidator.isValid(data)) {
						monitor.refresh();
						Sentence s = factory.createParser(data);
						parent.fireSentenceEvent(s);
					}
				}
				monitor.tick();
				Thread.sleep(50);
			} catch (Exception e) {
				// nevermind, keep trying..
			}
		}
		monitor.reset();
		parent.fireReadingStopped();
	}
}

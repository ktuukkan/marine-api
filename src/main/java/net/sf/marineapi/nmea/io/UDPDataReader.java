/*
 * UDPDataReader.java
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

import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 * DataReader implementation using DatagramSocket as data source.
 * 
 * @author Kimmo Tuukkanen, Ludovic Drouineau
 */
class UDPDataReader implements DataReader {

	private SentenceReader parent;
	private DatagramSocket socket;
	private volatile boolean isRunning = true;
	byte[] buffer = new byte[1024];

	/**
	 * Creates a new instance of StreamReader.
	 * 
	 * @param socket DatagramSocket to be used as data source.
	 * @param parent SentenceReader dispatching events for this reader.
	 */
	public UDPDataReader(DatagramSocket socket, SentenceReader parent) {
		this.socket = socket;
		this.parent = parent;
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

		ActivityMonitor monitor = new ActivityMonitor(parent);
		SentenceFactory factory = SentenceFactory.getInstance();

		while (isRunning) {
			try {
				DatagramPacket pkg = new DatagramPacket(buffer, buffer.length);
				socket.receive(pkg);
				String data = new String(pkg.getData(), 0, pkg.getLength());
				if (SentenceValidator.isValid(data)) {
					monitor.refresh();
					Sentence s = factory.createParser(data);
					parent.fireSentenceEvent(s);
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

	/**
	 * Stops the run loop.
	 */
	public void stop() {
		isRunning = false;
	}

}

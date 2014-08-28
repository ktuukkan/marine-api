/* 
 * SerialPortExample.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
package net.sf.marineapi.example;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

/**
 * Serial port example using GNU/RXTX libraries (see readme.txt). Scans through
 * all COM ports and seeks for NMEA 0183 data with default settings (4800
 * baud, 8 data bits, 1 stop bit and no parity). If NMEA data is found, starts
 * printing out all sentences the device is broadcasting.
 * 
 * Notice that on Linux you may need to set read/write privileges on correct
 * port (e.g. <code>sudo chmod 666 /dev/ttyUSB0<code>) or add your user in
 * dialout group before running this example.
 *  
 * @author Kimmo Tuukkanen
 */
public class SerialPortExample implements SentenceListener {

	/**
	 * Constructor
	 */
	public SerialPortExample() {
		init();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
	 */
	public void readingPaused() {
		System.out.println("-- Paused --");
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
	 */
	public void readingStarted() {
		System.out.println("-- Started --");
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
	 */
	public void readingStopped() {
		System.out.println("-- Stopped --");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi
	 * .nmea.event.SentenceEvent)
	 */
	public void sentenceRead(SentenceEvent event) {
		// here we receive each sentence read from the port
		System.out.println(event.getSentence());
	}

	/**
	 * Scan serial ports for NMEA data.
	 * 
	 * @return SerialPort from which NMEA data was found, or null if data was
	 *         not found in any of the ports.
	 */
	private SerialPort getSerialPort() {
		try {
			Enumeration<?> e = CommPortIdentifier.getPortIdentifiers();

			while (e.hasMoreElements()) {
				CommPortIdentifier id = (CommPortIdentifier) e.nextElement();

				if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {

					SerialPort sp = (SerialPort) id.open("SerialExample", 30);

					sp.setSerialPortParams(4800, SerialPort.DATABITS_8,
							SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

					InputStream is = sp.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader buf = new BufferedReader(isr);

					System.out.println("Scanning port " + sp.getName());

					// try each port few times before giving up
					for (int i = 0; i < 5; i++) {
						try {
							String data = buf.readLine();
							if (SentenceValidator.isValid(data)) {
								System.out.println("NMEA data found!");
								return sp;
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					is.close();
					isr.close();
					buf.close();
				}
			}
			System.out.println("NMEA data was not found..");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Init serial port and reader.
	 */
	private void init() {
		try {
			SerialPort sp = getSerialPort();

			if (sp != null) {
				InputStream is = sp.getInputStream();
				SentenceReader sr = new SentenceReader(is);
				sr.addSentenceListener(this);
				sr.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Startup method, no arguments required.
	 * 
	 * @param args None
	 */
	public static void main(String[] args) {
		new SerialPortExample();
	}
}

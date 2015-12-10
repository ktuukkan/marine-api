/*
 * AISListenerExample.java
 * Copyright (C) 2015 Jozéph Lázár
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.marineapi.ais.message.AISMessage01;
import net.sf.marineapi.ais.event.AbstractAISMessageListener;
import net.sf.marineapi.nmea.io.SentenceReader;

/**
 * Simple example application that takes a filename as command-line argument and
 * prints position from VDM sentences.
 * 
 * @author Jozéph Lázár
 */
public class AISListenerExample extends AbstractAISMessageListener<AISMessage01> {

	private SentenceReader reader;

	/**
	 * Creates a new instance of AISExample
	 * 
	 * @param file File containing NMEA data
	 */
	public AISListenerExample(File file) throws IOException {

		// create sentence reader and provide input stream
		InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);

		// listen for for all AIS VDM messages
		reader.addSentenceListener(this);
		reader.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.ais.event.AbstractAISMessageListener#onMessage(net
	 * .sf.marineapi.ais.sentence.AISMessage)
	 */
	@Override
	public void onMessage(AISMessage01 msg) {
		System.out.println(msg.getMMSI() + ": " + msg.getLatitudeInDegrees());
		System.out.println("onMessage: " + msg.toString());
	}

	/**
	 * Main method takes one command-line argument, the name of the file to
	 * read.
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Example usage:\njava AISExample ais.log");
			System.exit(1);
		}

		try {
			new AISListenerExample(new File(args[0]));
			System.out.println("Running, press CTRL-C to stop..");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}

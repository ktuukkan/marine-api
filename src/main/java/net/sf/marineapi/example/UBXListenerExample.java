/*
 * Copyright (C) 2020 Gunnar Hillert
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

import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.ublox.event.AbstractUBXMessageListener;
import net.sf.marineapi.ublox.message.UBXMessage00;
import net.sf.marineapi.ublox.message.UBXMessage03;

/**
 * Simple example application that takes a filename as command-line argument and
 * prints the position as well as satellite data from UBX (Also called PUBX) sentences.
 *
 * @author Gunnar Hillert
 */
public class UBXListenerExample {

	private SentenceReader reader;

	/**
	 * Creates a new instance of the UBX Listener Example
	 *
	 * @param file File containing NMEA data
	 */
	public UBXListenerExample(File file) throws IOException {

		// create sentence reader and provide input stream
		final InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);

		// listen for for all supported UBX messages
		reader.addSentenceListener(new UBXMessage00Listener());
		reader.addSentenceListener(new UBXMessage03Listener());
		reader.start();
	}

	/**
	 * Main method takes one command-line argument, the name of the file to
	 * read.
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Exactly 1 argument is required. Example usage:\njava UBXListenerExample pubx.log");
			System.exit(1);
		}

		try {
			new UBXListenerExample(new File(args[0]));
			System.out.println("Running, press CTRL-C to stop..");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	class UBXMessage00Listener extends AbstractUBXMessageListener<UBXMessage00> {
		@Override
		public void onMessage(UBXMessage00 msg) {
			final Position position = msg.getPosition();
			System.out.println(position.getLongitude() + " : " + position.getLatitude());
			System.out.println("onMessage: " + msg.toString());
		}
	}

	class UBXMessage03Listener extends AbstractUBXMessageListener<UBXMessage03> {
		@Override
		public void onMessage(UBXMessage03 msg) {
			final int numberOfTrackedSatellites = msg.getNumberOfTrackedSatellites();
			System.out.println(String.format("Tracking %s satellites.", numberOfTrackedSatellites));
			System.out.println("onMessage: " + msg.toString());
		}
	}

}

/*
 * AISExample.java
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

import net.sf.marineapi.ais.parser.AISMessage;
import net.sf.marineapi.ais.parser.AISMessage01Parser;
import net.sf.marineapi.ais.parser.AISMessage02Parser;
import net.sf.marineapi.ais.parser.AISMessage03Parser;
import net.sf.marineapi.ais.parser.AISMessage04Parser;
import net.sf.marineapi.ais.parser.AISMessage05Parser;
import net.sf.marineapi.ais.parser.AISMessage18Parser;
import net.sf.marineapi.ais.parser.AISMessage19Parser;
import net.sf.marineapi.ais.util.MMSI;
import net.sf.marineapi.ais.util.Violation;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.VDMSentence;

/**
 * Simple example application that takes a filename as command-line argument
 * and prints position from VDM sentences.
 *
 * @author Jozéph Lázár
 */
public class AISExample implements SentenceListener<VDMSentence> {

	private SentenceReader reader;

	/**
	 * Creates a new instance of AISExample
	 *
	 * @param file File containing NMEA data
	 */
	public AISExample(File file) throws IOException {

		// create sentence reader and provide input stream
		InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);

		// listen for for all AIS VDM messages
		reader.addSentenceListener(this, SentenceId.VDM);
		reader.start();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(T)
	 */
	public void sentenceRead(VDMSentence s) {

		try {
			AISMessage mes = null;
			switch (s.getMessageType()) {
			case 1:
				mes = new AISMessage01Parser(s.getMessageBody());
				break;
			case 2:
				mes = new AISMessage02Parser(s.getMessageBody());
				break;
			case 3:
				mes = new AISMessage03Parser(s.getMessageBody());
				break;
			case 4:
				mes = new AISMessage04Parser(s.getMessageBody());
				break;
			case 5:
				mes = new AISMessage05Parser(s.getMessageBody());
				break;
			case 18:
				mes = new AISMessage18Parser(s.getMessageBody());
				break;
			case 19:
				mes = new AISMessage19Parser(s.getMessageBody());
				break;
			}
			if (mes != null) {
				System.out.println("AIS" + s.getMessageType() +
						" MMSI: " + s.getMMSI() + " " + MMSI.toString(s.getMMSI()));
				System.out.println("Violations: " + mes.getNrOfViolations());
				if (mes.getNrOfViolations() > 0) {
					for (Violation v : mes.getViolations())
						System.out.println("\t" + v.toString());
				}
				System.out.println(mes.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			new AISExample(new File(args[0]));
			System.out.println("Running, press CTRL-C to stop..");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}

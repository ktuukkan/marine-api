/* 
 * FileExample.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;

/**
 * Simple example application that takes a filename as command-line argument and
 * prints Position from received GGA sentences.
 * 
 * @author Kimmo Tuukkanen
 */
public class FileExample implements SentenceListener {

	private SentenceReader reader;

	/**
	 * Creates a new instance of FileExample
	 * 
	 * @param file File containing NMEA data
	 */
	public FileExample(File file) throws IOException {

		// create sentence reader and provide input stream
		InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);

		// register self as a listener for GGA sentences
		reader.addSentenceListener(this, SentenceId.GGA);
		reader.start();
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

		// Safe to cast as we are registered only for GGA updates. Could
		// also cast to PositionSentence if interested only in position data.
		// When receiving all sentences without filtering, you should check the
		// sentence type before casting (e.g. with Sentence.getSentenceId()).
		GGASentence s = (GGASentence) event.getSentence();

		// Do something with sentence data..
		System.out.println(s.getPosition());
	}

	/**
	 * Main method takes one command-line argument, the name of the file to
	 * read.
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Example usage:\njava FileExample nmea.log");
			System.exit(1);
		}

		try {
			new FileExample(new File(args[0]));
			System.out.println("Running, press CTRL-C to stop..");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}

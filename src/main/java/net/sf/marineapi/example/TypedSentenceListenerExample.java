/* 
 * TypedSentenceListenerExample.java
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
package net.sf.marineapi.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.RMCSentence;

/**
 * Example application demonstrating the usage of AbstractSentenceListener.
 *  
 * @author Kimmo Tuukkanen
 */
public class TypedSentenceListenerExample
	extends AbstractSentenceListener<RMCSentence> {

	private SentenceReader reader;

	/**
	 * Creates a new instance.
	 * 
	 * @param file File containing NMEA data
	 */
	public TypedSentenceListenerExample(File file) throws IOException {
		// create reader and provide input stream
		InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);
		// register self as a generic listener
		reader.addSentenceListener(this);
		reader.start();
	}

	@Override
	public void sentenceRead(RMCSentence sentence) {
		
		// AbstractSentenceListener requires you to implement this method.
		// Only RMC sentences are broadcasted here as abstract listener is
		// filtering all the others. Thus, no need for checking sentence type
		// and casting. You can also override sentenceRead(SentenceEvent e),
		// but you really shouldn't.
		
		System.out.println(sentence.getPosition());
		
	}

	/**
	 * Main method that takes single file name as argument.
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage:\njava TypedSentenceListenerExample <file>");
			System.exit(1);
		}
		
		try {
			new TypedSentenceListenerExample(new File(args[0]));
			System.out.println("Running, press CTRL-C to stop..");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}

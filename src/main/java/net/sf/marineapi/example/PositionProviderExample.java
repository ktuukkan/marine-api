/* 
 * PositionProviderExample.java
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.marineapi.nmea.io.ExceptionListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.provider.PositionProvider;
import net.sf.marineapi.provider.event.PositionEvent;
import net.sf.marineapi.provider.event.PositionListener;

/**
 * Demonstrates the usage of PositionProvider.
 * 
 * @author Kimmo Tuukkanen
 * @see PositionProvider
 */
public class PositionProviderExample implements PositionListener, ExceptionListener {

	PositionProvider provider;

	public PositionProviderExample(File f) throws FileNotFoundException {
		InputStream stream = new FileInputStream(f);
		SentenceReader reader = new SentenceReader(stream);
		reader.setExceptionListener(this);
		provider = new PositionProvider(reader);
		provider.addListener(this);
		reader.start();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.PositionListener#providerUpdate(net.sf.marineapi
	 * .provider.event.PositionEvent)
	 */
	public void providerUpdate(PositionEvent evt) {
		// do something with the data..
		System.out.println("TPV: " + evt.toString());
	}

	public void onException(Exception e) {
		// supress warnings..
	}

	/**
	 * Startup method.
	 * 
	 * @param args NMEA log file
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Usage:\njava PositionProviderExample nmea.log");
			System.exit(1);
		}

		try {
			new PositionProviderExample(new File(args[0]));
			System.out.println("Running, press CTRL-C to stop..");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}

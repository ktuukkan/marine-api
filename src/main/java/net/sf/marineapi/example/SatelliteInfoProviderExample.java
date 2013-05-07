/* 
 * SatelliteInfoProviderExample.java
 * Copyright (C) 2013 Kimmo Tuukkanen
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
import net.sf.marineapi.nmea.util.SatelliteInfo;
import net.sf.marineapi.provider.SatelliteInfoProvider;
import net.sf.marineapi.provider.event.SatelliteInfoEvent;
import net.sf.marineapi.provider.event.SatelliteInfoListener;

/**
 * @author Kimmo Tuukkanen
 */
public class SatelliteInfoProviderExample implements SatelliteInfoListener {

	SentenceReader reader;
	SatelliteInfoProvider provider;

	public SatelliteInfoProviderExample(File file) throws IOException {

		// create sentence reader and provide input stream
		InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);

		// create provider and register listener
		provider = new SatelliteInfoProvider(reader);
		provider.addListener(this);

		reader.start();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.SatelliteInfoListener#providerUpdate(net.sf.marineapi
	 * .provider.event.SatelliteInfoEvent)
	 */
	public void providerUpdate(SatelliteInfoEvent event) {
		System.out.println("-- GSV report --");
		for (SatelliteInfo si : event.getSatelliteInfo()) {
			String ptrn = "%s: %d, %d";
			String msg = String.format(ptrn, si.getId(), si.getAzimuth(), si
					.getElevation());
			System.out.println(msg);
		}
		System.out.println("-----");
	}

	/**
	 * Main method takes one command-line argument, the name of the file to
	 * read.
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			String msg = "Example usage:\njava SatelliteInfoProviderExample nmea.log";
			System.out.println(msg);
			System.exit(0);
		}

		try {
			new SatelliteInfoProviderExample(new File(args[0]));
			System.out.println("Running, press CTRL-C to stop..");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}

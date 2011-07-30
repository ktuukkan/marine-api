/* 
 * TPVProviderExample.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
package net.sf.marineapi.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.provider.TPVProvider;
import net.sf.marineapi.provider.event.TPVEvent;
import net.sf.marineapi.provider.event.TPVListener;

/**
 * Demonstrates the usage of TPVProvider.
 * 
 * @author Kimmo Tuukkanen
 * @see TPVProvider
 */
public class TPVProviderExample implements TPVListener {

    TPVProvider provider;

    public TPVProviderExample(File f) throws FileNotFoundException {
        InputStream is = new FileInputStream(f);
        SentenceReader sr = new SentenceReader(is);
        provider = new TPVProvider(sr);
        provider.addListener(this);
        sr.start();
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.provider.event.TPVListener#gpsUpdated(net.sf.marineapi
     * .provider.event.TPVUpdateEvent)
     */
    public void tpvUpdate(TPVEvent evt) {
        // do something with the data..
        System.out.println("TPV: " + evt.toString());
    }

    /**
     * Startup method, no arguments required.
     * 
     * @param args None
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage:\njava TPVProviderExample nmea.log");
            System.exit(1);
        }

        try {
            new TPVProviderExample(new File(args[0]));
            System.out.println("Running, press CTRL-C to stop..");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

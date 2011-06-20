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

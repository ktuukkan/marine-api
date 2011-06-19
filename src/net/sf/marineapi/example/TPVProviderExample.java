package net.sf.marineapi.example;

import java.io.File;
import java.io.FileInputStream;
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

    public TPVProviderExample() {
        init();
    }

    private void init() {
        try {
            File f = new File("data/Garmin-GPS76.log");
            InputStream is = new FileInputStream(f);
            SentenceReader sr = new SentenceReader(is);
            provider = new TPVProvider(sr);
            provider.addListener(this);
            sr.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        new TPVProviderExample();
    }

}

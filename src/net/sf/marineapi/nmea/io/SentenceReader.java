/* 
 * SentenceReader.java
 * Copyright 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.io;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.parser.Sentence;

/**
 * An abstract sentence reader class. You may extend it to create your own
 * readers to read NMEA from different data sources (ports, files etc.).
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
@Deprecated
public abstract class SentenceReader extends Thread {

    // Thread's update rate in milliseconds.
    private int delay = 100;

    // Time to wait before receive flag is set to false.
    private int timeout = 3000;

    // Time stamp for last NMEA sentence
    private long lastUpdate = 0;

    // BufferedReader for NMEA input.
    protected BufferedReader in = null;

    // PrintWriter for NMEA output.
    protected PrintWriter out = null;

    // Flag for indicating if reader is receiving NMEA
    private boolean receiving = false;

    // Flag that keeps thread loop running
    private boolean running = true;

    // Vector for SentenceListeners
    private final List<SentenceListener> listeners = new Vector<SentenceListener>();

    // Buffer for writing to data source
    private final List<String> writeBuffer = new Vector<String>();

    /**
     * Constructor.
     */
    public SentenceReader() {
    }

    /**
     * Adds a SentenceListener to this reader. If the listener is already
     * registered in this reader, it won't be added twice.
     * 
     * @param sl <code>SentenceListener</code> to be registered.
     * @see net.sf.marineapi.nmea.event.SentenceListener
     */
    public void addSentenceListener(SentenceListener sl) {
        if (!listeners.contains(sl)) {
            listeners.add(sl);
        }
    }

    /**
     * Makes a clean exit from <code>run()</code> and closes input and output
     * streams. The <code>disconnect()</code> method is then invoked to close
     * the actual data source (eg. serial port).
     */
    public final void close() {
        receiving = false;
        running = false;
        interrupt();

        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        disconnect();
    }

    /**
     * Close communications with the data source. Implementation of this method
     * depends on the type of the NMEA source (serial port, socket, file etc).
     */
    protected abstract void disconnect();

    /**
     * Dispatch data to all listeners.
     * 
     * @param nmea sentence string.
     */
    private final void fireSentenceRead(String data) {
        try {
            SentenceEvent se = new SentenceEvent(this, data);
            lastUpdate = System.currentTimeMillis();
            receiving = true;

            for (SentenceListener sl : listeners) {
                sl.sentenceRead(se);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            receiving = false;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Returns an unmodifiable list of <code>SentenceListeners</code> assigned
     * to this reader.
     * 
     * @return List of SentenceListeners.
     */
    public final List<SentenceListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    /**
     * Tells if the reader is receiving NMEA data.
     * 
     * @return <code>true</code>, if new data has been received within the set
     *         timeout, otherwise <code>false</code>.
     */
    public boolean isReceiving() {
        return receiving;
    }

    /**
     * Remove a listener from reader.
     * 
     * @param sl <code>SentenceListener</code> to be removed.
     */
    public void removeSentenceListener(SentenceListener sl) {
        if (listeners.contains(sl)) {
            listeners.remove(sl);
        }
    }

    /**
     * Main processing method.
     */
    @Override
    public void run() {
        String data = "";

        while (running) {
            try {
                // Send oldest message from the output buffer
                if (writeBuffer.size() > 0) {
                    data = writeBuffer.get(0) + Sentence.TERMINATOR;
                    out.print(data);
                    out.flush();
                    writeBuffer.remove(0);
                }

                // Read data if available and dispatch to listeners
                if (in.ready()) {
                    data = in.readLine();
                    this.fireSentenceRead(data);
                }

                // Check reader receive status
                if ((System.currentTimeMillis() - lastUpdate) >= timeout) {
                    receiving = false;
                }

                sleep(delay);

            } catch (InterruptedException ie) {
                ie.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
                close();
            }
        }
    }

    /**
     * Sets the reader update rate in milliseconds. For some data sources, files
     * for example, you may want to slow down the reader. The default value is
     * 100 ms (0.1 sec) and values smaller than 50 ms are ignored.
     * 
     * @param millis Thread sleep time in milliseconds (minimum 50 ms).
     */
    public void setUpdateRate(int millis) {
        if (millis >= 50) {
            delay = millis;
        }
    }

    /**
     * Get the current reader update rate.
     * 
     * @return Update rate in milliseconds.
     * @see #setUpdateRate(int)
     */
    public int getUpdateRate() {
        return delay;
    }

    /**
     * Sets the reader timeout in milliseconds. When the set time has exceeded,
     * the receive flag is set to <code>false</code>. The default value is 3000
     * ms (3 sec) and values smaller than 1000 ms are ignored.
     * 
     * @param millis Receive timeout in milliseconds (minimum 1000 ms).
     * @see #isReceiving()
     */
    public void setReceiveTimeout(int millis) {
        if (millis >= 1000) {
            timeout = millis;
        }
    }

    /**
     * Get the current receive timeout.
     * 
     * @return Timeout in milliseconds.
     * @see #setReceiveTimeout(int)
     * @see #isReceiving()
     */
    public int getReceiveTimeout() {
        return timeout;
    }

    /**
     * Writes a String to data source. Sentence is first added to write buffer
     * which is unloaded by thread loop message-by-message. Because of this it
     * may take a while to get the message go through the reader, depending on
     * the current write buffer load.
     * 
     * @param s String to be sent to data source.
     */
    public void write(String s) {
        if (s != null && !"".equals(s)) {
            writeBuffer.add(s);
        }
    }

}

package net.sf.marineapi.example;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

// If using Java Communications API, remove gnu.io imports above and
// use corresponding javax.comm classes:
// 
// import javax.comm.CommPortIdentifier;
// import javax.comm.SerialPort;

/**
 * <p>
 * Serial port example using Java Communications API or RXTX libraries.
 * Application scans through all existing COM ports and seeks for NMEA data. If
 * valid data is found, scanning stops and application starts printing out the
 * sentences it reads from the port.
 * <ul>
 * <li><a
 * href="http://www.oracle.com/technetwork/java/index-jsp-141752.html">Java
 * Communications API</a>
 * <li><a href="http://rxtx.qbang.org/">RXTX wiki</a>
 * </ul>
 * 
 * @author Kimmo Tuukkanen
 */
public class SerialPortExample implements SentenceListener {

    public SerialPortExample() {
        init();
    }

    private void init() {
        try {
            SerialPort sp = getSerialPort();

            if (sp != null) {
                InputStream is = sp.getInputStream();
                SentenceReader sr = new SentenceReader(is);
                sr.addSentenceListener(this);
                sr.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scan serial ports for NMEA data.
     * 
     * @return SerialPort from which NMEA data was found, or null if data was
     *         not found in any of the ports.
     */
    private SerialPort getSerialPort() {
        try {
            Enumeration e = CommPortIdentifier.getPortIdentifiers();

            while (e.hasMoreElements()) {
                CommPortIdentifier id = (CommPortIdentifier) e.nextElement();

                if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                    SerialPort sp = (SerialPort) id.open("SerialExample", 30);

                    sp.setSerialPortParams(4800, SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    InputStream is = sp.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader buf = new BufferedReader(isr);

                    System.out.println("Scanning port " + sp.getName());

                    // try each port few times before giving up
                    for (int i = 0; i < 5; i++) {
                        try {
                            String data = buf.readLine();
                            if (SentenceValidator.isValid(data)) {
                                System.out.println("NMEA data found!");
                                return sp;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    is.close();
                    isr.close();
                    buf.close();
                }
            }
            System.out.println("NMEA data was not found..");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi
     * .nmea.event.SentenceEvent)
     */
    public void sentenceRead(SentenceEvent event) {
        // here we receive each sentence read from the port
        System.out.println(event.getSentence());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
     */
    public void readingStarted() {
        System.out.println("-- Start --");
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
     */
    public void readingStopped() {
        System.out.println("-- Stop --");
    }

    /**
     * Startup method, no arguments required.
     * 
     * @param args None
     */
    public static void main(String[] args) {
        new SerialPortExample();
    }

    public void readingPaused() {
        // TODO Auto-generated method stub

    }
}

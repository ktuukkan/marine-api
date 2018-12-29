package net.sf.marineapi.test.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Dummy UDP server repeating single NMEA sentence.
 */
public class UDPServerMock implements Runnable {

    public final String TXT = "$IITXT,1,1,UDP,TEST*0F";

    private DatagramSocket socket;
    private boolean running = true;

    public UDPServerMock() {
        new Thread(this).start();
    }

    public void run() {
        try {
            int port = 3810;
            InetAddress host = InetAddress.getLocalHost();
            byte[] data = TXT.getBytes();
            socket = new DatagramSocket();

            while (running) {
                DatagramPacket packet =
                    new DatagramPacket(data, data.length, host, port);
                socket.send(packet);
                Thread.sleep(10);
            }

        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public void stop() {
        running = false;
    }
}

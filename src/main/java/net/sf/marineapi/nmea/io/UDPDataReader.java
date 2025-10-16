/*
 * UDPDataReader.java
 * Copyright (C) 2010-2014 Kimmo Tuukkanen, Ludovic Drouineau
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
package net.sf.marineapi.nmea.io;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DataReader implementation using DatagramSocket as data source.
 *
 * @author Kimmo Tuukkanen, Ludovic Drouineau
 */
class UDPDataReader extends AbstractDataReader {

    private DatagramSocket socket;
    private byte[] buffer = new byte[1024];
    private Queue<UDPPacket> queue = new LinkedList<>();
    private Map<String, StringBuilder> senderBuffers = new ConcurrentHashMap<>();

    private static class UDPPacket {
        private final String data;
        private final String senderKey;

        public UDPPacket(String data, String senderKey) {
            this.data = data;
            this.senderKey = senderKey;
        }
    }

    /**
     * Creates a new instance of StreamReader.
     *
     * @param socket DatagramSocket to be used as data source.
     * @param parent SentenceReader dispatching events for this reader.
     */
    UDPDataReader(DatagramSocket socket, SentenceReader parent) {
        super(parent);
        this.socket = socket;
    }

    @Override
    public String read() throws Exception {
        UDPPacket packet;

        while (true) {
            // If there is a backlog of sentences in the queue, then return the old sentences first so that each packet is uploaded compete.
            if ((packet = queue.poll()) != null) {
                break;
            }

            // Once the backlog is cleared, then read the port, split the packet into sentences,
			// and store the individual sentences in the queue.  Queue will always start empty here.            
			packet = receive();

            StringBuilder tempBuffer = senderBuffers.computeIfAbsent(
                packet.senderKey, 
                k -> new StringBuilder()
            );

            tempBuffer.append(packet.data);

            String[] lines = tempBuffer.toString().split("\\r?\\n", -1);

            // Contains line breaks
            if (lines.length > 1) {
                for (int i = 0; i < lines.length - 1; i++) {
                    // Complete NMEA statement, add to queue
                    queue.add(new UDPPacket(lines[i], packet.senderKey));
                }
                tempBuffer.setLength(0);
                tempBuffer.append(lines[lines.length - 1]);
            }
        }
        return packet.data;
    }

    /**
	 * Receive UDP packet and return as UDPPacket.  Blocks until data is received.
	 * Exceptions bubble up to the {@link AbstractDataReader}    
	 */
    private UDPPacket receive() throws Exception {
        DatagramPacket pkg = new DatagramPacket(buffer, buffer.length);
        socket.receive(pkg);
        String senderKey = pkg.getAddress().getHostAddress() + "_" + pkg.getPort();
        return new UDPPacket(
            new String(pkg.getData(), 0, pkg.getLength()),
            senderKey
        );
    }

}

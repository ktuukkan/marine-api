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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataReader implementation using DatagramSocket as data source.
 * 
 * @author Kimmo Tuukkanen, Ludovic Drouineau
 */
class UDPDataReader extends AbstractDataReader {

	private static final Logger LOG =
		Logger.getLogger(UDPDataReader.class.getName());
	
	private DatagramSocket socket;
	private byte[] buffer = new byte[1024];
	private Queue<String> queue = new LinkedList<String>();
    private SentenceReader parent;

	/**
	 * Creates a new instance of StreamReader.
	 * 
	 * @param socket DatagramSocket to be used as data source.
	 * @param parent SentenceReader dispatching events for this reader.
	 */
	public UDPDataReader(DatagramSocket socket, SentenceReader parent) {
		super(parent);
        this.parent=parent;
		this.socket = socket;
	}

	@Override
	public String read() {
		String data = receive();
		if(data != null) {
			String[] lines = data.split("\\r?\\n");
			queue.addAll(Arrays.asList(lines));
		}
		return queue.poll();
	}
	
	/**
	 * Receive UDP packet and return as String
	 */
	private String receive() {
		String data = null;
		try {
			DatagramPacket pkg = new DatagramPacket(buffer, buffer.length);
			socket.receive(pkg);
			data = new String(pkg.getData(), 0, pkg.getLength());
		} catch (Exception e) {
            if(parent.getExceptionListener()!=null) {
                parent.getExceptionListener().onException(e);
            } else {
                LOG.log(Level.WARNING, "UDP receive failed", e);
            }
		}
		return data;
	}

}

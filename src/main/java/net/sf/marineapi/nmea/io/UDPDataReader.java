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

/**
 * DataReader implementation using DatagramSocket as data source.
 * 
 * @author Kimmo Tuukkanen, Ludovic Drouineau
 */
class UDPDataReader extends AbstractDataReader {

	private DatagramSocket socket;
	private byte[] buffer = new byte[1024];
	private Queue<String> queue = new LinkedList<String>();

	/**
	 * Creates a new instance of StreamReader.
	 * 
	 * @param socket DatagramSocket to be used as data source.
	 * @param parent SentenceReader dispatching events for this reader.
	 */
	public UDPDataReader(DatagramSocket socket, SentenceReader parent) {
		super(parent);
		this.socket = socket;
	}

	@Override
	public String read() throws Exception {
		while (true) {
			String data = queue.poll();
			if (data != null)
				return data;

			data = receive();
			String[] lines = data.split("\\r?\\n");
			queue.addAll(Arrays.asList(lines));
		}
	}

	/**
	 * Receive UDP packet and return as String
	 */
	private String receive() throws Exception {
		DatagramPacket pkg = new DatagramPacket(buffer, buffer.length);
		socket.receive(pkg);
		return new String(pkg.getData(), 0, pkg.getLength());
	}

}

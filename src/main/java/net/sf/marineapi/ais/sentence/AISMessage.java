/*
 * AISMessage.java
 * Copyright (C) 2015 Kimmo Tuukkanen
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
package net.sf.marineapi.ais.sentence;

/**
 * Common base interface of AIS messages.
 * 
 * @author Kimmo Tuukkanen
 */
public interface AISMessage {

	/**
	 * Returns the message type.
	 * Users of this interface should query first the message type and then
	 * instantiate the corresponding message class.
	 * For example, if the message type is 5 then the <code>AISMessage05</code>
	 * class should be created with the message body. 
	 * @return message types in the range from 1 to 27.
	 */
	public int getMessageType();

	/**
	 * Returns the repeat indicator which tells how many times this message
	 * has been repeated. 
	 * @return the integer repeat indicator
	 */
	public int getRepeatIndicator();

	/**
	 * Returns the  unique identifier (MMSI number) of the transmitting ship.
	 * @return the MMSI as an integer.
	 */
	public int getMMSI();

	/**
	 * Appends a payload fragment into the current message.
	 */
	public void append(String fragment, int index, int fillBits);
}

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
package net.sf.marineapi.ais.message;

/**
 * Common base interface of AIS messages.
 *
 * @author Kimmo Tuukkanen
 */
public interface AISMessage {

	/**
	 * Returns the message type.
	 *
	 * @return message types in the range from 1 to 27.
	 */
	int getMessageType();

	/**
	 * Returns the repeat indicator that tells how many times this message
	 * has been repeated.
	 *
	 * @return the number of repeats
	 */
	int getRepeatIndicator();

	/**
	 * Returns the  unique identifier (MMSI number) of the transmitting ship.
	 *
	 * @return the MMSI as an integer.
	 */
	int getMMSI();
}

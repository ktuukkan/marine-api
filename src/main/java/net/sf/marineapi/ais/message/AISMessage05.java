/*
 * AISMessage05.java
 * Copyright (C) 2015 Lázár József
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
 * Static and Voyage Related Data.
 * 
 * @author Lázár József
 */
public interface AISMessage05 extends AISMessage {

	/**
	 * Returns the AIS version indicator for the current message.
	 * @return AIS version indicator
	 */
	int getAISVersionIndicator();

	/**
	 * Returns the IMO number of the transmitting ship.
	 * @return an integer value representing the IMO number (1-999999999)
	 */
	int getIMONumber();

	/**
	 * Returns the call sign of the transmitting ship.
	 * @return at most 7 characters, representing the call sign
	 */
	String getCallSign();

	/**
	 * Returns the name of the transmitting ship.
	 * @return maximum 20 characters, representing the name
	 */
	String getName();

	/**
	 * Returns the type of ship and cargo.
	 * @return an integer value representing the type of ship and cargo
	 */
	int getTypeOfShipAndCargoType();

	/**
	 * Returns the distance from the reference point to the bow.
	 */
	int getBow();

	/**
	 * Returns the distance from the reference point to the stern of the ship.
	 */
	int getStern();

	/**
	 * Returns the distance from the reference point to the port side of the ship.
	 */
	int getPort();

	/**
	 * Returns the distance from the reference point to the starboard side of the ship.
	 */
	int getStarboard();

	/**
	 * Returns the type of electronic position fixing device.
	 * @return an integer value the the type of EPFD
	 */
	int getTypeOfEPFD();

	/**
	 * Returns the month of the estimated time of arrival.
	 * @return month 1..12 (0 for n/a)
	 */
	int getETAMonth();

	/**
	 * Returns the day of estimated time of arrival.
	 * @return day number 1..31 (0 for n/a)
	 */
	int getETADay();

	/**
	 * Returns the hour of estimated time of arrival.
	 * @return hour 0..23 (24 for n/a)
	 */
	int getETAHour();

	/**
	 * Return the minute of estimated time of arrival.
	 * @return minute 0..59 (60 for n/a)
	 */
	int getETAMinute();
	
	/**
	 * Returns the maximum draught.
	 * @return an integer value of the maximum static draught in 1/10 m
	 */
	int getMaximumDraught();

	/**
	 * Returns the destination.
	 * @return maximum 20 characters, representing the destination
	 */
	String getDestination();
}

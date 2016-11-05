/*
 * AISMessage19.java
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
 * Extended Class B Equipment Position Report.
 *  
 * @author Lázár József
 */
public interface AISMessage19 extends AISPositionReportB {

	/**
	 * Returns the name of the transmitting ship.
	 * 
	 * @return maximum 20 characters, representing the name
	 */
	String getName();

	/**
	 * Returns the type of ship and cargo.
	 * 
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
	 * Returns the distance from the reference point to the port side of the
	 * ship.
	 */
	int getPort();

	/**
	 * Returns the type of electronic position fixing device.
	 * 
	 * @return an integer value the the type of EPFD
	 */
	int getTypeOfEPFD();
}

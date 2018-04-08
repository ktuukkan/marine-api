/*
 * AISMessage08DAC200FID10.java
 * Copyright (C) 2018 Paweł Kozioł
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
 * Inland ship static and voyage related data (Inland AIS).
 *
 * @author Paweł Kozioł
 */
public interface AISMessage08DAC200FID10 extends AISMessage08 {

	/**
	 * Returns European Number of Identification a.k.a. European Vessel Identification Number.
	 * @return European Number of Identification (8 six-bit characters)
	 */
	String getENI();

	/**
	 * Returns length of the ship in meters with 0.1m precision.
	 * @return Length of the ship in meters with 0.1m precision
	 */
	double getShipLength();

	/**
	 * Returns beam of the ship in meters with 0.1m precision.
	 * @return Beam of the ship in meters with 0.1m precision
	 */
	double getShipBeam();

	/**
	 * Returns ship/combination type as full ERI codes with range 8000-8370
	 * or ERI SOLAS codes in the range 1-99.
	 *
	 * @return Ship/combination type
	 */
	int getShipType();

	/**
	 * Returns hazardous cargo code.
	 * @return Hazardous cargo code
	 */
	int getHazardCode();

	/**
	 * Returns draught in meters with 0.01m precision.
	 * @return Draught in meters with 0.01m precision
	 */
	double getDraught();

	/**
	 * Returns load status. 0 = N/A, 1 = Unloaded, 2 = Loaded.
	 * @return Load status
	 */
	int getLoadStatus();

	/**
	 * Returns speed inf. quality. 0 = low/GNSS (default) 1 = high
	 * @return Speed inf. quality
	 */
	boolean getSpeedQuality();

	/**
	 * Returns course inf. quality. 0 = low/GNSS (default) 1 = high
	 * @return Course inf. quality
	 */
	boolean getCourseQuality();

	/**
	 * Returns heading inf. quality. 0 = low/GNSS (default) 1 = high
	 * @return Heading inf. quality
	 */
	boolean getHeadingQuality();
}

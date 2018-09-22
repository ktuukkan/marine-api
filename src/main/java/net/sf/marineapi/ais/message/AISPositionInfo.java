/*
 * AISPositionInfo.java
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
 * Interface for all position information.
 * 
 * @author Lázár József
 */
interface AISPositionInfo extends AISMessage {
	
    /**
	 * Returns the position accuracy.
	 */
	boolean getPositionAccuracy();

	/**
	 * Returns the longitude in degrees.
	 */
	double getLongitudeInDegrees();

	/**
	 * Returns the latitude in degrees.
	 */
	double getLatitudeInDegrees();

	/**
	 * Returns true if longitude is available in the message.
	 * If false, getLongitudeInDegrees may return an out-of-range value.
	 */
	boolean hasLongitude();

	/**
	 * Returns true if latitude is available in the message.
	 * If false, getLatitudeInDegrees may return an out-of-range value.
	 */
	boolean hasLatitude();
}

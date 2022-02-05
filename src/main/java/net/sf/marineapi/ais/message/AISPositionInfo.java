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
public interface AISPositionInfo extends AISMessage {
	
    /**
	 * Tells if the position is accurate.
	 *
	 * @return {@code true} if accurate (&lt; 10 meters), otherwise {@code false}.
	 */
	boolean isAccurate();

	/**
	 * Returns the longitude in degrees.
	 *
	 * @return Longitude, in degrees.
	 */
	double getLongitudeInDegrees();

	/**
	 * Returns the latitude in degrees.
	 *
	 * @return Latitude, in degrees.
	 */
	double getLatitudeInDegrees();

	/**
	 * Tells if the longitude is available in the message. If not,
	 * {@link #getLongitudeInDegrees} may return an out-of-range value.
	 *
	 * @return {@code true} if available, otherwise {@code false}.
	 */
	boolean hasLongitude();

	/**
	 * Tells if the latitude is available in the message. If not,
	 * {@link #getLatitudeInDegrees()} may return an out-of-range value.
	 *
	 * @return {@code true} if available, otherwise {@code false}.
	 */
	boolean hasLatitude();
}

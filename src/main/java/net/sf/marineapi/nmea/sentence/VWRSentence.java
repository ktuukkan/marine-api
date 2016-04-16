/* 
 * VWRSentence.java
 * Copyright (C) 2016 Henri Laurent
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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
package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.nmea.util.Direction;

/**
 * Relative Wind Speed and Angle.
 * Wind direction magnitude in degrees Wind direction Left/Right of bow
 * Speed in Knots, Meters Per Second and Kilometers Per Hour
 * <p>
 * Example: <br>
 * <code>---</code>
 * 
 * @author Henri Laurent
 */
public interface VWRSentence extends Sentence {

	/** Units indicator for meters per second */
	char MPS = 'M';
	/** Units indicator for kilometers per hour */
	char KMPH = 'K';
	/** Units indicator for knots (nautical miles per hour) */
	char KNOT = 'N';

	/**
	 * Get the Wind angle magnitude in degrees
	 *
	 * @return Wind angle
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the
	 * data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getWindAngle();

    /**
     * Get the Wind direction Left/Right of bow
     *
     * @since NMEA 2.3
	 * @return {@link Direction} enum
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	Direction getDirectionLeftRight();

	/**
	 * Get relative wind speed, in kilometers per hour.
	 * 
	 * @return Speed in km/h
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getSpeedKmh();

	/**
	 * Get relative wind speed in knots.
	 * 
	 * @return Speed in knots (nautical miles per hour)
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getSpeedKnots();

	/**
	 * Set the Wind angle magnitude
	 * 
	 * @param mWindAngle Wind angle magnitude in degrees.
	 */
	void setWindAngle(double mWindAngle);

	/**
	 * Set the Wind direction Left/Right of bow
	 * 
	 * @param direction Direction to set
	 * @since NMEA 2.3
	 */
	void setDirectionLeftRight(Direction direction);

	/**
	 * Set the relative wind speed in kmh
	 * 
	 * @param kmh Speed in kilometers per hour (km/h).
	 */
	void setSpeedKmh(double kmh);

	/**
	 * Set the relative wind speed in knots.
	 * 
	 * @param knots Speed in knots (nautical miles per hour)
	 */
	void setSpeedKnots(double knots);
}

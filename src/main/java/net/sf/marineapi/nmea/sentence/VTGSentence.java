/* 
 * VTGSentence.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.nmea.util.FaaMode;

/**
 * Course and speed over the ground. True and magnetic COG, speed provided in
 * km/h and knots. Mode (the last "A" in example sentence) was added in NMEA 2.3
 * and may not always be available.
 * <p>
 * Example: <br>
 * <code>$GPVTG,46.96,T,,,16.89,N,31.28,K,A*43</code>
 * 
 * @author Kimmo Tuukkanen
 */
public interface VTGSentence extends Sentence {

	/** Char indicator for "true" */
	char TRUE = 'T';
	/** Char indicator for "magnetic" */
	char MAGNETIC = 'M';
	/** Units indicator for kilometers per hour */
	char KMPH = 'K';
	/** Units indicator for knots (nautical miles per hour) */
	char KNOT = 'N';
	/** Operating in manual mode (forced 2D or 3D). */
	char MODE_MANUAL = 'M';
	/** Operating in automatic mode (2D/3D). */
	char MODE_AUTOMATIC = 'A';

	/**
	 * Get the magnetic course over ground.
	 * 
	 * @return Magnetic course
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getMagneticCourse();

	/**
	 * Get the FAA operating mode of GPS receiver. The field may not be
	 * available, depending on the NMEA version.
	 * 
	 * @since NMEA 2.3
	 * @return {@link FaaMode} enum
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	FaaMode getMode();

	/**
	 * Get current speed over ground, in kilometers per hour.
	 * 
	 * @return Speed in km/h
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getSpeedKmh();

	/**
	 * Get speed over ground in knots.
	 * 
	 * @return Speed in knots (nautical miles per hour)
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getSpeedKnots();

	/**
	 * Get the true course over ground.
	 * 
	 * @return True course, in degrees
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getTrueCourse();

	/**
	 * Set the magnetic course over ground.
	 * 
	 * @param mcog Course in degrees.
	 */
	void setMagneticCourse(double mcog);

	/**
	 * Set the FAA operating mode of GPS receiver.
	 * 
	 * @param mode Mode to set
	 * @since NMEA 2.3
	 */
	void setMode(FaaMode mode);

	/**
	 * Set the current speed over ground.
	 * 
	 * @param kmh Speed in kilometers per hour (km/h).
	 */
	void setSpeedKmh(double kmh);

	/**
	 * Set the speed over ground, in knots.
	 * 
	 * @param knots Speed in knots (nautical miles per hour)
	 */
	void setSpeedKnots(double knots);

	/**
	 * Set the true course over ground.
	 * 
	 * @param tcog True course, in degrees
	 * @throws IllegalArgumentException If specified course is out of bounds
	 *             0..360 degrees.
	 */
	void setTrueCourse(double tcog);

}

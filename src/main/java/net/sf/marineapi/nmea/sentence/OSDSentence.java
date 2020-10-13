/* 
 * OSDSentence.java
 * Copyright (C) 2020 Joshua Sweaney
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

import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.ReferenceSystem;
import net.sf.marineapi.nmea.util.Units;

/**
 * Own ship data.<br>
 * Gives the movement vector of the ship.<br>
 * 
 * Includes (in this order): Heading (degrees true), Heading status {@link net.sf.marineapi.nmea.util.DataStatus} <br>
 * Vessel course (degrees true), Course reference {@link net.sf.marineapi.nmea.util.ReferenceSystem},<br>
 * Vessel speed, Speed reference (ReferenceSystem), Vessel set (degrees true), vessel drift,<br>
 * Speed units {@link net.sf.marineapi.nmea.util.Units}<br>
 * 
 * Example:<br>
 * {@code $RAOSD,35.1,A,36.0,P,10.2,P,15.3,0.1,N*41<CR><LF> }
 * 
 * @author Joshua Sweaney
 */
public interface OSDSentence {

    /**
     * Get ownship heading
     * 
     * @return Double ownship heading
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getHeading();

    /**
     * Get the status of heading data
     * @return DataStatus the status
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    DataStatus getHeadingStatus();

    /**
     * Get the course of ownship
     * @return Double the course
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getCourse();

    /**
     * Get the reference system used to calculate course
     * @return ReferenceSystem the reference
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    ReferenceSystem getCourseReference();

    /**
     * Get ownship speed
     * @return Double the speed
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getSpeed();

    /**
     * Get the reference system used to calculate speed
     * @return ReferenceSystem the reference
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    ReferenceSystem getSpeedReference();

    /**
     * Get the vessel set (water current direction)
     * @return Double the set
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getVesselSet();

    /**
     * Get the vessel drift
     * @return double the drift
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getVesselDrift();

    /**
     * Get the units of speed measurements
     * @return Units the speed units (K, N, S)
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    Units getSpeedUnits();

    /**
     * Set ownship heading
     * @param heading the heading
     */
    void setHeading(double heading);

    /**
     * Set the heading data status
     * @param status the status
     */
    void setHeadingStatus(DataStatus status);

    /**
     * Set ownship course
     * @param course the course
     */
    void setCourse(double course);

    /**
     * Set the reference system for the course
     * @param reference the reference
     */
    void setCourseReference(ReferenceSystem reference);

    /**
     * Set ownship speed
     * @param speed the speed
     */
    void setSpeed(double speed);

    /**
     * Set the reference system for the speed
     * @param reference the reference
     */
    void setSpeedReference(ReferenceSystem reference);

    /**
     * Set the vessel set
     * @param set the vessel set
     */
    void setVesselSet(double set);

    /**
     * Set the vessel drift
     * @param drift the vessel drift
     */
    void setVesselDrift(double drift);

    /**
     * Set the speed units
     * @param units the units
     */
    void setSpeedUnits(Units units);
    
}
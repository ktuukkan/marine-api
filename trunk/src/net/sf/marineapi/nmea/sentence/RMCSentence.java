/* 
 * RMCSentence.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.GpsMode;

/**
 * Interface for RMC sentence type. Recommended minimum specific GPS/Transit
 * data.
 * <p>
 * Example:<br>
 * <code>$GPRMC,120044,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E*7C</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface RMCSentence extends PositionSentence, TimeSentence,
        DateSentence {

    /**
     * Get the corrected course over ground. Correction is done by subtracting
     * or adding the magnetic variation from true course (easterly variation
     * subtracted and westerly added).
     * 
     * @return Corrected true course
     * @see #getCourse()
     * @see #getVariation()
     */
    double getCorrectedCourse();

    /**
     * Get true course over ground (COG), in degrees.
     * 
     * @return Course
     */
    double getCourse();

    /**
     * Gets the data status; valid or invalid.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID
     */
    DataStatus getDataStatus();

    /**
     * Get the direction of magnetic variation; east or west.
     * 
     * @return Direction.EAST or Direction.WEST
     */
    Direction getDirectionOfVariation();

    /**
     * Get the GPS operating mode.
     * 
     * @return GpsMode enum
     */
    GpsMode getGpsMode();

    /**
     * Get speed over ground (SOG), in knots (nautical miles per hour).
     * 
     * @return Speed
     */
    double getSpeed();

    /**
     * Get the magnetic variation in degrees. Easterly variation subtracts from
     * true course, and is thus returned as negative value. Otherwise, the value
     * is positive.
     * 
     * @return Magnetic variation
     */
    double getVariation();

}

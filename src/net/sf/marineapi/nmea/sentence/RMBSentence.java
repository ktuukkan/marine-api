/* 
 * RMBSentence.java
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
import net.sf.marineapi.nmea.util.Waypoint;

/**
 * Interface for RMB sentence type. Recommended minimum navigation information.
 * This sentence is transmitted by a GPS receiver when a destination waypoint is
 * active (GOTO mode).
 * <p>
 * Example:<br>
 * <code>$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface RMBSentence extends Sentence {

    /**
     * Get the arrival to waypoint status. Status is <code>INVALID</code> while
     * not arrived at destination, otherwise <code>VALID</code>.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID, <code>null</code> if
     *         field is empty.
     * @see #hasArrived()
     */
    DataStatus getArrivalStatus();

    /**
     * Get true bearing to destination, in degrees.
     * 
     * @return double
     */
    double getBearing();

    /**
     * Get cross track error (XTE), in nautical miles.
     * 
     * @return double
     */
    double getCrossTrackError();

    /**
     * Get the destination waypoint.
     * 
     * @return Waypoint
     */
    Waypoint getDestination();

    /**
     * Get the ID of origin waypoint.
     * 
     * @return Id String.
     */
    String getOriginId();

    /**
     * Get range to destination in nautical miles.
     * 
     * @return Range to destination
     */
    double getRange();

    /**
     * Get status of sentence data; valid or invalid.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID
     */
    DataStatus getStatus();

    /**
     * Get the direction to steer to correct error (left/right), for example
     * "steer left to correct".
     * 
     * @return Direction.LEFT or Direction.RIGHT
     */
    Direction getSteerTo();

    /**
     * Get velocity towards destination, in knots (nautical miles per hour).
     * 
     * @return velocity value
     */
    double getVelocity();

    /**
     * Tells if the destination waypoint has been reached or not.
     * 
     * @return True if has arrived to waypoint, otherwise false.
     */
    boolean hasArrived();

}

/* 
 * SentenceRMB.java
 * Copyright 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.Waypoint;

/**
 * RMB sentence parser. Recommended minimum navigation information. This
 * sentence is transmitted by a GPS receiver when a destination waypoint is
 * active (GOTO mode).
 * <p>
 * Example:
 * <code>$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceRMB extends PositionParser {

    // Sentence field indexes
    private static final int STATUS = 1;
    private static final int CROSS_TRACK_ERROR = 2;
    private static final int STEER_TO = 3;
    private static final int ORIGIN_WPT = 4;
    private static final int DEST_WPT = 5;
    private static final int DEST_LAT = 6;
    private static final int DEST_LAT_HEM = 7;
    private static final int DEST_LON = 8;
    private static final int DEST_LON_HEM = 9;
    private static final int RANGE_TO_DEST = 10;
    private static final int BEARING_TO_DEST = 11;
    private static final int VELOCITY = 12;
    private static final int ARRIVAL_STATUS = 13;

    /**
     * Constructor.
     * 
     * @param nmea RMB sentence string
     */
    public SentenceRMB(String nmea) {
        super(nmea, SentenceId.RMB);
    }

    /**
     * Get the arrival to waypoint status. Status is <code>INVALID</code> while
     * not arrived at destination, otherwise <code>VALID</code>.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID, <code>null</code> if
     *         field is empty.
     * @see #hasArrived()
     */
    public DataStatus getArrivalStatus() {
        return DataStatus.valueOf(getCharValue(ARRIVAL_STATUS));
    }

    /**
     * Get true bearing to destination, in degrees.
     * 
     * @return double
     */
    public double getBearing() {
        return getDoubleValue(BEARING_TO_DEST);
    }

    /**
     * Get cross track error (XTE), in nautical miles.
     * 
     * @return double
     */
    public double getCrossTrackError() {
        return getDoubleValue(CROSS_TRACK_ERROR);
    }

    /**
     * Get the destination waypoint.
     * 
     * @return Waypoint
     */
    public Waypoint getDestination() {
        String id = getStringValue(DEST_WPT);
        double lat = parseLatitude(DEST_LAT);
        double lon = parseLongitude(DEST_LON);
        Direction lath = parseHemisphereLat(DEST_LAT_HEM);
        Direction lonh = parseHemisphereLon(DEST_LON_HEM);
        return new Waypoint(id, lat, lath, lon, lonh);
    }

    /**
     * Get the ID of origin waypoint.
     * 
     * @return Id String.
     */
    public String getOriginId() {
        return getStringValue(ORIGIN_WPT);
    }

    /**
     * Get range to destination in nautical miles.
     * 
     * @return Range to destination
     */
    public double getRange() {
        return getDoubleValue(RANGE_TO_DEST);
    }

    /**
     * Get status of sentence data; valid or invalid.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID
     */
    public DataStatus getStatus() {
        return DataStatus.valueOf(getCharValue(STATUS));
    }

    /**
     * Get the direction to steer to correct error (left/right), for example
     * "steer left to correct".
     * 
     * @return Direction.LEFT or Direction.RIGHT
     */
    public Direction getSteerTo() {
        return Direction.valueOf(getCharValue(STEER_TO));
    }

    /**
     * Get velocity towards destination, in knots (nautical miles per hour).
     * 
     * @return velocity value
     */
    public double getVelocity() {
        return getDoubleValue(VELOCITY);
    }

    /**
     * Tells if the destination waypoint has been reached or not.
     * 
     * @return True if has arrived to waypoint, otherwise false.
     */
    public boolean hasArrived() {
        return DataStatus.VALID.equals(getArrivalStatus());
    }
}

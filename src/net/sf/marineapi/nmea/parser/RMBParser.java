/* 
 * RMBParser.java
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.RMBSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.Waypoint;

/**
 * RMB sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class RMBParser extends PositionParser implements RMBSentence {

    // field indexes
    private static final int STATUS = 0;
    private static final int CROSS_TRACK_ERROR = 1;
    private static final int STEER_TO = 2;
    private static final int ORIGIN_WPT = 3;
    private static final int DEST_WPT = 4;
    private static final int DEST_LAT = 5;
    private static final int DEST_LAT_HEM = 6;
    private static final int DEST_LON = 7;
    private static final int DEST_LON_HEM = 8;
    private static final int RANGE_TO_DEST = 9;
    private static final int BEARING_TO_DEST = 10;
    private static final int VELOCITY = 11;
    private static final int ARRIVAL_STATUS = 12;

    /**
     * Constructor.
     * 
     * @param nmea RMB sentence string
     */
    public RMBParser(String nmea) {
        super(nmea, SentenceId.RMB);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getArrivalStatus()
     */
    public DataStatus getArrivalStatus() {
        return DataStatus.valueOf(getCharValue(ARRIVAL_STATUS));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getBearing()
     */
    public double getBearing() {
        return getDoubleValue(BEARING_TO_DEST);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getCrossTrackError()
     */
    public double getCrossTrackError() {
        return getDoubleValue(CROSS_TRACK_ERROR);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getDestination()
     */
    public Waypoint getDestination() {
        String id = getStringValue(DEST_WPT);
        double lat = parseLatitude(DEST_LAT);
        double lon = parseLongitude(DEST_LON);
        Direction lath = parseHemisphereLat(DEST_LAT_HEM);
        Direction lonh = parseHemisphereLon(DEST_LON_HEM);
        return new Waypoint(id, lat, lath, lon, lonh);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getOriginId()
     */
    public String getOriginId() {
        return getStringValue(ORIGIN_WPT);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getRange()
     */
    public double getRange() {
        return getDoubleValue(RANGE_TO_DEST);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getStatus()
     */
    public DataStatus getStatus() {
        return DataStatus.valueOf(getCharValue(STATUS));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getSteerTo()
     */
    public Direction getSteerTo() {
        return Direction.valueOf(getCharValue(STEER_TO));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#getVelocity()
     */
    public double getVelocity() {
        return getDoubleValue(VELOCITY);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#hasArrived()
     */
    public boolean hasArrived() {
        return DataStatus.VALID.equals(getArrivalStatus());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMBSentence#setArrivalStatus(net.sf.marineapi
     * .nmea.util.DataStatus)
     */
    public void setArrivalStatus(DataStatus status) {
        setCharValue(ARRIVAL_STATUS, status.toChar());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#setBearing(double)
     */
    public void setBearing(double bearing) {
        if (bearing < 0 || bearing > 360) {
            throw new IllegalArgumentException(
                    "Bearing out of range 0..360 degrees");
        }
        setDoubleValue(BEARING_TO_DEST, bearing);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMBSentence#setCrossTrackError(double)
     */
    public void setCrossTrackError(double xte) {
        setDoubleValue(CROSS_TRACK_ERROR, xte);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMBSentence#setDestination(net.sf.marineapi
     * .nmea.util.Waypoint)
     */
    public void setDestination(Waypoint dest) {
        setStringValue(DEST_WPT, dest.getId());
        setLatitude(DEST_LAT, dest.getLatitude());
        setLatHemisphere(DEST_LAT_HEM, dest.getLatHemisphere());
        setLongitude(DEST_LON, dest.getLongitude());
        setLonHemisphere(DEST_LON_HEM, dest.getLonHemisphere());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMBSentence#setOriginId(java.lang.String)
     */
    public void setOriginId(String id) {
        setStringValue(ORIGIN_WPT, id);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#setRange(double)
     */
    public void setRange(double range) {
        setDoubleValue(RANGE_TO_DEST, range);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMBSentence#setStatus(net.sf.marineapi
     * .nmea.util.DataStatus)
     */
    public void setStatus(DataStatus status) {
        setCharValue(STATUS, status.toChar());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMBSentence#setSteerTo(net.sf.marineapi
     * .nmea.util.Direction)
     */
    public void setSteerTo(Direction steer) {
        if (steer != Direction.LEFT && steer != Direction.RIGHT) {
            throw new IllegalArgumentException(
                    "Expected steer-to direction is LEFT or RIGHT.");
        }
        setCharValue(STEER_TO, steer.toChar());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMBSentence#setVelocity(double)
     */
    public void setVelocity(double velocity) {
        if (velocity < 0) {
            throw new IllegalArgumentException("Velocity cannot be negative");
        }
        setDoubleValue(VELOCITY, velocity);
    }
}

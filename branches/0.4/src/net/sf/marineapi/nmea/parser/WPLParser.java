/* 
 * WPLParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.WPLSentence;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Waypoint;

/**
 * WPL sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class WPLParser extends PositionParser implements WPLSentence {

    // field ids
    private static final int LATITUDE = 0;
    private static final int LAT_HEMISPHERE = 1;
    private static final int LONGITUDE = 2;
    private static final int LON_HEMISPHERE = 3;
    private static final int WAYPOINT_ID = 4;

    /**
     * Creates WPL parser with empty sentence.
     */
    public WPLParser() {
        super(TalkerId.GP, SentenceId.WPL, 5);
    }

    /**
     * Creates a new instance of WPLParser.
     * 
     * @param nmea WPL sentence String.
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    public WPLParser(String nmea) {
        super(nmea, SentenceId.WPL);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.WPLSentence#getWaypoint()
     */
    public Waypoint getWaypoint() {
        String id = getStringValue(WAYPOINT_ID);
        double lat = parseLatitude(LATITUDE);
        double lon = parseLongitude(LONGITUDE);
        CompassPoint lath = parseHemisphereLat(LAT_HEMISPHERE);
        CompassPoint lonh = parseHemisphereLon(LON_HEMISPHERE);
        return new Waypoint(id, lat, lath, lon, lonh);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.WPLSentence#setWaypoint(net.sf.marineapi
     * .nmea.util.Waypoint)
     */
    public void setWaypoint(Waypoint wp) {
        setStringValue(WAYPOINT_ID, wp.getId());
        setLatitude(LATITUDE, wp.getLatitude());
        setLongitude(LONGITUDE, wp.getLongitude());
        setLatHemisphere(LAT_HEMISPHERE, wp.getLatHemisphere());
        setLonHemisphere(LON_HEMISPHERE, wp.getLonHemisphere());
    }
}

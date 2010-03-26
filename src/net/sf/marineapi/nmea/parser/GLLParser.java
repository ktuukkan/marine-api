/* 
 * GLLParser.java
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

import net.sf.marineapi.nmea.sentence.GLLSentence;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * GLL Sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class GLLParser extends PositionParser implements GLLSentence {

    // field indices
    private final static int LATITUDE = 1;
    private final static int LAT_HEMISPHERE = 2;
    private final static int LONGITUDE = 3;
    private final static int LON_HEMISPHERE = 4;
    private final static int UTC_TIME = 5;
    private final static int DATA_STATUS = 6;

    /**
     * Constructor.
     * 
     * @param nmea GLL sentence String.
     * @throws IllegalArgumentException If the given sentence is invalid or does
     *             not contain GLL sentence.
     */
    public GLLParser(String nmea) {
        super(nmea, SentenceId.GLL);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GLLSentence#getDataStatus()
     */
    public DataStatus getDataStatus() {
        return DataStatus.valueOf(getCharValue(DATA_STATUS));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.PositionSentence#getPosition()
     */
    public Position getPosition() {
        double lat = parseLatitude(LATITUDE);
        double lon = parseLongitude(LONGITUDE);
        Direction lath = parseHemisphereLat(LAT_HEMISPHERE);
        Direction lonh = parseHemisphereLon(LON_HEMISPHERE);
        return new Position(lat, lath, lon, lonh);
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.TimeSentence#getUtcHours()
     */
    public int getUtcHours() {
        return Integer.parseInt(getUtcTime().substring(0, 2));
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.TimeSentence#getUtcMinutes()
     */
    public int getUtcMinutes() {
        return Integer.parseInt(getUtcTime().substring(2, 4));
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.TimeSentence#getUtcSeconds()
     */
    public double getUtcSeconds() {
        return Double.parseDouble(getUtcTime().substring(4, 6));
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.TimeSentence#getUtcTime()
     */
    public String getUtcTime() {
        return getStringValue(UTC_TIME);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.GLLSentence#setDataStatus(net.sf.marineapi
     * .nmea.util.DataStatus)
     */
    public void setDataStatus(DataStatus status) {
        setCharValue(DATA_STATUS, status.toChar());
    }
}

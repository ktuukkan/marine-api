/* 
 * SentenceGLL.java
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
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * GLL Sentence parser. Geographic position (latitude/longitude).
 * <p>
 * Example: <code>$GPGLL,6011.552,N,02501.941,E,120045,A*26</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceGLL extends PositionParser implements PositionSentence,
        TimeSentence {

    // Sentence field indexes
    private final static int LATITUDE = 1;
    private final static int LAT_HEMISPHERE = 2;
    private final static int LONGITUDE = 3;
    private final static int LON_HEMISPHERE = 4;
    private final static int UTC_TIME = 5;
    private final static int DATA_STATUS = 6;

    /**
     * Data status; char indicator for "valid".
     */
    public final static char STATUS_VALID = 'A';
    /**
     * Data status; char indicator for "invalid".
     */
    public final static char STATUS_INVALID = 'V';

    /**
     * Constructor.
     * 
     * @param nmea GLL sentence String.
     * @throws IllegalArgumentException If the given sentence is invalid or does
     *             not contain GLL sentence.
     */
    public SentenceGLL(String nmea) {
        super(nmea, SentenceId.GLL);
    }

    /**
     * Get the data quality status, valid or invalid.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID
     */
    public DataStatus getDataStatus() {
        return DataStatus.valueOf(getCharValue(DATA_STATUS));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.PositionSentence#getPosition()
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
}

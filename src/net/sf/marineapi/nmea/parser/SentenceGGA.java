/* 
 * SentenceGGA.java
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

import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.Units;

/**
 * GGA sentence parser. Global Positioning System Fix Data. Time, position and
 * other fix related data for a GPS receiver.
 * <p>
 * Example:
 * <code>$GPGGA,120044,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*79</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceGGA extends PositionParser implements PositionSentence,
        TimeSentence {

    // GGA field indices
    private final static int UTC_TIME = 1;
    private final static int LATITUDE = 2;
    private final static int LAT_HEMISPHERE = 3;
    private final static int LONGITUDE = 4;
    private final static int LON_HEMISPHERE = 5;
    private final static int FIX_QUALITY = 6;
    private final static int SATELLITES_IN_USE = 7;
    private final static int HORIZONTAL_DILUTION = 8;
    private final static int ALTITUDE = 9;
    private final static int ALTITUDE_UNITS = 10;
    private final static int GEOIDAL_HEIGHT = 11;
    private final static int HEIGHT_UNITS = 12;
    private final static int DGPS_AGE = 13;
    private final static int DGPS_STATION_ID = 14;

    /**
     * Altitude presented in meters.
     */
    public final static char ALT_UNIT_METERS = 'M';
    /**
     * Altitude presented in feet.
     */
    public final static char ALT_UNIT_FEET = 'f';

    /**
     * Creates a new instance of GGA parser.
     * 
     * @param nmea GGA sentence String.
     * @throws IllegalArgumentException If the specified sentence is invalid or
     *             not a GGA sentence.
     */
    public SentenceGGA(String nmea) {
        super(nmea, SentenceId.GGA);
    }

    /**
     * Get altitude of antenna.
     * 
     * @return double value
     * @throws NumberFormatException If field contains unexpected value.
     */
    public double getAltitude() {
        return getDoubleValue(ALTITUDE);
    }

    /**
     * Gets the altitude units, meters or feet.
     * 
     * @return Units enum
     */
    public Units getAltitudeUnits() {
        char ch = getCharValue(ALTITUDE_UNITS);
        if (ch != ALT_UNIT_METERS && ch != ALT_UNIT_FEET) {
            throw new ParseException("Invalid altitude unit indicator (" + ch
                    + ").");
        }
        return Units.valueOf(ch);
    }

    /**
     * Gets the age of differential GPS data (DGPS).
     * 
     * @return Seconds since last valid RTCM transmission
     */
    public double getDgpsAge() {
        return getDoubleValue(DGPS_AGE);
    }

    /**
     * Gets the ID of DGPS station.
     * 
     * @return Station ID (0000-1024)
     */
    public String getDgpsStationId() {
        return getStringValue(DGPS_STATION_ID);
    }

    /**
     * Get the GPS fix quality.
     * 
     * @return GpsFixQuality enum
     */
    public GpsFixQuality getFixQuality() {
        return GpsFixQuality.valueOf(getIntValue(FIX_QUALITY));
    }

    /**
     * Get height/separation of geoid above WGS84 ellipsoid, i.e. difference
     * between WGS-84 earth ellipsoid and mean sea level. Negative values are
     * below WGS-84 ellipsoid.
     * 
     * @return Height value
     */
    public double getGeoidalHeight() {
        return getDoubleValue(GEOIDAL_HEIGHT);
    }

    /**
     * Get units of height above geoid.
     * 
     * @return Units of geoidal height value
     */
    public Units getGeoidalHeightUnits() {
        return Units.valueOf(getCharValue(HEIGHT_UNITS));
    }

    /**
     * Get the horizontal dilution of precision (HDOP), i.e. the relative
     * accuracy of horizontal position.
     * 
     * @return horizontal dilution
     */
    public double getHorizontalDOP() {
        return getDoubleValue(HORIZONTAL_DILUTION);
    }

    /**
     * Get the number of active satellites in use.
     * 
     * @return number of satellites
     */
    public int getSatelliteCount() {
        return getIntValue(SATELLITES_IN_USE);
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
     * @see net.sf.marineapi.nmea.parser.TimeSentence#getUtcTime()
     */
    public String getUtcTime() {
        return getStringValue(UTC_TIME);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.TimeSentence#getUtcHours()
     */
    public int getUtcHours() {
        return Integer.parseInt(getUtcTime().substring(0, 2));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.TimeSentence#getUtcMinutes()
     */
    public int getUtcMinutes() {
        return Integer.parseInt(getUtcTime().substring(2, 4));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.TimeSentence#getUtcSeconds()
     */
    public double getUtcSeconds() {
        return Integer.parseInt(getUtcTime().substring(4, 6));
    }

}

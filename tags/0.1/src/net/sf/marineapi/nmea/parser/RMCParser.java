/* 
 * RMCParser.java
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

import java.util.Date;
import java.util.GregorianCalendar;

import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.GpsMode;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;

/**
 * RMC sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class RMCParser extends PositionParser implements RMCSentence {

    private static final int UTC_TIME = 0;
    private static final int DATA_STATUS = 1;
    private static final int LATITUDE = 2;
    private static final int LAT_HEMISPHERE = 3;
    private static final int LONGITUDE = 4;
    private static final int LON_HEMISPHERE = 5;
    private static final int SPEED = 6;
    private static final int COURSE = 7;
    private static final int UTC_DATE = 8;
    private static final int MAG_VARIATION = 9;
    private static final int VAR_HEMISPHERE = 10;
    private static final int MODE = 11;

    /**
     * Creates a new instance of RMCParser.
     * 
     * @param nmea RMC sentence String.
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    public RMCParser(String nmea) {
        super(nmea, SentenceId.RMC);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#getCorrectedCourse()
     */
    public double getCorrectedCourse() {
        return getCourse() + getVariation();
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#getCourse()
     */
    public double getCourse() {
        return getDoubleValue(COURSE);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.DateSentence#getDate()
     */
    public Date getDate() {
        int y = getUtcYear();
        int m = getUtcMonth() - 1;
        int d = getUtcDay();
        Time t = getTime();
        int h = t.getHour();
        int mi = t.getMinutes();
        int s = (int) Math.floor(t.getSeconds());
        GregorianCalendar cal = new GregorianCalendar(y, m, d, h, mi, s);
        return cal.getTime();
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#getDirectionOfVariation()
     */
    public Direction getDirectionOfVariation() {
        return Direction.valueOf(getCharValue(VAR_HEMISPHERE));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#getGpsMode()
     */
    public GpsMode getGpsMode() {
        return GpsMode.valueOf(getCharValue(MODE));
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
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#getSpeed()
     */
    public double getSpeed() {
        return getDoubleValue(SPEED);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#getDataStatus()
     */
    public DataStatus getStatus() {
        return DataStatus.valueOf(getCharValue(DATA_STATUS));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.DateSentence#getUtcDay()
     */
    public int getUtcDay() {
        return Integer.parseInt(getStringValue(UTC_DATE).substring(0, 2));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.DateSentence#getUtcMonth()
     */
    public int getUtcMonth() {
        return Integer.parseInt(getStringValue(UTC_DATE).substring(2, 4));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.TimeSentence#getUtcTime()
     */
    public String getUtcTime() {
        return getStringValue(UTC_TIME);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.DateSentence#getUtcYear()
     */
    public int getUtcYear() {
        int y = Integer.parseInt(getStringValue(UTC_DATE).substring(4));
        if (y < 100 && y <= PIVOT_YEAR) {
            y += 2000;
        } else if (y < 100) {
            y += 1900;
        }
        return y;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#getVariation()
     */
    public double getVariation() {
        double variation = getDoubleValue(MAG_VARIATION);
        if (Direction.EAST == getDirectionOfVariation() && variation > 0) {
            variation = -(variation);
        }
        return variation;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#setCourse(double)
     */
    public void setCourse(double cog) {
        setDoubleValue(COURSE, cog);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMCSentence#setDirectionOfVariation(net
     * .sf.marineapi.nmea.util.Direction)
     */
    public void setDirectionOfVariation(Direction dir) {
        if (dir != Direction.EAST && dir != Direction.WEST) {
            throw new IllegalArgumentException(
                    "Invalid variation direction, expected EAST or WEST.");
        }
        setCharValue(VAR_HEMISPHERE, dir.toChar());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMCSentence#setGpsMode(net.sf.marineapi
     * .nmea.util.GpsMode)
     */
    public void setGpsMode(GpsMode mode) {
        setCharValue(MODE, mode.toChar());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.PositionSentence#setPosition(net.sf.marineapi
     * .nmea.util.Position)
     */
    public void setPosition(Position pos) {
        setLatitude(LATITUDE, pos.getLatitude());
        setLongitude(LONGITUDE, pos.getLongitude());
        setLatHemisphere(LAT_HEMISPHERE, pos.getLatHemisphere());
        setLonHemisphere(LON_HEMISPHERE, pos.getLonHemisphere());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#setSpeed(double)
     */
    public void setSpeed(double sog) {
        setDoubleValue(SPEED, sog);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RMCSentence#setDataStatus(net.sf.marineapi
     * .nmea.util.DataStatus)
     */
    public void setStatus(DataStatus status) {
        setCharValue(DATA_STATUS, status.toChar());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RMCSentence#setVariation(double)
     */
    public void setVariation(double var) {
        setDoubleValue(MAG_VARIATION, var);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.TimeSentence#getTime()
     */
    public Time getTime() {
        String str = getStringValue(UTC_TIME);
        int h = Integer.parseInt(str.substring(0, 2));
        int m = Integer.parseInt(str.substring(2, 4));
        double s = Double.parseDouble(str.substring(4, 6));
        return new Time(h, m, s);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.TimeSentence#setTime(net.sf.marineapi.
     * nmea.util.Time)
     */
    public void setTime(Time t) {
        int h = t.getHour();
        int m = t.getMinutes();
        int s = (int) Math.floor(t.getSeconds());
        String time = String.format("%02d%02d%02d", h, m, s);
        setStringValue(UTC_TIME, time);
    }
}

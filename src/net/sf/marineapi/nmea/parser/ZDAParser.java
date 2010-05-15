/* 
 * ZDAParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.ZDASentence;
import net.sf.marineapi.nmea.util.Time;

/**
 * ZDA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class ZDAParser extends SentenceParser implements ZDASentence {

    // field indices
    private static final int UTC_TIME = 0;
    private static final int UTC_DAY = 1;
    private static final int UTC_MONTH = 2;
    private static final int UTC_YEAR = 3;
    private static final int LOCAL_ZONE_HOURS = 4;
    private static final int LOCAL_ZONE_MINUTES = 5;

    /**
     * Creates a new instance of ZDAParser.
     * 
     * @param nmea ZDA sentence String
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    public ZDAParser(String nmea) {
        super(nmea, SentenceId.ZDA);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.DateSentence#getDate()
     */
    public Date getDate() {
        // FIXME remove duplicated code; see other Date/Time parsers
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
     * @see net.sf.marineapi.nmea.sentence.ZDASentence#getLocalZoneHours()
     */
    public int getLocalZoneHours() {
        return getIntValue(LOCAL_ZONE_HOURS);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.ZDASentence#getLocalZoneMinutes()
     */
    public int getLocalZoneMinutes() {
        return getIntValue(LOCAL_ZONE_MINUTES);
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.DateSentence#getUtcDay()
     */
    public int getUtcDay() {
        return getIntValue(UTC_DAY);
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.DateSentence#getUtcMonth()
     */
    public int getUtcMonth() {
        return getIntValue(UTC_MONTH);
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.DateSentence#getUtcYear()
     */
    public int getUtcYear() {
        return getIntValue(UTC_YEAR);
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

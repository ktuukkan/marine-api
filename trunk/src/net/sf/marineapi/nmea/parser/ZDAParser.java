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

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.ZDASentence;
import net.sf.marineapi.nmea.util.Date;
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
    private static final int DAY = 1;
    private static final int MONTH = 2;
    private static final int YEAR = 3;
    private static final int LOCAL_ZONE_HOURS = 4;
    private static final int LOCAL_ZONE_MINUTES = 5;

    /**
     * Creates WPL parser with empty sentence.
     * @param talker TalkerId to set
     */
    public ZDAParser(TalkerId talker) {
        super(talker, SentenceId.ZDA, 6);
    }

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
        int y = getIntValue(YEAR);
        int m = getIntValue(MONTH);
        int d = getIntValue(DAY);
        return new Date(y, m, d);
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
     * net.sf.marineapi.nmea.sentence.DateSentence#setDate(net.sf.marineapi.
     * nmea.util.Date)
     */
    public void setDate(Date date) {
        setIntValue(YEAR, date.getYear());
        setIntValue(MONTH, date.getMonth(), 2);
        setIntValue(DAY, date.getDay(), 2);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.ZDASentence#setLocalZoneHours(int)
     */
    public void setLocalZoneHours(int hours) {
        if (hours < -13 || hours > 13) {
            throw new IllegalArgumentException(
                    "Value must be within range -13..13");
        }
        setIntValue(LOCAL_ZONE_HOURS, hours, 2);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.ZDASentence#setLocalZoneMinutes()
     */
    public void setLocalZoneMinutes(int minutes) {
        if (minutes < -59 || minutes > 59) {
            throw new IllegalArgumentException(
                    "Value must be within range -59..59");
        }
        setIntValue(LOCAL_ZONE_MINUTES, minutes, 2);
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

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.ZDASentence#toDate()
     */
    public java.util.Date toDate() {
        Date d = getDate();
        Time t = getTime();

        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, d.getYear());
        cal.set(Calendar.MONTH, d.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, d.getDay());
        cal.set(Calendar.HOUR_OF_DAY, t.getHour());
        cal.set(Calendar.MINUTE, t.getMinutes());
        cal.set(Calendar.SECOND, (int) Math.floor(t.getSeconds()));
		cal.set(Calendar.MILLISECOND, 0); // precision is 1s

		return cal.getTime();
    }
}

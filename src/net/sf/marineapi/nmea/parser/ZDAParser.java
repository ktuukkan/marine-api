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

import net.sf.marineapi.nmea.sentence.ZDASentence;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * ZDA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class ZDAParser extends SentenceParser implements ZDASentence {

    // field indices
    private static final int UTC_TIME = 1;
    private static final int UTC_DAY = 2;
    private static final int UTC_MONTH = 3;
    private static final int UTC_YEAR = 4;
    private static final int LOCAL_ZONE_HOURS = 5;
    private static final int LOCAL_ZONE_MINUTES = 6;

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
        // TODO refactor by removing duplicate code; see RMC parser
        int y = getUtcYear();
        int m = getUtcMonth() - 1;
        int d = getUtcDay();
        int h = getUtcHours();
        int mi = getUtcMinutes();
        int s = (int) Math.floor(getUtcSeconds());
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
     * @see fi.hut.automationit.nmea.parser.TimeSentence#getUtcHours()
     */
    public int getUtcHours() {
        return Integer.parseInt(getStringValue(UTC_TIME).substring(0, 2));
    }

    /*
     * (non-Javadoc)
     * @see fi.hut.automationit.nmea.parser.TimeSentence#getUtcMinutes()
     */
    public int getUtcMinutes() {
        return Integer.parseInt(getStringValue(UTC_TIME).substring(2, 4));
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
     * @see fi.hut.automationit.nmea.parser.TimeSentence#getUtcSeconds()
     */
    public double getUtcSeconds() {
        return Double.parseDouble(getStringValue(UTC_TIME).substring(4));
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
     * @see fi.hut.automationit.nmea.parser.DateSentence#getUtcYear()
     */
    public int getUtcYear() {
        return getIntValue(UTC_YEAR);
    }

}

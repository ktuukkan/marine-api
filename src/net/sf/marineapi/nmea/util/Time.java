/* 
 * Time.java
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
package net.sf.marineapi.nmea.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Represents a time of day in 24-hour clock, i.e. UTC time as used as default
 * in NMEA 0183.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class Time {

    // hour of day
    private int hour;
    // minute of hour
    private int minutes;
    // seconds of a minute, may include decimal sub-second in some sentences
    private double seconds;

    /**
     * Creates a new instance of Time.
     * 
     * @param hour Hour of day
     * @param min Minute of hour
     * @param sec Second of minute
     */
    public Time(int hour, int min, double sec) {
        setHour(hour);
        setMinutes(min);
        setSeconds(sec);
    }

    /**
     * Get the hour of day.
     * 
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Get the minute of hour.
     * 
     * @return the minute
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Get the second of minute.
     * 
     * @return the second
     */
    public double getSeconds() {
        return seconds;
    }

    /**
     * Set the hour of day.
     * 
     * @param hour the hour to set
     * @throws IllegalArgumentException If hour value out of bounds 0..23
     */
    public void setHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException(
                    "Valid hour value is between 0..23");
        }
        this.hour = hour;
    }

    /**
     * Set the minute of hour.
     * 
     * @param minutes the minute to set
     * @throws IllegalArgumentException If minutes value out of bounds 0..59
     */
    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException(
                    "Valid minutes value is between 0..59");
        }
        this.minutes = minutes;
    }

    /**
     * Set seconds of minute.
     * 
     * @param seconds the seconds to set
     * @throws IllegalArgumentException If seconds value out of bounds 0..59
     */
    public void setSeconds(double seconds) {
        if (seconds < 0 || seconds > 59.999) {
            throw new IllegalArgumentException(
                    "Invalid value for second (0..59)");
        }
        this.seconds = seconds;
    }

    /**
     * Set the time values from specified <code>java.util.Date</code>. The date
     * information of is ignored, only hours, minutes and seconds are relevant.
     * 
     * @param d Date
     */
    public void setTime(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        setHour(cal.get(Calendar.HOUR_OF_DAY));
        setMinutes(cal.get(Calendar.MINUTE));
        setSeconds(cal.get(Calendar.SECOND));
    }

    /**
     * Convert to <code>java.util.Date</code>.
     * 
     * @param d Date that defines year, month and day for time.
     * @return A Date that is combination of specified Date and Time
     */
    public Date toDate(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR, getHour());
        cal.set(Calendar.MINUTE, getMinutes());
        cal.set(Calendar.SECOND, (int) Math.floor(getSeconds()));
        return cal.getTime();
    }

    /**
     * Get time as milliseconds since beginning of day.
     * 
     * @return Milliseconds
     */
    public long getMilliseconds() {
        long m = (int) Math.floor(getSeconds()) * 1000;
        m += getMinutes() * 60 * 1000;
        m += getHour() * 3600 * 1000;
        return m;
    }
}

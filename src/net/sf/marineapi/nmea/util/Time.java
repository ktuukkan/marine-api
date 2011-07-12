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

import net.sf.marineapi.nmea.sentence.TimeSentence;

/**
 * Represents a time of day in 24-hour clock, i.e. the UTC time used as default
 * in NMEA 0183. Transmitted by {@link TimeSentence}.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.sentence.TimeSentence
 * @see net.sf.marineapi.nmea.util.Date
 */
public class Time {

    // hour of day
    private int hour = 0;
    // minute of hour
    private int minutes = 0;
    // seconds of a minute, may include decimal sub-second in some sentences
    private double seconds = 0.0;

    /**
     * Creates a new instance of <code>Time</code> using the current system
     * time.
     */
    public Time() {
        GregorianCalendar c = new GregorianCalendar();
        this.hour = c.get(Calendar.HOUR);
        this.minutes = c.get(Calendar.MINUTE);
        this.seconds = c.get(Calendar.SECOND);
    }

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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Time) {
            Time d = (Time) obj;
            if (d.getHour() == this.hour && d.getMinutes() == this.minutes
                    && d.getSeconds() == this.seconds) {
                return true;
            }
        }
        return false;
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
     * Get time as milliseconds since beginning of day.
     * 
     * @return Milliseconds
     */
    public long getMilliseconds() {
        long m = (long) Math.floor(getSeconds()) * 1000;
        m += getMinutes() * 60 * 1000;
        m += getHour() * 3600 * 1000;
        return m;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        String s = String.format("%02d%02d%02f", hour, minutes, seconds);
        return s.hashCode();
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
     * Set the time values from specified {@link java.util.Date}. The date
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
     * Convert to {@link java.util.Date}.
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String ptr = "%02d:%02d:%02.03f";
        return String.format(ptr, getHour(), getMinutes(), getSeconds());
    }
}

/* 
 * Date.java
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

/**
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class Date {

    /**
     * A pivot value that is used to determine century of the two-digit year
     * values. Two-digit values lower than or equal to pivot year are assigned
     * to 21th century, while values greater than pivot are assigned to 20th
     * century.
     */
    public static final int PIVOT_YEAR = 50;

    private int day;
    private int month;
    private int year;

    /**
     * Default constructor, initializes the date to January 1st, 1970
     * (1970-01-01).
     */
    public Date() {
        this.year = 1970;
        this.month = 1;
        this.day = 1;
    }

    /**
     * Constructor with date values.
     * 
     * @param year Year, two or four digit value [0..99] or [1000..]
     * @param month Month [1..12]
     * @param day Day [1..31]
     * @throws IllegalArgumentException If any of the parameter is out of
     *             bounds.
     */
    public Date(int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    /**
     * Get day of month.
     * 
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Set day of month.
     * 
     * @param day the day to set
     */
    public void setDay(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day out of bounds [1..31]");
        }
        this.day = day;
    }

    /**
     * Get month, valid values are 1-12 where 1 denotes January, 2 denotes
     * February etc.
     * 
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Get month, valid values are 1-12 where 1 denotes January, 2 denotes
     * February etc.
     * 
     * @param month the month to set
     * @throws IllegalArgumentException If specified value is out of bounds
     *             [1..12]
     */
    public void setMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(
                    "Month value out of bounds [1..12]");
        }
        this.month = month;
    }

    /**
     * Get year. The date fields in NMEA 0183 may present year by using either
     * two or four digits. In case of only two digits, the century is determined
     * by comparing the value against <code>#PIVOT_YEAR</code>. Values lower
     * than or equal to pivot are added to 2000, while values greater than pivot
     * are added to 1900.
     * 
     * @return The four-digit year
     * @see #PIVOT_YEAR
     */
    public int getYear() {
        return year;
    }

    /**
     * Set year. The date fields in NMEA 0183 may present year by using either
     * two or four digits. In case of only two digits, the century is determined
     * by comparing the value against <code>#PIVOT_YEAR</code>. Values lower
     * than or equal to pivot are added to 2000, while values greater than pivot
     * are added to 1900.
     * 
     * @param year Year to set, two or four digit value.
     * @throws IllegalArgumentException If specified value is negative or
     *             three-digit value.
     */
    public void setYear(int year) {
        if (year < 0 || (year > 99 && year < 1000) || year > 9999) {
            throw new IllegalArgumentException(
                    "Year must be two or four digit value");
        }
        if (year < 100 && year > PIVOT_YEAR) {
            this.year = 1900 + year;
        } else if (year < 100 && year <= PIVOT_YEAR) {
            this.year = 2000 + year;
        } else {
            this.year = year;
        }
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
        if (obj instanceof Date) {
            Date d = (Date) obj;
            if (d.getDay() == this.day && d.getMonth() == this.month
                    && d.getYear() == this.year) {
                return true;
            }
        }
        return false;
    }
}

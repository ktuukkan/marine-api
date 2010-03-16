/* 
 * DateSentence.java
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
package net.sf.marineapi.nmea.sentence;

import java.util.Date;

import net.sf.marineapi.nmea.parser.ParseException;

/**
 * Common interface for sentences that contain UTC date information. Notice that
 * some sentences contain only time without date information.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.sentence.TimeSentence
 */
public interface DateSentence extends Sentence {

    /**
     * Pivot year used to determine century for a two-digit year values.
     * Two-digit values lower than or equal to pivot year are assigned to 21th
     * century, while values greater than pivot are assigned to 20th century.
     */
    public static final int PIVOT_YEAR = 50;

    /**
     * Parses the date information in a Date object. Sub-second precision may be
     * provided by some sentences, but generally the precision is by 1 second.
     * 
     * @return Date
     * @throws ParseException If data invalid or not available
     */
    Date getDate();

    /**
     * Get day of UTC date.
     * 
     * @return integer
     * @throws ParseException If data invalid or not available
     */
    int getUtcDay();

    /**
     * Get month of UTC date.
     * 
     * @return integer
     * @throws ParseException If data invalid or not available
     */
    int getUtcMonth();

    /**
     * Get year of UTC date. The date fields in NMEA 0183 may contain two-digit
     * value for year. If this is the case, then the century is determined by
     * comparing the two-digit value against <code>PIVOT_YEAR</code>. Values
     * lower than or equal to pivot are added to 2000, while values greater than
     * pivot are added to 1900.
     * 
     * @return integer
     * @throws ParseException If data invalid or not available
     */
    int getUtcYear();
}

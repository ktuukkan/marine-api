/* 
 * TimeSentence.java
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

/**
 * Interface for sentences that provide a UTC time information. Notice that some
 * sentences may contain only UTC time, while others may provide also date.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.parser.DateSentence
 */
public interface TimeSentence {

    /**
     * Returns the hours of UTC time.
     * 
     * @return hours (0-23)
     * @throws ParseException If data invalid or not available
     */
    int getUtcHours();

    /**
     * Returns the minutes of UTC time.
     * 
     * @return minutes (0-59)
     * @throws ParseException If data invalid or not available
     */
    int getUtcMinutes();

    /**
     * Returns the seconds of UTC time. Some sentences may also provide the
     * fractional sub-second.
     * 
     * @return seconds (0-59)
     * @throws ParseException If data invalid or not available
     */
    double getUtcSeconds();

    /**
     * Get the time stamp (UTC time, formerly known as GMT). Time is returned as
     * String, for example "123456" stands for "12:34:56 UTC".
     * 
     * @return UTC String
     * @throws ParseException If data invalid or not available
     */
    String getUtcTime();
}

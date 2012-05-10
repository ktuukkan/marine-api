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

import net.sf.marineapi.nmea.util.Date;

/**
 * Sentences that contains date information. Notice that some sentences may
 * contain only time without the date.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.sentence.TimeSentence
 */
public interface DateSentence extends Sentence {

    /**
     * Parses the date information from sentence fields and returns a
     * {@link Date}.
     * 
     * @return Date object
     * @throws net.sf.marineapi.parser.DataNotAvailableException If the data is not available.
     * @throws net.sf.marineapi.parser.ParseException If the field contains unexpected or illegal value.
     */
    Date getDate();

    /**
     * Set date. Depending on the sentence type, the values may be inserted to
     * multiple fields or combined into one. Four-digit year value may also be
     * reduced into two-digit format.
     * 
     * @param date {@link Date}
     */
    void setDate(Date date);
}

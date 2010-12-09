/* 
 * GLLSentence.java
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

import net.sf.marineapi.nmea.parser.DataNotAvailableException;
import net.sf.marineapi.nmea.util.DataStatus;

/**
 * Current geographic position and time.
 * <p>
 * Example: <br>
 * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*26</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface GLLSentence extends PositionSentence, TimeSentence {

    /**
     * Data status; char indicator for "valid".
     */
    char STATUS_VALID = 'A';
    /**
     * Data status; char indicator for "invalid".
     */
    char STATUS_INVALID = 'V';

    /**
     * Get the data quality status, valid or invalid.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    DataStatus getStatus();

    /**
     * Set the data quality status, valid or invalid.
     * 
     * @param status DataStatus to set
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    void setStatus(DataStatus status);

}

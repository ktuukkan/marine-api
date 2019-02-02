/* 
 * GLLSentence.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
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

import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.FaaMode;

/**
 * Current geographic position and time.
 * <p>
 * Example: <br>
 * {@code $GPGLL,6011.552,N,02501.941,E,120045,A*26}
 * 
 * @author Kimmo Tuukkanen
 */
public interface GLLSentence extends PositionSentence, TimeSentence {

	/**
	 * Get the data quality status, valid or invalid.
	 * 
	 * @return {@link DataStatus#ACTIVE} or {@link DataStatus#VOID}
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	DataStatus getStatus();

	/**
	 * Set the data quality status, valid or invalid.
	 * 
	 * @param status DataStatus to set
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	void setStatus(DataStatus status);

	/**
	 * Get the FAA operating mode for GPS. Notice that this field is available in NMEA v3.0 and later.
	 *
	 * @return FaaMode enum
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	FaaMode getMode();

	/**
	 * Set the FAA operation mode of GPS. Notice that this field is available in NMEA v3.0 and later. Thus, the number of
	 * sentence fields may be adjusted when setting this value.
	 *
	 * @param mode FaaMode enum to set
	 */
	void setMode(FaaMode mode);

}

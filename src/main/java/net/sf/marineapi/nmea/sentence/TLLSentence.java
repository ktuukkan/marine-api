/*
 * TTMSentence.java
 * Copyright (C) 2020 Epameinondas Pantzopoulos
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

import net.sf.marineapi.nmea.util.TargetStatus;
import net.sf.marineapi.nmea.util.Time;

/**
 * Sent by the Radar (ARPA / MARPA) and handled by the AIS Decoder in the same way as an AIS target 
 * 
 * example ({@code $RATLL,01,3731.51052,N,02436.00000,E,TEST1,161617.88,T,*0C}
 * 
 * @author Epameinondas Pantzopoulos
 */
public interface TLLSentence extends PositionSentence{

	/**
	 * Get the number assigned to this target by the radar.
	 *
	 * @return Target number in the range 0 - 999.
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException
	 *             If the data is not available.
	 */
	int getNumber();
	
	/**
	 * Get the name of the target as assigned by the radar.
	 *
	 * @return Name.
	 */
	String getName();
	
	/**
	 * Get the time of day.
	 * 
	 * @return Time
	 */
	Time getTime();
	
	/**
	 * Get the status of the target. A target is first in state QUERY while the
	 * radar works out firm data of the target. At first the calculated course
	 * and speed are rough and varies a lot; with time they settle at which
	 * point the target becomes TRACKING. A target no longer detected becomes
	 * LOST before TTM sentences will not be sent at all for the target.
	 *
	 * @return The state (QUERY, TRACKING, LOST)
	 */
	TargetStatus getStatus();
	
	/**
	 * A target may be used to calculate own ship position.
	 *
	 * @return True if this target is used to calculate own ship position.
	 */
	boolean getReference();
	
	/**
	 * Set the number of the target. Uniquely identifies the target.
	 *
	 * @param number
	 *            The number in the range 0 to 99 inclusive.
	 */
	void setNumber(int number);
	
	/**
	 * Set the name of the target.
	 *
	 * @param name
	 *            The name as a string, probably not too long...
	 */
	void setName(String name);
	
	/**
	 * Set the time of day.
	 * 
	 * @param t Time to set
	 */
	void setTime(Time t);
	
	/**
	 * Set the Status of the target.
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getStatus()
	 * @param status
	 *            The status
	 */
	void setStatus(TargetStatus status);
	
	/**
	 * A target may be used to calculate own ship position.
	 *
	 * @param isReference
	 *            True if this target is used to calculate own ship position.
	 */
	void setReference(boolean isReference);
	
}

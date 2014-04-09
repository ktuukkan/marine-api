/*
 * XTESentence.java
 * Copyright (C) 2014 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://Kimmo Tuukkanenkkan.github.io/marine-api/>
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
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.FaaMode;

/**
 * Measured cross-track error when navigating towards waypoint.
 * 
 * @author Kimmo Tuukkanen
 */
public interface XTESentence extends Sentence {

	/**
	 * Returns the Loran-C cycle lock status, not used for GPS.
	 */
	DataStatus getCycleLockStatus();

	/**
	 * Returns the cross-track error magnitude/distance.
	 * 
	 * @return Cross-track error distance in nautical miles
	 */
	double getMagnitude();

	/**
	 * Returns the FAA mode. Optional, NMEA 2.3 and later.
	 * 
	 * @return FaaMode
	 */
	FaaMode getMode();

	/**
	 * Returns the signal/fix status.
	 * 
	 * @return DataStatus
	 */
	DataStatus getStatus();

	/**
	 * Returns the direction in which to steer in order to get back on route.
	 * 
	 * @return {@link Direction#LEFT} or {@link Direction#RIGHT}
	 */
	Direction getSteerTo();

	/**
	 * Sets the Loran-C cycle lock status. Not used for GPS, may be omitted or
	 * {@link DataStatus#VOID}.
	 * 
	 * @param status DataStatus to set
	 */
	void setCycleLockStatus(DataStatus status);

	/**
	 * Sets the cross-track error magnitude/distance.
	 * 
	 * @param distance Cross-track error distance in nautical miles
	 */
	void setMagnitude(double distance);

	/**
	 * Sets the FAA mode. Optional, NMEA 2.3 and later.
	 * 
	 * @param mode FaaMode to set
	 */
	void setMode(FaaMode mode);

	/**
	 * Sets the signal/fix status.
	 * 
	 * @param status DataStatus to set
	 */
	void setStatus(DataStatus status);

	/**
	 * Set direction in which to steer in order to get back on route.
	 * 
	 * @param direction {@link Direction#RIGHT} or {@link Direction#LEFT}
	 */
	void setSteerTo(Direction direction);
}

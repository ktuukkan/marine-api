/*
 * RSASentence.java
 * Copyright (C) 2014 Lázár József, Kimmo Tuukkanen
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
import net.sf.marineapi.nmea.util.Side;

/**
 * <p>
 * Rudder angle, measured in degrees. Negative value represents port side,
 * positive starboard side turn. May contain value for both port and starboard
 * rudder. {@link Side#PORT} is used for vessels with single rudder.
 * </p>
 * <p>
 * Example:<br>
 * <code>$IIRSA,9,A,,*38</code>
 * </p>
 * 
 * @author Lázár József, Kimmo Tuukkanen
 */
public interface RSASentence extends Sentence {

	/**
	 * Returns the rudder angle for specified side.
	 * 
	 * @return Rudder angle in degrees.
	 */
	double getRudderAngle(Side side);

	/**
	 * Sets the rudder's angle for specified side.
	 * 
	 * @param side Rudder side
	 * @param angle Rudder angle in degrees
	 */
	void setRudderAngle(Side side, double angle);

	/**
	 * Returns the data status (valid/invalid) for specified side.
	 * 
	 * @param side Rudder side
	 * @return Data status
	 */
	DataStatus getStatus(Side side);

	/**
	 * Set data status for specified side.
	 * @param side Rudder side
	 * @param status Data status to set
	 */
	void setStatus(Side side, DataStatus status);
}

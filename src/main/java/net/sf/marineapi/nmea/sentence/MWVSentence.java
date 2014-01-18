/* 
 * MWVSentence.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
import net.sf.marineapi.nmea.util.Units;

/**
 * <p>Wind speed and angle. Speed in km/h, m/s, or knots. Wind angle is given in
 * degrees relative to bow or true north.</p>
 * 
 * <p>Example:<br><code>$IIMWV,199,R,5.9,N,A*2E</code></p>
 * 
 * @author Kimmo Tuukkanen
 */
public interface MWVSentence extends Sentence {

	/**
	 * Get wind angle.
	 * 
	 * @return Wind angle in degrees.
	 */
	double getAngle();

	/**
	 * Returns the wind speed.
	 * 
	 * @return Wind speed value
	 */
	double getSpeed();

	/**
	 * Returns the wind speed unit.
	 * 
	 * @return {@link Units#METER} for meters per second, {@link Units#KMH} for
	 *         kilometers per hour and {@link Units#KNOT} for knots.
	 */
	Units getSpeedUnit();

	/**
	 * Get data validity status.
	 * 
	 * @return Data status
	 */
	DataStatus getStatus();

	/**
	 * Tells if the angle is relative or true.
	 * 
	 * @return True if relative to true north, otherwise false (relative to bow)
	 */
	boolean isTrue();

	/**
	 * Set wind angle.
	 * 
	 * @param angle Wind angle in degrees.
	 * @see #setTrue(boolean)
	 */
	void setAngle(double angle);

	/**
	 * Set the wind speed value.
	 * 
	 * @param speed Wind speed to set.
	 */
	void setSpeed(double speed);

	/**
	 * Set wind speed unit.
	 * 
	 * @param unit {@link Units#METER} for meters per second, {@link Units#KMH}
	 *            for kilometers per hour and {@link Units#KNOT} for knots.
	 * @throws IllegalArgumentException If trying to set invalid unit
	 */
	void setSpeedUnit(Units unit);

	/**
	 * Set data validity status.
	 * 
	 * @param status Data status to set.
	 */
	void setStatus(DataStatus status);

	/**
	 * Set angle to relative or true.
	 * 
	 * @param isTrue True for true angle, false for relative to bow.
	 * @see #setAngle(double)
	 */
	void setTrue(boolean isTrue);
}

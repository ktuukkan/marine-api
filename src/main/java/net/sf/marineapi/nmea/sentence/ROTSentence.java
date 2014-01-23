/*
 * ROTSentence.java
 * Copyright (C) 2014 Mike Tamis, Kimmo Tuukkanen
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

/**
 * <p>Vessel's rate of turn given in degrees per minute. Negative values
 * indicate bow turning to port.</p>
 * 
 * <p>Example:<br><code>$GPROT,35.6,A*4E</code></p>
 * 
 * @author Mike Tamis, Kimmo Tuukkanen
 */
public interface ROTSentence extends Sentence {

	/**
	 * Returns the vessel's rate of turn.
	 * 
	 * @return Rate of Turn value (degrees per minute)
	 */
	double getRateOfTurn();

	/**
	 * Sets the vessel's rate of turn value.
	 * 
	 * @param rot Rate of Turn value to set (degrees per minute)
	 */
	void setRateOfTurn(double rot);

	/**
	 * Returns the data status (valid/invalid).
	 * 
	 * @return True means data is valid
	 */
	DataStatus getStatus();

	/**
	 * Sets the data status.
	 * 
	 * @param status DataStatus to set.
	 */
	void setStatus(DataStatus status);
}

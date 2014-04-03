/*
 * RSASentence.java
 * Copyright (C) 2014 Lázár József
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

import net.sf.marineapi.nmea.util.Side;

/**
 * <p>Rudder angle, measured in degrees.</p>
 * 
 * <p>Example:<br><code>$IIRSA,9,A,,*38</code></p>
 * 
 * @author Lázár József
 */
public interface RSASentence extends Sentence {

	/**
	 * Returns the rudder's angle. Negative value represent port
	 * side, positiv startboard side turn.
	 * 
	 * @return rudder's angle (degrees)
	 */
	double getRudderAngle(Side side);

	/**
	 * Sets the rudder's angle. Negative value represent port
	 * side, positiv startboard side turn.
	 * 
	 * @param rot rudder's angle (degrees )
	 */
	void setRudderAngle(Side side, double angle);
}

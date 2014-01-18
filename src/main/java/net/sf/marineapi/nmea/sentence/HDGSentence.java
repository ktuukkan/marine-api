/* 
 * HDGSentence.java
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

/**
 * <p>Vessel heading with magnetic deviation and variation.</p>
 * 
 * <p>Example:<br><code>$HCHDG,205.2,,,2.7,W</code></p>
 * 
 * @author Kimmo Tuukkanen
 */
public interface HDGSentence extends HeadingSentence {

	/**
	 * Get magnetic deviation.
	 * 
	 * @return Deviation, in degrees.
	 */
	double getDeviation();

	/**
	 * Get magnetic variation. Returns negative values for easterly variation
	 * and positive for westerly.
	 * 
	 * @return Variation, in degrees.
	 */
	double getVariation();

	/**
	 * Set magnetic deviation. Provide negative values to set easterly deviation
	 * and positive to set westerly. Sets also the correct direction indicator
	 * according to value (East/West).
	 * 
	 * @param deviation Deviation, in degrees.
	 * @throws IllegalArgumentException If value is out of range [-180..180].
	 */
	void setDeviation(double deviation);

	/**
	 * Set magnetic variation. Provide negative values to set easterly variation
	 * and positive to set westerly. Sets also the correct direction indicator
	 * according to value (East/West).
	 * 
	 * @param variation Variation, in degrees.
	 * @throws IllegalArgumentException If value is out of range [-180..180].
	 */
	void setVariation(double variation);
}

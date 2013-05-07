/* 
 * MTWSentence.java
 * Copyright (C) 2011 Warren Zahra, Kimmo Tuukkanen
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
 * Water temperature in degrees Celsius.
 * 
 * @author Warren Zahra, Kimmo Tuukkanen
 */
public interface MTWSentence extends Sentence {

	/**
	 * Get the water temperature.
	 * 
	 * @return Temperature in degrees Celsius.
	 */
	double getTemperature();

	/**
	 * Set the water temperature.
	 * 
	 * @param temp Water temperature in degrees Celsius.
	 */
	void setTemperature(double temp);
}

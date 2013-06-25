/* 
 * Units.java
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
package net.sf.marineapi.nmea.util;

/**
 * Defines the supported units of measure.
 * 
 * @author Kimmo Tuukkanen
 */
public enum Units {

	/** Temperature in degrees Celsius (centigrade) */
	CELSIUS('C'),

	/** Depth in fathoms */
	FATHOMS('F'),

	/** Length in feet */
	FEET('f'),

	/** Speed in kilometers per hour */
	KMH('K'),

	/** Speed in knots (nautical miles per hour) */
	KNOT('N'),

	/** Length in meter */
	METER('M');

	private char ch;

	private Units(char c) {
		ch = c;
	}

	/**
	 * Returns the corresponding char constant.
	 * 
	 * @return Char indicator of enum
	 */
	public char toChar() {
		return ch;
	}

	/**
	 * Get the enum corresponding to specified char.
	 * 
	 * @param ch Char indicator for unit
	 * @return Units enum
	 */
	public static Units valueOf(char ch) {
		for (Units u : values()) {
			if (u.toChar() == ch) {
				return u;
			}
		}
		return valueOf(String.valueOf(ch));
	}
}

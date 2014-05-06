/*
 * AcquisitionType.java
 * Copyright (C) 2014 Johan Bergkvist
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
 * Aquisition types.
 *
 * @author Johan Bergkvist
 * @see net.sf.marineapi.nmea.sentence.TTMSentence
 */
public enum AcquisitionType {

	AUTO('A'),
	MANUAL('M'),
	REPORTED('R');

	private char ch;

	private AcquisitionType(char ch) {
		this.ch = ch;
	}

	/**
	 * Returns the corresponding char constant.
	 *
	 * @return Char indicator for AcquisitionType
	 */
	public char toChar() {
		return ch;
	}

	/**
	 * Get the enum corresponding to specified char.
	 *
	 * @param c Char indicator for AcquisitionType
	 * @return AcquisitionType
	 */
	public static AcquisitionType valueOf(char c) {
		for (AcquisitionType d : values()) {
			if (d.toChar() == c) {
				return d;
			}
		}
		return valueOf(String.valueOf(c));
	}
}

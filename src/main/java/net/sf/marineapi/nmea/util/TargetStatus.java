/*
 * TargetStatus.java
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
 * Defines the status of a target reported in a TTM sentence.
 *
 * @author Johan Bergkvist
 * @see net.sf.marineapi.nmea.sentence.TTMSentence
 */
public enum TargetStatus {

	QUERY('Q'),
	LOST('L'),
	TRACKING('T');

	private char ch;

	private TargetStatus(char ch) {
		this.ch = ch;
	}

	/**
	 * Returns the corresponding char constant.
	 *
	 * @return Char indicator for Status
	 */
	public char toChar() {
		return ch;
	}

	/**
	 * Get the enum corresponding to specified char.
	 *
	 * @param c Char indicator for Status
	 * @return Status
	 */
	public static TargetStatus valueOf(char c) {
		for (TargetStatus d : values()) {
			if (d.toChar() == c) {
				return d;
			}
		}
		return valueOf(String.valueOf(c));
	}
}

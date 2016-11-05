/*
 * MMSI.java
 * Copyright (C) 2015 Lázár József
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
package net.sf.marineapi.ais.util;

/**
 * Checks an MMSI value for validity.
 * 
 * @author Lázár József
 */
public class MMSI {

	private static final long MINVALUE = 0;
	private static final long MAXVALUE = 999999999;

	/**
	 * Checks whether the MMSI value is correct.
	 * @return true if the value is semantically correct.
	 */
	public static boolean isCorrect(long value) {
		return (MINVALUE <= value) && (value <= MAXVALUE);
	}

	/**
	 * Returns the origin associated with the MMSI number.
	 * @return a string describing the region of the transmitter
	 */
	public static String toString(long value) {
		int firstDigit = (int)(value / 100000000L); 
		switch (firstDigit) {
		case 0:
			return "Ship group, coast station, or group of coast stations";
		case 1:
			return "SAR aircraft";
		case 2:
			return "Europe";
		case 3:
			return "North and Central America and Caribbean";
		case 4:
			return "Asia";
		case 5:
			return "Oceana";
		case 6:
			return "Africa";
		case 7:
			return "South America";
		case 8:
			return "Assigned for regional Use";
		case 9:
			return "Nav aids or craft associated with a parent ship";
		default:	
			return "Invalid MMSI number";
		}
	}
}
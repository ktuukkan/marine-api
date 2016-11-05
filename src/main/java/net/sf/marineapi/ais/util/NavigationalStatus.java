/*
 * NavigationalStatus.java
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
 * Checks the navigational status for validity.
 * 
 * @author Lázár József
 */
public class NavigationalStatus {

	public static final String		RANGE = "[0,8] + [14,15]";
	final static private String[]	VALUES = {
		"under way using engine",				// 0
		"at anchor",							// 1
		"not under command",					// 2
		"restricted manoeuvrability",			// 3
		"constrained by her draught",			// 4
		"moored",								// 5
		"aground",								// 6
		"engaged in fishing",					// 7
		"under way sailing",					// 8
		"reserved for future amendment",		// 9
		"reserved for future amendment",		//10
		"reserved for future use",				//11
		"reserved for future use",				//12
		"reserved for future use",				//13
		"AIS-SART (active)",					//14
		"not defined"							//15
	};
	
	/**
	 * @return text string describing the navigational status
	 */
	public static String toString(int value) {
		if (value >= 0 && value <=15)
			return VALUES[value];
		else
			return VALUES[15];
	}
	
	/**
	 * @return true if the status falls within the range
	 */
	public static boolean isCorrect(int value) {
		return (0 <= value) && (value <= 15) &&
			   !((9 <= value) && (value <= 13));
	}
}

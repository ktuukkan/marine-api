/*
 * ManeuverIndicator.java
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
 * Checks a 2-bit signed integer maneuver value for validity.
 * 
 * @author Lázár József
 */
public class ManeuverIndicator {

	private static final int	DEFAULTVALUE	= 0;
	private static final int	MINVALUE		= 1 ;
	private static final int	MAXVALUE		= 2;
	public static final String	RANGE =
			"[" + MINVALUE + "," + MAXVALUE + "] + {" + DEFAULTVALUE + "}";

	/**
	 * Checks if the value is in the correct range.
	 * @return true if the value is correct
	 */
	public static boolean isCorrect(int value) {
		return (MINVALUE <= value && value <= MAXVALUE) || (value == DEFAULTVALUE);
	}
	
	/**
	 * Checks if the maneuver value is available.
	 * @return true if the value is not the default value
	 */
	public static boolean isAvailable(int value) {
		return value != DEFAULTVALUE;
	}
	
	/**
	 * @return a string representing the maneuvre indicator value
	 */
	public static String toString(int value) {
		switch (value) {
			case 0:
				return "no special maneuver indicator";
			case 1:
				return "not in special maneuver";
			case 2:
				return "in special maneuver";
			default:
				return "invalid special maneuver indicator";
		}
	}
}
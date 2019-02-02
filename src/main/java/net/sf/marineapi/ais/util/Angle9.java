/*
 * Angle9.java
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
 * Checks a 9-bit signed integer angular value for validity.
 * 
 * @author Lázár József
 */
public class Angle9 {

	private static final int	DEFAULTVALUE	= 511;
	private static final int	MINVALUE		= 0 ;
	private static final int	MAXVALUE		= 359;

	/** Valid range with default value for "no value" */
	public static final String	RANGE			=
			"[" + MINVALUE + "," + MAXVALUE + "] + {" + DEFAULTVALUE + "}";
	
	/**
	 * Tells if the given angular value is correct, i.e. within range of 0..359
	 * or equals the default value (511).
	 *
	 * @param value Angular value to validate
	 * @return {@code true} if correct, otherwise {@code false}.
	 */
	public static boolean isCorrect(int value) {
		return (MINVALUE <= value && value <= MAXVALUE) || (value == DEFAULTVALUE);
	}
	
	/**
	 * Checks if the angular value is available, i.e. equals not the default
	 * value (511).
	 *
	 * @param value Angular value to check
	 * @return {@code true} if available, otherwise {@code false}.
	 */
	public static boolean isAvailable(int value) {
		return value != DEFAULTVALUE;
	}

	/**
	 * Returns the String representation of given angular value.
	 *
	 * @param value Angular value to stringify
	 * @return Angular value as String or "no heading" or "invalid heading"
	 */
	public static String getTrueHeadingString(int value) {
		String headingString;
		if (value == DEFAULTVALUE)
			headingString = "no heading";
		else if (value > MAXVALUE)
			headingString = "invalid heading";
		else
			headingString = Integer.toString(value);
		return headingString;
	}

	/**
	 * Returns the String representation of given angular value.
	 *
	 * @param value Angular value to stringify
	 * @return Angular value as String or "not available" or "illegal value"
	 */
	public static String toString(int value) {
		String msg;
		if (isCorrect(value)) {
			if (isAvailable(value))
				msg = Integer.toString(value);
			else 
				msg = "not available";
		}
		else
			msg = "illegal value";
		return msg;
	}
}
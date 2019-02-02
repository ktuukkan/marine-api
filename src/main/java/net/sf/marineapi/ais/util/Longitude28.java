/*
 * Longitude28.java
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

import java.text.DecimalFormat;

/**
 * Checks a 28-bit signed integer longitude value for validity.
 * 
 * @author Lázár József
 */
public class Longitude28 {
	private static final DecimalFormat COORD_FORMAT = new DecimalFormat("##0.000000;-##0.000000");

	private static final int MINUTE_PART_MULTIPLIER = 60 * 10000;
	private static final int MIN_VALUE = -180 * MINUTE_PART_MULTIPLIER;
	private static final int MAX_VALUE = 180 * MINUTE_PART_MULTIPLIER;
	private static final int DEFAULT_VALUE = 181 * MINUTE_PART_MULTIPLIER;

	/** The range of valid longitude values with default for "no value". */
	public static final String RANGE = "[" + MIN_VALUE + "," + MAX_VALUE + "] + {" + DEFAULT_VALUE + "}";

	/**
	 * Converts the longitude value (in 1/10000 minutes) to degrees.
	 *
	 * @param value Int value to convert
	 * @return the longitude value in degrees
	 */
	public static double toDegrees(int value) {
		return (double)value / (double)MINUTE_PART_MULTIPLIER;
	}

	/**
	 * Tells if the given longitude is available, i.e. within expected range.
	 *
	 * @param value Longitude value to validate
	 * @return {@code true} if available, otherwise {@code false}.
	 */
	public static boolean isAvailable(int value) {
		return value >= MIN_VALUE && value <= MAX_VALUE;
	}

	/**
	 * Tells if the given value is correct, i.e. within expected range and not
	 * the no-value.
	 *
	 * @param value Longitude value to validate
	 * @return {@code true} if correct, otherwise {@code false}.
	 */
	public static boolean isCorrect(int value) {
		return isAvailable(value) || (value == DEFAULT_VALUE);
	}

	/**
	 * Returns the string representation of given longitude value.
	 *
	 * @param value Value to stringify
	 * @return formatted value, "invalid longitude" or "longitude not available"
	 */
	public static String toString(int value) {
		if (!isCorrect(value)) {
			return "invalid longitude";
		} else if (!isAvailable(value)) {
			return "longitude not available";
		} else {
			return COORD_FORMAT.format(toDegrees(value));
		}
	}
}
/*
 * Latitude17.java
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
 * Checks a 17-bit signed integer latitude value for validity.
 *
 */
public class Latitude17 {
	private static final DecimalFormat COORD_FORMAT = new DecimalFormat("##0.000000;-##0.000000");

	private static final int MINUTE_PART_MULTIPLIER = 60 * 10;
	private static final int MIN_VALUE = -90 * MINUTE_PART_MULTIPLIER;
	private static final int MAX_VALUE = 90 * MINUTE_PART_MULTIPLIER;
	private static final int DEFAULT_VALUE = 91 * MINUTE_PART_MULTIPLIER;

	/** Valid range with default value for "no value" */
	public static final String RANGE = "[" + MIN_VALUE + "," + MAX_VALUE + "] + {" + DEFAULT_VALUE + "}";

	/**
	 * Converts the latitude value (in 1/10000 minutes) to degrees.
	 *
	 * @param value Int value to convert
	 * @return The latitude value in degrees
	 */
	public static double toDegrees(int value) {
		return (double)value / (double)MINUTE_PART_MULTIPLIER;
	}

	/**
	 * Tells if the given latitude is available, i.e. within expected range.
	 *
	 * @param value Latitude value to validate
	 * @return {@code true} if available, otherwise {@code false}.
	 */
	public static boolean isAvailable(int value) {
		return value >= MIN_VALUE && value <= MAX_VALUE;
	}

	/**
	 * Tells if the given value is correct, i.e. within expected range and not
	 * the no-value.
	 *
	 * @param value Latitude value to validate
	 * @return {@code true} if correct, otherwise {@code false}.
	 */
	public static boolean isCorrect(int value) {
		return isAvailable(value) || (value == DEFAULT_VALUE);
	}

	/**
	 * Returns the String representation of given latitude value.
	 * @param value Value to stringify
	 * @return "invalid latitude", "latitude not available" or value formatted
	 * 			in degrees.
	 */
	public static String toString(int value) {
		if (!isCorrect(value)) {
			return "invalid latitude";
		} else if (!isAvailable(value)) {
			return "latitude not available";
		} else {
			return COORD_FORMAT.format(toDegrees(value));
		}
	}
}
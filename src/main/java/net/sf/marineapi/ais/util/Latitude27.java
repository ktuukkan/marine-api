/*
 * Latitude27.java
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
 * Checks a 27-bit signed integer latitude value for validity.
 * 
 * @author Lázár József
 */
public class Latitude27 {

	private static final double TO_DEGREES = 1.0 / (60.0 * 10000.0);

	/**
	 * Converts the latitude value (in 1/10000 minutes) to degrees.
	 * @return the latitude value in degrees
	 */
	public static double toDegrees(int value) {
		return value * TO_DEGREES; 
	}
}
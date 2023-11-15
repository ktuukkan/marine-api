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

    private static final String[] REGION_DESCRIPTIONS = {
        "Ship group, coast station, or group of coast stations",
        "SAR aircraft",
        "Europe",
        "North and Central America and Caribbean",
        "Asia",
        "Oceana",
        "Africa",
        "South America",
        "Assigned for regional Use",
        "Nav aids or craft associated with a parent ship",
        "Invalid MMSI number"
    };

    /**
     * Checks whether the MMSI value is correct, i.e. within valid range.
     *
     * @param value MMSI value to check
     * @return true if the value is semantically correct.
     */
    public static boolean isCorrect(long value) {
        return (MINVALUE <= value) && (value <= MAXVALUE);
    }

    /**
     * Returns the origin associated with the MMSI number.
     *
     * @param value MMSI value to stringify
     * @return A String describing the region of the transmitter
     */
    public static String toString(long value) {
        int firstDigit = (int) (value / 100000000L);
        if (0 <= firstDigit && firstDigit <= 9) {
            return REGION_DESCRIPTIONS[firstDigit];
        }
        return REGION_DESCRIPTIONS[10];
    }
}
/*
 * SpeedOverGround.java
 * Copyright (C) 2018 Jyrki Jakobsson
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
 * Checks a speed-over-ground value for validity.
 *
 * @author Jyrki Jakobsson
 */
public class SpeedOverGround {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 1022;
    private static final int DEFAULT_VALUE = 1023;

    public static boolean isAvailable(int value) {
        return value >= MIN_VALUE && value <= MAX_VALUE;
    }

    public static boolean isCorrect(int value) {
        return isAvailable(value) || (value == DEFAULT_VALUE);
    }

    public static double toKnots(int value) {
        return value / 10d;
    }

    public static String toString(int value) {
        if (!isAvailable(value))
            return "no SOG";
        else if (value == 1022)
            return ">=102.2";
        else
            return new DecimalFormat("##0.0").format(toKnots(value));
    }
}

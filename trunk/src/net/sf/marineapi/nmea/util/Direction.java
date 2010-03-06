/* 
 * Direction.java
 * Copyright 2010 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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
 * Defines the supported compass and relative directions.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public enum Direction {

    /** North */
    NORTH('N'),
    /** East */
    EAST('E'),
    /** South */
    SOUTH('S'),
    /** West */
    WEST('W'),
    /** Left */
    LEFT('L'),
    /** Right */
    RIGHT('R');

    private char ch;

    private Direction(char c) {
        this.ch = c;
    }

    /**
     * Get the enum corresponding to specified char.
     */
    public static Direction valueOf(char c) {
        for (Direction d : values()) {
            if (d.toChar() == c) {
                return d;
            }
        }
        return null;
    }

    /**
     * Returns the corresponding char constant.
     */
    private char toChar() {
        return ch;
    }
}

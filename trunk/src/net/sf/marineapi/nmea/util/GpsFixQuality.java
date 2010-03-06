/* 
 * GpsFixQuality.java
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
 * GpsFixQuality defines the supported fix quality types.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public enum GpsFixQuality {

    /** Normal GPS fix or standard position service (SPS) */
    NORMAL(1),

    /** Differential GPS fix. */
    DGPS(2),

    /** Precise Positioning Service fix. */
    PPS(3),

    /** No GPS fix acquired. */
    INVALID(0);

    private final int value;

    GpsFixQuality(int intValue) {
        value = intValue;
    }

    /**
     * Returns the corresponding int indicator for fix quality.
     * 
     * @return Fix quality indicator value as indicated in sentences.
     */
    public int toInt() {
        return value;
    }

    /**
     * Get GpsFixQuality enum that corresponds the actual integer identifier
     * used in the sentences.
     * 
     * @param val Status identifier value
     * @return GpsFixQuality enum
     */
    public static GpsFixQuality valueOf(int val) {
        for (GpsFixQuality gfq : values()) {
            if (gfq.toInt() == val) {
                return gfq;
            }
        }
        return null;
    }
}

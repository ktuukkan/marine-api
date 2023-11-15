/*
 * PositioningDevice.java
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
 * Provides functionality related to positioning devices.
 * 
 * @author Lázár József
 */
public class PositioningDevice {

    private static final String[] DEVICE_TYPES = {
        "undefined device",
        "GPS",
        "GLONASS",
        "combined GPS/GLONASS",
        "Loran-C",
        "Chayka",
        "integrated navigation system",
        "surveyed",
        "Galileo",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "internal GNSS",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used",
        "not used"
    };

    /**
     * Returns a text string for the EPFD.
     *
     * @param deviceType Device type value to stringify.
     * @return a text string describing the positioning device type
     */
    static public String toString(int deviceType) {
        if (isValidDeviceType(deviceType)) {
            return DEVICE_TYPES[deviceType];
        }
        return "not used";
    }

    /**
     * Checks whether the device type value is correct.
     *
     * @param deviceType Device type value to check.
     * @return true if the value is semantically correct.
     */
    static private boolean isValidDeviceType(int deviceType) {
        return 0 <= deviceType && deviceType < DEVICE_TYPES.length;
    }
}

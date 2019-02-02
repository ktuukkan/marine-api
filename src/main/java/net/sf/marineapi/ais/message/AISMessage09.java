/*
 * AISMessage09.java
 * Copyright (C) 2016 Henri Laurent
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
package net.sf.marineapi.ais.message;

/**
 * Standard SAR Aircraft Position Report
 *
 * Tracking information for search-and-rescue aircraft.
 *
 * Total number of bits is 168.
 *
 * @author Henri Laurent
 */
public interface AISMessage09 extends AISPositionInfo {

    /**
     * Returns the altitude of the aircraft. The special value 4095 indicates
     * altitude is not available; 4094 indicates 4094 meters or higher.
     *
     * @return Altitude, in meters.
     */
    int getAltitude();

    /**
     * Returns the speed over ground. Not deciknots as in the common navigation
     * block; planes go faster. The special value 1023 indicates speed not
     * available, 1022 indicates 1022 knots or higher.
     *
     * @return Speed over ground, in knots.
     */
    int getSpeedOverGround();

    /**
     * Returns the course over ground.
     *
     * @return Course over ground, in degrees.
     */
    double getCourseOverGround();

    /**
     * Returns the UTC second.
     *
     * @return An integer value representing the UTC second (0-59)
     */
    int getTimeStamp();

    /**
     * Data terminal ready (0 = available 1 = not available = default)
     *
     * @return {@code true} if available, otherwise false.
     */
    boolean getDTEFlag();

    /**
     * Returns the Assigned-mode flag
     *
     * @return {@code true} if assigned mode, otherwise {@code false}.
     */
    boolean getAssignedModeFlag();

    /**
     * Returns the RAIM flag.
     *
     * @return {@code true} if RAIM in use, otherwise {@code false}.
     */
    boolean getRAIMFlag();

    /**
     * Returns the Radio status.
     *
     * @return Radio status int
     */
    int getRadioStatus();
}

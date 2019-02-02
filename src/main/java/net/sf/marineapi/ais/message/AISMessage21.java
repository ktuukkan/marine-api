/*
 * AISMessage21.java
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
 * Aid-to-Navigation Report
 *
 * Identification and location message to be emitted by aids to navigation such as buoys and lighthouses.
 *
 * This message is unusual in that it varies in length depending on the presence and size of the Name Extension field.
 * May vary between 272 and 360 bits
 *
 * @author Henri Laurent
 */
public interface AISMessage21 extends AISPositionInfo {

    /**
     * Returns the Aid type for the current message.
     * @return Aid type
     */
    int getAidType();

    /**
     * Returns the name of the transmitting ship.
     * @return maximum 20 characters, representing the name
     */
    String getName();

    /**
     * Returns the distance from the reference point to the bow.
     *
     * @return Distance to bow, in meters.
     */
    int getBow();

    /**
     * Returns the distance from the reference point to the stern of the ship.
     *
     * @return Distance to stern, in meters.
     */
    int getStern();

    /**
     * Returns the distance from the reference point to the port side of the
     * ship.
     *
     * @return Distance to port side, in meters.
     */
    int getPort();

    /**
     * Returns the distance from the reference point to the starboard side of
     * the ship.
     *
     * @return Distance to starboard side, in meters.
     */
    int getStarboard();

    /**
     * Returns the type of electronic position fixing device.
     *
     * @return an integer value of the position device
     */
    int getTypeOfEPFD();

    /**
     * Returns the UTC second.
     *
     * @return an integer value representing the UTC second (0-59)
     */
    int getUtcSecond();

    /**
     * Returns the Off-position indicator: 0 means on position; 1 means off
     * position. Only valid if UTC second is equal to or below 59.
     *
     * @return {@code true} if off-position, otherwise {@code false}.
     */
    boolean getOffPositionIndicator();

    /**
     * Returns a Regional integer (reserved)
     *
     * @return an integer value
     */
    int getRegional();

    /**
     * Returns the RAIM flag
     *
     * @return {@code true} if RAIM in use, otherwise {@code false}.
     */
    boolean getRAIMFlag();

    /**
     * Returns the Virtual-aid flag
     *
     * @return {@code true} if virtual, otherwise {@code false}
     */
    boolean getVirtualAidFlag();

    /**
     * Returns the Assigned-mode flag
     *
     * @return {@code true} if assigned, otherwise {@code false}.
     */
    boolean getAssignedModeFlag();

    /**
     * Returns the name extension.
     *
     * @return maximum 14 characters, representing the name extension
     */
    String getNameExtension();
}

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
     */
    int getBow();

    /**
     * Returns the distance from the reference point to the stern of the ship.
     */
    int getStern();

    /**
     * Returns the distance from the reference point to the port side of the ship.
     */
    int getPort();

    /**
     * Returns the distance from the reference point to the starboard side of the ship.
     */
    int getStarboard();

    /**
     * Returns the type of electronic position fixing device.
     * @return an integer value of the position device
     */
    int getTypeOfEPFD();

    /**
     * Returns the UTC second.
     * @return an integer value representing the UTC second (0-59)
     */
    int getUtcSecond();

    /**
     * Returns the Off-position indicator.
     */
    boolean getOffPositionIndicator();

    /**
     * Returns a Regional integer
     * @return an integer value of the Regional reserved
     */
    int getRegional();

    /**
     * Returns the RAIM flag
     */
    boolean getRAIMFlag();

    /**
     * Returns the Virtual-aid flag
     */
    boolean getVirtualAidFlag();

    /**
     * Returns the Assigned-mode flag
     */
    boolean getAssignedModeFlag();

    /**
     * Returns the name extension
     * @return maximum 14 characters, representing the name extension
     */
    String getNameExtension();
}

/*
 * AISMessage24.java
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
 * Static Data Report
 *
 * Equivalent of a Type 5 message for ships using Class B equipment
 *
 * @author Henri Laurent
 */
public interface AISMessage24 extends AISMessage {

    /**
     * Returns the Part Number indicator for the current message.
     * @return Part Number indicator
     */
    int getPartNumber();

    /**
     * Returns the name of the transmitting ship.
     * @return maximum 20 characters, representing the name
     */
    String getName();

    /**
     * Returns the type of ship and cargo.
     * @return an integer value representing the type of ship and cargo
     */
    int getTypeOfShipAndCargoType();

    /**
     * Returns the Vendor id
     * @return maximum 3 characters, representing the vendor id
     */
    String getVendorId();

    /**
     * Returns the Unit Model code
     *
     * @return model code int
     */
    int getUnitModelCode();

    /**
     * Returns the Serial Number
     *
     * @return serial number int
     */
    int getSerialNumber();

    /**
     * Returns the call sign of the transmitting ship.
     *
     * @return at most 7 characters, representing the call sign
     */
    String getCallSign();

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
     * @return Distance to starborad side, in meters.
     */
    int getStarboard();

}

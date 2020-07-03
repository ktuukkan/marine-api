/*
 * Copyright (C) 2020 Gunnar Hillert
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
package net.sf.marineapi.ublox.util;

import net.sf.marineapi.ublox.message.UBXMessage03;

/**
 * Defines the satellite statuses defined by proprietary u-blox NMEA extension
 *
 * @author Gunnar Hillert
 *
 * @see UBXMessage03
 */
public enum UbloxSatelliteStatus {

	/**
	 *  Not used.
	 */
	NOT_USED('-'),

	/**
	 * Used in solution.
	 */
	USED_IN_SOLUTION('U'),

	/**
	 * Ephemeris available, but not used for navigation.
	 */
	NOT_USED_EPHEMERIS_AVAILABLE('e');


	private char statusFlag;

	private UbloxSatelliteStatus(char statusFlag) {
		this.statusFlag = statusFlag;
	}

	public char getStatusFlag() {
		return statusFlag;
	}

	public static UbloxSatelliteStatus fromStatusFlag(char statusFlag) {

		for (UbloxSatelliteStatus ubloxSatelliteStatusType : UbloxSatelliteStatus.values()) {
			if (ubloxSatelliteStatusType.getStatusFlag() == statusFlag) {
				return ubloxSatelliteStatusType;
			}
		}

		return null;
	}
}

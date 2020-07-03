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
package net.sf.marineapi.ublox.message;

import java.util.List;

import net.sf.marineapi.ublox.util.UbloxSatelliteInfo;

/**
 * Proprietary u-blox NMEA extension "Satellite status".
 *
 * @author Gunnar Hillert
 *
 */
public interface UBXMessage03 extends UBXMessage {

	/**
	 * Number of GNSS satellites tracked.
	 *
	 * @return Number of satellites tracked
	 */
	int getNumberOfTrackedSatellites();

	/**
	 * Returns a collection of satellite statuses.
	 *
	 * @see UbloxSatelliteInfo
	 * @return Collection of UbloxSatelliteInfo
	 */
	List<UbloxSatelliteInfo> getSatellites();

}

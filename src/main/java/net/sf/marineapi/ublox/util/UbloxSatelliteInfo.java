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

import net.sf.marineapi.nmea.util.SatelliteInfo;
import net.sf.marineapi.ublox.message.UBXMessage03;

/**
 * Extends {@link SatelliteInfo} to provide additional properties supported by
 * the proprietary u-blox NMEA extension {@link UBXMessage03}.
 *
 * @author Gunnar Hillert
 *
 */
public class UbloxSatelliteInfo extends SatelliteInfo {

	private final UbloxSatelliteStatus satelliteStatus;

	private final int satelliteCarrierLockTime;

	public UbloxSatelliteInfo(String id, int elevation, int azimuth, int noise,
			UbloxSatelliteStatus satelliteStatus, int satelliteCarrierLockTime) {
		super(id, elevation, azimuth, noise);
		this.satelliteStatus = satelliteStatus;
		this.satelliteCarrierLockTime = satelliteCarrierLockTime;
	}

	/**
	 * Satellite carrier lock time (range: 0-64)
	 *
	 * <ul>
	 *   <li> 0 = code lock only
	 *   <li>64 = lock for 64 seconds or more
	 * </ul>
	 *
	 * @return Numeric value 0-64
	 */
	public int getSatelliteCarrierLockTime() {
		return satelliteCarrierLockTime;
	}

	/**
	 * @return The {@link UbloxSatelliteStatus}.
	 */
	public UbloxSatelliteStatus getSatelliteStatus() {
		return satelliteStatus;
	}

	@Override
	public String toString() {
		return super.toString() + " " +
			String.format("UbloxSatelliteInfo [satelliteStatus=%s, satelliteCarrierLockTime=%s sec]", satelliteStatus,
				satelliteCarrierLockTime);
	}

}

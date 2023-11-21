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

/**
 *
 * @author Gunnar Hillert
 *
 */
public enum UbloxNavigationStatus {

	/**
	 * No Fix.
	 */
	NO_FIX("NF"),

	/**
	 * Dead reckoning only solution.
	 */
	DEAD_RECKONING_ONLY ("DR"),

	/**
	 * Stand alone 2D solution.
	 */
	STAND_ALONE_2D("G2"),

	/**
	 * Stand alone 3D solution.
	 */
	STAND_ALONE_3D("G3"),

	/**
	 * Differential 2D solution.
	 */
	DIFFERENTIAL_2D_SOLUTION("D2"),

	/**
	 * Differential 3D solution.
	 */
	DIFFERENTIAL_3D_SOLUTION("D3"),

	/**
	 * Combined GPS + dead reckoning solution.
	 */
	COMBINED_GPS_AND_DEAD_RECKONING ("RK"),

	/**
	 * Time only solution.
	 */
	TIME_ONLY("TT");


	private String navigationStatusCode;

	private UbloxNavigationStatus(String navigationStatusCode) {
		this.navigationStatusCode = navigationStatusCode;
	}

	public String getNavigationStatusCode() {
		return navigationStatusCode;
	}

	public static UbloxNavigationStatus fromNavigationStatusCode(String navigationStatusCode) {

		for (UbloxNavigationStatus ubloxNavigationStatus : UbloxNavigationStatus.values()) {
			if (ubloxNavigationStatus.getNavigationStatusCode().equals(navigationStatusCode)) {
				return ubloxNavigationStatus;
			}
		}

		return null;
	}
}

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

import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.ublox.util.UbloxNavigationStatus;

/**
 * Proprietary u-blox NMEA extension "Lat/Long position data".
 *
 * @author Gunnar Hillert
 *
 */
public interface UBXMessage00 extends UBXMessage {

	/**
	 * @return UTC  time
	 */
	Time getUtcTime();

	/**
	 * Holds all positional data:
	 *
	 * <ul>
	 *   <li>Latitude (degrees and minutes)
	 *   <li>North/South Indicator
	 *   <li>Longitude (degrees and minutes)
	 *   <li>East/West indicator
	 *   <li>Altitude above user datum ellipsoid
	 * </ul>
	 *
	 * @return Position
	 */
	Position getPosition();

	/**
	 * Navigation Status:
	 *
	 * •NF = No Fix
	 * •DR = Dead reckoning only solution
	 * •G2 = Stand alone 2D solution
	 * •G3 = Stand alone 3D solution
	 * •D2 = Differential 2D solution
	 * •D3 = Differential 3D solution
	 * •RK = Combined GPS + dead reckoning solution
	 * •TT = Time only solution
	 *
	 * @return UbloxNavigationStatus
	 */
	UbloxNavigationStatus getNavigationStatus();

	/**
	 * @return Horizontal accuracy estimate
	 */
	double getHorizontalAccuracyEstimate();

	/**
	 * @return Vertical accuracy estimate
	 */
	double getVerticaAccuracyEstimate();

	/**
	 * @return Speed over ground
	 */
	double getSpeedOverGround();

	/**
	 * @return Course over ground
	 */
	double getCourseOverGround();

	/**
	 * @return Vertical velocity (positive downwards)
	 */
	double getVerticaVelocity();

	/**
	 * @return Age of differential corrections (blank when DGPS is notused)
	 */
	int getAgeOfDifferentialCorrections();

	/**
	 * @return HDOP, Horizontal Dilution of Precision
	 */
	double getHDOP();

	/**
	 * @return VDOP, Vertical Dilution of Precision
	 */
	double getVDOP();

	/**
	 * @return TDOP, Time Dilution of Precision
	 */
	double getTDOP();

	/**
	 * @return Number of satellites used in the navigation solution.
	 */
	int getNumberOfSatellitesUsed();

}

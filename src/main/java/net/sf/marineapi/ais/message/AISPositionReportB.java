/*
 * AISPositionReportB.java
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
package net.sf.marineapi.ais.message;

/**
 * Common interface for all Class B messages providing position reports.
 * 
 * @author Lázár József
 */
public interface AISPositionReportB extends AISPositionInfo {

	/**
	 * Returns the speed over ground.
	 *
	 * @return Speed over ground
	 */
	double getSpeedOverGround();

	/**
	 * Returns the course over ground.
	 *
	 * @return Course over ground
	 */
	double getCourseOverGround();

	/**
	 * Returns the true heading.
	 *
	 * @return true heading value, in degrees
	 */
	int getTrueHeading();

	/**
	 * Returns the message time stamp. UTC second when the report was generated
	 * by the EPFS (0-59). 60 if time stamp is not available, which should also
	 * be the default value. 61 if positioning system is in manual input mode.
	 * 62 if electronic position fixing system operates in estimated (dead
	 * reckoning) mode. 63 if the positioning system is inoperative.
	 *
	 * @return Timestamp value
	 */
	int getTimeStamp();

	/**
	 * Returns true if speed over ground is available in the message.
	 *
	 * @return {@code true} if has SOG, otherwise {@code false}.
	 */
	boolean hasSpeedOverGround();

	/**
	 * Returns true if course over ground is available in the message.
	 *
	 * @return {@code true} if has COG, otherwise {@code false}.
	 */
	boolean hasCourseOverGround();

	/**
	 * Returns true if true heading is available in the message.
	 *
	 * @return {@code true} if has heading, otherwise {@code false}.
	 */
	boolean hasTrueHeading();

	/**
	 * Returns true if timestamp is available in the message.
	 *
	 * @return {@code true} if has timestamp, otherwise {@code false}.
	 */
	boolean hasTimeStamp();
}

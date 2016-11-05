/* 
 * FaaMode.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.util;

/**
 * <p>FAA operating modes reported by APB, BWC, BWR, GLL, RMA, RMB, RMC, VTG,
 * WCV and XTE sentences since NMEA 2.3. Also, the mode field in GGA was
 * extended to contain these statuses.</p>
 * <p>
 * Notice that FAA mode dominates the {@link DataStatus} fields. Status field
 * will be set to {@link DataStatus#ACTIVE} for modes {@link #AUTOMATIC} and
 * {@link #DGPS}, and {@link DataStatus#VOID} for all other modes.</p>
 * 
 * @author Kimmo Tuukkanen
 * @see GpsFixQuality
 * @see GpsFixStatus
 * @see DataStatus
 */
public enum FaaMode {

	/** Operating in autonomous mode (automatic 2D/3D). */
	AUTOMATIC('A'),

	/** Operating in manual mode (forced 2D or 3D). */
	MANUAL('M'),

	/** Operating in differential mode (DGPS). */
	DGPS('D'),

	/** Operating in estimating mode (dead-reckoning). */
	ESTIMATED('E'),

	/** Operating in precise mode, no degradation like Selective Availability (NMEA 4.00 and later). */
	PRECISE('P'),

	/** Simulated data (running in simulator/demo mode) */
	SIMULATED('S'),

	/** No valid GPS data available. */
	NONE('N');

	private final char mode;

	FaaMode(char modeCh) {
		mode = modeCh;
	}

	/**
	 * Returns the corresponding char indicator of GPS mode.
	 * 
	 * @return Mode char used in sentences.
	 */
	public char toChar() {
		return mode;
	}

	/**
	 * Returns the FaaMode enum corresponding the actual char indicator used in
	 * the sentencess.
	 * 
	 * @param ch Char mode indicator
	 * @return FaaMode enum
	 */
	public static FaaMode valueOf(char ch) {
		for (FaaMode gm : values()) {
			if (gm.toChar() == ch) {
				return gm;
			}
		}
		return valueOf(String.valueOf(ch));
	}
}

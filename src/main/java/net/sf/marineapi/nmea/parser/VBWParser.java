/*
 * VBWParser.java
 * Copyright (C) 2015 ESRG LLC.
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VBWSentence;
import net.sf.marineapi.nmea.util.DataStatus;

/**
 * VBW sentence parser.
 * 
 * @author Jeremy Wilson
 */
class VBWParser extends SentenceParser implements VBWSentence {

	public static final int LONG_WATERSPEED = 0;
	public static final int TRAV_WATERSPEED = 1;
	public static final int WATER_SPEED_STATUS = 2;
	public static final int LONG_GROUNDSPEED = 3;
	public static final int TRAV_GROUNDSPEED = 4;
	public static final int GROUND_SPEED_STATUS = 5;
	public static final int STERN_WATERSPEED = 6;
	public static final int STERN_SPEED_STATUS = 7;
	public static final int STERN_GROUNDSPEED = 8;
	public static final int STERN_GROUNDSPEED_STATUS = 9;

	/**
	 * Create a new instance of VBWParser.
	 * 
	 * @param nmea VBW sentence String.
	 * @throws IllegalArgumentException If specified sentence is invalid.
	 */
	public VBWParser(String nmea) {
		super(nmea, SentenceId.VBW);
	}

	/**
	 * Create a VBW parser with an empty sentence.
	 * 
	 * @param talker TalkerId to set
	 */
	public VBWParser(TalkerId talker) {
		super(talker, SentenceId.VBW, 10);
	}

	public double getLongWaterSpeed() {
		return getDoubleValue(LONG_WATERSPEED);
	}

	public DataStatus getWaterSpeedStatus() {
		return DataStatus.valueOf(getCharValue(WATER_SPEED_STATUS));
	}

	public DataStatus getGroundSpeedStatus() {
		return DataStatus.valueOf(getCharValue(GROUND_SPEED_STATUS));
	}

	public double getLongGroundSpeed() {
		return getDoubleValue(LONG_GROUNDSPEED);
	}

	public double getTravWaterSpeed() {
		return getDoubleValue(TRAV_WATERSPEED);
	}

	public double getTravGroundSpeed() {
		return getDoubleValue(TRAV_GROUNDSPEED);
	}

	public double getSternWaterSpeed() {
		return getDoubleValue(STERN_WATERSPEED);
	}

	public DataStatus getSternWaterSpeedStatus() {
		return DataStatus.valueOf(getCharValue(STERN_SPEED_STATUS));
	}

	public double getSternGroundSpeed() {
		return getDoubleValue(STERN_GROUNDSPEED);
	}

	public DataStatus getSternGroundSpeedStatus() {
		return DataStatus.valueOf(getCharValue(STERN_GROUNDSPEED_STATUS));
	}

	public void setLongWaterSpeed(double speed) {
		setDoubleValue(LONG_WATERSPEED, speed, 2, 1);
	}

	public void setLongGroundSpeed(double speed) {
		setDoubleValue(LONG_GROUNDSPEED, speed, 2, 1);
	}

	public void setTravWaterSpeed(double speed) {
		setDoubleValue(TRAV_WATERSPEED, speed, 2, 1);
	}

	public void setTravGroundSpeed(double speed) {
		setDoubleValue(TRAV_GROUNDSPEED, speed, 2, 1);
	}

	public void setWaterSpeedStatus(DataStatus status) {
		setCharValue(WATER_SPEED_STATUS, status.toChar());
	}

	public void setGroundSpeedStatus(DataStatus status) {
		setCharValue(GROUND_SPEED_STATUS, status.toChar());
	}

	public void setSternWaterSpeed(double speed) {
		setDoubleValue(STERN_WATERSPEED, speed, 2, 1);
	}

	public void setSternWaterSpeedStatus(DataStatus status) {
		setCharValue(STERN_SPEED_STATUS, status.toChar());
	}

	public void setSternGroundSpeed(double speed) {
		setDoubleValue(STERN_GROUNDSPEED, speed, 2, 1);
	}

	public void setSternGroundSpeedStatus(DataStatus status) {
		setCharValue(STERN_GROUNDSPEED_STATUS, status.toChar());
	}
}

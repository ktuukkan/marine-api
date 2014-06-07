/*
 * APBParser.java
 * Copyright (C) 2014 ktuu
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

import net.sf.marineapi.nmea.sentence.APBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;

/**
 * @author ktuu
 * 
 */
class APBParser extends SentenceParser implements APBSentence {

	private static final int SIGNAL_STATUS = 0;
	private static final int CYCLE_LOCK_STATUS = 1;
	private static final int XTE_DISTANCE = 2;
	private static final int XTE_STEER_TO = 3;
	private static final int XTE_UNITS = 4;
	private static final int CIRCLE_STATUS = 5;
	private static final int PERPENDICULAR_STATUS = 6;
	private static final int BEARING_ORIGIN_DEST = 7;
	private static final int BEARING_ORIGIN_DEST_TYPE = 8;
	private static final int DEST_WAYPOINT_ID = 9;
	private static final int BEARING_POS_DEST = 10;
	private static final int BEARING_POS_DEST_TYPE = 11;
	private static final int HEADING_TO_DEST = 12;
	private static final int HEADING_TO_DEST_TYPE = 13;

	/**
	 * Creates a new instance of APBParser.
	 * 
	 * @param nmea NMEA sentence String.
	 */
	public APBParser(String nmea) {
		super(nmea);
	}

	/**
	 * Creates a new empty APBParser.
	 * 
	 * @param talker TalkerId to set
	 */
	public APBParser(TalkerId talker) {
		super(talker, "APB", 14);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getBearginPositionToDestination
	 * ()
	 */
	@Override
	public double getBearingPositionToDestination() {
		return getDoubleValue(BEARING_POS_DEST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getBearingOriginToDestination
	 * ()
	 */
	@Override
	public double getBearingOriginToDestination() {
		return getDoubleValue(BEARING_ORIGIN_DEST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getCrossTrackError()
	 */
	@Override
	public double getCrossTrackError() {
		return getDoubleValue(XTE_DISTANCE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getCrossTrackUnits()
	 */
	@Override
	public char getCrossTrackUnits() {
		return getCharValue(XTE_UNITS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getCycleLockStatus()
	 */
	@Override
	public DataStatus getCycleLockStatus() {
		return DataStatus.valueOf(getCharValue(CYCLE_LOCK_STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getDestionationWaypointId()
	 */
	@Override
	public String getDestionationWaypointId() {
		return getStringValue(DEST_WAYPOINT_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#getHeadingToDestionation()
	 */
	@Override
	public double getHeadingToDestionation() {
		return getDoubleValue(HEADING_TO_DEST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getStatus()
	 */
	@Override
	public DataStatus getStatus() {
		return DataStatus.valueOf(getCharValue(SIGNAL_STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#getSteerTo()
	 */
	@Override
	public Direction getSteerTo() {
		return Direction.valueOf(getCharValue(XTE_STEER_TO));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#isArrivalCircleEntered()
	 */
	@Override
	public boolean isArrivalCircleEntered() {
		return getCharValue(CIRCLE_STATUS) == 'A';
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#isBearingOriginToDestionationTrue
	 * ()
	 */
	@Override
	public boolean isBearingOriginToDestionationTrue() {
		return getCharValue(BEARING_ORIGIN_DEST_TYPE) == 'T';
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#isBearingPositionToDestinationTrue
	 * ()
	 */
	@Override
	public boolean isBearingPositionToDestinationTrue() {
		return getCharValue(BEARING_POS_DEST_TYPE) == 'T';

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#isHeadingTrue()
	 */
	@Override
	public boolean isHeadingToDestinationTrue() {
		return getCharValue(HEADING_TO_DEST_TYPE) == 'T';
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#isPerpendicularPassed()
	 */
	@Override
	public boolean isPerpendicularPassed() {
		return getCharValue(PERPENDICULAR_STATUS) == 'A';
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setArrivalCircleEntered(boolean
	 * )
	 */
	@Override
	public void setArrivalCircleEntered(boolean isEntered) {
		DataStatus s = isEntered ? DataStatus.ACTIVE : DataStatus.VOID;
		setCharValue(CIRCLE_STATUS, s.toChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setBearingOriginToDestination
	 * (double)
	 */
	@Override
	public void setBearingOriginToDestination(double bearing) {
		setDegreesValue(BEARING_ORIGIN_DEST, bearing);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setBearingOriginToDestionationTrue
	 * (boolean)
	 */
	@Override
	public void setBearingOriginToDestionationTrue(boolean isTrue) {
		char c = isTrue ? 'T' : 'M';
		setCharValue(BEARING_ORIGIN_DEST_TYPE, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setBearingPositionToDestination
	 * (double)
	 */
	@Override
	public void setBearingPositionToDestination(double bearing) {
		setDegreesValue(BEARING_POS_DEST, bearing);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#
	 * setBearingPositionToDestinationTrue(boolean)
	 */
	@Override
	public void setBearingPositionToDestinationTrue(boolean isTrue) {
		char c = isTrue ? 'T' : 'M';
		setCharValue(BEARING_POS_DEST_TYPE, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setCrossTrackError(double)
	 */
	@Override
	public void setCrossTrackError(double distance) {
		setDoubleValue(XTE_DISTANCE, distance, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.APBSentence#setCrossTrackUnits(char)
	 */
	@Override
	public void setCrossTrackUnits(char unit) {
		if (unit != APBSentence.KM && unit != APBSentence.NM) {
			throw new IllegalAccessError(
				"Invalid distance unit char, expected 'K' or 'N'");
		}
		setCharValue(XTE_UNITS, unit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setCycleLockStatus(net.sf.
	 * marineapi.nmea.util.DataStatus)
	 */
	@Override
	public void setCycleLockStatus(DataStatus status) {
		setCharValue(CYCLE_LOCK_STATUS, status.toChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setDestinationWaypointId(java
	 * .lang.String)
	 */
	@Override
	public void setDestinationWaypointId(String id) {
		setStringValue(DEST_WAYPOINT_ID, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setHeadingToDestination(double
	 * )
	 */
	@Override
	public void setHeadingToDestination(double heading) {
		setDoubleValue(HEADING_TO_DEST, heading);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setHeadingToDestinationTrue
	 * (boolean)
	 */
	@Override
	public void setHeadingToDestinationTrue(boolean isTrue) {
		char c = isTrue ? 'T' : 'M';
		setCharValue(HEADING_TO_DEST_TYPE, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setPerpendicularPassed(boolean
	 * )
	 */
	@Override
	public void setPerpendicularPassed(boolean isPassed) {
		DataStatus s = isPassed ? DataStatus.ACTIVE : DataStatus.VOID;
		setCharValue(PERPENDICULAR_STATUS, s.toChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
	@Override
	public void setStatus(DataStatus status) {
		setCharValue(SIGNAL_STATUS, status.toChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.APBSentence#setSteerTo(net.sf.marineapi
	 * .nmea.util.Direction)
	 */
	@Override
	public void setSteerTo(Direction direction) {
		setCharValue(XTE_STEER_TO, direction.toChar());
	}

}

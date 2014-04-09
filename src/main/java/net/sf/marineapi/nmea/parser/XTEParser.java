/*
 * XTEParser.java
 * Copyright (C) 2014 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://Kimmo Tuukkanenkkan.github.io/marine-api/>
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
import net.sf.marineapi.nmea.sentence.XTESentence;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.FaaMode;

/**
 * XTE sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
class XTEParser extends SentenceParser implements XTESentence {

	private final static int SIGNAL_STATUS = 0;
	private final static int CYCLE_LOCK_STATUS = 1;
	private final static int DISTANCE = 2;
	private final static int DIRECTION = 3;
	private final static int DISTANCE_UNIT = 4;
	private final static int FAA_MODE = 5;

	/**
	 * Creates new instance of XTEParser.
	 * 
	 * @param nmea XTE sentence String
	 */
	public XTEParser(String nmea) {
		super(nmea);
		setFieldCount(6);
	}

	public XTEParser(TalkerId talker) {
		super(talker, SentenceId.XTE, 6);
		setMode(FaaMode.NONE);
		setStatus(DataStatus.VOID);
		setCycleLockStatus(DataStatus.VOID);
		setCharValue(DISTANCE_UNIT, 'N');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getCycleLockStatus()
	 */
	@Override
	public DataStatus getCycleLockStatus() {
		return DataStatus.valueOf(getCharValue(CYCLE_LOCK_STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getMagnitude()
	 */
	@Override
	public double getMagnitude() {
		return getDoubleValue(DISTANCE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getMode()
	 */
	@Override
	public FaaMode getMode() {
		return FaaMode.valueOf(getCharValue(FAA_MODE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getStatus()
	 */
	@Override
	public DataStatus getStatus() {
		return DataStatus.valueOf(getCharValue(SIGNAL_STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#getSteerTo()
	 */
	@Override
	public Direction getSteerTo() {
		return Direction.valueOf(getCharValue(DIRECTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.XTESentence#setCycleLockStatus(net.sf.
	 * marineapi.nmea.util.DataStatus)
	 */
	@Override
	public void setCycleLockStatus(DataStatus status) {
		setCharValue(CYCLE_LOCK_STATUS, status.toChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.XTESentence#setMagnitude(double)
	 */
	@Override
	public void setMagnitude(double distance) {
		setDoubleValue(DISTANCE, distance, 0, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.XTESentence#setMode(net.sf.marineapi.nmea
	 * .util.FaaMode)
	 */
	@Override
	public void setMode(FaaMode mode) {
		setCharValue(FAA_MODE, mode.toChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.XTESentence#setStatus(net.sf.marineapi
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
	 * net.sf.marineapi.nmea.sentence.XTESentence#setSteerTo(net.sf.marineapi
	 * .nmea.util.Direction)
	 */
	@Override
	public void setSteerTo(Direction direction) {
		setCharValue(DIRECTION, direction.toChar());
	}

}

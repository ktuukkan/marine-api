/*
 * VDRParser.java
 * Copyright (C) 2014 Kimmo Tuukkanen
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
import net.sf.marineapi.nmea.sentence.VDRSentence;
import net.sf.marineapi.nmea.util.Units;

/**
 * VDR sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
class VDRParser extends SentenceParser implements VDRSentence {

	private static final int TRUE_DIRECTION = 0;
	private static final int TRUE_INDICATOR = 1;
	private static final int MAGN_DIRECTION = 2;
	private static final int MAGN_INDICATOR = 3;
	private static final int SPEED = 4;
	private static final int SPEED_UNITS = 5;

	/**
	 * Creates a new instance of VDRParser.
	 * 
	 * @param nmea VDR sentence String
	 */
	public VDRParser(String nmea) {
		super(nmea);
	}

	/**
	 * Creates a new empty instance of VDRParser.
	 * 
	 * @param tid TalkerId to set
	 */
	public VDRParser(TalkerId tid) {
		super(tid, SentenceId.VDR, 6);
		setCharValue(TRUE_INDICATOR, 'T');
		setCharValue(MAGN_INDICATOR, 'M');
		setCharValue(SPEED_UNITS, Units.KNOT.toChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VDRSentence#getMagneticDirection()
	 */
	@Override
	public double getMagneticDirection() {
		return getDoubleValue(MAGN_DIRECTION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VDRSentence#getSpeed()
	 */
	@Override
	public double getSpeed() {
		return getDoubleValue(SPEED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VDRSentence#getTrueDirection()
	 */
	@Override
	public double getTrueDirection() {
		return getDoubleValue(TRUE_DIRECTION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.VDRSentence#setMagneticDirection(double)
	 */
	@Override
	public void setMagneticDirection(double direction) {
		setDegreesValue(MAGN_DIRECTION, direction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VDRSentence#setSpeed(double)
	 */
	@Override
	public void setSpeed(double speed) {
		setDoubleValue(SPEED, speed, 0, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.VDRSentence#setTrueDirection(double)
	 */
	@Override
	public void setTrueDirection(double direction) {
		setDegreesValue(TRUE_DIRECTION, direction);
	}

}

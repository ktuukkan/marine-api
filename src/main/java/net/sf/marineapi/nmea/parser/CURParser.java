/* 
 * CURParser.java
 * Copyright (C) 2016 Henri Laurent
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

import net.sf.marineapi.nmea.sentence.CURSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * CUR sentence parser.
 * 
 * @author Henri Laurent
 * @see CURSentence
 */
class CURParser extends SentenceParser implements CURSentence {

	// field indices
        @SuppressWarnings("unused")
	private static final int DATA_STATUS = 0;
        @SuppressWarnings("unused")
	private static final int DATA_SET = 1;
        @SuppressWarnings("unused")
	private static final int LAYER = 2;
	@SuppressWarnings("unused")
	private static final int CURRENT_DEPTH = 3; // in meters
	private static final int CURRENT_DIRECTION = 4; // in degrees
	private static final int DIRECTION_REFERENCE = 5; // True/Relative T/R
	private static final int CURRENT_SPEED = 6; // in knots
	@SuppressWarnings("unused")
	private static final int REFERENCE_LAYER_DEPTH = 7; // in meters
	@SuppressWarnings("unused")
	private static final int CURRENT_HEADING = 8;
	private static final int HEADING_REFERENCE = 9; // True/Magentic T/M
	private static final int SPEED_REFERENCE = 10; // Bottom/Water/Positioning system B/W/P

	/**
	 * Creates a new instance of CUR parser.
	 *
	 * @param nmea CUR sentence String
	 * @throws IllegalArgumentException If specified String is invalid or does
	 *             not contain a CUR sentence.
	 */
	public CURParser(String nmea) {
		super(nmea, SentenceId.CUR);
	}

	/**
	 * Creates CUR parser with empty sentence.
	 *
	 * @param talker TalkerId to set
	 */
	public CURParser(TalkerId talker) {
		super(talker, SentenceId.CUR, 11);
		setCharValue(DIRECTION_REFERENCE, 'T');
		setCharValue(HEADING_REFERENCE, 'T');
		setCharValue(SPEED_REFERENCE, 'B');
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getCurrentDirection()
	 */
	public double getCurrentDirection() {
		return getDoubleValue(CURRENT_DIRECTION);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getCurrentDirectionReference()
	 */
	public String getCurrentDirectionReference() {
		return getStringValue(DIRECTION_REFERENCE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getCurrentHeadingReference()
	 */
	public String getCurrentHeadingReference() {
		return getStringValue(HEADING_REFERENCE);
	}


	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getCurrentSpeed()
	 */
	public double getCurrentSpeed() {
		return getDoubleValue(CURRENT_SPEED);
	}


}

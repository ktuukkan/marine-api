/*
 * GLLParser.java
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.GLLSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;

/**
 * GLL Sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class GLLParser extends PositionParser implements GLLSentence {

	// field indices
	private static final int LATITUDE = 0;
	private static final int LAT_HEMISPHERE = 1;
	private static final int LONGITUDE = 2;
	private static final int LON_HEMISPHERE = 3;
	private static final int UTC_TIME = 4;
	private static final int DATA_STATUS = 5;

	/**
	 * Creates a new instance of GLLParser.
	 *
	 * @param nmea GLL sentence String.
	 * @throws IllegalArgumentException If the given sentence is invalid or does
	 *             not contain GLL sentence.
	 */
	public GLLParser(String nmea) {
		super(nmea, SentenceId.GLL);
	}

	/**
	 * Creates GSA parser with empty sentence.
	 *
	 * @param talker TalkerId to set
	 */
	public GLLParser(TalkerId talker) {
		super(talker, SentenceId.GLL, 6);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.PositionSentence#getPosition()
	 */
	public Position getPosition() {
		Position p = parsePosition(
			LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE);
		return p;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GLLSentence#getDataStatus()
	 */
	public DataStatus getStatus() {
		return DataStatus.valueOf(getCharValue(DATA_STATUS));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.TimeSentence#getTime()
	 */
	public Time getTime() {
		String str = getStringValue(UTC_TIME);
		return new Time(str);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.PositionSentence#setPosition(net.sf.marineapi
	 * .nmea.util.Position)
	 */
	public void setPosition(Position pos) {
		setPositionValues(
			pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GLLSentence#setDataStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
	public void setStatus(DataStatus status) {
		setCharValue(DATA_STATUS, status.toChar());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.TimeSentence#setTime(net.sf.marineapi.
	 * nmea.util.Time)
	 */
	public void setTime(Time t) {
		setStringValue(UTC_TIME, t.toString());
	}
}

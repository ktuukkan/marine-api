/*
 * ROTParser.java
 * Copyright (C) 2014 Mike Tamis, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.ROTSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;

/**
 * ROT sentence parser.
 * 
 * @author Mike Tamis, Kimmo Tuukkanen
 */
class ROTParser extends SentenceParser implements ROTSentence {

	private static final int RATE_OF_TURN = 0;
	private static final int STATUS = 1;

	/**
	 * Creates a new ROT parser.
	 * 
	 * @param nmea ROT sentence String to parse.
	 */
	public ROTParser(String nmea) {
		super(nmea, SentenceId.ROT);
	}

	/**
	 * Creates a new empty ROT sentence.
	 * 
	 * @param talker Talker id to set
	 */
	public ROTParser(TalkerId talker) {
		super(talker, SentenceId.ROT, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.parser.RateOfTurnSentance#getRateOfTurn()
	 */
	public double getRateOfTurn() {
		return getDoubleValue(RATE_OF_TURN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RateOfTurnSentance#getStatus()
	 */
	public DataStatus getStatus() {
		return DataStatus.valueOf(getCharValue(STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.ROTSentence#setRateOfTurn(double)
	 */
	public void setRateOfTurn(double rot) {
		setDegreesValue(RATE_OF_TURN, rot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.ROTSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
	public void setStatus(DataStatus status) {
		setCharValue(STATUS, status.toChar());
	}
}

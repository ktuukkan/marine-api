/*
 * RPMParser.java
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

import net.sf.marineapi.nmea.sentence.RPMSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;

/**
 * RPM parser
 * 
 * @author Kimmo Tuukkanen
 */
class RPMParser extends SentenceParser implements RPMSentence {

	private static final int SOURCE = 0;
	private static final int SOURCE_NUMBER = 1;
	private static final int REVOLUTIONS = 2;
	private static final int PITCH = 3;
	private static final int STATUS = 4;

	/**
	 * Creates a new instance of RPMParser.
	 * 
	 * @param nmea NMEA sentence String.
	 */
	public RPMParser(String nmea) {
		super(nmea);
	}

	/**
	 * Creates a new empty parser.
	 * 
	 * @param talker TalkerId to set.
	 */
	public RPMParser(TalkerId talker) {
		super(talker, "RPM", 5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getId()
	 */
	@Override
	public int getId() {
		return getIntValue(SOURCE_NUMBER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getPitch()
	 */
	@Override
	public double getPitch() {
		return getDoubleValue(PITCH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getRPM()
	 */
	@Override
	public double getRPM() {
		return getDoubleValue(REVOLUTIONS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getSource()
	 */
	@Override
	public char getSource() {
		return getCharValue(SOURCE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#getStatus()
	 */
	@Override
	public DataStatus getStatus() {
		return DataStatus.valueOf(getCharValue(STATUS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#isEngine()
	 */
	@Override
	public boolean isEngine() {
		return getCharValue(SOURCE) == RPMSentence.ENGINE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#isShaft()
	 */
	@Override
	public boolean isShaft() {
		return getCharValue(SOURCE) == RPMSentence.SHAFT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#setId(int)
	 */
	@Override
	public void setId(int id) {
		setIntValue(SOURCE_NUMBER, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#setPitch(double)
	 */
	@Override
	public void setPitch(double pitch) {
		setDoubleValue(PITCH, pitch, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.marineapi.nmea.sentence.RPMSentence#setSource(char)
	 */
	@Override
	public void setSource(char source) {
		if (source != RPMSentence.ENGINE && source != RPMSentence.SHAFT) {
			throw new IllegalArgumentException(
				"Invalid source indicator, expected 'E' or 'S'");
		}
		setCharValue(SOURCE, source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.marineapi.nmea.sentence.RPMSentence#setStatus(net.sf.marineapi
	 * .nmea.util.DataStatus)
	 */
	@Override
	public void setStatus(DataStatus status) {
		setCharValue(STATUS, status.toChar());
	}

}

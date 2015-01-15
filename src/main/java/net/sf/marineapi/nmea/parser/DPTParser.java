/* 
 * DPTParser.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.DPTSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * DPT sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
class DPTParser extends SentenceParser implements DPTSentence {

	private static final int DEPTH = 0;
	private static final int OFFSET = 1;
	private static final int MAXIMUM = 2;

	/**
	 * Creates a new instance of DPTParser.
	 * 
	 * @param nmea DPT sentence String
	 */
	public DPTParser(String nmea) {
		super(nmea, SentenceId.DPT);
	}

	/**
	 * Creates a new instance of DPTParser with empty data fields.
	 * 
	 * @param talker TalkerId to set
	 */
	public DPTParser(TalkerId talker) {
		super(talker, SentenceId.DPT, 3);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DepthSentence#getDepth()
	 */
	public double getDepth() {
		return getDoubleValue(DEPTH);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DPTSentence#getOffset()
	 */
	public double getOffset() {
		return getDoubleValue(OFFSET);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DepthSentence#setDepth(double)
	 */
	public void setDepth(double depth) {
		setDoubleValue(DEPTH, depth, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DPTSentence#setOffset(double)
	 */
	public void setOffset(double offset) {
		setDoubleValue(OFFSET, offset, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DPTSentence#getMaximum()
	 */
	public double getMaximum() {
		return getDoubleValue(MAXIMUM);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DPTSentence#setMaximum(int)
	 */
	public void setMaximum(double max) {
		setDoubleValue(MAXIMUM, max);
	}

}

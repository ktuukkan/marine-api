/* 
 * MTAParser.java
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

import net.sf.marineapi.nmea.sentence.MTASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.Units;

/**
 * MTA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
class MTAParser extends SentenceParser implements MTASentence {

	private static final int TEMPERATURE = 0;
	private static final int UNIT_INDICATOR = 1;

	/**
	 * Constructor.
	 */
	public MTAParser(String mta) {
		super(mta, SentenceId.MTA);
	}

	/**
	 * Constructor.
	 */
	public MTAParser(TalkerId talker) {
		super(talker, SentenceId.MTA, 2);
		setCharValue(UNIT_INDICATOR, Units.CELSIUS.toChar());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MTASentence#getTemperature()
	 */
	public double getTemperature() {
		return getDoubleValue(TEMPERATURE);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.MTASentence#setTemperature(double)
	 */
	public void setTemperature(double temp) {
		setDoubleValue(TEMPERATURE, temp, 1, 2);
	}

}

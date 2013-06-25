/* 
 * BODParser.java
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

import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * BOD sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.sentence.BODSentence
 */
class BODParser extends SentenceParser implements BODSentence {

	// field indices
	private static final int BEARING_TRUE = 0;
	private static final int TRUE_INDICATOR = 1;
	private static final int BEARING_MAGN = 2;
	private static final int MAGN_INDICATOR = 3;
	private static final int DESTINATION = 4;
	private static final int ORIGIN = 5;

	/**
	 * Creates a new instance of BOD parser.
	 * 
	 * @param nmea BOD sentence String
	 * @throws IllegalArgumentException If specified String is invalid or does
	 *             not contain a BOD sentence.
	 */
	public BODParser(String nmea) {
		super(nmea, SentenceId.BOD);
	}

	/**
	 * Creates GSA parser with empty sentence.
	 * 
	 * @param talker TalkerId to set
	 */
	public BODParser(TalkerId talker) {
		super(talker, SentenceId.BOD, 6);
		setCharValue(TRUE_INDICATOR, 'T');
		setCharValue(MAGN_INDICATOR, 'M');
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#getDestinationWaypointId()
	 */
	public String getDestinationWaypointId() {
		return getStringValue(DESTINATION);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getMagneticBearing()
	 */
	public double getMagneticBearing() {
		return getDoubleValue(BEARING_MAGN);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getOriginWaypointId()
	 */
	public String getOriginWaypointId() {
		return getStringValue(ORIGIN);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#getTrueBearing()
	 */
	public double getTrueBearing() {
		return getDoubleValue(BEARING_TRUE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setDestinationWaypointId(java
	 * .lang.String)
	 */
	public void setDestinationWaypointId(String id) {
		setStringValue(DESTINATION, id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setMagneticBearing(double)
	 */
	public void setMagneticBearing(double bearing) {
		setDegreesValue(BEARING_MAGN, bearing);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.BODSentence#setOriginWaypointId(java.lang
	 * .String)
	 */
	public void setOriginWaypointId(String id) {
		setStringValue(ORIGIN, id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.BODSentence#setTrueBearing(double)
	 */
	public void setTrueBearing(double bearing) {
		setDegreesValue(BEARING_TRUE, bearing);
	}
}

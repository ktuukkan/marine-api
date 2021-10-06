/*
 * GSTParser.java
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

import net.sf.marineapi.nmea.sentence.GSTSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.Time;

/**
 * GST sentence parser.
 *
 * @author Tero Laitinen
 */
class GSTParser extends SentenceParser implements GSTSentence {

	// GST field indices
	private static final int UTC_TIME = 0;
	private static final int PSEUDORANGE_RESIDUALS_RMS = 1;
	private static final int ERROR_ELLIPSE_SEMI_MAJOR = 2;
	private static final int ERROR_ELLIPSE_SEMI_MINOR = 3;
	private static final int ERROR_ELLIPSE_ORIENTATION = 4;
	private static final int LATITUDE_ERROR = 5;
	private static final int LONGITUDE_ERROR = 6;
	private static final int ALTITUDE_ERROR = 7;

	/**
	 * Creates a new instance of GST parser.
	 *
	 * @param nmea GST sentence String.
	 * @throws IllegalArgumentException If the specified sentence is invalid or
	 *						 not a GST sentence.
	 */
	public GSTParser(String nmea) {
		super(nmea, SentenceId.GST);
	}

	/**
	 * Creates GSA parser with empty sentence.
	 *
	 * @param talker TalkerId to set
	 */
	public GSTParser(TalkerId talker) {
		super(talker, SentenceId.GST, 8);
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
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getPseudoRangeResidualsRMS
	 */
	public double getPseudoRangeResidualsRMS() {
		return getDoubleValue(PSEUDORANGE_RESIDUALS_RMS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getSemiMajorError
	 */
	public double getSemiMajorError() {
		return getDoubleValue(ERROR_ELLIPSE_SEMI_MAJOR);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getSemiMinorError
	 */
	public double getSemiMinorError() {
		return getDoubleValue(ERROR_ELLIPSE_SEMI_MINOR);
	}



	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getErrorEllipseOrientation
	 */
	public double getErrorEllipseOrientation() {
		return getDoubleValue(ERROR_ELLIPSE_ORIENTATION);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getLatitudeError
	 */
	public double getLatitudeError() {
		return getDoubleValue(LATITUDE_ERROR);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getLongitudeError
	 */
	public double getLongitudeError() {
		return getDoubleValue(LONGITUDE_ERROR);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getAltitudeError
	 */
	public double getAltitudeError() {
		return getDoubleValue(ALTITUDE_ERROR);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.TimeSentence#setTime(net.sf.marineapi.nmea.util.Time)
	 */
	public void setTime(Time t) {
		setStringValue(UTC_TIME, t.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setPseudoRangeResidualsRMS
	 */
	public void setPseudoRangeResidualsRMS(double rms) {
		setDoubleValue(PSEUDORANGE_RESIDUALS_RMS, rms);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setSemiMajorError
	 */
	public void setSemiMajorError(double error) {
		setDoubleValue(ERROR_ELLIPSE_SEMI_MAJOR, error);
	}

/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setSemiMinorError
	 */
	public void setSemiMinorError(double error) {
		setDoubleValue(ERROR_ELLIPSE_SEMI_MINOR, error);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setErrorEllipseOrientation
	 */
	public void setErrorEllipseOrientation(double orientation) {
		setDoubleValue(ERROR_ELLIPSE_ORIENTATION, orientation);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setLatitudeError
	 */
	public void setLatitudeError(double error) {
		setDoubleValue(LATITUDE_ERROR, error);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setLongitudeError
	 */
	public void setLongitudeError(double error) {
		setDoubleValue(LONGITUDE_ERROR, error);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setAltitudeError
	 */
	public void setAltitudeError(double error) {
		setDoubleValue(ALTITUDE_ERROR, error);
	}
}

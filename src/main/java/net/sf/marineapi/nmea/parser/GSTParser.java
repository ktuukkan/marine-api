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
import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.nmea.util.Units;

/**
 * GST sentence parser.
 * 
 * @author Tero Laitinen
 */
class GSTParser extends SentenceParser implements GSTSentence {

	// GST field indices
	private static final int UTC_TIME = 0;
	private static final int PSEUDORANGE_RESIDUALS_RMS = 1;
	private static final int ERROR_ELLIPSE_SEMI_MAJOR_1_SIGMA = 2;
	private static final int ERROR_ELLIPSE_SEMI_MINOR_1_SIGMA = 3;
	private static final int ERROR_ELLIPSE_ORIENTATION = 4;
	private static final int LATITUDE_1_SIGMA_ERROR = 5;
	private static final int LONGITUDE_1_SIGMA_ERROR = 6;
	private static final int HEIGHT_1_SIGMA_ERROR = 7;

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
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getErrorEllipseSemiMajorAxis1SigmaError
	 */
	public double getErrorEllipseSemiMajorAxis1SigmaError() {
		return getDoubleValue(ERROR_ELLIPSE_SEMI_MAJOR_1_SIGMA);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getErrorEllipseSemiMinorAxis1SigmaError
	 */
	public double getErrorEllipseSemiMinorAxis1SigmaError() {
		return getDoubleValue(ERROR_ELLIPSE_SEMI_MINOR_1_SIGMA);
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
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getLatitude1SigmaError
	 */
	public double getLatitude1SigmaError() {
		return getDoubleValue(LATITUDE_1_SIGMA_ERROR);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getLongitude1SigmaError
	 */
	public double getLongitude1SigmaError() {
		return getDoubleValue(LONGITUDE_1_SIGMA_ERROR);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#getHeight1SigmaError
	 */
	public double getHeight1SigmaError() {
		return getDoubleValue(HEIGHT_1_SIGMA_ERROR);
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

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setPseudoRangeResidualsRMS
	 */
	public void setPseudoRangeResidualsRMS(double rms) {
		setDoubleValue(PSEUDORANGE_RESIDUALS_RMS, rms);
	}
 
	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setErrorEllipseSemiMajorAxis1SigmaError
	 */
	public void setErrorEllipseSemiMajorAxis1SigmaError(double error) {
		setDoubleValue(ERROR_ELLIPSE_SEMI_MAJOR_1_SIGMA, error);
	} 

/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setErrorEllipseSemiMinorAxis1SigmaError
	 */
	public void setErrorEllipseSemiMinorAxis1SigmaError(double error) {
		setDoubleValue(ERROR_ELLIPSE_SEMI_MINOR_1_SIGMA, error);
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
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setLatitude1SigmaError
	 */
	public void setLatitude1SigmaError(double error) {
		setDoubleValue(LATITUDE_1_SIGMA_ERROR, error);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setLongitude1SigmaError
	 */
	public void setLongitude1SigmaError(double error) {
		setDoubleValue(LONGITUDE_1_SIGMA_ERROR, error);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GSTSentence#setHeight1SigmaError
	 */
	public void setHeight1SigmaError(double error) {
		setDoubleValue(HEIGHT_1_SIGMA_ERROR, error);
	}
}

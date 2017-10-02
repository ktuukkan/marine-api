/* 
 * GSTSentence.java
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
package net.sf.marineapi.nmea.sentence;


/**
 * GPS Pseudorange Noise Statistics. 
 * <p>
 * Example:<br>
 * <code>$GPGST,172814.0,0.006,0.023,0.020,273.6,0.023,0.020,0.031*6A</code>
 * 
 * @author Tero Laitinen
 */
public interface GSTSentence extends TimeSentence {

	/**
	 * Get RMS value of the pseudorange residuals; includes carrier phase 
	 * residuals during periods of RTK (float) and RTK (fixed) processing
	 * 
	 * @return RMS value
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getPseudoRangeResidualsRMS();

	/** 
	 * Get Error ellipse semi-major axis 1 sigma error, in meters.
	 * 
	 * @return error ellipse error value
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	*/
	double getErrorEllipseSemiMajorAxis1SigmaError();

	/** 
	 * Get Error ellipse semi-minor axis 1 sigma error, in meters.
	 * 
	 * @return error ellipse error value
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	*/
	double getErrorEllipseSemiMinorAxis1SigmaError();

	/** 
	 * Get Error ellipse orientation, degrees from true north
	 * 
	 * @return error ellipse orientation
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	*/
	double getErrorEllipseOrientation();

	/** 
	 * Get Latitude 1 sigma error, in meters
	 * 
	 * @return latitude 1 sigma error
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	*/
	double getLatitude1SigmaError();

	/** 
	 * Get Longitude 1 sigma error, in meters
	 * 
	 * @return longitude 1 sigma error
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	*/
	double getLongitude1SigmaError();

	/** 
	 * Get Height 1 sigma error, in meters
	 * 
	 * @return height 1 sigma error
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	*/
	double getHeight1SigmaError();


	/**
	 * Sets the RMS value of the pseudorange residuals.
	 * 
	 * @param rms RMS value of the pseudorange residuals
	 **/

	void setPseudoRangeResidualsRMS(double rms);

	/** 
	 * Sets the error ellipse semi-major axis 1 sigma error, in meters.
	 *
	 * @param error semi-major error ellipse error
	*/
	void setErrorEllipseSemiMajorAxis1SigmaError(double error);

	/** 
	 * Sets the error ellipse semi-minor axis 1 sigma error, in meters.
	 *
	 * @param error semi-minor error ellipse error
	*/
	void setErrorEllipseSemiMinorAxis1SigmaError(double error);

	/** 
	 * Sets the error ellipse orientation, degrees from true north
	 *
	 * @param orientation error ellipse orientation
	*/
	void setErrorEllipseOrientation(double orientation);

	/** 
	 * Sets the latitude 1 sigma error, in meters
	 *
	 * @param error latitude 1 sigma error
	*/
	void setLatitude1SigmaError(double error);

	/** 
	 * Sets the longitude 1 sigma error, in meters
	 * 
	 * @param error longitude 1 sigma error
	*/
	void setLongitude1SigmaError(double error);

	/** 
	 * Sets the height 1 sigma error, in meters
	 *
	 * @param error height 1 sigma error 
	*/
	void setHeight1SigmaError(double error);
}

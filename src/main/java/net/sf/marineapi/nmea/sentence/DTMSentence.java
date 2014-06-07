/*
 * DTMSentence.java
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
package net.sf.marineapi.nmea.sentence;

/**
 * <p>Datum reference.</p>
 * 
 * <p>Example:<br/>
 * <code>$GPDTM,W84,,0.000000,N,0.000000,E,0.0,W84*6F</code></p>
 * 
 * @author Kimmo Tuukkanen
 */
public interface DTMSentence extends Sentence {

	/**
	 * Returns the altitude offset.
	 * 
	 * @return Altitude offset, in meters.
	 */
	double getAltitudeOffset();

	/**
	 * Returns the local datum code.
	 * 
	 * @return Datum code
	 */
	String getDatumCode();

	/**
	 * Returns the local datum subcode, may be blank.
	 * 
	 * @return Datum subcode
	 */
	String getDatumSubCode();

	/**
	 * Returns the latitude offset. Positive values depict northern offset,
	 * negative for southern.
	 * 
	 * @return Offset value in minutes.
	 */
	double getLatitudeOffset();

	/**
	 * Returns the longitude offset. Positive values for east, negative west.
	 * 
	 * @return Longitude offset in minutes.
	 */
	double getLongitudeOffset();

	/**
	 * Returns the datum name, e.g. "W84" for WGS84 used by GPS.
	 * 
	 * @return Datum name
	 */
	String getName();

	/**
	 * Sets the local datum code.
	 * 
	 * @param code Code to set
	 */
	void setDatumCode(String code);

	/**
	 * Sets the local datum code, may be blank.
	 * 
	 * @param code Code to set
	 */
	void setDatumSubCode(String code);

	/**
	 * Sets the latitude offset. Positive values depict northern offset,
	 * negative for southern.
	 * 
	 * @param offset Latitude offset in minutes.
	 */
	void setLatitudeOffset(double offset);

	/**
	 * Sets the longitude offset. Positive values for east, negative west.
	 * 
	 * @param offset Longitude offset in minutes.
	 */
	void setLongitudeOffset(double offset);

	/**
	 * Sets the datum name.
	 * 
	 * @param name Name to set.
	 */
	void setName(String name);
}

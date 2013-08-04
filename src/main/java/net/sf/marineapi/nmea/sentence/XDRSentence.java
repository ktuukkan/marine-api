/*
 * XDRSentence.java
 * Copyright (C) 2013 Robert Huitema, Kimmo Tuukkanen
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

import java.util.List;

import net.sf.marineapi.nmea.util.Measurement;

/**
 * <p>
 * XDR sentence - transducer measurements.
 * <p>
 * Measurements are delivered in sets containing four fields; transducer type,
 * measurement value, unit of measurement and transducer name. There may be
 * any number of sets like this, each describing a sensor. Measurements are
 * parsed and returned as {@link net.sf.marineapi.nmea.util.Measurement} objects. 
 * 
 * @author Robert Huitema, Kimmo Tuukkanen
 */
public interface XDRSentence extends Sentence {

	/**
	 * Returns all measurement values.
	 * 
	 * @return List of measurements, ordered as they appear in sentence.
	 */
	List<Measurement> getMeasurements();
	
	/**
	 * Set single measurement. Sentence length is adjusted to required number of
	 * data fields.
	 *
	 * @param m Measurement to set.
	 */
	void setMeasurement(Measurement m);

	/**
	 * Set multiple measurements. Overwrites all existing data and adjusts the
	 * sentence length to required number of data fields. Measurements are
	 * inserted in given order.
	 * 
	 * @param measurements List of measurements to set.
	 */
	void setMeasurements(List<Measurement> measurements);
}

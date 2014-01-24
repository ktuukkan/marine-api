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
 * Transducer measurements. Measurements are delivered in sets containing four
 * fields; transducer type, measurement value, unit of measurement and
 * transducer name. There may be any number of sets like this, each describing a
 * sensor. Notice that inserting too many measuments in one sentence may result
 * in exceeding the maximum sentence length (82 chars).
 * 
 * @see net.sf.marineapi.nmea.util.Measurement
 * @author Robert Huitema, Kimmo Tuukkanen
 */
public interface XDRSentence extends Sentence {

	/**
	 * Adds specified measurement in sentence placing it last. Multiple
	 * measurements are inserted in given order.
	 * 
	 * @param m Measurements to add.
	 */
	void addMeasurement(Measurement... m);

	/**
	 * Returns all measurements.
	 * 
	 * @return List of measurements, ordered as they appear in sentence.
	 */
	List<Measurement> getMeasurements();

	/**
	 * Set single measurement. Overwrites all existing values and adjusts the
	 * number of data fields to minimum required by one measurement (4).
	 * 
	 * @param m Measurement to set.
	 */
	void setMeasurement(Measurement m);

	/**
	 * Set multiple measurements in given order. Overwrites all existing values
	 * and adjusts the number of data fields as required by given measurements.
	 * 
	 * @param measurements List of measurements to set.
	 */
	void setMeasurements(List<Measurement> measurements);
}

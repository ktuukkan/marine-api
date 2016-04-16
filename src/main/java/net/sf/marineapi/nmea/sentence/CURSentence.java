/* 
 * CURSentence.java
 * Copyright (C) 2016 Henri Laurent
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
 * Multi-layer current data sentence is transmitted by navigation central
 * like the NKE ones.
 * <p>
 * Example:<br>
 * <code> </code>
 *
 * @author Henri Laurent
 */
public interface CURSentence extends Sentence {

	/**
	 * Get the Current Speed in knots
	 *
	 * @return current speed
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getCurrentSpeed();

	/**
	 * Get the current direction.
	 * <p>
	 *
	 * @return current direction
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	double getCurrentDirection();

	/**
	 * Get the current direction reference.
	 * <p>
	 *
	 * @return current direction reference T/R
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	String getCurrentDirectionReference();

	/**
	 * Get the current heading reference.
	 *
	 * @return current heading reference T/M
	 * True/Magentic T/M
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
	String getCurrentHeadingReference();

}

/*
 * GSVSentence.java
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

import java.util.List;

import net.sf.marineapi.nmea.util.SatelliteInfo;

/**
 * Detailed GPS satellite data; satellites in view, satellite elevation, azimuth
 * and signal noise ratio (SNR). GSV sentences are transmitted typically in
 * groups of two or three sentences, depending on the number of satellites in
 * view. Each GSV sentence may contain information about up to four satellites.
 * The last sentence in sequence may contain empty satellite information fields.
 * The empty fields may also be omitted, depending on the device model and
 * manufacturer.
 * <p>
 * Example: <br>
 * {@code $GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,63,058,50,21,53,329,47*73}
 *
 * @author Kimmo Tuukkanen
 */
public interface GSVSentence extends Sentence {

	/**
	 * Get the number of satellites in view.
	 *
	 * @return Satellite count
	 */
	int getSatelliteCount();

	/**
	 * Get the satellites information.
	 *
	 * @return List of SatelliteInfo objects.
	 */
	List<SatelliteInfo> getSatelliteInfo();

	/**
	 * Get the total number of sentences in GSV sequence.
	 *
	 * @return Number of sentences
	 */
	int getSentenceCount();

	/**
	 * Get the GNSS signal ID (only NMEA 4.10 and later).
	 * <p>
	 * Be aware, that the returned signal id may differ between NMEA versions. For
	 * example, in the NMEA protocol version 4.10 the BeiDou and QZSS signal IDs
	 * are not defined. Therefore u-blox devices, for example, will send a proprietary
	 * signal id value that differs from the one defined in NMEA Protocol 4.11.
	 *
	 * NMEA Protocol 4.11 defines the following signal ids:
	 *
	 * <ul>
	 *   <li>GPS L1C/A = 1
	 *   <li>GPS L2 CL = 6
	 *   <li>GPS L2 CM = 5
	 *   <li>SBAS L1C/A = 1
	 *   <li>Galileo E1 C = 7
	 *   <li>Galileo E1 B = 7
	 *   <li>Galileo E5 bI = 2
	 *   <li>Galileo E5 bQ = 2
	 *   <li>BeiDou B1I D1 = 1
	 *   <li>BeiDou B1I D2 = 1
	 *   <li>BeiDou B2I D1 = 11
	 *   <li>BeiDou B2I D2 = 11
	 *   <li>QZSS L1C/A = 1
	 *   <li>QZSS L1S = 4
	 *   <li>QZSS L2 CM = 5
	 *   <li>QZSS L2 CL = 6
	 *   <li>GLONASS L1 OF = 1
	 *   <li>GLONASS L2 OF = 3
	 * </ul>
	 *
	 * @return signal ID
	 */
	int getSignalId();

	/**
	 * Get the index of this sentence in GSV sequence.
	 *
	 * @return Sentence index
	 */
	int getSentenceIndex();

	/**
	 * Tells if this is the first sentence in GSV sequence.
	 *
	 * @return true if first, otherwise false.
	 * @see #getSentenceCount()
	 * @see #getSentenceIndex()
	 */
	boolean isFirst();

	/**
	 * Tells if this is the last sentence in GSV sequence. This is a convenience
	 * method for comparison of
	 * {@code ({@link #getSentenceCount()} == {@link #getSentenceIndex()})}
	 * .
	 *
	 * @return {@code true} if first, otherwise {@code false}.
	 */
	boolean isLast();

	/**
	 * Set the number of satellites in view.
	 *
	 * @param count Satellite count
	 * @throws IllegalArgumentException If specified number is negative
	 */
	void setSatelliteCount(int count);

	/**
	 * Set the satellite information.
	 *
	 * @param info List of SatelliteInfo objects, size from 0 to 4.
	 * @throws IllegalArgumentException If specified list size is greater that
	 *             maximum allowed number of satellites per sentence (4).
	 */
	void setSatelliteInfo(List<SatelliteInfo> info);

	/**
	 * Set the total number of sentences in GSV sequence.
	 *
	 * @param count Number of sentences
	 * @throws IllegalArgumentException If specified count is negative
	 */
	void setSentenceCount(int count);

	/**
	 * Set the index of this sentence in GSV sequence.
	 *
	 * @param index Sentence index to set
	 * @throws IllegalArgumentException If specified index is negative
	 */
	void setSentenceIndex(int index);

}

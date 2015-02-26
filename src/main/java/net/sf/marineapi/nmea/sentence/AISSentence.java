/* 
 * AISSentence.java
 * Copyright (C) 2015 Lázár József
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
 * <p>
 * Base interface for all AIS sentences. Notice that &quot;AIS&quot; does not
 * refer to a single sentence type, but another system/standard that transmits
 * it's messages using NMEA 0183 format (Automatic Identification System).
 * </p>
 * <p>
 * Because AIS sentences are parsed in two-phases, they all share the same NMEA
 * sentence layout. Thus, there is only one interface for AIS sentences (this
 * one).
 * </p>
 * 
 * @author Lázár József, Kimmo Tuukkanen
 */
public interface AISSentence extends Sentence {

	/**
	 * Count of fragments in the currently accumulating message.
	 * 
	 * @return number of fragments.
	 */
	public int getNumberOfFragments();

	/**
	 * Returns the fragment number of this sentence (1 based).
	 * 
	 * @return fragment index
	 */
	public int getFragmentNumber();

	/**
	 * Returns the sequential message ID for multi-sentence messages.
	 * 
	 * @return sequential message ID
	 */
	public String getMessageId();

	/**
	 * Returns the radio channel information of the messsage.
	 * 
	 * @return radio channel id
	 */
	public String getRadioChannel();

	/**
	 * Returns the raw 6-bit decoded message.
	 * 
	 * @return message body
	 */
	public String getPayload();

	/**
	 * Returns the number of fill bits required to pad the data payload to a 6
	 * bit boundary, ranging from 0 to 5.
	 * 
	 * Equivalently, subtracting 5 from this tells how many least significant
	 * bits of the last 6-bit nibble in the data payload should be ignored.
	 * 
	 * @return number of fill bits
	 */
	public int getFillBits();

	/**
	 * Tells if the AIS message is being delivered over multiple sentences.
	 * 
	 * @return true if this sentence is part of a sequence
	 */
	public boolean isFragmented();

	/**
	 * Tells if this is the first fragment in message sequence.
	 * 
	 * @return true if first fragment in sequence
	 */
	public boolean isFirstFragment();

	/**
	 * Tells if this is the last fragment in message sequence.
	 * 
	 * @return true if last part of a sequence
	 */
	public boolean isLastFragment();

	/**
	 * <p>
	 * Returns whether given sentence is part of message sequence.
	 * </p>
	 * 
	 * <p>
	 * Sentences are considered to belong in same sequence when:
	 * </p>
	 * <ul>
	 * <li>Same number of fragments, higher fragment #, same channel and same
	 * messageId</li>
	 * <li>Same number of fragments, next fragment #, and either same channel or
	 * same messageId</li>
	 * </ul>
	 * 
	 * @param line VDMSentence to compare with.
	 * @return true if this and given sentence belong in same sequence
	 */
	public boolean isPartOfMessage(AISSentence line);

}

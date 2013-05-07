/* 
 * Sentence.java
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
 * Base interface and constants for NMEA 0183 sentences.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface Sentence {

	/**
	 * Address field index, the first field of all sentences.
	 */
	int ADDRESS_FIELD = 0;

	/**
	 * Sentence begin character
	 */
	char BEGIN_CHAR = '$';

	/**
	 * Alternative sentence begin character used in VDO and VDM sentences.
	 */
	char ALTERNATIVE_BEGIN_CHAR = '!';

	/**
	 * Checksum field delimiter char
	 */
	char CHECKSUM_DELIMITER = '*';

	/**
	 * Sentence data fields delimiter char
	 */
	char FIELD_DELIMITER = ',';

	/**
	 * Maximum length of NMEA 0183 sentences, including <code>BEGIN_CHAR</code>
	 * and <code>TERMINATOR</code>.
	 */
	int MAX_LENGTH = 82;

	/**
	 * Sentence terminator (&lt;CR&gt;&lt;LF&gt;)
	 */
	String TERMINATOR = "\r\n";

	/**
	 * Returns the current number of data fields in sentence, excluding ID field
	 * and checksum.
	 * 
	 * @return Data field count
	 */
	int getFieldCount();

	/**
	 * Get the sentence begin character. Although most of the sentences start
	 * with '$', some of them use '!' as begin character.
	 * 
	 * @return Sentence begin char, e.g. "$" or "!".
	 */
	char getBeginChar();

	/**
	 * Get the sentence ID that specifies the sentence type and data it holds.
	 * ID is the last three characters in address field. For example, in case of
	 * <code>$GPGGA</code> the method returns {@link SentenceId#GGA}.
	 * 
	 * @return Sentence id String, e.g. "GLL" or "GGA".
	 * @see SentenceId
	 */
	String getSentenceId();

	/**
	 * Gets the talker ID of the sentence. Talker ID is the next two characters
	 * after <code>$</code> in sentence address field. For example, in case of
	 * <code>$GPGGA</code>, the method returns {@link TalkerId#GP}.
	 * 
	 * @return Talker id enum.
	 */
	TalkerId getTalkerId();

	/**
	 * Tells if the sentence is of proprietary format.
	 * 
	 * @return True if proprietary, otherwise false.
	 */
	boolean isProprietary();

	/**
	 * Tells if the sentence formatting matches NMEA 0183 format.
	 * 
	 * @return True if validly formatted, otherwise false.
	 */
	boolean isValid();

	/**
	 * Set the sentence begin character. Although most of the sentences start
	 * with '$', some of them use '!' as begin character.
	 * 
	 * @param ch Sentence begin char to set ('$' or '!')
	 * @see Sentence#BEGIN_CHAR
	 * @see Sentence#ALTERNATIVE_BEGIN_CHAR
	 */
	void setBeginChar(char ch);

	/**
	 * Set the talker ID of the sentence. Typically, the ID might be changed if
	 * the sentence is to be sent from a computer to an NMEA device.
	 * 
	 * @param id TalkerId to set
	 */
	void setTalkerId(TalkerId id);

	/**
	 * Formats and validates the String representation of sentence without the
	 * line terminator CR/LF. If formatting results in invalid sentence, e.g. 82
	 * character limit exceeds due too long values set in fields, an exception
	 * is thrown.
	 * 
	 * @see #toString()
	 * @return A valid NMEA sentence String
	 * @throws IllegalStateException If formatting results in invalid sentence.
	 */
	String toSentence();

	/**
	 * Returns the String representation of sentence in NMEA 0183 format.
	 * 
	 * @return Sentence String
	 */
	String toString();
}

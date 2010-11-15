/* 
 * Sentence.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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
    static final int ADDRESS_FIELD = 0;

    /**
     * Sentence begin character
     */
    static final char BEGIN_CHAR = '$';

    /**
     * Checksum field delimiter char
     */
    static final char CHECKSUM_DELIMITER = '*';

    /**
     * Sentence data fields delimiter char
     */
    static final char FIELD_DELIMITER = ',';

    /**
     * Maximum length of NMEA 0183 sentences, including <code>BEGIN_CHAR</code>
     * and <code>TERMINATOR</code>.
     */
    static final int MAX_LENGTH = 82;

    /**
     * Sentence terminator (&lt;CR&gt;&lt;LF&gt;)
     */
    static final String TERMINATOR = "\r\n";

    /**
     * Get the sentence ID that specifies sentence type. ID is the last three
     * characters in address field. For example, in case of <code>$GPGGA</code>
     * the method returns <code>SentenceId.GGA</code>.
     * 
     * @return SentenceId enum
     */
    SentenceId getSentenceId();

    /**
     * Gets the talker ID of the sentence. Talker ID is the next two characters
     * after <code>$</code> in sentence address field. For example, in case of
     * <code>$GPGGA</code>, the method returns <code>TalkerId.GP</code>.
     * 
     * @return Talker id String.
     */
    TalkerId getTalkerId();

    /**
     * Set the talker ID of the sentence. Typically, the ID might be changed if
     * the sentence is to be sent from a computer to an NMEA device.
     * 
     * @param id TalkerId to set
     */
    void setTalkerId(TalkerId id);

    /**
     * Returns the String representation of sentence, in NMEA 0183 format.
     * 
     * @return Sentence String
     */
    String toString();
}

/*
 * TXTSentence.java
 * Copyright (C) 2018 Kimmo Tuukkanen
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
 * Text message sentence. Transmits various information on the device, such as
 * power-up screen, software version etc.</p>
 * <p>
 * Example:<br>
 * {@code $GPTXT,01,01,TARG1,Message*35}
 * </p>
 *
 * @author Kimmo Tuukkanen
 */
public interface TXTSentence extends Sentence {

    /**
     * Get total number of sentences in message sequence.
     *
     * @return Number of transmission messages.
     */
    int getMessageCount();

    /**
     * Set total number of sentences in message sequence.
     *
     * @param count Number of transmission messages.
     * @throws IllegalArgumentException If given count is zero or negative
     */
    void setMessageCount(int count);

    /**
     * Returns the sentence index in message sequence.
     *
     * @return Message number of this sentence.
     * @see #getMessageCount()
     */
    int getMessageIndex();

    /**
     * Sets the sentence index in message sequence.
     *
     * @param index Message index to set
     * @throws IllegalArgumentException If given index is negative
     */
    void setMessageIndex(int index);

    /**
     * Returns the message identifier. This field may be used for various
     * purposes depending on the device. Originally a numeric field but may
     * also contain String values. For example, should match target name in
     * {@code TLL} or waypoint name in {@code WPL}.
     *
     * @return Message identifier
     */
    String getIdentifier();

    /**
     * Sets the message identifier.
     *
     * @param id Identifier to be set
     * @see #getIdentifier()
     */
    void setIdentifier(String id);

    /**
     * Returns the message content.
     *
     * @return ASCII text content
     */
    String getMessage();

    /**
     * Sets the message content.
     *
     * @param msg ASCII text content to set.
     */
    void setMessage(String msg);

}

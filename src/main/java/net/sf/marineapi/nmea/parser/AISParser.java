/*
 * AISParser.java
 * Copyright (C) 2016 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.AISSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Common AIS sentence parser. Handles only the NMEA layer for VDM and VDO
 * sentences. The actual payload message is parsed by AIS message parsers.
 *
 * @author Kimmo Tuukkanen
 * @see AISSentence
 * @see VDOParser
 * @see VDMParser
 */
abstract class AISParser extends SentenceParser implements AISSentence {

    // NMEA message fields
    private static final int NUMBER_OF_FRAGMENTS = 0;
    private static final int FRAGMENT_NUMBER = 1;
    private static final int MESSAGE_ID	= 2;
    private static final int RADIO_CHANNEL = 3;
    private static final int PAYLOAD = 4;
    private static final int FILL_BITS = 5;

    /**
     * Creates a new instance of VDOParser.
     *
     * @param nmea NMEA sentence String.
     */
    public AISParser(String nmea, SentenceId sid) {
        super(nmea, sid);
    }

    /**
     * Creates a new empty VDOParser.
     *
     * @param tid TalkerId to set
     * @param sid
     */
    public AISParser(TalkerId tid, SentenceId sid) {
        super('!', tid, sid, 6);
    }

    @Override
    public int getNumberOfFragments() {
        return getIntValue(NUMBER_OF_FRAGMENTS);
    }

    @Override
    public int getFragmentNumber() {
        return getIntValue(FRAGMENT_NUMBER);
    }

    @Override
    public String getMessageId() {
        return getStringValue(MESSAGE_ID);
    }

    @Override
    public String getRadioChannel() {
        return getStringValue(RADIO_CHANNEL);
    }

    @Override
    public String getPayload() {
        return getStringValue(PAYLOAD);
    }

    @Override
    public int getFillBits() {
        return getIntValue(FILL_BITS);
    }

    @Override
    public boolean isFragmented() {
        return getNumberOfFragments() > 1;
    }

    @Override
    public boolean isFirstFragment() {
        return getFragmentNumber() == 1;
    }

    @Override
    public boolean isLastFragment() {
        return getNumberOfFragments() == getFragmentNumber();
    }

    @Override
    public boolean isPartOfMessage(AISSentence line) {
        if (getNumberOfFragments() == line.getNumberOfFragments() &&
                getFragmentNumber() < line.getFragmentNumber()) {

            if (getFragmentNumber() + 1 == line.getFragmentNumber()) {
                return (getRadioChannel().equals(line.getRadioChannel()) ||
                        getMessageId().equals(line.getMessageId()));
            } else {
                return (getRadioChannel().equals(line.getRadioChannel()) &&
                        getMessageId().equals(line.getMessageId()));
            }
        } else {
            return false;
        }
    }
}

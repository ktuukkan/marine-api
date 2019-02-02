/*
 * TXTParser.java
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TXTSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

import java.util.regex.Pattern;

/**
 * TXT sentence parser.
 *
 * $GPTXT,01,01,TARG1,Message*35
 *
 * TXT - TXT protocol header
 * 01 - Total number of messages in this transmission
 * 01 - Message number in this transmission
 * TARG1 - Target name associated with this message
 * Message - Text of message for Terrain Navigator Pro to display.
 *
 * @author Kimmo Tuukkanen
 */
class TXTParser extends SentenceParser implements TXTSentence {

    private static final Pattern ASCII = Pattern.compile("^[\\x20-\\x7F]*$");

    private static final int MESSAGE_COUNT = 0;
    private static final int MESSAGE_INDEX = 1;
    private static final int IDENTIFIER = 2;
    private static final int MESSAGE = 3;

    /**
     * Constructor with sentence String.
     *
     * @param nmea TXT sentence String
     */
    public TXTParser(String nmea) {
        super(nmea, SentenceId.TXT);
    }

    /**
     * Constructs an empty TXT sentence.
     *
     * @param tid TalkerId to set
     */
    public TXTParser(TalkerId tid) {
        super(tid, SentenceId.TXT, 4);
    }

    @Override
    public int getMessageCount() {
        return getIntValue(MESSAGE_COUNT);
    }

    @Override
    public void setMessageCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Message count cannot be zero or negative");
        }
        setIntValue(MESSAGE_COUNT, count);
    }

    @Override
    public int getMessageIndex() {
        return getIntValue(MESSAGE_INDEX);
    }

    @Override
    public void setMessageIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Message index cannot be negative");
        }
        setIntValue(MESSAGE_INDEX, index);
    }

    @Override
    public String getIdentifier() {
        return getStringValue(IDENTIFIER);
    }

    @Override
    public void setIdentifier(String id) {
        setStringValue(IDENTIFIER, id);
    }

    @Override
    public String getMessage() {
        return getStringValue(MESSAGE);
    }

    @Override
    public void setMessage(String msg) {
        if (!ASCII.matcher(msg).matches()) {
            throw new IllegalArgumentException("Message must be in ASCII character set");
        }
        setStringValue(MESSAGE, msg);
    }
}

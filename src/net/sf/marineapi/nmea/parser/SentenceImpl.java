/* 
 * SentenceImpl.java
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.NMEA;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.TalkerId;

/**
 * <p>
 * Base sentence parser for all sentence types. Provides general services, such
 * as validation and checksum calculation.
 * <p>
 * The basic structure of an NMEA sentence is: <br>
 * <code>
 * $&lt;address field&gt;,&lt;data field #1&gt;,&lt;data field #2&gt;,...,&lt;data field #n&gt;*&lt;checksum&gt;
 * </code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class SentenceImpl implements Sentence {

    // The first two characters after '$'.
    private TalkerId talkerId;
    // The next three characters after talker id.
    private final SentenceId sentenceId;
    // data fields, including the address field at index 0
    private final String[] dataFields;

    /**
     * Creates a new instance of SentenceImpl. Sentence type is resolved
     * automatically and checked against the supported types.
     * 
     * @param nmea A valid NMEA 0183 sentence
     * @throws IllegalArgumentException If the specified sentence is invalid or
     *             if sentence type is not supported.
     */
    public SentenceImpl(String nmea) {
        // check for valid sentence string
        if (!NMEA.isValid(nmea)) {
            String msg = String.format("Invalid data [%s]", nmea);
            throw new IllegalArgumentException(msg);
        }

        talkerId = parseTalkerId(nmea);
        sentenceId = parseSentenceId(nmea);

        // extract data fields
        if (nmea.contains(String.valueOf(CHECKSUM_DELIMITER))) {
            String data = nmea.substring(0, nmea.indexOf(CHECKSUM_DELIMITER));
            dataFields = data.split(String.valueOf(FIELD_DELIMITER), -1);
        } else {
            dataFields = nmea.split(String.valueOf(FIELD_DELIMITER), -1);
        }
    }

    /**
     * Creates a new instance of SentenceImpl. Sentence may be constructed only
     * if parameter <code>nmea</code> contains a valid NMEA 0183 sentence of the
     * specified <code>type</code>.
     * <p>
     * For example, GGASentenceImpl class should specify the type
     * <code>SentenceId.GGA</code> as <code>type</code> to succeed.
     * 
     * @param nmea NMEA sentence <code>String</code>
     * @param type Type of the sentence in <code>nmea</code> parameter
     * @throws IllegalArgumentException If the given String is not a valid NMEA
     *             0831 sentence or does not match the specified type.
     */
    protected SentenceImpl(String nmea, SentenceId type) {
        this(nmea);
        if (type == null) {
            throw new IllegalArgumentException(
                    "Sentence type must be specified");
        }
        if (!type.equals(getSentenceId())) {
            String msg = String.format("Sentence type mismatch [%s]",
                    getSentenceId());
            throw new IllegalArgumentException(msg);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.Sentence#getSentenceId()
     */
    public final SentenceId getSentenceId() {
        return sentenceId;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.Sentence#getTalkerId()
     */
    public final TalkerId getTalkerId() {
        return talkerId;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.parser.Sentence#setTalkerId(net.sf.marineapi.nmea
     * .util.TalkerId)
     */
    public final void setTalkerId(TalkerId id) {
        this.talkerId = id;
    }

    /**
     * Parse a single character from the specified sentence field.
     * 
     * @param index Field index in sentence
     * @return Character contained in the field
     * @throws ParseException If field contains more than one characters
     */
    protected final char getCharValue(int index) {
        String val = getStringValue(index);
        if (val.length() > 1) {
            String msg = String.format("Expected char, found String [%s]", val);
            throw new ParseException(msg);
        }
        return val.charAt(0);
    }

    /**
     * Parse double value from the specified sentence field.
     * 
     * @param index Field index in sentence
     * @return Field parsed by <code>Double.parseDouble()</code>
     */
    protected final double getDoubleValue(int index) {
        double value;
        try {
            value = Double.parseDouble(getStringValue(index));
        } catch (NumberFormatException ex) {
            throw new ParseException("Field does not contain double value", ex);
        }
        return value;
    }

    /**
     * Returns the number of data fields in sentence, including the address
     * field and excluding checksum.
     * 
     * @return The number of fields
     */
    protected final int getFieldCount() {
        return dataFields.length;
    }

    /**
     * Parse integer value from the specified sentence field.
     * 
     * @param index Field index in sentence
     * @return Field parsed by <code>Integer.parseInt()</code>
     */
    protected final int getIntValue(int index) {
        int value;
        try {
            value = Integer.parseInt(getStringValue(index));
        } catch (NumberFormatException ex) {
            throw new ParseException("Field does not contain int value", ex);
        }
        return value;
    }

    /**
     * Get contents of a data field as String. Field indexing starts from 0;
     * first field is the sentence ID, followed by the actual data fields.
     * Checksum is not considered as a data field and has no index.
     * <p>
     * Field indexing, let i = 0: <br>
     * <code>$&lt;id&gt;,&lt;i&gt;,&lt;i+1&gt;,&lt;i+2&gt;,...,&lt;i+n&gt;*&lt;checksum&gt;</code>
     * 
     * @param index Field index
     * @return Field value as String
     * @throws DataNotAvailableException If the field contains no value
     */
    protected final String getStringValue(int index) {
        String value = dataFields[index];
        if (value == null || value.isEmpty()) {
            throw new DataNotAvailableException("Data not available");
        }
        return value;
    }

    /**
     * Tells is if the field specified by the given index contains a value.
     * 
     * @param index Field index
     * @return True if field contains value, otherwise false.
     */
    protected final boolean hasValue(int index) {
        boolean result = true;
        try {
            getStringValue(index);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * Parses the sentence Id from specified NMEA sentence.
     * 
     * @param nmea NMEA 0183 sentence String
     * @return SentenceId Sentence ID of the specified sentence
     */
    static SentenceId parseSentenceId(String nmea) {
        String sid = nmea.substring(3, 6);
        try {
            return SentenceId.valueOf(sid);
        } catch (Exception e) {
            String msg = String.format("Unsupported sentence Id [%s]", sid);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Parses the talker Id from specified NMEA sentence.
     * 
     * @param nmea NMEA 0183 sentence String
     * @return TalkerId Talker ID of the specified sentence
     */
    static TalkerId parseTalkerId(String nmea) {
        String tid = nmea.substring(1, 3);
        try {
            return TalkerId.valueOf(tid);
        } catch (Exception e) {
            String msg = String.format("Unsupported talker Id [%s]", tid);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Get the String representation of the sentence without the line terminator
     * (&lt;CR&gt;&lt;LF&gt;). Checksum is calculated and appended at the end of
     * the sentence.
     * 
     * @return NMEA 0183 sentence String
     */
    @Override
    public final String toString() {

        StringBuilder sb = new StringBuilder(MAX_LENGTH);
        sb.append(BEGIN_CHAR);
        sb.append(talkerId.toString());
        sb.append(sentenceId.toString());

        for (int i = 1; i < dataFields.length; i++) {
            sb.append(FIELD_DELIMITER);
            sb.append(dataFields[i]);
        }

        String sentence = NMEA.appendChecksum(sb.toString());
        if (NMEA.isValid(sentence)) {
            return sentence;
        }

        String msg = String.format("Invalid result [%s]", sentence);
        throw new IllegalStateException(msg);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SentenceImpl) {
            Sentence s = (Sentence) o;
            return s.toString().equals(toString());
        }
        return false;
    }

}

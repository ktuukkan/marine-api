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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.TalkerId;

/**
 * <p>
 * Sentence parser that implements the common features of all sentences.
 * Provides also general services, such as validation and checksum calculation.
 * <p>
 * The basic structure of an NMEA sentence is: <br>
 * <code>
 * $&lt;address field&gt;,&lt;data field #1&gt;,&lt;data field #2&gt;,...,&lt;data field #n&gt;*&lt;checksum&gt;
 * </code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class Sentence {

    /**
     * Sentence terminator (&lt;CR&gt;&lt;LF&gt;)
     */
    public final static String TERMINATOR = "\r\n";
    /**
     * Sentence begin character
     */
    public final static char BEGIN_CHAR = '$';
    /**
     * Sentence data fields delimiter char
     */
    public final static char FIELD_DELIMITER = ',';
    /**
     * Checksum field delimiter char
     */
    public final static char CHECKSUM_DELIMITER = '*';
    /**
     * Maximum length of a sentence String, including <code>BEGIN_CHAR</code>
     * and <code>TERMINATOR</code>.
     */
    public final static int MAX_LENGTH = 82;
    /**
     * Address field index, the first field of all sentences.
     */
    public final static int ADDRESS_FIELD = 0;

    // The first two characters after '$'.
    private TalkerId talkerId;
    // The next three characters after talker id.
    private final SentenceId sentenceId;
    // Sentence data fields array, including the address field at index 0
    private final String[] dataFields;

    /**
     * Creates a new instance of Sentence. Sentence may be constructed only if
     * parameter <code>nmea</code> contains a valid NMEA 0183 sentence. Sentence
     * type is resolved automatically and checked against the supported types.
     * 
     * @param nmea NMEA sentence <code>String</code>
     * @throws IllegalArgumentException If the given String is not a valid NMEA
     *             0831 sentence or if sentence type is not supported.
     */
    public Sentence(String nmea) {
        // check for valid sentence string
        if (!Sentence.isValid(nmea)) {
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
     * Parses the talker Id from specified NMEA sentence.
     * 
     * @param nmea Sentence
     * @return TalkerId
     */
    private TalkerId parseTalkerId(String nmea) {
        String tid = nmea.substring(1, 3);
        try {
            return TalkerId.valueOf(tid);
        } catch (Exception e) {
            String msg = String.format("Unsupported talker Id [%s]", tid);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Parses the sentence Id from specified NMEA sentence.
     * 
     * @param nmea Sentence
     * @return SentenceId
     */
    private SentenceId parseSentenceId(String nmea) {
        String sid = nmea.substring(3, 6);
        try {
            return SentenceId.valueOf(sid);
        } catch (Exception e) {
            String msg = String.format("Unsupported sentence Id [%s]", sid);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Creates a new instance of Sentence. Sentence may be constructed only if
     * parameter <code>nmea</code> contains a valid NMEA 0183 sentence of the
     * specified <code>type</code>.
     * <p>
     * For example, SentenceGGA class should specify the type
     * <code>SentenceId.GGA</code> as <code>type</code> to succeed.
     * 
     * @param nmea NMEA sentence <code>String</code>
     * @param type Type of the sentence in <code>nmea</code> parameter
     * @throws IllegalArgumentException If the given String is not a valid NMEA
     *             0831 sentence or does not match the specified type.
     */
    protected Sentence(String nmea, SentenceId type) {
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

    /**
     * Calculate and append a checksum to sentence String. If the sentence
     * already contains the checksum, it is replaced with a new one.
     * <p>
     * For example, <br>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A</code><br>
     * results in <br>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*26</code>
     * <p>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*00</code><br>
     * results in <br>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*26</code>
     * 
     * @param sentence Sentence String without checksum.
     * @return The given String with checksum.
     */
    public static String appendChecksum(String sentence) {

        String str = sentence;

        int i = str.indexOf(CHECKSUM_DELIMITER);
        if (i != -1) {
            str = str.substring(0, i);
        }

        return str + CHECKSUM_DELIMITER + calculateChecksum(str);
    }

    /**
     * Calculates the checksum of sentence String. Checksum is a XOR of each
     * character between, but not including, the $ and * characters. The
     * resulting hex value is returned as a String in two digit format, padded
     * with a leading zero if necessary. The method will calculate the checksum
     * for any given String and the sentence validity is not checked.
     * 
     * @param nmea NMEA Sentence with or without checksum.
     * @return Checksum String (Hex value, with leading zero if necessary)
     */
    public static String calculateChecksum(String nmea) {
        char ch;
        int sum = 0;
        for (int i = 0; i < nmea.length(); i++) {
            ch = nmea.charAt(i);
            if (ch == BEGIN_CHAR) {
                continue;
            } else if (ch == CHECKSUM_DELIMITER) {
                break;
            } else if (sum == 0) {
                sum = (byte) ch;
            } else {
                sum ^= (byte) ch;
            }
        }
        return String.format("%02X", sum);
    }

    /**
     * Validates of the specified String against NMEA sentence format. Sentence
     * is considered valid if it (1) starts with '$', (2) is at most 80
     * characters long (without &lt;CR&gt;&lt;LF&gt;), (3) contains no illegal
     * characters and (4) has a correct checksum. Sentences without a checksum
     * are validated by checking the general sentence characteristics.
     * 
     * @param nmea Sentence string.
     * @return <code>True</code> if valid, otherwise <code>false</code>.
     */
    public static boolean isValid(String nmea) {
        if (nmea == null) {
            return false;
        }

        final String re;
        int chkd = nmea.indexOf(String.valueOf(CHECKSUM_DELIMITER));

        if (chkd < 0) {
            re = "^[$]{1}[A-Z0-9]{5}[,][A-Za-z0-9,. -]{0,73}$";
            return nmea.matches(re);
        }

        re = "^[$]{1}[A-Z0-9]{5}[,][A-Za-z0-9,. -]{0,70}[*][A-F0-9]{2}$";
        int start = nmea.indexOf(CHECKSUM_DELIMITER) + 1;
        String chk = nmea.substring(start, nmea.length());

        return nmea.matches(re) && chk.equals(calculateChecksum(nmea));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Sentence) {
            Sentence s = (Sentence) o;
            return s.toString().equals(toString());
        }
        return false;
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
     * Get the sentence ID that specifies the type of a sentence. ID is the last
     * three characters of the address field. For example, if the address field
     * is <code>$GPGGA</code>, this method returns the enum <code>GGA</code>.
     * 
     * @return SentenceId enum
     */
    public final SentenceId getSentenceId() {
        return sentenceId;
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
     * Gets the talker ID of the sentence. Talker ID is the next two characters
     * after the '$' in sentence address field. For example, if the address
     * field is <code>$GPGGA</code>, the method returns the enum <code>GP</code>
     * .
     * 
     * @return Talker id String.
     */
    public final TalkerId getTalkerId() {
        return talkerId;
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
     * Set the talker ID of the sentence. Typically, the ID might be changed if
     * the sentence is to be sent from a computer to an NMEA device.
     * 
     * @param id TalkerId to set
     */
    public final void setTalkerId(TalkerId id) {
        this.talkerId = id;
    }

    /**
     * Get the String representation of the sentence without the line terminator
     * (&lt;CR&gt;&lt;LF&gt;). Checksum is calculated and appended at the end of
     * the sentence.
     * 
     * @return sentence string
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

        String sentence = appendChecksum(sb.toString());
        if (isValid(sentence)) {
            return sentence;
        }

        String msg = String.format("Invalid result [%s]", sentence);
        throw new IllegalStateException(msg);
    }
}

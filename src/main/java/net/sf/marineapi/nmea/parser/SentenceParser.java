/*
 * SentenceParser.java
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
package net.sf.marineapi.nmea.parser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.marineapi.nmea.sentence.Checksum;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.SentenceValidator;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * <p>
 * Base class for all NMEA 0183 sentence parsers. Contains generic methods such
 * as data field setters and getters, data formatting, validation etc.
 * <p>
 * NMEA 0183 data is transmitted in form of ASCII Strings that are called
 * <em>sentences</em>. Each sentence starts with a '$', a two letter
 * <em>talker ID</em>, a three letter <em>sentence ID</em>, followed by a number
 * of comma separated <em>data fields</em>, <em>optional checksum</em> and a
 * carriage return/line feed terminator (<code>CR/LF</code>). Sentence may
 * contain up to 82 characters including the <code>CR/LF</code>. If data for
 * certain field is not available, the field value is simply omitted, but the
 * commas that would delimit it are still sent, with no space between them.
 * <p>
 * Sentence structure:<br>
 * <code>
 * $&lt;id&gt;,&lt;field #0&gt;,&lt;field #1&gt;,...,&lt;field #n&gt;*&lt;checksum&gt;(CR/LF)
 * </code>
 * <p>
 * For more details, see <a href="http://catb.org/gpsd/NMEA.html"
 * target="_blank">NMEA Revealed</a> by Eric S. Raymond.
 * <p>
 * This class can also be used to implement and integrate parsers not provided
 * by in the library. See {@link SentenceFactory} for more instructions.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceParser implements Sentence {

	// The first character which will be '$' most of the times but could be '!'.
	private char beginChar;

	// The first two characters after '$'.
	private TalkerId talkerId;

	// The next three characters after talker id.
	private final String sentenceId;

	// actual data fields (sentence id and checksum omitted)
	private List<String> fields = new ArrayList<String>();

	/**
	 * Creates a new instance of SentenceParser. Validates the input String and
	 * resolves talker id and sentence type.
	 * 
	 * @param nmea A valid NMEA 0183 sentence
	 * @throws IllegalArgumentException If the specified sentence is invalid or
	 *             if sentence type is not supported.
	 */
	public SentenceParser(String nmea) {

		if (!SentenceValidator.isValid(nmea)) {
			String msg = String.format("Invalid data [%s]", nmea);
			throw new IllegalArgumentException(msg);
		}

		beginChar = nmea.charAt(0);
		talkerId = TalkerId.parse(nmea);
		sentenceId = SentenceId.parseStr(nmea);
		
		int begin = nmea.indexOf(Sentence.FIELD_DELIMITER) + 1;
		int end = Checksum.index(nmea);
		
		String csv = nmea.substring(begin, end);
		String[] values = csv.split(String.valueOf(FIELD_DELIMITER), -1);
		fields.addAll(Arrays.asList(values));
	}

	/**
	 * Creates a new empty sentence with specified begin char, talker id,
	 * sentence id and number of fields.
	 *
	 * @param begin Begin char, $ or !
	 * @param tid TalkerId to set
	 * @param sid SentenceId to set
	 * @param size Number of sentence data fields
	 */
	protected SentenceParser(char begin, TalkerId tid, SentenceId sid, int size) {
		this(begin, tid, sid.toString(), size);
	}

	/**
	 * Creates a new empty sentence with specified begin char, talker id,
	 * sentence id and number of fields.
	 * 
	 * @param begin The begin character, e.g. '$' or '!'
	 * @param talker TalkerId to set
	 * @param type Sentence id as String, e.g. "GGA or "GLL".
	 * @param size Number of sentence data fields
	 */
	protected SentenceParser(char begin, TalkerId talker, String type, int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Minimum number of fields is 1");
		}
		if (talker == null) {
			throw new IllegalArgumentException("Talker ID must be specified");
		}
		if (type == null || "".equals(type)) {
			throw new IllegalArgumentException("Sentence ID must be specified");
		}
		beginChar = begin;
		talkerId = talker;
		sentenceId = type;
		String[] values = new String[size];
		Arrays.fill(values, "");
		fields.addAll(Arrays.asList(values));
	}

	/**
	 * Creates a new instance of SentenceParser. Parser may be constructed only
	 * if parameter <code>nmea</code> contains a valid NMEA 0183 sentence of the
	 * specified <code>type</code>.
	 * <p>
	 * For example, GGA sentence parser should specify "GGA" as the type.
	 *
	 * @param nmea NMEA 0183 sentence String
	 * @param type Expected type of the sentence in <code>nmea</code> parameter
	 * @throws IllegalArgumentException If the specified sentence is not a valid
	 *             or is not of expected type.
	 */
	protected SentenceParser(String nmea, String type) {
		this(nmea);
		if (type == null || "".equals(type)) {
			throw new IllegalArgumentException(
				"Sentence type must be specified.");
		}
		String sid = getSentenceId();
		if (!sid.equals(type)) {
			String ptrn = "Sentence id mismatch; expected [%s], found [%s].";
			String msg = String.format(ptrn, type, sid);
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Creates a new empty sentence with specified talker and sentence IDs.
	 * 
	 * @param talker Talker type Id, e.g. "GP" or "LC".
	 * @param type Sentence type Id, e.g. "GGA or "GLL".
	 * @param size Number of data fields
	 */
	protected SentenceParser(TalkerId talker, String type, int size) {
		this(Sentence.BEGIN_CHAR, talker, type, size);
	}

	/**
	 * Creates a new instance of SentenceParser with specified sentence data.
	 * Type of the sentence is checked against the specified expected sentence
	 * type id.
	 * 
	 * @param nmea Sentence String
	 * @param type Sentence type enum
	 */
	SentenceParser(String nmea, SentenceId type) {
		this(nmea, type.toString());
	}

	/**
	 * Creates a new instance of SentenceParser without any data.
	 * 
	 * @param tid Talker id to set in sentence
	 * @param sid Sentence id to set in sentence
	 * @param size Number of data fields following the sentence id field
	 */
	SentenceParser(TalkerId tid, SentenceId sid, int size) {
		this(tid, sid.toString(), size);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof SentenceParser) {
			SentenceParser sp = (SentenceParser) obj;
			return sp.toString().equals(toString());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#getBeginChar()
	 */
	public final char getBeginChar() {
		return beginChar;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#getFieldCount()
	 */
	public final int getFieldCount() {
		if (fields == null) {
			return 0;
		}
		return fields.size();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#getSentenceId()
	 */
	public final String getSentenceId() {
		return sentenceId;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#getTalkerId()
	 */
	public final TalkerId getTalkerId() {
		return talkerId;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public boolean isAISSentence() {
		final String[] types = {"VDO", "VDM"};
		return Arrays.asList(types).contains(getSentenceId());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#isProprietary()
	 */
	public boolean isProprietary() {
		return TalkerId.P.equals(getTalkerId());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#isValid()
	 */
	public boolean isValid() {
		return SentenceValidator.isValid(toString());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#reset()
	 */
	public final void reset() {
		for (int i = 0; i < fields.size(); i++) {
			fields.set(i, "");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#setBeginChar(char)
	 */
	public void setBeginChar(char ch) {
		if (ch != BEGIN_CHAR && ch != ALTERNATIVE_BEGIN_CHAR) {
			String msg = "Invalid begin char; expected '$' or '!'";
			throw new IllegalArgumentException(msg);
		}
		beginChar = ch;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.Sentence#setTalkerId(net.sf.marineapi.
	 * nmea.util.TalkerId)
	 */
	public final void setTalkerId(TalkerId id) {
		this.talkerId = id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#toSentence()
	 */
	public final String toSentence() {
		String s = toString();
		if (!SentenceValidator.isValid(s)) {
			String msg = String.format("Validation failed [%s]", toString());
			throw new IllegalStateException(msg);
		}
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.Sentence#toSentence(int)
	 */
	public final String toSentence(int maxLength) {
		String s = toSentence();
		if (s.length() > maxLength) {
			String msg = "Sentence max length exceeded " + maxLength;
			throw new IllegalStateException(msg);
		}
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder(MAX_LENGTH);
		sb.append(talkerId.toString());
		sb.append(sentenceId);
		
		for (String field : fields) {
			sb.append(FIELD_DELIMITER);
			sb.append(field == null ? "" : field);
		}
		
		final String checksum = Checksum.xor(sb.toString());
		sb.append(CHECKSUM_DELIMITER);
		sb.append(checksum);
		sb.insert(0, beginChar);

		return sb.toString();
	}

	/**
	 * Parse a single character from the specified sentence field.
	 * 
	 * @param index Data field index in sentence
	 * @return Character contained in the field
	 * @throws net.sf.marineapi.nmea.parser.ParseException If field contains more
	 *             than one character
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
	 * @param index Data field index in sentence
	 * @return Field as parsed by {@link java.lang.Double#parseDouble(String)}
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
	 * @return Field parsed by {@link java.lang.Integer#parseInt(String)}
	 */
	protected final int getIntValue(int index) {
		int value;
		try {
			value = Integer.parseInt(getStringValue(index));
		} catch (NumberFormatException ex) {
			throw new ParseException("Field does not contain integer value", ex);
		}
		return value;
	}

	/**
	 * Get contents of a data field as a String. Field indexing is zero-based.
	 * The address field (e.g. <code>$GPGGA</code>) and checksum at the end are
	 * not considered as a data fields and cannot therefore be fetched with this
	 * method.
	 * <p>
	 * Field indexing, let i = 1: <br>
	 * <code>$&lt;id&gt;,&lt;i&gt;,&lt;i+1&gt;,&lt;i+2&gt;,...,&lt;i+n&gt;*&lt;checksum&gt;</code>
	 * 
	 * @param index Field index
	 * @return Field value as String
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the field is
	 *             empty
	 */
	protected final String getStringValue(int index) {
		String value = fields.get(index);
		if (value == null || "".equals(value)) {
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
		return fields.size() > index &&
			fields.get(index) != null && !fields.get(index).isEmpty();
	}

	/**
	 * Set a character in specified field.
	 * 
	 * @param index Field index
	 * @param value Value to set
	 */
	protected final void setCharValue(int index, char value) {
		setStringValue(index, String.valueOf(value));
	}

	/**
	 * Set degrees value, e.g. course or heading.
	 * 
	 * @param index Field index where to insert value
	 * @param deg The degrees value to set
	 * @throws IllegalArgumentException If degrees value out of range [0..360]
	 */
	protected final void setDegreesValue(int index, double deg) {
		if (deg < 0 || deg > 360) {
			throw new IllegalArgumentException("Value out of bounds [0..360]");
		}
		setDoubleValue(index, deg, 3, 1);
	}

	/**
	 * Set double value in specified field. Value is set "as-is" without any
	 * formatting or rounding.
	 * 
	 * @param index Field index
	 * @param value Value to set
	 * @see #setDoubleValue(int, double, int, int)
	 */
	protected final void setDoubleValue(int index, double value) {
		setStringValue(index, String.valueOf(value));
	}

	/**
	 * Set double value in specified field, with given number of digits before
	 * and after the decimal separator ('.'). When necessary, the value is
	 * padded with leading zeros and/or rounded to meet the requested number of
	 * digits.
	 * 
	 * @param index Field index
	 * @param value Value to set
	 * @param leading Number of digits before decimal separator
	 * @param decimals Maximum number of digits after decimal separator
	 * @see #setDoubleValue(int, double)
	 */
	protected final void setDoubleValue(int index, double value, int leading,
		int decimals) {

		StringBuilder pattern = new StringBuilder();
		for (int i = 0; i < leading; i++) {
			pattern.append('0');
		}
		if (decimals > 0) {
			pattern.append('.');
			for (int i = 0; i < decimals; i++) {
				pattern.append('0');
			}
		}
		if (pattern.length() == 0) {
			pattern.append('0');
		}

		DecimalFormat nf = new DecimalFormat(pattern.toString());
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(dfs);

		setStringValue(index, nf.format(value));
	}

	/**
	 * Sets the number of sentence data fields. Increases or decreases the
	 * fields array, values in fields not affected by the change remain
	 * unchanged. Does nothing if specified new size is equal to count returned
	 * by {@link #getFieldCount()}.
	 * 
	 * @param size Number of data fields, must be greater than zero.
	 */
	protected final void setFieldCount(int size) {
		if (size < 1) {
			throw new IllegalArgumentException(
				"Number of fields must be greater than zero.");
		}
		
		if(size < fields.size()) {
			fields = fields.subList(0, size);
		} else if (size > fields.size()) {
			for(int i = fields.size(); i < size; i++) {
				fields.add("");
			}
		}
	}

	/**
	 * Set integer value in specified field.
	 * 
	 * @param index Field index
	 * @param value Value to set
	 */
	protected final void setIntValue(int index, int value) {
		setStringValue(index, String.valueOf(value));
	}

	/**
	 * Set integer value in specified field, with specified minimum number of
	 * digits. Leading zeros are added to value if when necessary.
	 * 
	 * @param index Field index
	 * @param value Value to set
	 * @param leading Number of digits to use.
	 */
	protected final void setIntValue(int index, int value, int leading) {
		String pattern = "%d";
		if (leading > 0) {
			pattern = "%0" + leading + "d";
		}
		setStringValue(index, String.format(pattern, value));
	}

	/**
	 * Set String value in specified data field.
	 * 
	 * @param index Field index
	 * @param value String to set, <code>null</code> converts to empty String.
	 */
	protected final void setStringValue(int index, String value) {
		fields.set(index, value == null ? "" : value);
	}

	/**
	 * Replace multiple fields with given String array, starting at the
	 * specified index. If parameter <code>first</code> is zero, all sentence
	 * fields are replaced.
	 * <p>
	 * If the length of <code>newFields</code> does not fit in the sentence
	 * field count or it contains less values, fields are removed or added
	 * accordingly. As the result, total number of fields may increase or
	 * decrease. Thus, if the sentence field count must not change, you may need
	 * to add empty Strings to <code>newFields</code> in order to preserve the
	 * original number of fields. Also, all existing values after
	 * <code>first</code> are lost.
	 * 
	 * @param first Index of first field to set
	 * @param newFields Array of Strings to set
	 */
	protected final void setStringValues(int first, String[] newFields) {
		
		List<String> temp = new ArrayList<String>();		
		temp.addAll(fields.subList(0, first));
		
		for (String field : newFields) {
			temp.add(field == null ? "" : field);
		}
		fields.clear();
		fields = temp;
	}
}

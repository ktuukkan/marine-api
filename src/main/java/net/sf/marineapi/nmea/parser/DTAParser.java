/* 
 * DTAParser.java
 * Copyright (C) 2019 Kimmo Tuukkanen
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.marineapi.nmea.sentence.DTASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * DTA sentence parser.
 * 
 * @author Bob Schwarz
 * @see <a href="https://github.com/LoadBalanced/marine-api">marine-api fork</a>
 * 
 */
class DTAParser extends SentenceParser implements DTASentence {


	private static final int CHANNEL_NUMBER = 0;
	private static final int GAS_CONCENTRATION = 1;
	private static final int CONFIDENCE_FACTOR_R2 = 2;
	private static final int DISTANCE= 3;
	private static final int LIGHT_LEVEL= 4;
	private static final int DATE_TIME= 5;
	private static final int SER_NUMBER = 6;
	private static final int STATUS_CODE= 7;
	
	private static final DateFormat DATE_PARSER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
	private int offset = getFieldCount() - 8;
	
    /**
     * Creates a new instance of DTAParser with 8 data fields.
     * 
     * @param talker - DTA talkerId
     */
    public DTAParser(TalkerId talker) {
        super(talker, SentenceId.DTA.name(), 8);
    }
	
    /**
     * Creates a new instance of DTAParser with specified data fields.
     * 
     * @param talker - DTA talkerId
     * @param size - number of data fields in DTASentence (not counting header and checksum).
     */
	public DTAParser(TalkerId talker, int size) {
		super(talker, SentenceId.DTA, size);
	}
	
    /**
     * Creates a new instance of DTAParser with specified type and data fields.
     * 
     * @param talker - DTA talkerId
     * @param type - SentenceId enum
     * @param size - number of data fields in DTASentence (not counting header and checksum).
     * 
     */
	public DTAParser(TalkerId talker, SentenceId type, int size) {
		super(talker, type, size);
	}

    /**
     * Creates a new instance of DTAParser.
     * 
     * @param nmea - DTA sentence String
     */
    public DTAParser(String nmea) {
        super(nmea, SentenceId.DTA.name());
    }

    /**
     * Creates a new instance of DTAParser.
     * 
     * @param nmea - DTA sentence String
     * @param type - SentenceId enum
     */
	public DTAParser(String nmea, SentenceId type) {
		super(nmea, type.toString());
	}
		
	/**
	 * Returns the field index fixed with possible offset.
	*/
	private int getFieldIndex(int field) {
		return offset + field;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getChannelNumber()
	 */
	@Override
	public int getChannelNumber() {
		if (offset == -1) {
			return 1;
		}
		return getIntValue(CHANNEL_NUMBER);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getGasConcentration()
	 */
	@Override
	public double getGasConcentration() {
		return getDoubleValue(getFieldIndex(GAS_CONCENTRATION));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getConfidenceFactorR2()
	 */
	@Override
	public int getConfidenceFactorR2() {
		return getIntValue(getFieldIndex(CONFIDENCE_FACTOR_R2));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getDistance()
	 */
	@Override
	public double getDistance() {
		return getDoubleValue(getFieldIndex(DISTANCE));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getLightLevel()
	 */
	@Override
	public int getLightLevel() {
		return getIntValue(getFieldIndex(LIGHT_LEVEL));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getDateTime()
	 */
	@Override
	public Date getDateTime() {
		Date value;
		try {
			value = DATE_PARSER.parse(getStringValue(getFieldIndex(DATE_TIME)));
		} catch (java.text.ParseException ex) {
			throw new ParseException("Field does not contain date value", ex);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getSerialNumber()
	 */
	@Override
	public String getSerialNumber() {
		return getStringValue(getFieldIndex(SER_NUMBER));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.DTASentence#getStatusCode()
	 */
	@Override
	public int getStatusCode() {
		return getIntValue(getFieldIndex(STATUS_CODE));
	}
}

/*
 * TTMParser.java
 * Copyright (C) 2014 Johan Bergkvist
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

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TTMSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.AcquisitionType;
import net.sf.marineapi.nmea.util.TargetStatus;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.nmea.util.Units;

/**
 * TTM sentence parser.
 *
 * @author Johan Bergkvist
 */
class TTMParser extends SentenceParser implements TTMSentence {

	private static final int NUMBER = 0;
	private static final int DISTANCE = 1;
	private static final int BEARING = 2;
	private static final int BEARING_TRUE_REL = 3;
	private static final int SPEED = 4;
	private static final int COURSE = 5;
	private static final int COURSE_TRUE_REL = 6;
	private static final int DISTANCE_CPA = 7;
	private static final int TIME_CPA = 8;
	private static final int UNITS = 9;
	private static final int NAME = 10;
	private static final int STATUS = 11;
	private static final int REFERENCE = 12;
	private static final int UTC_TIME = 13;
	private static final int ACQUISITON_TYPE = 14;

	/**
	 * Create a new instance of TTMParser.
	 *
	 * @param nmea
	 *            TTM sentence String.
	 * @throws IllegalArgumentException
	 *             If specified sentence is invalid.
	 */
	public TTMParser(String nmea) {
		super(nmea, SentenceId.TTM);
	}

	/**
	 * Create a TTM parser with an empty sentence.
	 *
	 * @param talker
	 *            TalkerId to set
	 */
	public TTMParser(TalkerId talker) {
		super(talker, SentenceId.TTM, 15);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TimeSentence#getTime()
	 */
	@Override
	public Time getTime() {
		String str = getStringValue(UTC_TIME);
		return new Time(str);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TimeSentence#setTime()
	 */
	@Override
	public void setTime(Time t) {
		/*
		 * The TTM specification calls for seconds with TWO decimals, not the
		 * usual three implemented by the Time.toString(). So we create our own
		 * string.
		 */
		String str = String.format("%02d%02d", t.getHour(), t.getMinutes());

		DecimalFormat nf = new DecimalFormat("00.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(dfs);

		str += nf.format(t.getSeconds());
		setStringValue(UTC_TIME, str);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getNumber()
	 */
	@Override
	public int getNumber() {
		return getIntValue(NUMBER);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getDistance()
	 */
	@Override
	public double getDistance() {
		return getDoubleValue(DISTANCE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getBearing()
	 */
	@Override
	public double getBearing() {
		return getDoubleValue(BEARING);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getSpeed()
	 */
	@Override
	public double getSpeed() {
		return getDoubleValue(SPEED);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getCourse()
	 */
	@Override
	public double getCourse() {
		return getDoubleValue(COURSE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getDistanceOfCPA()
	 */
	@Override
	public double getDistanceOfCPA() {
		return getDoubleValue(DISTANCE_CPA);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getTimeToCPA()
	 */
	@Override
	public double getTimeToCPA() {
		return getDoubleValue(TIME_CPA);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getTimeToCPA()
	 */
	@Override
	public Units getUnits() {
		return Units.valueOf(getCharValue(UNITS));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getName()
	 */
	@Override
	public String getName() {
		return getStringValue(NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getStatus()
	 */
	@Override
	public TargetStatus getStatus() {
		return TargetStatus.valueOf(getCharValue(STATUS));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getAcquisitionType()
	 */
	@Override
	public AcquisitionType getAcquisitionType() {
		return AcquisitionType.valueOf(getCharValue(ACQUISITON_TYPE));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getReference()
	 */
	@Override
	public boolean getReference() {
		return getCharValue(REFERENCE) == 'R';
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setNumber()
	 */
	@Override
	public void setNumber(int number) {
		setIntValue(NUMBER, number, 2);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setDistance()
	 */
	@Override
	public void setDistance(double distance) {
		setDoubleValue(DISTANCE, distance, 1, 1);
		setCharValue(UNITS, 'N');
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setBearing()
	 */
	@Override
	public void setBearing(double bearing) {
		setDoubleValue(BEARING, bearing, 1, 1);
		setCharValue(BEARING_TRUE_REL, 'T');
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setSpeed()
	 */
	@Override
	public void setSpeed(double speed) {
		setDoubleValue(SPEED, speed, 1, 1);
		setCharValue(UNITS, 'N');
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setCourse()
	 */
	@Override
	public void setCourse(double course) {
		setDoubleValue(COURSE, course, 1, 1);
		setCharValue(COURSE_TRUE_REL, 'T');
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setDistanceOfCPA()
	 */
	@Override
	public void setDistanceOfCPA(double distance) {
		setDoubleValue(DISTANCE_CPA, distance, 1, 1);
		setCharValue(UNITS, 'N');
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setTimeToCPA()
	 */
	@Override
	public void setTimeToCPA(double minutes) {
		setDoubleValue(TIME_CPA, minutes, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setName()
	 */
	@Override
	public void setName(String name) {
		setStringValue(NAME, name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setStatus()
	 */
	@Override
	public void setStatus(TargetStatus status) {
		setCharValue(STATUS, status.toChar());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setReference()
	 */
	@Override
	public void setReference(boolean isReference) {
		if (isReference) {
			setCharValue(REFERENCE, 'R');
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#setAcquisitionType()
	 */
	@Override
	public void setAcquisitionType(AcquisitionType acquisitionType) {
		setCharValue(ACQUISITON_TYPE, acquisitionType.toChar());
	}
}

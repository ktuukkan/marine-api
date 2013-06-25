/* 
 * GGAParser.java
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

import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.nmea.util.Units;

/**
 * GGA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
class GGAParser extends PositionParser implements GGASentence {

	// GGA field indices
	private static final int UTC_TIME = 0;
	private static final int LATITUDE = 1;
	private static final int LAT_HEMISPHERE = 2;
	private static final int LONGITUDE = 3;
	private static final int LON_HEMISPHERE = 4;
	private static final int FIX_QUALITY = 5;
	private static final int SATELLITES_IN_USE = 6;
	private static final int HORIZONTAL_DILUTION = 7;
	private static final int ALTITUDE = 8;
	private static final int ALTITUDE_UNITS = 9;
	private static final int GEOIDAL_HEIGHT = 10;
	private static final int HEIGHT_UNITS = 11;
	private static final int DGPS_AGE = 12;
	private static final int DGPS_STATION_ID = 13;

	/**
	 * Creates a new instance of GGA parser.
	 * 
	 * @param nmea GGA sentence String.
	 * @throws IllegalArgumentException If the specified sentence is invalid or
	 *             not a GGA sentence.
	 */
	public GGAParser(String nmea) {
		super(nmea, SentenceId.GGA);
	}

	/**
	 * Creates GSA parser with empty sentence.
	 * 
	 * @param talker TalkerId to set
	 */
	public GGAParser(TalkerId talker) {
		super(talker, SentenceId.GGA, 14);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getAltitude()
	 */
	public double getAltitude() {
		return getDoubleValue(ALTITUDE);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getAltitudeUnits()
	 */
	public Units getAltitudeUnits() {
		char ch = getCharValue(ALTITUDE_UNITS);
		if (ch != ALT_UNIT_METERS && ch != ALT_UNIT_FEET) {
			String msg = "Invalid altitude unit indicator: %s";
			throw new ParseException(String.format(msg, ch));
		}
		return Units.valueOf(ch);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getDgpsAge()
	 */
	public double getDgpsAge() {
		return getDoubleValue(DGPS_AGE);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getDgpsStationId()
	 */
	public String getDgpsStationId() {
		return getStringValue(DGPS_STATION_ID);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getFixQuality()
	 */
	public GpsFixQuality getFixQuality() {
		return GpsFixQuality.valueOf(getIntValue(FIX_QUALITY));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getGeoidalHeight()
	 */
	public double getGeoidalHeight() {
		return getDoubleValue(GEOIDAL_HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getGeoidalHeightUnits()
	 */
	public Units getGeoidalHeightUnits() {
		return Units.valueOf(getCharValue(HEIGHT_UNITS));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getHorizontalDOP()
	 */
	public double getHorizontalDOP() {
		return getDoubleValue(HORIZONTAL_DILUTION);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.PositionSentence#getPosition()
	 */
	public Position getPosition() {
		double lat = parseLatitude(LATITUDE);
		double lon = parseLongitude(LONGITUDE);
		CompassPoint lath = parseHemisphereLat(LAT_HEMISPHERE);
		CompassPoint lonh = parseHemisphereLon(LON_HEMISPHERE);
		Position pos = new Position(lat, lath, lon, lonh);
		try {
			double alt = getAltitude();
			if (getAltitudeUnits().equals(Units.FEET)) {
				alt = (alt / 0.3048);
			}
			pos.setAltitude(alt);
		} catch (DataNotAvailableException e) {
			// alt not available, nevermind
		}
		return pos;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#getSatelliteCount()
	 */
	public int getSatelliteCount() {
		return getIntValue(SATELLITES_IN_USE);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.TimeSentence#getTime()
	 */
	public Time getTime() {
		String str = getStringValue(UTC_TIME);
		int h = Integer.parseInt(str.substring(0, 2));
		int m = Integer.parseInt(str.substring(2, 4));
		double s = Double.parseDouble(str.substring(4, 6));
		return new Time(h, m, s);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setAltitude(double)
	 */
	public void setAltitude(double alt) {
		setDoubleValue(ALTITUDE, alt, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setAltitudeUnits(net.sf.marineapi
	 * .nmea.util.Units)
	 */
	public void setAltitudeUnits(Units unit) {
		setCharValue(ALTITUDE_UNITS, unit.toChar());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setDgpsAge(int)
	 */
	public void setDgpsAge(double age) {
		setDoubleValue(DGPS_AGE, age, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setDgpsStationId(java.lang
	 * .String)
	 */
	public void setDgpsStationId(String id) {
		setStringValue(DGPS_STATION_ID, id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setFixQuality(net.sf.marineapi
	 * .nmea.util.GpsFixQuality)
	 */
	public void setFixQuality(GpsFixQuality quality) {
		setIntValue(FIX_QUALITY, quality.toInt());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setGeoidalHeight(double)
	 */
	public void setGeoidalHeight(double height) {
		setDoubleValue(GEOIDAL_HEIGHT, height, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.GGASentence#setGeoidalHeightUnits(net.
	 * sf.marineapi.nmea.util.Units)
	 */
	public void setGeoidalHeightUnits(Units unit) {
		setCharValue(HEIGHT_UNITS, unit.toChar());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.sentence.GGASentence#setHorizontalDOP(double)
	 */
	public void setHorizontalDOP(double hdop) {
		setDoubleValue(HORIZONTAL_DILUTION, hdop, 1, 1);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.PositionSentence#setPosition(net.sf.marineapi
	 * .nmea.util.Position)
	 */
	public void setPosition(Position pos) {
		setLatitude(LATITUDE, pos.getLatitude());
		setLongitude(LONGITUDE, pos.getLongitude());
		setLatHemisphere(LAT_HEMISPHERE, pos.getLatHemisphere());
		setLonHemisphere(LON_HEMISPHERE, pos.getLonHemisphere());
		setAltitude(pos.getAltitude());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.sentence.TimeSentence#setTime(net.sf.marineapi.
	 * nmea.util.Time)
	 */
	public void setTime(Time t) {
		int h = t.getHour();
		int m = t.getMinutes();
		int s = (int) Math.floor(t.getSeconds());
		String time = String.format("%02d%02d%02d", h, m, s);
		setStringValue(UTC_TIME, time);
	}

}

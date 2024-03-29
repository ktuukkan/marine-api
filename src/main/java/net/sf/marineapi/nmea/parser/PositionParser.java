/*
 * PositionParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Position;

/**
 * <p>
 * Abstract base class for sentence parsers that handle geographic position or
 * waypoint data and parse NMEA 0183 lat/lon values.</p>
 * <p>
 * Notice that {@link net.sf.marineapi.nmea.sentence.PositionSentence} interface
 * is not implemented by this parser because the extending parser may not always
 * provide current location.
 *
 * @author Kimmo Tuukkanen
 */
public abstract class PositionParser extends SentenceParser {

	/**
	 * Constructor.
	 *
	 * @param nmea Sentence string to parse.
 	 * @param type Expected Sentence ID
	 * @see SentenceParser#SentenceParser(String, SentenceId)
	 */
	protected PositionParser(String nmea, SentenceId type) {
		super(nmea, type);
	}

	/**
	 * Constructor for empty sentence.
	 *
	 * @param talker Talker ID to set
	 * @param type Sentence ID to set
	 * @param size Number of empty data fields to set.
	 * @see SentenceParser#SentenceParser(TalkerId, SentenceId, int)
	 */
	protected PositionParser(TalkerId talker, SentenceId type, int size) {
		super(talker, type, size);
	}

	/**
	 * Parses the hemisphere of latitude from specified field.
	 *
	 * @param index Index of field that contains the latitude hemisphere value.
	 * @return Hemisphere of latitude
	 */
	protected CompassPoint parseHemisphereLat(int index) {
		char ch = getCharValue(index);
		return PositionParser.parseHemisphereLat(ch);
	}

	/**
	 * Parses the hemisphere of longitude from the specified field.
	 *
	 * @param index Field index for longitude hemisphere indicator
	 * @return Hemisphere of longitude
	 */
	protected CompassPoint parseHemisphereLon(int index) {
		char ch = getCharValue(index);
		return PositionParser.parseHemisphereLon(ch);
	}

	/**
	 * Parse latitude or longitude degrees and minutes from the specified field.
	 * Assumes the {@code dddmm.mmmm} formatting where the two digits to the
	 * left of dot denote full minutes and any remaining digits to the left
	 * denote full degrees (usually padded with leading zeros). Digits to the
	 * right of the dot denote the minute decimals.
	 *
	 * @see PositionParser
	 * @param index Index of the lat/lon field.
	 * @return Degrees decimal value
	 */
	protected double parseDegrees(int index) {
		String field = getStringValue(index);
		return PositionParser.parseDegrees(field);
	}

	/**
	 * Parses a {@code Position} from specified fields.
	 *
	 * @see PositionParser
	 * @param latIndex Latitude field index
	 * @param latHemIndex Latitude hemisphere field index
	 * @param lonIndex Longitude field index
	 * @param lonHemIndex Longitude hemisphere field index
	 * @return Position object
	 */
	protected Position parsePosition(int latIndex, int latHemIndex,
		int lonIndex, int lonHemIndex) {

		final String latitudeValue = getStringValue(latIndex);
		final char latitudeHemisphereIndicator = getCharValue(latHemIndex);

		final String longitudeValue = getStringValue(lonIndex);
		final char longitudeHemisphereIndicator = getCharValue(lonHemIndex);

		return PositionParser.parsePosition(
				latitudeValue, latitudeHemisphereIndicator,
				longitudeValue, longitudeHemisphereIndicator);
	}

	/**
	 * Set the hemisphere of latitude in specified field.
	 *
	 * @param field Field index
	 * @param hem Direction.NORTH or Direction.SOUTH
	 * @throws IllegalArgumentException If specified Direction is other than
	 *             NORTH or SOUTH.
	 */
	protected void setLatHemisphere(int field, CompassPoint hem) {
		if (hem != CompassPoint.NORTH && hem != CompassPoint.SOUTH) {
			throw new IllegalArgumentException("Invalid latitude hemisphere: "
				+ hem);
		}
		setCharValue(field, hem.toChar());
	}

	/**
	 * Sets the latitude value in specified field, formatted in "ddmm.mmm".
	 *
	 * @param index Field index
	 * @param lat Latitude value in degrees
	 */
	protected void setLatitude(int index, double lat) {

		int deg = (int) Math.floor(lat);
		double min = (lat - deg) * 60;

		DecimalFormat df = new DecimalFormat("00.000");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);

		String result = String.format("%02d%s", deg, df.format(min));
		setStringValue(index, result);
	}

	/**
	 * Sets the longitude value in specified field, formatted in "dddmm.mmm".
	 * Does not check if the given value is logically correct to current
	 * longitude hemisphere.
	 *
	 * @param index Field index
	 * @param lon Longitude value in degrees
	 */
	protected void setLongitude(int index, double lon) {

		int deg = (int) Math.floor(lon);
		double min = (lon - deg) * 60;

		DecimalFormat nf = new DecimalFormat("00.000");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(dfs);

		String result = String.format("%03d%s", deg, nf.format(min));
		setStringValue(index, result);
	}

	/**
	 * Set the hemisphere of longitude in specified field. Does not check if the
	 * given value is logically correct to current longitude value.
	 *
	 * @param field Field index
	 * @param hem Direction.EAST or Direction.WEST
	 * @throws IllegalArgumentException If specified Direction is other than
	 *             EAST or WEST.
	 */
	protected void setLonHemisphere(int field, CompassPoint hem) {
		if (hem != CompassPoint.EAST && hem != CompassPoint.WEST) {
			throw new IllegalArgumentException("Invalid longitude hemisphere: "
				+ hem);
		}
		setCharValue(field, hem.toChar());
	}

	/**
	 * Sets the values from specified {@code Position} according to given
	 * field indices. Sets the absolute values of latitude and longitude, and
	 * hemisphere indicators as given by {@code Position}. Does not set
	 * altitude.
	 *
	 * @param p Position to set
	 * @param latIndex Index of latitude field
	 * @param latHemIndex Index of latitude hemisphere field
	 * @param lonIndex Index of longitude field
	 * @param lonHemIndex Index of longitude hemisphere field
	 */
	protected void setPositionValues(Position p, int latIndex, int latHemIndex,
		int lonIndex, int lonHemIndex) {

		setLatitude(latIndex, Math.abs(p.getLatitude()));
		setLongitude(lonIndex, Math.abs(p.getLongitude()));
		setLatHemisphere(latHemIndex, p.getLatitudeHemisphere());
		setLonHemisphere(lonHemIndex, p.getLongitudeHemisphere());
	}

	/**
	 * Parse latitude or longitude degrees and minutes from the specified string.
	 * Assumes the {@code dddmm.mmmm} formatting where the two digits to the
	 * left of dot denote full minutes and any remaining digits to the left
	 * denote full degrees (usually padded with leading zeros). Digits to the
	 * right of the dot denote the minute decimals.
	 *
	 * @param degreeStr NMEA 0183 degrees/minutes String
	 * @return Degrees decimal value
	 */
	public static double parseDegrees(String degreeStr) {

		int dotIndex = degreeStr.indexOf(".");

		String degStr = dotIndex > 2 ? degreeStr.substring(0, dotIndex - 2) : "0";
		String minStr = dotIndex > 2 ? degreeStr.substring(dotIndex - 2) : degreeStr;

		int deg = Integer.parseInt(degStr);
		double min = Double.parseDouble(minStr);

		return deg + (min / 60);
	}

	/**
	 * Parses the hemisphere of latitude from specified char.
	 *
	 * @param ch Hemisphere char indicator.
	 * @return Corresponding {@link CompassPoint} enum value.
	 * @throws ParseException If specified char is not 'N' or 'S'.
	 */
	public static CompassPoint parseHemisphereLat(char ch) {
		CompassPoint d = CompassPoint.valueOf(ch);
		if (d != CompassPoint.NORTH && d != CompassPoint.SOUTH) {
			throw new ParseException("Invalid latitude hemisphere '" + ch + "'");
		}
		return d;
	}

	/**
	 * Parses the hemisphere of longitude from the specified char.
	 *
	 * @param ch Hemisphere char indicator.
	 * @return Corresponding {@link CompassPoint} enum value.
	 * @throws ParseException If specified char is not 'E' or 'W'.
	 */
	public static CompassPoint parseHemisphereLon(char ch) {
		CompassPoint d = CompassPoint.valueOf(ch);
		if (d != CompassPoint.EAST && d != CompassPoint.WEST) {
			throw new ParseException("Invalid longitude hemisphere " + ch + "'");
		}
		return d;
	}

	/**
	 * Parses a {@code Position} from specified fields. The parameters are the raw
	 * values from the NMEA Sentence.
	 *
	 * @param latitudeValue String value from the NMEA Sentence
	 * @param latitudeHemisphereIndicator Character value from the NMEA Sentence
	 * @param longitudeValue String value from the NMEA Sentence
	 * @param longitudeHemisphereIndicator Character value from the NMEA Sentence
	 * @return Position object
	 */
	public static Position parsePosition(String latitudeValue, char latitudeHemisphereIndicator,
		String longitudeValue, char longitudeHemisphereIndicator) {

		double lat = parseDegrees(latitudeValue);
		double lon = parseDegrees(longitudeValue);
		CompassPoint lath = parseHemisphereLat(latitudeHemisphereIndicator);
		CompassPoint lonh = parseHemisphereLon(longitudeHemisphereIndicator);
		if (lath.equals(CompassPoint.SOUTH)) {
			lat = -lat;
		}
		if (lonh.equals(CompassPoint.WEST)) {
			lon = -lon;
		}
		return new Position(lat, lon);
	}
}

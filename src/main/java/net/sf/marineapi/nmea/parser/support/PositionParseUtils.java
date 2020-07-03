/*
 * Copyright 2020 Gunnar Hillert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.marineapi.nmea.parser.support;

import net.sf.marineapi.nmea.parser.ParseException;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Position;

/**
 * Contains {@link Position} related utility methods used by various parsers.
 *
 * @author Gunnar Hillert
 *
 */
public class PositionParseUtils {


	/**
	 * Parse latitude or longitude degrees and minutes from the specified field.
	 * Assumes the {@code dddmm.mmmm} formatting where the two digits to the
	 * left of dot denote full minutes and any remaining digits to the left
	 * denote full degrees (usually padded with leading zeros). Digits to the
	 * right of the dot denote the minute decimals.
	 *
	 * @param degreeValue Degree String value from the NMEA Sentence
	 * @return Degrees decimal value
	 */
	public static double parseDegrees(String degreeValue) {

		int dotIndex = degreeValue.indexOf(".");

		String degStr = dotIndex > 2 ? degreeValue.substring(0, dotIndex - 2) : "0";
		String minStr = dotIndex > 2 ? degreeValue.substring(dotIndex - 2) : degreeValue;

		int deg = Integer.parseInt(degStr);
		double min = Double.parseDouble(minStr);

		return deg + (min / 60);
	}

	/**
	 * Parses the hemisphere of latitude from specified field.
	 *
	 * @param latitudeHemisphereIndicator String value from the NMEA Sentence
	 * @return Hemisphere of latitude
	 */
	public static CompassPoint parseHemisphereLat(char latitudeHemisphereIndicator) {
		CompassPoint d = CompassPoint.valueOf(latitudeHemisphereIndicator);
		if (d != CompassPoint.NORTH && d != CompassPoint.SOUTH) {
			throw new ParseException("Invalid latitude hemisphere '" + latitudeHemisphereIndicator + "'");
		}
		return d;
	}

	/**
	 * Parses the hemisphere of longitude from the specified field.
	 *
	 * @param longitudeHemisphereIndicator String value from the NMEA Sentence
	 * @return Hemisphere of longitude
	 */
	public static CompassPoint parseHemisphereLon(char longitudeHemisphereIndicator) {
		CompassPoint d = CompassPoint.valueOf(longitudeHemisphereIndicator);
		if (d != CompassPoint.EAST && d != CompassPoint.WEST) {
			throw new ParseException("Invalid longitude hemisphere " + longitudeHemisphereIndicator + "'");
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

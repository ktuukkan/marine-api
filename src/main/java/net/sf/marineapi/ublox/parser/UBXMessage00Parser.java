/*
 * Copyright (C) 2020 Gunnar Hillert
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
package net.sf.marineapi.ublox.parser;

import net.sf.marineapi.nmea.parser.PositionParser;
import net.sf.marineapi.nmea.sentence.UBXSentence;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.ublox.message.UBXMessage00;
import net.sf.marineapi.ublox.util.UbloxNavigationStatus;

/**
 * Parser implementation for {@link UBXMessage00} (Lat/Long Position Data).
 *
 * @author Gunnar Hillert
 */
class UBXMessage00Parser extends UBXMessageParser implements UBXMessage00 {

	private static final int UTC_TIME = 1;
	private static final int LATITUDE = 2;
	private static final int LAT_HEMISPHERE = 3;
	private static final int LONGITUDE = 4;
	private static final int LON_HEMISPHERE = 5;
	private static final int ALTITUDE = 6;

	private static final int NAVIGATION_STATUS = 7;
	private static final int HORIZONTAL_ACCURACY_ESTIMATE = 8;
	private static final int VERTICAL_ACCURACY_ESTIMATE = 9;
	private static final int SPEED_OVER_GROUND = 10;
	private static final int COURSE_OVER_GROUND = 11;
	private static final int VERTICA_VELOCITY = 12;
	private static final int AGE_OF_DIFFERENTIAL_CORRECTIONS = 13;
	private static final int HDOP = 14;
	private static final int VDOP = 15;
	private static final int TDOP = 16;
	private static final int NUMBER_OF_SATELLITES_USED = 17;

	public UBXMessage00Parser(UBXSentence sentence) {
		super(sentence);
	}

	/**
	 * @see UBXMessage00#getUtcTime()
	 */
	@Override
	public Time getUtcTime() {
		String str = getStringValue(UTC_TIME);
		return new Time(str);
	}

	/**
	 * @see UBXMessage00#getPosition()
	 */
	@Override
	public Position getPosition() {
		final String latitudeField  = this.sentence.getUBXFieldStringValue(LATITUDE);
		final char latitudeHemisphereIndicatorField = this.sentence.getUBXFieldCharValue(LAT_HEMISPHERE);
		final String longitudeField = this.sentence.getUBXFieldStringValue(LONGITUDE);
		final char longitudeHemisphereIndicatorField = this.sentence.getUBXFieldCharValue(LON_HEMISPHERE);

		final Position position = PositionParser.parsePosition(
				latitudeField, latitudeHemisphereIndicatorField,
				longitudeField, longitudeHemisphereIndicatorField);

		final double altitude = this.sentence.getUBXFieldDoubleValue(ALTITUDE);
		position.setAltitude(altitude);

		return position;
	}

	/**
	 * @see UBXMessage00#getNavigationStatus()
	 */
	@Override
	public UbloxNavigationStatus getNavigationStatus() {
		return UbloxNavigationStatus.fromNavigationStatusCode(this.sentence.getUBXFieldStringValue(NAVIGATION_STATUS));
	}

	/**
	 * @see UBXMessage00#getHorizontalAccuracyEstimate()
	 */
	@Override
	public double getHorizontalAccuracyEstimate() {
		return this.sentence.getUBXFieldDoubleValue(HORIZONTAL_ACCURACY_ESTIMATE);
	}

	/**
	 * @see UBXMessage00#getVerticaAccuracyEstimate()
	 */
	@Override
	public double getVerticaAccuracyEstimate() {
		return this.sentence.getUBXFieldDoubleValue(VERTICAL_ACCURACY_ESTIMATE);
	}

	/**
	 * @see UBXMessage00#getSpeedOverGround()
	 */
	@Override
	public double getSpeedOverGround() {
		return this.sentence.getUBXFieldDoubleValue(SPEED_OVER_GROUND);
	}

	/**
	 * @see UBXMessage00#getCourseOverGround()
	 */
	@Override
	public double getCourseOverGround() {
		return this.sentence.getUBXFieldDoubleValue(COURSE_OVER_GROUND);
	}

	/**
	 * @see UBXMessage00#getVerticaVelocity()
	 */
	@Override
	public double getVerticaVelocity() {
		return this.sentence.getUBXFieldDoubleValue(VERTICA_VELOCITY);
	}

	/**
	 * @see UBXMessage00#getAgeOfDifferentialCorrections()
	 */
	@Override
	public int getAgeOfDifferentialCorrections() {
		return this.sentence.getUBXFieldIntValue(AGE_OF_DIFFERENTIAL_CORRECTIONS);
	}

	/**
	 * @see UBXMessage00#getHDOP()
	 */
	@Override
	public double getHDOP() {
		return this.sentence.getUBXFieldDoubleValue(HDOP);
	}

	/**
	 * @see UBXMessage00#getVDOP()
	 */
	@Override
	public double getVDOP() {
		return this.sentence.getUBXFieldDoubleValue(VDOP);
	}

	/**
	 * @see UBXMessage00#getTDOP()
	 */
	@Override
	public double getTDOP() {
		return this.sentence.getUBXFieldDoubleValue(TDOP);
	}

	/**
	 * @see UBXMessage00#getNumberOfSatellitesUsed()
	 */
	@Override
	public int getNumberOfSatellitesUsed() {
		return this.sentence.getUBXFieldIntValue(NUMBER_OF_SATELLITES_USED);
	}
}

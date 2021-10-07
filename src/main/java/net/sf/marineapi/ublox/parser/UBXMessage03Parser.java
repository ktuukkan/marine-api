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

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.parser.DataNotAvailableException;
import net.sf.marineapi.nmea.sentence.UBXSentence;
import net.sf.marineapi.ublox.message.UBXMessage03;
import net.sf.marineapi.ublox.util.UbloxSatelliteInfo;
import net.sf.marineapi.ublox.util.UbloxSatelliteStatus;

/**
 * Parser implementation for {@link UBXMessage03} (Satellite Status).
 *
 * @author Gunnar Hillert
 *
 */
class UBXMessage03Parser extends UBXMessageParser implements UBXMessage03 {

	private static final int NUMBER_OF_TRACKED_SATELLITES = 1;

	private static final int UBX_SATELLITE_ID = 2;
	private static final int SATELLITE_STATUS = 3;
	private static final int SATELLITE_AZIMUTH = 4;
	private static final int SATELLITE_ELEVATION = 5;
	private static final int SATELLITE_SIGNAL_STRENGTH  = 6;
	private static final int SATELLIT_CARRIER_LOCK_TIME = 7;

	public UBXMessage03Parser(UBXSentence sentence) {
		super(sentence);
	}

	/**
	 * @see UBXMessage03#getNumberOfTrackedSatellites()
	 */
	@Override
	public int getNumberOfTrackedSatellites() {
		return this.sentence.getUBXFieldIntValue(NUMBER_OF_TRACKED_SATELLITES);
	}

	/**
	 * @see UBXMessage03#getSatellites()
	 */
	@Override
	public List<UbloxSatelliteInfo> getSatellites() {
		final int numberOfTrackedSatellites = this.getNumberOfTrackedSatellites();

		final List<UbloxSatelliteInfo> satellites = new ArrayList<>(numberOfTrackedSatellites);
		for (int i=0; i<numberOfTrackedSatellites; i++) {

			final int satelliteId = sentence.getUBXFieldIntValue(UBX_SATELLITE_ID + i*6);
			final UbloxSatelliteStatus satelliteStatus = UbloxSatelliteStatus.fromStatusFlag(sentence.getUBXFieldCharValue(SATELLITE_STATUS + i*6));

			int satelliteAzimuth;
			try {
				satelliteAzimuth = sentence.getUBXFieldIntValue(SATELLITE_AZIMUTH + i*6);
			}
			catch (DataNotAvailableException e) {
				satelliteAzimuth = -1;
			}

			int satelliteElevation;
			try {
				satelliteElevation = sentence.getUBXFieldIntValue(SATELLITE_ELEVATION + i*6);
			}
			catch (DataNotAvailableException e) {
				satelliteElevation = -1;
			}

			int signalStrength;

			try {
				signalStrength = sentence.getUBXFieldIntValue(SATELLITE_SIGNAL_STRENGTH + i*6);
			} catch (DataNotAvailableException e) {
				signalStrength = -1;
			}

			int satelliteCarrierLockTime;
			try {
				satelliteCarrierLockTime = sentence.getUBXFieldIntValue(SATELLIT_CARRIER_LOCK_TIME + i*6);
			} catch (DataNotAvailableException e) {
				satelliteCarrierLockTime = -1;
			}

			final UbloxSatelliteInfo satelliteInfo = new UbloxSatelliteInfo(
				String.valueOf(satelliteId), satelliteElevation, satelliteAzimuth, signalStrength, satelliteStatus, satelliteCarrierLockTime);
			satellites.add(satelliteInfo);
		}
		return satellites;
	}

}

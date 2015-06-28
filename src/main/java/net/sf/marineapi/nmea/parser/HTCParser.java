/*
 * HTCParser.java
 * Copyright (C) 2015 Paweł Kozioł, Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.HTCSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.SteeringMode;
import net.sf.marineapi.nmea.util.TurnMode;

public class HTCParser extends SentenceParser implements HTCSentence {

	private static final int OVERRIDE = 0;
	private static final int COMMANDED_RUDDER_ANGLE = 1;
	private static final int COMMANDED_RUDDER_DIRECTION = 2;
	private static final int SELECTED_STEERING_MODE = 3;
	private static final int TURN_MODE = 4;
	private static final int COMMANDED_RUDDER_LIMIT = 5;
	private static final int COMMANDED_OFF_HEADING_LIMIT = 6;
	private static final int COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES = 7;
	private static final int COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES = 8;
	private static final int COMMANDED_HEADING_TO_STEER = 9;
	private static final int COMMANDED_OFF_TRACK_LIMIT = 10;
	private static final int COMMANDED_TRACK = 11;
	private static final int HEADING_REFERENCE_IN_USE = 12;

	public HTCParser(String nmea) {
		super(nmea, SentenceId.HTC);
	}

	public HTCParser(TalkerId talker) {
		super(talker, SentenceId.HTC, 13);
	}

	HTCParser(String nmea, SentenceId type) {
		super(nmea, type);
	}

	HTCParser(TalkerId tid, SentenceId sid, int size) {
		super(tid, sid, size);
	}

	@Override
	public DataStatus getOverride() {
		if (hasValue(OVERRIDE)) {
			return DataStatus.valueOf(getCharValue(OVERRIDE));
		} else {
			return null;
		}
	}

	@Override
	public double getCommandedRudderAngle() {
		if (hasValue(COMMANDED_RUDDER_ANGLE)) {
			return getDoubleValue(COMMANDED_RUDDER_ANGLE);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public Direction getCommandedRudderDirection() {
		if (hasValue(COMMANDED_RUDDER_DIRECTION)) {
			return Direction.valueOf(getCharValue(COMMANDED_RUDDER_DIRECTION));
		} else {
			return null;
		}
	}

	@Override
	public SteeringMode getSelectedSteeringMode() {
		if (hasValue(SELECTED_STEERING_MODE)) {
			return SteeringMode.valueOf(getCharValue(SELECTED_STEERING_MODE));
		} else {
			return null;
		}
	}

	@Override
	public TurnMode getTurnMode() {
		if (hasValue(TURN_MODE)) {
			return TurnMode.valueOf(getCharValue(TURN_MODE));
		} else {
			return null;
		}
	}

	@Override
	public double getCommandedRudderLimit() {
		if (hasValue(COMMANDED_RUDDER_LIMIT)) {
			return getDoubleValue(COMMANDED_RUDDER_LIMIT);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public double getCommandedOffHeadingLimit() {
		if (hasValue(COMMANDED_OFF_HEADING_LIMIT)) {
			return getDoubleValue(COMMANDED_OFF_HEADING_LIMIT);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public double getCommandedRadiusOfTurnForHeadingChanges() {
		if (hasValue(COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES)) {
			return getDoubleValue(COMMANDED_RADIUS_OF_TURN_FOR_HEADING_CHANGES);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public double getCommandedRateOfTurnForHeadingChanges() {
		if (hasValue(COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES)) {
			return getDoubleValue(COMMANDED_RATE_OF_TURN_FOR_HEADING_CHANGES);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public double getCommandedHeadingToSteer() {
		if (hasValue(COMMANDED_HEADING_TO_STEER)) {
			return getDoubleValue(COMMANDED_HEADING_TO_STEER);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public double getCommandedOffTrackLimit() {
		if (hasValue(COMMANDED_OFF_TRACK_LIMIT)) {
			return getDoubleValue(COMMANDED_OFF_TRACK_LIMIT);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public double getCommandedTrack() {
		if (hasValue(COMMANDED_TRACK)) {
			return getDoubleValue(COMMANDED_TRACK);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public boolean isHeadingReferenceInUseTrue() {
		return hasValue(HEADING_REFERENCE_IN_USE)
				&& getCharValue(HEADING_REFERENCE_IN_USE) == 'T';
	}
}

/*
 * HTDParser.java
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

import net.sf.marineapi.nmea.sentence.HTDSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;

public class HTDParser extends HTCParser implements HTDSentence
{
	private static final int RUDDER_STATUS = 13;
	private static final int OFF_HEADING_STATUS = 14;
	private static final int OFF_TRACK_STATUS = 15;
	private static final int HEADING = 16;

	public HTDParser(String nmea) {
		super(nmea, SentenceId.HTD);
	}

	public HTDParser(TalkerId talker) {
		super(talker, SentenceId.HTD, 17);
	}

	@Override
	public DataStatus getRudderStatus() {
		if (hasValue(RUDDER_STATUS)) {
			return DataStatus.valueOf(getCharValue(RUDDER_STATUS));
		} else {
			return null;
		}
	}

	@Override
	public DataStatus getOffHeadinStatus() {
		if (hasValue(OFF_HEADING_STATUS)) {
			return DataStatus.valueOf(getCharValue(OFF_HEADING_STATUS));
		} else {
			return null;
		}
	}

	@Override
	public DataStatus getOffTrackStatus() {
		if (hasValue(OFF_TRACK_STATUS)) {
			return DataStatus.valueOf(getCharValue(OFF_TRACK_STATUS));
		} else {
			return null;
		}
	}

	@Override
	public double getHeading() {
		if (hasValue(HEADING)) {
			return getDoubleValue(HEADING);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public boolean isTrue() {
		return isHeadingReferenceInUseTrue();
	}

	@Override
	public void setHeading(double hdt) {
		setDoubleValue(HEADING, hdt);
	}
}

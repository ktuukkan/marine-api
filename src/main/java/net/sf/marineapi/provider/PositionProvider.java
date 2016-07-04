/* 
 * PositionProvider.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
package net.sf.marineapi.provider;

import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.parser.DataNotAvailableException;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.GLLSentence;
import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.VTGSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.provider.event.PositionEvent;

/**
 * <p>
 * Provides Time, Position and Velocity reports from GPS. Data is captured from
 * RMC, GGA and GLL sentences. RMC is used for date/time, speed and course. GGA
 * is used as primary source for position as it contains also the altitude. When
 * GGA is not available, position may be taken from GLL or RMC. If this is the
 * case, there is no altitude included in the
 * {@link net.sf.marineapi.nmea.util.Position}. GPS data statuses are also
 * captured and events are dispatched only when sentences report
 * {@link net.sf.marineapi.nmea.util.DataStatus#ACTIVE}. FAA mode transmitted in
 * RMC is also checked and captured when available, but may be <code>null</code>
 * depending on used NMEA version.
 *  
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.provider.event.PositionListener
 * @see net.sf.marineapi.provider.event.PositionEvent
 * @see net.sf.marineapi.nmea.io.SentenceReader
 */
public class PositionProvider extends AbstractProvider<PositionEvent> {

	/**
	 * Creates a new instance of PositionProvider.
	 * 
	 * @param reader SentenceReader that provides the required sentences.
	 */
	public PositionProvider(SentenceReader reader) {
		super(reader, SentenceId.RMC, SentenceId.GGA, SentenceId.GLL, SentenceId.VTG);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#createProviderEvent()
	 */
	@Override
	protected PositionEvent createProviderEvent() {
		Position p = null;
		Double sog = null;
		Double cog = null;
		Date d = null;
		Time t = null;
		FaaMode mode = null;
		GpsFixQuality fix = null;

		for (Sentence s : getSentences()) {
			if (s instanceof RMCSentence) {
				RMCSentence rmc = (RMCSentence) s;
				sog = rmc.getSpeed();
				try {
					cog = rmc.getCourse();
				} catch (DataNotAvailableException e) {
					// If we are not moving, cource can be undefined. Leave null in that case.
				}
				d = rmc.getDate();
				t = rmc.getTime();
				if (p == null) {
					p = rmc.getPosition();
					if(rmc.getFieldCount() > 11) {
						mode = rmc.getMode();
					}
				}
			} else if (s instanceof VTGSentence) {
				VTGSentence vtg = (VTGSentence) s;
				sog = vtg.getSpeedKnots();
				try {
					cog = vtg.getTrueCourse();
				} catch (DataNotAvailableException e) {
					// If we are not moving, cource can be undefined. Leave null in that case.
				}
			} else if (s instanceof GGASentence) {
				// Using GGA as primary position source as it contains both
				// position and altitude
				GGASentence gga = (GGASentence) s;
				p = gga.getPosition();
				fix = gga.getFixQuality();

				// Some receivers do not provide RMC message
				if (t == null) {
					t = gga.getTime();
				}
			} else if (s instanceof GLLSentence && p == null) {
				GLLSentence gll = (GLLSentence) s;
				p = gll.getPosition();
			}
		}

		// Ag-Star reciever does not provide RMC sentence. So we have to guess what date it is
		if (d == null) {
			d = new Date();
		}

		return new PositionEvent(this, p, sog, cog, d, t, mode, fix);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isReady()
	 */
	@Override
	protected boolean isReady() {
		return hasOne("RMC", "VTG") && hasOne("GGA", "GLL");
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isValid()
	 */
	@Override
	protected boolean isValid() {

		for (Sentence s : getSentences()) {

			if (s instanceof RMCSentence) {
				RMCSentence rmc = (RMCSentence)s;
				DataStatus ds = rmc.getStatus();
				if (DataStatus.VOID.equals(ds) ||
					(rmc.getFieldCount() > 11 && FaaMode.NONE.equals(rmc.getMode()))) {
					return false;
				}
			} else if (s instanceof GGASentence) {
				GpsFixQuality fq = ((GGASentence) s).getFixQuality();
				if (GpsFixQuality.INVALID.equals(fq)) {
					return false;
				}
			} else if (s instanceof GLLSentence) {
				DataStatus ds = ((GLLSentence) s).getStatus();
				if (DataStatus.VOID.equals(ds)) {
					return false;
				}
			}
		}

		return true;
	}
}

/* 
 * TPVProvider.java
 * Copyright (C) 2011 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.GLLSentence;
import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.provider.event.TPVEvent;

/**
 * <p>
 * Provides Time, Position & Velocity reports from GPS. Data is captured from
 * RMC, GGA and GLL sentences. RMC is used for date/time, speed and course. GGA
 * is used as primary source for position as it contains also the altitude. When
 * GGA is not available, position may be taken from GLL or RMC. If this is the
 * case, there is no altitude included in the
 * {@link net.sf.marineapi.nmea.util.Position}. GPS data statuses are also
 * captured and events are dispatched only when sentences report
 * {@link net.sf.marineapi.nmea.util.DataStatus#ACTIVE}.
 * <p>
 * When constructing {@link net.sf.marineapi.provider.event.TPVEvent}, the
 * maximum age of captured sentences is 1000 ms, i.e. all sentences are from
 * within the default NMEA update rate (1/s).
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.provider.event.TPVListener
 * @see net.sf.marineapi.provider.event.TPVEvent
 * @see net.sf.marineapi.nmea.io.SentenceReader
 */
public class TPVProvider extends AbstractProvider<TPVEvent> {

    /**
     * Creates a new instance of TPVProvider.
     * 
     * @param reader SentenceReader which provides the required sentences.
     */
    public TPVProvider(SentenceReader reader) {
        super(reader, SentenceId.RMC, SentenceId.GGA, SentenceId.GLL);
    }

    /**
     * Creates a TPVEvent based on captured sentences.
     */
    @Override
    protected TPVEvent createEvent() {
        Position p = null;
        Double sog = null;
        Double cog = null;
        Date d = null;
        Time t = null;
        FaaMode mode = null;
        GpsFixQuality fix = null;

        for (SentenceEvent se : events) {
            Sentence s = se.getSentence();
            if (s instanceof RMCSentence) {
                RMCSentence rmc = (RMCSentence) s;
                sog = rmc.getSpeed();
                cog = rmc.getCourse();
                d = rmc.getDate();
                t = rmc.getTime();
                mode = rmc.getMode();
                if (p == null) {
                    p = rmc.getPosition();
                }
            } else if (s instanceof GGASentence) {
                // Using GGA as primary position source as it contains both
                // position and altitude
                GGASentence gga = (GGASentence) s;
                p = gga.getPosition();
                fix = gga.getFixQuality();
            } else if (s instanceof GLLSentence && p == null) {
                GLLSentence gll = (GLLSentence) s;
                p = gll.getPosition();
            }
        }

        return new TPVEvent(this, p, sog, cog, d, t, mode, fix);
    }

    /**
     * Tells if the needed data has been captured.
     */
    @Override
    protected boolean isReady() {

        boolean hasRmc = false;
        boolean hasGga = false;
        boolean hasGll = false;

        for (SentenceEvent se : events) {
            Sentence s = se.getSentence();
            if (s instanceof RMCSentence) {
                hasRmc = true;
            } else if (s instanceof GGASentence) {
                hasGga = true;
            } else if (s instanceof GLLSentence) {
                hasGll = true;
            }
        }

        return hasRmc && (hasGga || hasGll);
    }

    /**
     * Tells if the captured events are from within the last 1000 milliseconds
     * and contain valid data.
     */
    @Override
    protected boolean isValid() {

        long now = System.currentTimeMillis();

        for (SentenceEvent se : events) {

            long age = now - se.getTimeStamp();
            if (age > 1000) {
                return false;
            }

            Sentence s = se.getSentence();
            if (s instanceof RMCSentence) {
                DataStatus ds = ((RMCSentence) s).getStatus();
                FaaMode gm = ((RMCSentence) s).getMode();
                if (DataStatus.VOID.equals(ds) || FaaMode.NONE.equals(gm)) {
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

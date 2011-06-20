/* 
 * TPVProvider.java
 * Copyright (C) 2010-2011 Kimmo Tuukkanen
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

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.GGASentence;
import net.sf.marineapi.nmea.sentence.GLLSentence;
import net.sf.marineapi.nmea.sentence.PositionSentence;
import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.GpsMode;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.provider.event.TPVEvent;
import net.sf.marineapi.provider.event.TPVListener;

/**
 * <p>
 * Provides a time/position/velocity report from GPS.
 * <p>
 * Provider assumes that RMC, GGA and/or GLL sentences are being dispatched by
 * provided {@link SentenceReader}. Uses RMC for date/time, speed and course,
 * and favors GGA for position as it contains the altitude. When GGA is not
 * available, position may be taken from GLL or RMC. If this is the case, there
 * is no altitude included in the {@linkplain Position}.
 * <p>
 * When constructing the {@link TPVEvent}, captured sentences must be from
 * within the last 1000 milliseconds (i.e. standard NMEA update rate, 1/s). If
 * required sentences are not present in current NMEA stream, TPVEvents will not
 * be fired.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see TPVListener
 * @see TPVEvent
 * @see SentenceReader
 */
public class TPVProvider implements SentenceListener {

    private SentenceReader reader;
    private List<SentenceEvent> events = new ArrayList<SentenceEvent>();
    private List<TPVListener> listeners = new ArrayList<TPVListener>();

    /**
     * Creates a new instance of GPSProvider.
     * 
     * @param reader SentenceReader to be used as the data source.
     */
    public TPVProvider(SentenceReader reader) {
        this.reader = reader;
        reader.addSentenceListener(this, SentenceId.RMC);
        reader.addSentenceListener(this, SentenceId.GGA);
        reader.addSentenceListener(this, SentenceId.GLL);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
     */
    public void readingPaused() {
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
     */
    public void readingStarted() {
        reset();
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
     */
    public void readingStopped() {
        reset();
        reader.removeSentenceListener(this);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi
     * .nmea.event.SentenceEvent)
     */
    public void sentenceRead(SentenceEvent event) {

        Sentence s = event.getSentence();

        if (s instanceof PositionSentence) {

            events.add(event);

            if (isReady()) {
                if (isValid()) {
                    fireTPVEvent(createTPVEvent());
                }
                reset();
            }
        }
    }

    /**
     * Creates a TPVEvent based on captured sentences.
     */
    private TPVEvent createTPVEvent() {
        Position p = null;
        Double sog = null;
        Double cog = null;
        Date d = null;
        Time t = null;
        GpsMode mode = null;
        GpsFixQuality fix = null;

        for (SentenceEvent se : events) {
            Sentence s = se.getSentence();
            if (s instanceof RMCSentence) {
                RMCSentence rmc = (RMCSentence) s;
                sog = rmc.getSpeed();
                cog = rmc.getCourse();
                d = rmc.getDate();
                t = rmc.getTime();
                mode = rmc.getGpsMode();
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
     * Tells if the captured events are from within the last 1000 milliseconds
     * and contain valid data.
     */
    private boolean isValid() {

        long now = System.currentTimeMillis();

        for (SentenceEvent se : events) {

            long age = now - se.getTimeStamp();
            if (age > 1000) {
                return false;
            }

            Sentence s = se.getSentence();
            if (s instanceof RMCSentence) {
                DataStatus ds = ((RMCSentence) s).getStatus();
                GpsMode gm = ((RMCSentence) s).getGpsMode();
                if (DataStatus.VOID.equals(ds) || GpsMode.NONE.equals(gm)) {
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

    /**
     * Dispatch the TPV event to all listeners.
     * 
     * @param event TPVUpdateEvent to dispatch
     */
    private void fireTPVEvent(TPVEvent event) {
        for (TPVListener listener : listeners) {
            listener.tpvUpdate(event.clone());
        }
    }

    /**
     * Clears the list of collected events.
     */
    private void reset() {
        events.clear();
    }

    /**
     * Tells if the needed data has been captured.
     */
    private boolean isReady() {

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
     * Inserts a listener to provider.
     * 
     * @param listener Listener to add
     */
    public void addListener(TPVListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the specified listener from provider.
     * 
     * @param listener Listener to remove
     */
    public void removeListener(TPVListener listener) {
        listeners.remove(listener);
    }
}

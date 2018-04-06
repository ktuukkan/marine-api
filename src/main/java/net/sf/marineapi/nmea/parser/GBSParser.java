/*
 * GBSParser.java
 * Copyright (C) 2018 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.GBSSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.Time;

/**
 * GBS sentence parser.
 *
 * $GPGBS,015509.00,-0.031,-0.186,0.219,19,0.000,-0.354,6.972*4D
 */
public class GBSParser extends SentenceParser implements GBSSentence {

    private static final int UTC = 0;
    private static final int LAT_ERROR = 1;
    private static final int LON_ERROR = 2;
    private static final int ALT_ERROR = 3;
    private static final int SATELLITE_ID = 4;
    private static final int PROBABILITY = 5;
    private static final int ESTIMATE = 6;
    private static final int DEVIATION = 7;

    /**
     * Creates an empty GBS parser.
     *
     * @param tid Talker ID to set.
     */
    public GBSParser(TalkerId tid) {
        super(tid, SentenceId.GBS, 8);
    }

    /**
     * Creates a parser for given GBS sentence.
     *
     * @param nmea GBS sentence String
     */
    public GBSParser(String nmea) {
        super(nmea, SentenceId.GBS);
    }

    @Override
    public double getLatitudeError() {
        return getDoubleValue(LAT_ERROR);
    }

    @Override
    public void setLatitudeError(double err) {
        setDoubleValue(LAT_ERROR, err);
    }

    @Override
    public double getLongitudeError() {
        return getDoubleValue(LON_ERROR);
    }

    @Override
    public void setLongitudeError(double err) {
        setDoubleValue(LON_ERROR, err);
    }

    @Override
    public double getAltitudeError() {
        return getDoubleValue(ALT_ERROR);
    }

    @Override
    public void setAltitudeError(double err) {
        setDoubleValue(ALT_ERROR, err);
    }

    @Override
    public String getSatelliteId() {
        return getStringValue(SATELLITE_ID);
    }

    @Override
    public void setSatelliteId(String id) {
        setStringValue(SATELLITE_ID, id);
    }

    @Override
    public double getProbability() {
        return getDoubleValue(PROBABILITY);
    }

    @Override
    public void setProbability(double probability) {
        setDoubleValue(PROBABILITY, probability);
    }

    @Override
    public double getEstimate() {
        return getDoubleValue(ESTIMATE);
    }

    @Override
    public void setEstimate(double estimate) {
        setDoubleValue(ESTIMATE, estimate);
    }

    @Override
    public double getDeviation() {
        return getDoubleValue(DEVIATION);
    }

    @Override
    public void setDeviation(double deviation) {
        setDoubleValue(DEVIATION, deviation);
    }

    @Override
    public Time getTime() {
        return new Time(getStringValue(UTC));
    }

    @Override
    public void setTime(Time t) {
        setStringValue(UTC, t.toString());
    }
}

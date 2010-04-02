/* 
 * GSVParser.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.sentence.GSVSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.SatelliteInfo;

/**
 * GSV sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class GSVParser extends SentenceParser implements GSVSentence {

    // field indices
    private static final int NUMBER_OF_SENTENCES = 1;
    private static final int SENTENCE_NUMBER = 2;
    private static final int SATELLITES_IN_VIEW = 3;

    // Satellite ID field indices
    private static final int[] ID_FIELDS = { 4, 8, 12, 16 };

    // Satellite data fields, relative to each satellite Id field
    private static final int ID_NUMBER = 0;
    private static final int ELEVATION = 1;
    private static final int AZIMUTH = 2;
    private static final int NOISE = 3;

    /**
     * Constructor.
     * 
     * @param nmea GSV Sentence
     */
    public GSVParser(String nmea) {
        super(nmea, SentenceId.GSV);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSatelliteCount()
     */
    public int getSatelliteCount() {
        return getIntValue(SATELLITES_IN_VIEW);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSatelliteInfo()
     */
    public List<SatelliteInfo> getSatelliteInfo() {

        List<SatelliteInfo> satellites = new ArrayList<SatelliteInfo>(4);

        for (int satelliteIndex : ID_FIELDS) {
            try {
                String id = getSatelliteId(satelliteIndex);
                double elev = getElevation(satelliteIndex);
                double azm = getAzimuth(satelliteIndex);
                double snr = getSignalNoiseRatio(satelliteIndex);
                satellites.add(new SatelliteInfo(id, elev, azm, snr));
            } catch (ParseException e) {
                // nevermind missing satellite info
            }
        }

        return satellites;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSentenceCount()
     */
    public int getSentenceCount() {
        return getIntValue(NUMBER_OF_SENTENCES);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#getSentenceIndex()
     */
    public int getSentenceIndex() {
        return getIntValue(SENTENCE_NUMBER);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#isFirst()
     */
    public boolean isFirst() {
        return (getSentenceIndex() == 1);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#isLast()
     */
    public boolean isLast() {
        return (getSentenceIndex() == getSentenceCount());
    }

    /**
     * Get satellite azimuth. Parameter <code>sv</code> should be one of the
     * <code>SATELLITE#</code> constants.
     * 
     * @param sv Satellite ID index.
     * @return integer value
     */
    private double getAzimuth(int sv) {
        return Double.parseDouble(getSatelliteData(sv, AZIMUTH));
    }

    /**
     * Get satellite elevation, in degrees (max. 90&deg;). Parameter
     * <code>sv</code> should be one of the <code>SATELLITE#</code> constants.
     * 
     * @param sv Satellite ID index.
     * @return integer value
     */
    private double getElevation(int sv) {
        return Double.parseDouble(getSatelliteData(sv, ELEVATION));
    }

    /**
     * Get satellite data field. Parameter <code>a</code> should be one of the
     * class <code>SATELLITE#</code> constants and parameter <code>b</code> one
     * of the constants defining the satellite data fields relative to
     * <code>SATELLITE#</code> field.
     * 
     * @param a Satellite ID field index.
     * @param b Data field index relative to ID field.
     * @throws ParseException
     */
    private String getSatelliteData(int a, int b) {
        if (b != ID_NUMBER && b != ELEVATION && b != AZIMUTH && b != NOISE) {
            throw new ParseException("Satellite field index out of bounds");
        }
        return getStringValue((a + b));
    }

    /**
     * Get satellite ID, for example "05". Parameter <code>sv</code> should be
     * one of the <code>SATELLITE#</code> constants.
     * 
     * @param sv Satellite ID index.
     * @return integer value
     */
    private String getSatelliteId(int sv) {
        return getSatelliteData(sv, ID_NUMBER);
    }

    /**
     * Get noise ratio of satellite signal.
     * 
     * @param sv Satellite ID index.
     * @return integer value
     */
    private double getSignalNoiseRatio(int sv) {
        return Double.parseDouble(getSatelliteData(sv, NOISE));
    }

}

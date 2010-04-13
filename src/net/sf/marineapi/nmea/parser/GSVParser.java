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
    // satellite id fields
    private static final int[] ID_FIELDS = { 4, 8, 12, 16 };
    // satellite data fields, relative to each id field
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

        for (int idf : ID_FIELDS) {
            try {
                String id = getStringValue(idf);
                double elev = getDoubleValue(idf + ELEVATION);
                double azm = getDoubleValue(idf + AZIMUTH);
                double snr = getDoubleValue(idf + NOISE);
                satellites.add(new SatelliteInfo(id, elev, azm, snr));
            } catch (DataNotAvailableException e) {
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

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.GSVSentence#setSatelliteInfo(java.util
     * .List)
     */
    public void setSatelliteInfo(List<SatelliteInfo> info) {
        if (info.size() > 4) {
            throw new IllegalArgumentException("Maximum list size is 4");
        }
        int i = 0;
        for (SatelliteInfo si : info) {
            int idf = ID_FIELDS[i++];
            setStringValue(idf, si.getId());
            setStringValue(idf + ELEVATION, String.valueOf(si.getElevation()));
            setStringValue(idf + AZIMUTH, String.valueOf(si.getAzimuth()));
            setStringValue(idf + NOISE, String.valueOf(si.getNoise()));
        }
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#setSentenceCount(int)
     */
    public void setSentenceCount(int count) {
        setIntValue(NUMBER_OF_SENTENCES, count);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSVSentence#setSentenceIndex(int)
     */
    public void setSentenceIndex(int index) {
        setIntValue(SENTENCE_NUMBER, index);
    }

}

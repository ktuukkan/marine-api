/* 
 * VHWParser.java
 * Copyright (C) 2011 Warren Zahra
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

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VHWSentence;

/**
 * WHV sentence parser.
 * 
 * @author Warren Zahra, Kimmo Tuukkanen
 * @version $Revision$
 */
class VHWParser extends SentenceParser implements VHWSentence {

    private static final int TRUE_HEADING = 0;
    private static final int TRUE_INDICATOR = 1;
    private static final int MAGNETIC_HEADING = 2;
    private static final int MAGNETIC_INDICATOR = 3;
    private static final int SPEED_KNOTS = 4;
    private static final int KNOTS_INDICATOR = 5;
    private static final int SPEED_KMH = 6;
    private static final int KMH_INDICATOR = 7;

    /**
     * Creates a new instance of VHW parser with given data.
     * 
     * @param nmea VHW sentence String
     */
    public VHWParser(String nmea) {
        super(nmea);
    }

    /**
     * Creates a new empty VHW parser instance.
     * 
     * @param talker Talker ID to set
     */
    public VHWParser(TalkerId talker) {
        super(talker, SentenceId.VHW, 8);
        setCharValue(TRUE_INDICATOR, 'T');
        setCharValue(MAGNETIC_INDICATOR, 'M');
        setCharValue(KNOTS_INDICATOR, 'N');
        setCharValue(KMH_INDICATOR, 'K');
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.HeadingSentence#getHeading()
     */
    public double getHeading() {
        return getDoubleValue(TRUE_HEADING);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VHWSentence#getMagneticHeading()
     */
    public double getMagneticHeading() {
        return getDoubleValue(MAGNETIC_HEADING);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VHWSentence#getSpeedKilometres()
     */
    public double getSpeedKmh() {
        return getDoubleValue(SPEED_KMH);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VHWSentence#getSpeedKnots()
     */
    public double getSpeedKnots() {
        return getDoubleValue(SPEED_KNOTS);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.HeadingSentence#isTrue()
     */
    public boolean isTrue() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.HeadingSentence#setHeading(double)
     */
    public void setHeading(double hdg) {
        setDegreesValue(TRUE_HEADING, hdg);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.VHWSentence#setMagneticHeading(double)
     */
    public void setMagneticHeading(double hdg) {
        setDegreesValue(MAGNETIC_HEADING, hdg);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VHWSentence#setSpeedKmh(double)
     */
    public void setSpeedKmh(double kmh) {
        setDoubleValue(SPEED_KMH, kmh, 1, 1);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VHWSentence#setSpeedKnots(double)
     */
    public void setSpeedKnots(double knots) {
        setDoubleValue(SPEED_KNOTS, knots, 1, 1);
    }

}

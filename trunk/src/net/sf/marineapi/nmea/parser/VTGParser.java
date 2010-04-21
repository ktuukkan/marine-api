/* 
 * VTGParser.java
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

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.VTGSentence;
import net.sf.marineapi.nmea.util.GpsMode;

/**
 * VTG sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class VTGParser extends SentenceParser implements VTGSentence {

    // field indexes
    private static final int TRUE_COURSE = 0;
    @SuppressWarnings("unused")
    private static final int TRUE_INDICATOR = 1;
    private static final int MAGNETIC_COURSE = 2;
    @SuppressWarnings("unused")
    private static final int MAGNETIC_INDICATOR = 3;
    private static final int SPEED_KNOTS = 4;
    @SuppressWarnings("unused")
    private static final int KNOTS_INDICATOR = 5;
    private static final int SPEED_KMPH = 6;
    @SuppressWarnings("unused")
    private static final int KMPH_INDICATOR = 7;
    private static final int MODE = 8;

    /**
     * Creates a new instance of VTGParser.
     * 
     * @param nmea VTG sentence String
     * @throws IllegalArgumentException If specified sentence is invalid
     */
    public VTGParser(String nmea) {
        super(nmea, SentenceId.VTG);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VTGSentence#getMagneticCourse()
     */
    public double getMagneticCourse() {
        return getDoubleValue(MAGNETIC_COURSE);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VTGSentence#getMode()
     */
    public GpsMode getMode() {
        return GpsMode.valueOf(getCharValue(MODE));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VTGSentence#getSpeedKmh()
     */
    public double getSpeedKmh() {
        return getDoubleValue(SPEED_KMPH);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VTGSentence#getSpeedKnots()
     */
    public double getSpeedKnots() {
        return getDoubleValue(SPEED_KNOTS);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.VTGSentence#getTrueCourse()
     */
    public double getTrueCourse() {
        return getDoubleValue(TRUE_COURSE);
    }
}

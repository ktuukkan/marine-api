/* 
 * BODSentenceImpl.java
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

import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * BOD sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.sentence.BODSentence
 */
class BODSentenceImpl extends SentenceImpl implements BODSentence {

    // field indices
    private static final int BEARING_TRUE = 1;
    private static final int TRUE_INDICATOR = 2;
    private static final int BEARING_MAGN = 3;
    private static final int MAGN_INDICATOR = 4;
    private static final int DESTINATION = 5;
    private static final int ORIGIN = 6;

    /**
     * Creates a new instance of BOD parser.
     * 
     * @param nmea NMEA sentence
     * @throws IllegalArgumentException If specified String is invalid or does
     *             not contain a BOD sentence.
     */
    public BODSentenceImpl(String nmea) {
        super(nmea, SentenceId.BOD);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.BODSentence#getTrueBearing()
     */
    public double getTrueBearing() {
        return getDoubleValue(BEARING_TRUE);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.BODSentence#getMagneticBearing()
     */
    public double getMagneticBearing() {
        return getDoubleValue(BEARING_MAGN);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.BODSentence#getOriginWaypointId()
     */
    public String getOriginWaypointId() {
        return getStringValue(ORIGIN);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.BODSentence#getDestinationWaypointId()
     */
    public String getDestinationWaypointId() {
        return getStringValue(DESTINATION);
    }
}

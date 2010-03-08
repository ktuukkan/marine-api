/* 
 * SentenceBOD.java
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

import net.sf.marineapi.nmea.util.SentenceId;

/**
 * BOD sentence parser. Bearing Origin to Destination (BOD), true and magnetic
 * in degrees. This sentence is transmitted by a GPS in the GOTO mode (with or
 * without active route).
 * <p>
 * Example: <code>$GPBOD,234.9,T,228.8,M,RUSKI,*1D</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceBOD extends Sentence {

    // Sentence field indices
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
    public SentenceBOD(String nmea) {
        super(nmea, SentenceId.BOD);
    }

    /**
     * Get the true bearing from origin to destination.
     * <p>
     * <i>Notice: The bearing is calculated from the origin when GOTO is
     * activated and it is <b>not</b> updated dynamically.</i>
     * 
     * @return true bearing value
     */
    public double getTrueBearing() {
        return getDoubleValue(BEARING_TRUE);
    }

    /**
     * Get the magnetic bearing from origin to destination.
     * <p>
     * <i>Notice: The bearing is calculated from the origin when GOTO is
     * activated and it is <b>not</b> updated dynamically.</i>
     * 
     * @return magnetic bearing value
     */
    public double getMagneticBearing() {
        return getDoubleValue(BEARING_MAGN);
    }

    /**
     * Get the ID of origin waypoint. This field is available only when route is
     * active.
     * 
     * @return waypoint id
     */
    public String getOriginWaypointId() {
        return getStringValue(ORIGIN);
    }

    /**
     * Get the ID of destination waypoint. This field should be always available
     * in GOTO mode.
     * 
     * @return waypoint id
     */
    public String getDestinationWaypointId() {
        return getStringValue(DESTINATION);
    }
}

/* 
 * SentenceVTG.java
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

import net.sf.marineapi.nmea.util.GpsMode;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * VTG sentence parser. True and magnetic course over ground (COG), speed in
 * km/h and knots (nautical miles per hour). Mode (last field, "A" in example)
 * may not always be available, depending on the NMEA version.
 * <p>
 * Example: <br>
 * <code>$GPVTG,46.96,T,,,16.89,N,31.28,K,A*43</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceVTG extends Sentence {

    /** Units indicator for kilometers per hour */
    public static final char KMPH = 'K';

    /** Units indicator for knots (nautical miles per hour) */
    public static final char KNOT = 'N';

    /** Operating in manual mode (forced 2D or 3D). */
    public static final char MODE_MANUAL = 'M';

    /** Operating in automatic mode (2D/3D). */
    public static final char MODE_AUTOMATIC = 'A';

    // Sentence field indexes
    private static final int TRUE_COURSE = 1;
    private static final int TRUE_INDICATOR = 2;
    private static final int MAGNETIC_COURSE = 3;
    private static final int MAGNETIC_INDICATOR = 4;
    private static final int SPEED_KNOTS = 5;
    private static final int KNOTS_INDICATOR = 6;
    private static final int SPEED_KMPH = 7;
    private static final int KMPH_INDICATOR = 8;
    private static final int MODE = 9;

    /**
     * Constructor.
     * 
     * @param nmea VTG sentence String
     * @throws IllegalArgumentException
     */
    public SentenceVTG(String nmea) {
        super(nmea, SentenceId.VTG);
    }

    /**
     * Get the true course over ground.
     * 
     * @return True course
     */
    public double getTrueCourse() {
        return getDoubleValue(TRUE_COURSE);
    }

    /**
     * Get the magnetic course over ground.
     * 
     * @return Magnetic course
     */
    public double getMagneticCourse() {
        return getDoubleValue(MAGNETIC_COURSE);
    }

    /**
     * Get speed over ground in knots (nautical miles per hour).
     * 
     * @return Speed in knots
     */
    public double getSpeedKnots() {
        return getDoubleValue(SPEED_KNOTS);
    }

    /**
     * Get speed over ground in kilometers per hour.
     * 
     * @return Speed in km/h
     */
    public double getSpeedKmh() {
        return getDoubleValue(SPEED_KMPH);
    }

    /**
     * Get the receiver operating mode. The field may not be available,
     * depending on the NMEA version.
     * 
     * @return GpsMode or <code>null</code> if mode is not available
     */
    public GpsMode getMode() {
        return GpsMode.valueOf(getCharValue(MODE));
    }
}

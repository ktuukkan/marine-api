/* 
 * SentenceGSA.java
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

import net.sf.marineapi.nmea.util.GpsFixStatus;
import net.sf.marineapi.nmea.util.GpsMode;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * GSA sentence parser. Dilution of precision (DOP) of GPS fix and list of
 * active satellites.
 * <p>
 * Example: <code>$GPGSA,A,3,02,,,07,,09,24,26,,,,,1.6,1.6,1.0*3D</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceGSA extends Sentence {

    // field indices
    private final static int GPS_MODE = 1;
    private final static int FIX_MODE = 2;
    private final static int FIRST_SV = 3;
    private final static int LAST_SV = 14;
    private final static int POSITION_DOP = 15;
    private final static int HORIZONTAL_DOP = 16;
    private final static int VERTICAL_DOP = 17;

    // satellite ids array
    private String[] satellites = null;

    /**
     * Creates a new instance of GSA parser.
     * 
     * @param nmea Sentence String
     * @throws SentenceException if specified NMEA string is invalid
     */
    public SentenceGSA(String nmea) {
        super(nmea, SentenceId.GSA);
    }

    /**
     * Get the GPS operation mode.
     * 
     * @return GpsMode enum
     */
    public GpsMode getGpsMode() {
        return GpsMode.valueOf(getCharValue(GPS_MODE));
    }

    /**
     * Get the GPS fix mode (2D, 3D or no fix).
     * 
     * @return GpsFixStatus enum
     */
    public GpsFixStatus getFixStatus() {
        return GpsFixStatus.valueOf(getIntValue(FIX_MODE));
    }

    /**
     * Get the dilution of precision (PDOP) for position.
     * 
     * @return double
     */
    public double getPositionDOP() {
        return getDoubleValue(POSITION_DOP);
    }

    /**
     * Get the horizontal dilution Of precision (HDOP).
     * 
     * @return double
     */
    public double getHorizontalDOP() {
        return getDoubleValue(HORIZONTAL_DOP);
    }

    /**
     * Get the vertical dilution of precision (VDOP).
     * 
     * @return double
     */
    public double getVerticalDOP() {
        return getDoubleValue(VERTICAL_DOP);
    }

    /**
     * Get list of satellites used for acquiring the GPS fix.
     * 
     * @return String array containing satellite IDs.
     */
    public String[] getSatellitesIds() {
        if (this.satellites == null) {
            this.parseSatellites();
        }
        return this.satellites;
    }

    /**
     * Parses the satellite IDs to String array.
     */
    private void parseSatellites() {
        List<String> result = new ArrayList<String>();
        for (int i = FIRST_SV; i <= LAST_SV; i++) {
            if (hasValue(i)) {
                result.add(getStringValue(i));
            }
        }
        this.satellites = result.toArray(new String[result.size()]);
    }

}

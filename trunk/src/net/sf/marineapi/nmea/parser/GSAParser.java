/* 
 * GSAParser.java
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

import net.sf.marineapi.nmea.sentence.GSASentence;
import net.sf.marineapi.nmea.util.GpsFixStatus;
import net.sf.marineapi.nmea.util.GpsMode;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * GSA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class GSAParser extends SentenceParser implements GSASentence {

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
     * @param nmea GSA sentence
     * @throws SentenceException if specified NMEA string is invalid
     */
    public GSAParser(String nmea) {
        super(nmea, SentenceId.GSA);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getGpsMode()
     */
    public GpsMode getGpsMode() {
        return GpsMode.valueOf(getCharValue(GPS_MODE));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getFixStatus()
     */
    public GpsFixStatus getFixStatus() {
        return GpsFixStatus.valueOf(getIntValue(FIX_MODE));
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getPositionDOP()
     */
    public double getPositionDOP() {
        return getDoubleValue(POSITION_DOP);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getHorizontalDOP()
     */
    public double getHorizontalDOP() {
        return getDoubleValue(HORIZONTAL_DOP);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getVerticalDOP()
     */
    public double getVerticalDOP() {
        return getDoubleValue(VERTICAL_DOP);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getSatellitesIds()
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

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
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.GpsFixStatus;

/**
 * GSA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class GSAParser extends SentenceParser implements GSASentence {

    // field indices
    private static final int GPS_MODE = 0;
    private static final int FIX_MODE = 1;
    private static final int FIRST_SV = 2;
    private static final int LAST_SV = 13;
    private static final int POSITION_DOP = 14;
    private static final int HORIZONTAL_DOP = 15;
    private static final int VERTICAL_DOP = 16;

    /**
     * Creates GSA parser with empty sentence.
     */
    public GSAParser() {
        super(TalkerId.GP, SentenceId.GSA, 17);
    }

    /**
     * Creates a new instance of GSA parser.
     * 
     * @param nmea GSA sentence String
     * @throws IllegalArgumentException If specified sentence is invalid.
     */
    public GSAParser(String nmea) {
        super(nmea, SentenceId.GSA);
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
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getMode()
     */
    public FaaMode getMode() {
        return FaaMode.valueOf(getCharValue(GPS_MODE));
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
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getPositionDOP()
     */
    public double getPositionDOP() {
        return getDoubleValue(POSITION_DOP);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#getSatellitesIds()
     */
    public String[] getSatellitesIds() {
        List<String> result = new ArrayList<String>();
        for (int i = FIRST_SV; i <= LAST_SV; i++) {
            if (hasValue(i)) {
                result.add(getStringValue(i));
            }
        }
        return result.toArray(new String[result.size()]);
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
     * @see
     * net.sf.marineapi.nmea.sentence.GSASentence#setFixStatus(net.sf.marineapi
     * .nmea.util.GpsFixStatus)
     */
    public void setFixStatus(GpsFixStatus status) {
        setIntValue(FIX_MODE, status.toInt());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.GSASentence#setFaaMode(net.sf.marineapi
     * .nmea.util.FaaMode)
     */
    public void setMode(FaaMode mode) {
        setCharValue(GPS_MODE, mode.toChar());
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.GSASentence#setHorizontalPrecision(double)
     */
    public void setHorizontalDOP(double hdop) {
        setDoubleValue(HORIZONTAL_DOP, hdop, 1, 1);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#setPositionDOP(double)
     */
    public void setPositionDOP(double pdop) {
        setDoubleValue(POSITION_DOP, pdop, 1, 1);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.GSASentence#setSatelliteIds(java.lang.
     * String[])
     */
    public void setSatelliteIds(String[] ids) {
        if (ids.length > (LAST_SV - FIRST_SV + 1)) {
            throw new IllegalArgumentException("List length exceeded (12)");
        }
        int j = 0;
        for (int i = FIRST_SV; i <= LAST_SV; i++) {
            String id = (j < ids.length) ? ids[j++] : "";
            setStringValue(i, id);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.GSASentence#setVerticalDOP(double)
     */
    public void setVerticalDOP(double vdop) {
        setDoubleValue(VERTICAL_DOP, vdop, 1, 1);
    }

}

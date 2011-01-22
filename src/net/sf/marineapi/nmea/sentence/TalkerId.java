/* 
 * TalkerId.java
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
package net.sf.marineapi.nmea.sentence;

/**
 * Talker defines the supported NMEA Talker IDs. Talker ID is the first two
 * characters in the address field, e.g. "GP" in "$GPGGA". *
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.sentence.SentenceId
 */
public enum TalkerId {
    /** Autopilot - General */
    AG,
    /** Autopilot - Magnetic */
    AP,
    /** Global Positioning System receiver */
    GP,
    /** Loran-C receiver */
    LC,
    /** Integrated Instrumentation */
    II,
    /** Integrated Navigation */
    IN,
    /** Electronic Chart Display & Information System (ECDIS) */
    EC,
    /** Digital Selective Calling (DSC) */
    CD,
    /** GLONASS, according to IEIC 61162-1 */
    GL,
    /** Mixed GPS and GLONASS data, according to IEIC 61162-1 */
    GN,
    /** Heading - Magnetic Compass */
    HC,
    /** Heading - North Seeking Gyro */
    HE,
    /** Heading - Non North Seeking Gyro */
    HN,
    /** RADAR and/or ARPA */
    RA,
    /** Depth - Sounder */
    SD,
    /** Velocity Sensor, Doppler, other/general */
    VD,
    /** Velocity Sensor, Speed Log, Water, Magnetic */
    DM,
    /** Velocity Sensor, Speed Log, Water, Mechanical */
    VW,
    /** Weather Instruments */
    WI;

    /**
     * Parses the talker id from specified sentence String.
     * 
     * @param nmea Sentence String
     * @return TalkerId enum
     */
    public static TalkerId parse(String nmea) {
        String tid = nmea.substring(1, 3);
        return TalkerId.valueOf(tid);
    }
}

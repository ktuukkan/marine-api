/* 
 * SentenceId.java
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
 * Defines the supported NMEA 0831 sentence types. Sentence address field is a
 * combination of talker and sentence IDs, for example GPBOD, GPGGA or GPGGL.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.sentence.TalkerId
 */
public enum SentenceId {

    /** Bearing Origin to Destination */
    BOD,
    /** Depth of water below transducer; in meters, feet and fathoms */
    DBT,
    /** Depth of water below transducer; in meters. */
    DPT,
    /** Global Positioning System fix data */
    GGA,
    /** Geographic position (latitude/longitude) */
    GLL,
    /** Dilution of precision (DOP) of GPS fix and active satellites */
    GSA,
    /** Detailed satellite data */
    GSV,
    /** Vessel heading in degrees with magnetic variation and deviation. */
    HDG,
    /** Vessel heading in degrees with respect to true north. */
    HDM,
    /** Vessel heading in degrees true */
    HDT,
    /** Wind speed and angle */
    MWV,
    /** Recommended minimum navigation information */
    RMB,
    /** Recommended minimum specific GPS/TRANSIT data */
    RMC,
    /** Route data and waypoint list */
    RTE,
    /** Track made good and ground speed */
    VTG,
    /** Waypoint location (latitude/longitude) */
    WPL,
    /** UTC time and date with local time zone offset */
    ZDA;

    /**
     * Parses the sentence id from specified sentence String and returns a
     * corresponding <code>SentenceId</code> enum (assuming it exists).
     * 
     * @param nmea Sentence String
     * @return SentenceId enum
     * @throws IllegalArgumentException If specified String is not valid
     *             sentence
     */
    public static SentenceId parse(String nmea) {
        String sid = parseStr(nmea);
        return SentenceId.valueOf(sid);
    }

    /**
     * Parses the sentence id from specified sentence String and returns it as
     * String.
     * 
     * @param nmea Sentence String
     * @return Sentence Id, e.g. "GGA" or "GLL"
     * @throws IllegalArgumentException If specified String is not valid
     */
    public static String parseStr(String nmea) {

        if (!SentenceValidator.isValid(nmea)) {
            throw new IllegalArgumentException("String is not valid sentence");
        }

        String id = null;
        if (nmea.startsWith("$P")) {
            id = nmea.substring(2, 6);
        } else {
            id = nmea.substring(3, 6);
        }
        return id;
    }
}

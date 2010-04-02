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
    /** Global Positioning System fix data */
    GGA,
    /** Geographic position (latitude/longitude) */
    GLL,
    /** Dilution of precision (DOP) of GPS fix and active satellites */
    GSA,
    /** Detailed satellite data */
    GSV,
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
}

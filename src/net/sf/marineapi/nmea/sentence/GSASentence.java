/* 
 * GSASentence.java
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

import net.sf.marineapi.nmea.util.GpsFixStatus;
import net.sf.marineapi.nmea.util.GpsMode;

/**
 * Interface for GSA sentence type. Dilution of precision (DOP) of GPS fix and
 * list of active satellites. DOP is an indication of the effect of satellite
 * geometry on the accuracy of the fix. It is a unitless number where smaller is
 * better.
 * <p>
 * Example:<br>
 * <code>$GPGSA,A,3,02,,,07,,09,24,26,,,,,1.6,1.6,1.0*3D</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface GSASentence extends Sentence {

    /**
     * Get the GPS fix mode (2D, 3D or no fix).
     * 
     * @return GpsFixStatus enum
     */
    GpsFixStatus getFixStatus();

    /**
     * Get the GPS operation mode.
     * 
     * @return GpsMode enum
     */
    GpsMode getGpsMode();

    /**
     * Get the horizontal dilution Of precision (HDOP).
     * 
     * @return double
     */
    double getHorizontalDOP();

    /**
     * Get the dilution of precision (PDOP) for position.
     * 
     * @return double
     */
    double getPositionDOP();

    /**
     * Get list of satellites used for acquiring the GPS fix.
     * 
     * @return String array containing satellite IDs.
     */
    String[] getSatellitesIds();

    /**
     * Get the vertical dilution of precision (VDOP).
     * 
     * @return double
     */
    double getVerticalDOP();

}

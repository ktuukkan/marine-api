/* 
 * SatelliteInfo.java
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
package net.sf.marineapi.nmea.util;

/**
 * SatelliteInfo represents the information about a single GPS satellite
 * vehicle.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.sentence.GSVSentence
 */
public class SatelliteInfo {

    private final String id;
    private final double elevation;
    private final double azimuth;
    private final double noise;

    /**
     * Creates a new instance of SatelliteInfo
     * 
     * @param id Satellite ID
     * @param elevation Current elevation of the satellite
     * @param azimuth Current azimuth of the satellite
     * @param noise Current noise ratio of the satellite signal
     */
    public SatelliteInfo(String id, double elevation, double azimuth,
            double noise) {

        this.id = id;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.noise = noise;
    }

    /**
     * Get satellite azimuth, in degrees from true north (0..359&deg;).
     * 
     * @return azimuth value
     */
    public double getAzimuth() {
        return this.azimuth;
    }

    /**
     * Get satellite elevation, in degrees (max. 90&deg;).
     * 
     * @return elevation value
     */
    public double getElevation() {
        return this.elevation;
    }

    /**
     * Get the ID of satellite vehicle, for example "05".
     * 
     * @return ID String
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get satellite the signal noise ratio, in dB (0-99 dB).
     * 
     * @return noise value
     */
    public double getSignalNoiseRatio() {
        return this.noise;
    }

}

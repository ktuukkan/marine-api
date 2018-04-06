/*
 * GBSSentence.java
 * Copyright (C) 2018 Kimmo Tuukkanen
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
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
 * GNSS satellite fault detection (RAIM support).
 * <p>
 * Example:<br>
 * <code>$GPGBS,215643.00,0.1190,-0.0872,-0.3320,13,0.9009,-0.8342,2.3281*46</code>
 *
 * @author Kimmo Tuukkanen
 */
public interface GBSSentence extends TimeSentence {

    /**
     * Returns the expected error in latitude.
     *
     * @return Error in meters, due to bias, with noise = 0.
     */
    double getLatitudeError();

    /**
     * Sets the expected error in latitude.
     *
     * @param err Error in meters, due to bias, with noise = 0
     */
    void setLatitudeError(double err);

    /**
     * Returns the expected error in longitude.
     *
     * @return Error in meters, due to bias, with noise = 0.
     */
    double getLongitudeError();

    /**
     * Sets the expected error in longitude.
     *
     * @param err Error in meters, due to bias, with noise = 0
     */
    void setLongitudeError(double err);

    /**
     * Returns the expected error in altitude.
     *
     * @return Error in meters, due to bias, with noise = 0.
     */
    double getAltitudeError();

    /**
     * Sets the expected error in altitude.
     *
     * @param err Error in meters, due to bias, with noise = 0
     */
    void setAltitudeError(double err);

    /**
     * Returns the ID of most likely failed satellite.
     *
     * @return Satellite ID number
     */
    String getSatelliteId();

    /**
     * Sets the ID of most likely failed satellite.
     *
     * @param id Satellite ID to set
     */
    void setSatelliteId(String id);

    /**
     * Returns the probability of missed detection of most likely failed
     * satellite.
     *
     * @return Probability of missed detection
     */
    double getProbability();

    /**
     * Sets the probability of missed detection of most likely failed satellite.
     *
     * @param probability Probability value
     */
    void setProbability(double probability);

    /**
     * Returns the estimate of bias on the most likely failed satellite.
     *
     * @return Bias estimate, in meters.
     */
    double getEstimate();

    /**
     * Sets the estimate of bias on the most likely failed satellite.
     *
     * @param estimate Bias estimate to set, in meters.
     */
    void setEstimate(double estimate);

    /**
     * Returns the standard deviation of bias estimate.
     *
     * @return Standard deviation of estimate
     */
    double getDeviation();

    /**
     * Sets the standard deviation of bias estimate.
     *
     * @param deviation Standard deviation value to set.
     */
    void setDeviation(double deviation);
}

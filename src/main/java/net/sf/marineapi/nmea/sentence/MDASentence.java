/*
 * MDASentence.java
 * Copyright (C) 2015 INDI for Java NMEA 0183 stream driver
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
 * Meteorological Composite - Barometric pressure, air and water temperature,
 * humidity, dew point and wind speed and direction relative to the surface of
 * the earth.
 * 
 * @author Richard van Nieuwenhoven
 */
public interface MDASentence extends Sentence {

    /**
     * @return Wind speed, knots. NaN if not available.
     */
    public double getWindSpeedKnots();

    /**
     * @return Absolute humidity, percent, to the nearest 0,1 percent. NaN if
     *         not available.
     */
    double getAbsoluteHumidity();

    /**
     * @return Air temperature, degrees C, to the nearest 0,1 degree C. NaN if
     *         not available.
     */
    double getAirTemperature();

    /**
     * @return Barometric pressure, bars, to the nearest .001 bar. NaN if not
     *         available.
     */
    double getSecondaryBarometricPressure();

    /**
     * @return B = bar I = inches of mercury (inHg) P = pascal (1 bar = 100000
     *         Pa = 29,53 inHg). The secondary unit is almost always in bars.
     */
    char getSecondaryBarometricPressureUnit();

    /**
     * @return Barometric pressure, inches of mercury. NaN if not available.
     */
    double getPrimaryBarometricPressure();

    /**
     * @return B = bar I = inches of mercury (inHg) P = pascal (1 bar = 100000
     *         Pa = 29,53 inHg).
     */
    char getPrimaryBarometricPressureUnit();

    /**
     * @return Dew point, degrees C, to the nearest 0,1 degree C. NaN if not
     *         available.
     */
    double getDewPoint();

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    double getMagneticWindDirection();

    /**
     * @return Relative humidity, percent, to the nearest 0,1 percent. NaN if
     *         not available.
     */
    double getRelativeHumidity();

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    double getTrueWindDirection();

    /**
     * @return Water temperature, degrees C. NaN if not available.
     */
    double getWaterTemperature();

    /**
     * @return Wind speed, meters per second, to the nearest 0,1 m/s. NaN if not
     *         available.
     */
    double getWindSpeed();

    /**
     * Sets the absolute humidity.
     * 
     * @param humitidy
     *            Humidity percent to set.
     */
    void setAbsoluteHumidity(double humitidy);

    /**
     * Sets the air temperature.
     * 
     * @param temp
     *            Temperature to set, degrees Celsius.
     */
    void setAirTemperature(double temp);

    /**
     * Sets the barometric pressure (bars).
     * 
     * @param pressure
     *            Pressure to set, in bars.
     */
    void setSecondaryBarometricPressure(double pressure);

    /**
     * Sets the barometric pressure (hg).
     * 
     * @param pressure
     *            Pressure value to set, in inches of mercury.
     */
    void setPrimaryBarometricPressure(double pressure);

    /**
     * Sets the dew point temperature.
     * 
     * @param dewPoint
     *            Dew point in degrees Celsius.
     */
    void setDewPoint(double dewPoint);

    /**
     * Sets the magnetic wind direction.
     * 
     * @param direction
     *            Direction to set, degrees Magnetic [0..360]
     */
    void setMagneticWindDirection(double direction);

    /**
     * Sets the relative humidity
     * 
     * @param humidity
     *            Humidity percent to set.
     */
    void setRelativeHumidity(double humidity);

    /**
     * Sets the True wind direction.
     * 
     * @param direction
     *            Direction to set, degrees True [0..360]
     */
    void setTrueWindDirection(double direction);

    /**
     * Sets the Water temperature.
     * 
     * @param temp
     *            Temperature in degrees Celsius.
     */
    void setWaterTemperature(double temp);

    /**
     * Sets the wind speed.
     * 
     * @param speed
     *            Wind speed in meters per second.
     */
    void setWindSpeed(double speed);

    /**
     * Sets the wind speed, in knots.
     * 
     * @param speed
     *            Wind speed in knots.
     * @see #setWindSpeedKnots(double)
     */
    void setWindSpeedKnots(double speed);
}

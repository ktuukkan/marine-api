package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.nmea.sentence.Sentence;

/**
 * Meteorological Composite - Barometric pressure, air and water temperature,
 * humidity, dew point and wind speed and direction relative to the surface
 * of the earth.
 * 
 * @author Richard van Nieuwenhoven
 *
 */
public interface MDASentence extends Sentence {

    /**
     * @return Barometric pressure, bars, to the nearest .001 bar. NaN if not
     *         available.
     */
    double getBarometricPressure();
    
    /**
     * @return Barometric pressure, inches of mercury. NaN if not
     *         available.
     */
    double getBarometricPressureInHg();

    /**
     * @return Air temperature, degrees C, to the nearest 0,1 degree C. NaN if
     *         not available.
     */
    double getAirTemperature();

    /**
     * @return Water temperature, degrees C. NaN if not
     *         available.
     */
    double getWaterTemperature();

    /**
     * @return Relative humidity, percent, to the nearest 0,1 percent. NaN if
     *         not available.
     */
    double getRelativeHumidity();

    /**
     * @return Absolute humidity, percent, to the nearest 0,1 percent. NaN if
     *         not available.
     */
    double getAbsoluteHumidity();

    /**
     * @return Dew point, degrees C, to the nearest 0,1 degree C. NaN if not
     *         available.
     */
    double getDewPoint();

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    double getTrueWindDirection();

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    double getMagneticWindDirection();

    /**
     * @return Wind speed, meters per second, to the nearest 0,1 m/s. NaN if not
     *         available.
     */
    double getWindSpeed();

    /**
     * @return Wind speed, knots. NaN if not available.
     */
    public double getWindSpeedKnot();
}

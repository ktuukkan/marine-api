package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.MDASentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Meteorological Composite - Barometric pressure, air and water temperature,
 * humidity, dew point and wind speed and direction relative to the surface
 * of the earth.
 * 
 * @author Richard van Nieuwenhoven
 *
 */
public class MDAParser extends SentenceParser implements MDASentence {

    public static final String MDASentenceId = "MDA";

    /**
     * Barometric pressure, inches of mercury, to the nearest 0,01 inch.
     */
    private static int BAROMETRIC_PRESSURE = 0;

    /**
     * I = inches of mercury (inHg) P = pascal (1 bar = 100000 Pa = 29,53 inHg).
     */
    private static int BAROMETRIC_PRESSURE_UNIT = 1;

    /**
     * Barometric pressure, bars, to the nearest .001 bar.
     */
    private static int BAROMETRIC_PRESSURE_B = 2;

    /**
     * B = bars.
     */
    private static int BAROMETRIC_PRESSURE_B_UNIT = 3;

    /**
     * Air temperature, degrees C, to the nearest 0,1 degree C.
     */
    private static int AIR_TEMPERATURE = 4;

    /**
     * C = degrees C.
     */
    private static int AIR_TEMPERATURE_UNIT = 5;

    /**
     * Water temperature, degrees C.
     */
    private static int WATER_TEMPERATURE = 6;

    /**
     * C = degrees C.
     */
    private static int WATER_TEMPERATURE_UNIT = 7;

    /**
     * Relative humidity, percent, to the nearest 0,1 percent.
     */
    private static int RELATIVE_HUMIDITY = 8;

    /**
     * Absolute humidity, percent .
     */
    private static int ABSOLUTE_HUMIDITY = 9;

    /**
     * Dew point, degrees C, to the nearest 0,1 degree C.
     */
    private static int DEW_POINT = 10;

    /**
     * C = degrees C.
     */
    private static int DEW_POINT_UNIT = 11;

    /**
     * Wind direction, degrees True, to the nearest 0,1 degree.
     */
    private static int WIND_DIRECTION_TRUE = 12;

    /**
     * T = true
     */
    private static int WIND_DIRECTION_TRUE_UNIT = 13;

    /**
     * Wind direction, degrees Magnetic, to the nearest 0,1 degree.
     */
    private static int WIND_DIRECTION_MAGNETIC = 14;

    /**
     * M = magnetic.
     */
    private static int WIND_DIRECTION_MAGNETIC_UNIT = 15;

    /**
     * Wind speed, knots, to the nearest 0,1 knot.
     */
    private static int WIND_SPEED_KNOTS = 16;

    /**
     * N = knots.
     */
    private static int WIND_SPEED_KNOTS_UNIT = 17;

    /**
     * Wind speed, meters per second, to the nearest 0,1 m/s.
     */
    private static int WIND_SPEED_METERS = 18;

    /**
     * M = meters per second
     */
    private static int WIND_SPEED_METERS_UNIT = 19;

    /**
     * Creates a new instance of MWVParser.
     * 
     * @param nmea
     *            MWV sentence String
     */
    public MDAParser(String nmea) {
        super(nmea, MDASentenceId);
    }

    /**
     * Creates a new empty instance of MWVParser.
     * 
     * @param talker
     *            Talker id to set
     */
    public MDAParser(TalkerId talker) {
        super(talker, MDASentenceId, 20);
    }

    /**
     * @return Barometric pressure, bars, to the nearest .001 bar. NaN if not
     *         available.
     */
    @Override
    public double getBarometricPressure() {
        if (hasValue(BAROMETRIC_PRESSURE_B) && hasValue(BAROMETRIC_PRESSURE_B_UNIT) && getStringValue(BAROMETRIC_PRESSURE_B_UNIT).equalsIgnoreCase("B")) {
            return getDoubleValue(BAROMETRIC_PRESSURE_B);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Barometric pressure, inches of mercury. NaN if not
     *         available.
     */
    @Override
    public double getBarometricPressureInHg() {
        if (hasValue(BAROMETRIC_PRESSURE) && hasValue(BAROMETRIC_PRESSURE_UNIT) && getStringValue(BAROMETRIC_PRESSURE_UNIT).equalsIgnoreCase("I")) {
            return getDoubleValue(BAROMETRIC_PRESSURE);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Air temperature, degrees C, to the nearest 0,1 degree C. NaN if
     *         not available.
     */
    @Override
    public double getAirTemperature() {
        if (hasValue(AIR_TEMPERATURE) && hasValue(AIR_TEMPERATURE_UNIT) && getStringValue(AIR_TEMPERATURE_UNIT).equalsIgnoreCase("C")) {
            return getDoubleValue(AIR_TEMPERATURE);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Water temperature, degrees C. NaN if not
     *         available.
     */
    @Override
    public double getWaterTemperature() {
        if (hasValue(WATER_TEMPERATURE) && hasValue(WATER_TEMPERATURE_UNIT) && getStringValue(WATER_TEMPERATURE_UNIT).equalsIgnoreCase("C")) {
            return getDoubleValue(WATER_TEMPERATURE);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Relative humidity, percent, to the nearest 0,1 percent. NaN if
     *         not available.
     */
    @Override
    public double getRelativeHumidity() {
        if (hasValue(RELATIVE_HUMIDITY)) {
            return getDoubleValue(RELATIVE_HUMIDITY);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Absolute humidity, percent, to the nearest 0,1 percent. NaN if
     *         not available.
     */
    @Override
    public double getAbsoluteHumidity() {
        if (hasValue(ABSOLUTE_HUMIDITY)) {
            return getDoubleValue(ABSOLUTE_HUMIDITY);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Dew point, degrees C, to the nearest 0,1 degree C. NaN if not
     *         available.
     */
    @Override
    public double getDewPoint() {
        if (hasValue(DEW_POINT) && hasValue(DEW_POINT_UNIT) && getStringValue(DEW_POINT_UNIT).equalsIgnoreCase("C")) {
            return getDoubleValue(DEW_POINT);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    @Override
    public double getTrueWindDirection() {
        if (hasValue(WIND_DIRECTION_TRUE) && hasValue(WIND_DIRECTION_TRUE_UNIT) && getStringValue(WIND_DIRECTION_TRUE_UNIT).equalsIgnoreCase("T")) {
            return getDoubleValue(WIND_DIRECTION_TRUE);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Wind direction, degrees True, to the nearest 0,1 degree. NaN if
     *         not available.
     */
    @Override
    public double getMagneticWindDirection() {
        if (hasValue(WIND_DIRECTION_MAGNETIC) && hasValue(WIND_DIRECTION_MAGNETIC_UNIT) && getStringValue(WIND_DIRECTION_MAGNETIC_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_DIRECTION_MAGNETIC);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Wind speed, meters per second, to the nearest 0,1 m/s. NaN if not
     *         available.
     */
    @Override
    public double getWindSpeed() {
        if (hasValue(WIND_SPEED_METERS) && hasValue(WIND_SPEED_METERS_UNIT) && getStringValue(WIND_SPEED_METERS_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_SPEED_METERS);
        } else {
            return Double.NaN;
        }
    }

    /**
     * @return Wind speed, knots. NaN if not available.
     */
    @Override
    public double getWindSpeedKnot() {
        if (hasValue(WIND_SPEED_KNOTS) && hasValue(WIND_SPEED_KNOTS_UNIT) && getStringValue(WIND_SPEED_KNOTS_UNIT).equalsIgnoreCase("N")) {
            return getDoubleValue(WIND_SPEED_KNOTS);
        } else {
            return Double.NaN;
        }
    }

}

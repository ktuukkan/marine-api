/*
 * MWDParser.java
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.MWDSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Wind speed and direction.
 * 
 * @author Richard van Nieuwenhoven
 */
class MWDParser extends SentenceParser implements MWDSentence {

    /**
     * Wind direction, degrees True, to the nearest 0,1 degree.
     */
    private static int WIND_DIRECTION_TRUE = 0;

    /**
     * T = true
     */
    private static int WIND_DIRECTION_TRUE_UNIT = 1;

    /**
     * Wind direction, degrees Magnetic, to the nearest 0,1 degree.
     */
    private static int WIND_DIRECTION_MAGNETIC = 2;

    /**
     * M = magnetic.
     */
    private static int WIND_DIRECTION_MAGNETIC_UNIT = 3;

    /**
     * Wind speed, knots, to the nearest 0,1 knot.
     */
    private static int WIND_SPEED_KNOTS = 4;

    /**
     * N = knots.
     */
    private static int WIND_SPEED_KNOTS_UNIT = 5;

    /**
     * Wind speed, meters per second, to the nearest 0,1 m/s.
     */
    private static int WIND_SPEED_METERS = 6;

    /**
     * M = meters per second
     */
    private static int WIND_SPEED_METERS_UNIT = 7;

    /**
     * Creates a new instance of MWDParser.
     * 
     * @param nmea MWV sentence String
     */
    public MWDParser(String nmea) {
        super(nmea, SentenceId.MWD);
    }

    /**
     * Creates a new empty instance of MWDParser.
     * 
     * @param talker Talker id to set
     */
    public MWDParser(TalkerId talker) {
        super(talker, SentenceId.MWD, 8);
        setCharValue(WIND_DIRECTION_TRUE_UNIT, 'T');
        setCharValue(WIND_DIRECTION_MAGNETIC_UNIT, 'M');
        setCharValue(WIND_SPEED_METERS_UNIT, 'M');
        setCharValue(WIND_SPEED_KNOTS_UNIT, 'N');
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getMagneticWindDirection()
     */
    @Override
    public double getMagneticWindDirection() {
        if (hasValue(WIND_DIRECTION_MAGNETIC) && hasValue(WIND_DIRECTION_MAGNETIC_UNIT) && getStringValue(WIND_DIRECTION_MAGNETIC_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_DIRECTION_MAGNETIC);
        } else {
            return Double.NaN;
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getTrueWindDirection()
     */
    @Override
    public double getTrueWindDirection() {
        if (hasValue(WIND_DIRECTION_TRUE) && hasValue(WIND_DIRECTION_TRUE_UNIT) && getStringValue(WIND_DIRECTION_TRUE_UNIT).equalsIgnoreCase("T")) {
            return getDoubleValue(WIND_DIRECTION_TRUE);
        } else {
            return Double.NaN;
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getWindSpeed()
     */
    @Override
    public double getWindSpeed() {
        if (hasValue(WIND_SPEED_METERS) && hasValue(WIND_SPEED_METERS_UNIT) && getStringValue(WIND_SPEED_METERS_UNIT).equalsIgnoreCase("M")) {
            return getDoubleValue(WIND_SPEED_METERS);
        } else {
            return Double.NaN;
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#getWindSpeedKnots()
     */
    @Override
    public double getWindSpeedKnots() {
        if (hasValue(WIND_SPEED_KNOTS) && hasValue(WIND_SPEED_KNOTS_UNIT) && getStringValue(WIND_SPEED_KNOTS_UNIT).equalsIgnoreCase("N")) {
            return getDoubleValue(WIND_SPEED_KNOTS);
        } else {
            return Double.NaN;
        }
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setMagneticWindDirections(double)
     */
    @Override
    public void setMagneticWindDirection(double direction) {
        setDegreesValue(WIND_DIRECTION_MAGNETIC, direction);
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setTrueWindDirection(double)
     */
    @Override
    public void setTrueWindDirection(double direction) {
        setDegreesValue(WIND_DIRECTION_TRUE, direction);       
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setWindSpeed(double)
     */
    @Override
    public void setWindSpeed(double speed) {
        setDoubleValue(WIND_SPEED_METERS, speed);        
    }

    /* (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.MWDSentence#setWindSpeedKnots(double)
     */
    @Override
    public void setWindSpeedKnots(double speed) {
        setDoubleValue(WIND_SPEED_KNOTS, speed);
    }
}

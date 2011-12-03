/* 
 * MWVSentence.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Units;

/**
 * Wind speed and angle. Speed in km/h, m/s, or knots, angle in degrees relative
 * to bow or true north.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface MWVSentence extends Sentence {

    /**
     * Get wind angle.
     * 
     * @return Wind angle in degrees.
     */
    double getAngle();

    /**
     * Set wind angle.
     * 
     * @param angle Wind angle in degrees.
     */
    void setAngle(double angle);

    /**
     * Tells if the angle is relative or true.
     * 
     * @return True is true angle, otherwise false (relative to bow)
     */
    boolean isTrue();

    /**
     * Set angle to relative or true.
     * 
     * @param isTrue True for true angle, false for relative to bow.
     */
    void setTrue(boolean isTrue);

    /**
     * Returns the wind speed.
     * 
     * @return Wind speed value
     */
    double getSpeed();

    /**
     * Set the wind speed value.
     * 
     * @param speed Wind speed to set.
     */
    void setSpeed(double speed);

    /**
     * Returns the wind speed unit.
     * 
     * @return {@link Units#METER} for meters per second, {@link Units#KMH} for
     *         kilometers per hour and {@link Units#KNOT} for knots.
     */
    Units getSpeedUnit();

    /**
     * Set wind speed unit.
     * 
     * @param unit {@link Units#METER} for meters per second, {@link Units#KMH}
     *            for kilometers per hour and {@link Units#KNOT} for knots.
     * @throws IllegalArgumentException If trying to set invalid unit
     */
    void setSpeedUnit(Units unit);

    /**
     * Get data validity status.
     * 
     * @return Data status
     */
    DataStatus getStatus();

    /**
     * Set data validity status.
     * 
     * @param status Data status to set.
     */
    void setStatus(DataStatus status);
}

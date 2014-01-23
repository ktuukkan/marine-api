/*
 * RateOfTurnSentence.java
 * Copyright (C) 2014 Mike Tamis
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
 * Common Rate of Turn interface.
 *
 * @author Mike Tamis
 */
public interface RateOfTurnSentence extends Sentence {

    /**
     * Returns the vessel's Rate Of Turn, degrees per minute, "-" means bow turns to port.
     *
     * @return Rate of Turn in degrees per minute
     */
     double getRateOfTurn();

    /**
     * Returns Status
     * @return True means data is valid
     */
    boolean getStatus();

}

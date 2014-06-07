/*
 * VLWSentence.java
 * Copyright (C) 2014 Kimmo Tuukkanen
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
 * <p>Distance traveled through water, cumulative and trip.</p>
 * 
 * <p>Example:<br/>
 * <code>$VWVLW,2.8,N,2.8,N*4C</code></p>
 * 
 * @author Kimmo Tuukkanen
 */
public interface VLWSentence extends Sentence {

	/** Kilometers */
	public static final char KM = 'K';

	/** Nautical miles */
	public static final char NM = 'N';

	/**
	 * Returns the total cumulative distance traveled.
	 * 
	 * @return Distance
	 * @see #getTotalUnits()
	 */
	double getTotal();

	/**
	 * Returns the unit of measurement for cumulative total distance.
	 * 
	 * @return Char indicator for unit
	 * @see #KM
	 * @see #NM
	 */
	char getTotalUnits();

	/**
	 * Returns the distance traveled since last reset.
	 * 
	 * @return Trip distance
	 * @see #getTripUnits()
	 */
	double getTrip();

	/**
	 * Returns the unit of measurement for distance since last reset.
	 * 
	 * @return Char indicator for unit
	 * @see #KM
	 * @see #NM
	 */
	char getTripUnits();

	/**
	 * Sets the total cumulative distance traveled.
	 * 
	 * @param distance Total distance to set.
	 * @see #setTotalUnits(char)
	 */
	void setTotal(double distance);

	/**
	 * Sets the units of measure for cumulative total distance.
	 * 
	 * @param unit Unit to set; 'K' for kilomters, 'N' for nautical miles.
	 * @throws IllegalArgumentException If trying to set invalid units char.
	 * @see #KM
	 * @see #NM
	 */
	void setTotalUnits(char unit);

	/**
	 * Sets the distance traveled since last reset.
	 * 
	 * @param distance Trip distance to set.
	 * @see #setTripUnits(char)
	 */
	void setTrip(double distance);

	/**
	 * Sets the units of measure for distance since last reset.
	 * 
	 * @param unit Unit to set; 'K' for kilomters, 'N' for nautical miles.
	 * @throws IllegalArgumentException If trying to set invalid units char.
	 * @see #KM
	 * @see #NM
	 */
	void setTripUnits(char unit);
}

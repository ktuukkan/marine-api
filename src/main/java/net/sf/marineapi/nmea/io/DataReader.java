/*
 * DataReader.java
 * Copyright (C) 2012 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.io;

/**
 * Common interface for data readers.
 * 
 * @author Kimmo Tuukkanen
 */
interface DataReader extends Runnable {

	/**
	 * Returns the current pause time between read attempts.
	 * 
	 * @return Reader interval in milliseconds.
	 */
	int getInterval();

	/**
	 * Tells if the reader is running and actively scanning the data source for
	 * new data.
	 * 
	 * @return <code>true</code> if running, otherwise <code>false</code>.
	 */
	boolean isRunning();

	/**
	 * Does nothing. This method exists for backward compatibility only.
	 * 
	 * @param interval Interval in milliseconds.
	 */
	void setInterval(int interval);

	/**
	 * Stops the reader permanently.
	 */
	void stop();
}

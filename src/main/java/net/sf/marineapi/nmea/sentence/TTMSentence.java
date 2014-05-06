/*
 * TTMSentence.java
 * Copyright (C) 2014 Johan Bergkvist
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

import net.sf.marineapi.nmea.util.AcquisitionType;
import net.sf.marineapi.nmea.util.TargetStatus;
import net.sf.marineapi.nmea.util.Units;

/**
 * Tracked Target Message. Range and bearing from radar to target. Course and
 * speed of target. One message per target.
 * <p>
 * Example:<br>
 * <code>$RATTM,11,11.4,13.6,T,7.0,20.0,T,0.0,0.0,N,,Q,,154125.82,A,*17</code>
 *
 * @author Johan Bergkvist
 */
public interface TTMSentence extends TimeSentence {

	/**
	 * Get the number assigned to this target by the radar. The TTM sentence
	 * mandate a two digit number, so valid numbers are 0 to 99 inclusive.
	 *
	 * @return Target number in the range 0 - 99.
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException
	 *             If the data is not available.
	 */
	int getNumber();

	/**
	 * Get the distance from the radar to the target.
	 *
	 * @return Distance to target in nautical miles.
	 */
	double getDistance();

	/**
	 * Get the true bearing from the radar to the target.
	 *
	 * @return True bearing in degrees.
	 */
	double getBearing();

	/**
	 * Get the speed the target.
	 *
	 * @return Speed in kts (nautical miles per hour).
	 */
	double getSpeed();

	/**
	 * Get the course the target is maintaining.
	 *
	 * @return True course in degrees.
	 */
	double getCourse();

	/**
	 * Get the distance at the Closest Point of Approach (CPA).
	 *
	 * @return The distance in nautical miles.
	 */
	double getDistanceOfCPA();

	/**
	 * Get the time to the Closest Point of Approach (CPA).
	 *
	 * @return The time in minutes.
	 */
	double getTimeToCPA();

	/**
	 * Get the unit of measure used for distance and speeds.
	 *
	 * @return The unit of measure.
	 */
	Units getUnits();

	/**
	 * Get the name of the target as assigned by the radar.
	 *
	 * @return Name.
	 */
	String getName();

	/**
	 * Get the status of the target. A target is first in state QUERY while the
	 * radar works out firm data of the target. At first the calculated course
	 * and speed are rough and varies a lot; with time they settle at which
	 * point the target becomes TRACKING. A target no longer detected becomes
	 * LOST before TTM sentences will not be sent at all for the target.
	 *
	 * @return The state (QUERY, TRACKING, LOST)
	 */
	TargetStatus getStatus();

	/**
	 * A target may be used to calculate own ship position.
	 *
	 * @return True if this target is used to calculate own ship position.
	 */
	boolean getReference();

	/**
	 * Get the acquisition type of the target. AUTO indicates that the radar
	 * initiated the target. MANUAL indicates that the radar operator initiated
	 * the target in the radar. REPORTED indicates that the target came from
	 * another source. TODO: Is that true?
	 *
	 * @return The acquisition type (AUTO, MANUAL, REPORTED)
	 */
	AcquisitionType getAcquisitionType();

	/**
	 * Set the number of the target. Uniquely identifies the target.
	 *
	 * @param number
	 *            The number in the range 0 to 99 inclusive.
	 */
	void setNumber(int number);

	/**
	 * Set the distance from the radar to the target.
	 *
	 * @param distance
	 *            Distance in nautical miles.
	 */
	void setDistance(double distance);

	/**
	 * Set the true bearing from the radar to the target.
	 *
	 * @param bearing
	 *            True bearing in degrees.
	 */
	void setBearing(double bearing);

	/**
	 * Set the speed of the target.
	 *
	 * @param speed
	 *            Speed in kts (nautical miles per hour).
	 */
	void setSpeed(double speed);

	/**
	 * Set the course of the target.
	 *
	 * @param course
	 *            True course in degrees.
	 */
	void setCourse(double course);

	/**
	 * Set the distance at CPA.
	 *
	 * @param distance
	 *            Distance in nautical miles
	 */
	void setDistanceOfCPA(double distance);

	/**
	 * Set the time to CPA.
	 *
	 * @param minutes
	 *            Time to CPA in minutes.
	 */
	void setTimeToCPA(double minutes);

	/**
	 * Set the name of the target.
	 *
	 * @param name
	 *            The name as a string, probably not too long...
	 */
	void setName(String name);

	/**
	 * Set the Status of the target.
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getStatus()
	 * @param status
	 *            The status
	 */
	void setStatus(TargetStatus status);

	/**
	 * A target may be used to calculate own ship position.
	 *
	 * @param isReference
	 *            True if this target is used to calculate own ship position.
	 */
	void setReference(boolean isReference);

	/**
	 * Set the acquisition type of the target.
	 *
	 * @see net.sf.marineapi.nmea.sentence.TTMSentence#getAcquisitionType()
	 * @param acquisitionType
	 *            The acquisition type.
	 */
	void setAcquisitionType(AcquisitionType acquisitionType);
}

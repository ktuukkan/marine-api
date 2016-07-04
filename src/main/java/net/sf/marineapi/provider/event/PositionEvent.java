/* 
 * PositionEvent.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
package net.sf.marineapi.provider.event;

import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;

/**
 * GPS time/position/velocity report with current position, altitude, speed,
 * course and a time stamp. Notice that altitude may be missing, depending on
 * the source sentence of position (only
 * {@link net.sf.marineapi.nmea.sentence.GGASentence} contains altitude).
 * 
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.provider.PositionProvider
 * @see net.sf.marineapi.provider.event.PositionListener
 */
public class PositionEvent extends ProviderEvent implements Cloneable {

	private static final long serialVersionUID = 1L;
	private Double course;
	private Date date;
	private Position position;
	private Double speed;
	private Time time;
	private FaaMode mode;
	private GpsFixQuality fix;

	/**
	 * Creates a new instance of PositionEvent.
	 * 
	 * @param source Source object of event
	 */
	public PositionEvent(Object source, Position p, double sog, Double cog, Date d,
			Time t, FaaMode m, GpsFixQuality fq) {
		super(source);
		position = p;
		speed = sog;
		course = cog;
		date = d;
		time = t;
		mode = m;
		fix = fq;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PositionEvent clone() {
		return new PositionEvent(getSource(), position, speed, course, date, time,
				mode, fix);
	}

	/**
	 * Returns the current (true) course over ground.
	 * 
	 * @return the course
	 */
	public Double getCourse() {
		return course;
	}

	/**
	 * Returns the date.
	 * 
	 * @return Date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns the current GPS fix quality.
	 * 
	 * @return GpsFixQuality
	 */
	public GpsFixQuality getFixQuality() {
		return fix;
	}

	/**
	 * <p>Returns the current FAA operating mode of GPS receiver.</p>
	 * <p>Notice: may be always <code>null</code>, depending on the NMEA version
	 * in use.</p>
	 * 
	 * @return FaaMode
	 */
	public FaaMode getMode() {
		return mode;
	}

	/**
	 * Returns the current position.
	 * 
	 * @return Position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Returns the current speed over ground, in km/h.
	 * 
	 * @return the speed
	 */
	public Double getSpeed() {
		return speed * 1.852;
	}

	/**
	 * Returns the time.
	 * 
	 * @return Time
	 */
	public Time getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.EventObject#toString()
	 */
	@Override
	public String toString() {
		String ptr = "t[%s %s] p%s v[%.01f, %.01f]";
		return String.format(ptr, date, time, position, speed, course);
	}
}

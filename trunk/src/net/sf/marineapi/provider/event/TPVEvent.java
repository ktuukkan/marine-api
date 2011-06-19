/* 
 * GPSUpdateEvent.java
 * Copyright (C) 2010-2011 Kimmo Tuukkanen
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
package net.sf.marineapi.provider.event;

import java.util.EventObject;

import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.provider.TPVProvider;

/**
 * GPS update event with current position, altitude, speed, course and time
 * stamp.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see TPVProvider
 * @see TPVListener
 */
public class TPVEvent extends EventObject {

    private static final long serialVersionUID = 1L;
    private Double course;
    private Date date;
    private Position position;
    private Double speed;
    private Time time;

    /**
     * Creates a new instance of GPSUpdateEvent.
     * 
     * @param source Source object of event
     */
    public TPVEvent(Object source, Position p, double sog, double cog, Date d,
            Time t) {
        super(source);
        position = p;
        speed = sog;
        course = cog;
        date = d;
        time = t;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public TPVEvent clone() {
        return new TPVEvent(getSource(), position, speed, course, date, time);
    }

    /**
     * @return the course
     */
    public Double getCourse() {
        return course;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return the speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * @return the time
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
        String ptr = "time[%s, %s] pos%s velocity[%f, %f] ";
        return String.format(ptr, date, time, position, speed, course);
    }
}

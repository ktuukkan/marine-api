/* 
 * SentenceEvent.java
 * Copyright 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.event;

import java.util.EventObject;

import net.sf.marineapi.nmea.parser.Sentence;

/**
 * Event object to be sent when sentence events occur. Sentence event occurs
 * when a sentence has been read from NMEA source.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceEvent extends EventObject {

    private static final long serialVersionUID = -2756954014186470514L;
    private final long timestamp;
    private final Sentence sentence;

    /**
     * Creates a new SentenceEvent object.
     * 
     * @param source <code>Object</code> that fired this event
     * @param nmea A valid NMEA sentence <code>String</code>
     * @throws <code>IllegalArgumentExcepion</code> if sentence is invalid
     */
    public SentenceEvent(Object source, String nmea) {
        super(source);
        timestamp = System.currentTimeMillis();
        sentence = new Sentence(nmea);
    }

    /**
     * Get the time stamp when this event was created.
     * 
     * @return Milliseconds time stamp
     */
    public long getTimeStamp() {
        return timestamp;
    }

    /**
     * Gets the Sentence object that triggered the event.
     * 
     * @return Sentence object
     */
    public Sentence getSentence() {
        return sentence;
    }
}

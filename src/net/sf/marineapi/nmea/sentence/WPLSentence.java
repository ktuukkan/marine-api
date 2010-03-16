/* 
 * WPLSentence.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.util.Waypoint;

/**
 * Interface for WPL sentence type. Waypoint location (latitude/longitude) and
 * waypoint ID. This sentence is transmitted by some GPS models in GOTO mode.
 * <p>
 * Example: <br>
 * <code>$GPWPL,5536.200,N,01436.500,E,RUSKI*1F</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface WPLSentence extends Sentence {

    /**
     * Get the destination waypoint.
     * 
     * @return Waypoint
     */
    Waypoint getWaypoint();

}

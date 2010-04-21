/* 
 * RTESentence.java
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

import net.sf.marineapi.nmea.parser.DataNotAvailableException;
import net.sf.marineapi.nmea.util.RouteType;

/**
 * Interface for RTE sentence type. Route data and list of waypoint IDs.
 * <p>
 * Example:<br>
 * <code>$GPRTE,1,1,c,0,MELIN,RUSKI,KNUDAN*25</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface RTESentence extends Sentence {

    /**
     * Get the number or name of the route.
     * 
     * @return Route ID or name as String
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    String getRouteId();

    /**
     * Set the route name or number.
     * 
     * @param id Route ID or name as String
     */
    void setRouteId(String id);

    /**
     * Get the number of sentences in RTE sequence.
     * 
     * @return integer
     * @see #getSentenceIndex()
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    int getSentenceCount();

    /**
     * Set the number of sentences in RTE sequence.
     * 
     * @param count Sentence count in sequence
     * @throws IllegalArgumentException If the specified count is negative.
     */
    void setSentenceCount(int count);

    /**
     * Get the index of sentence in RTE sequence.
     * 
     * @return integer
     * @see #getSentenceCount()
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    int getSentenceIndex();

    /**
     * Set the index of sentence in RTE sequence.
     * 
     * @param index Sentence index in sequence
     * @throws IllegalArgumentException If specified index is negative.
     */
    void setSentenceIndex(int index);

    /**
     * Get the number of waypoints IDs in this sentence.
     * 
     * @return Waypoint count
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    int getWaypointCount();

    /**
     * Get the list of route waypoints.
     * 
     * @return Waypoint IDs as String array
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    String[] getWaypointIds();

    /**
     * Set the list of route waypoints.
     * 
     * @param ids String array of waypoint IDs
     */
    void setWaypointIds(String[] ids);

    /**
     * Set the type of route.
     * 
     * @param type RouteType to set
     */
    void setRouteType(RouteType type);

    /**
     * Tells if the sentence holds a current active route data.
     * 
     * @return true if active route, otherwise false.
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    boolean isActiveRoute();

    /**
     * Tells if this is the first sentence in RTE sequence.
     * 
     * @return true if there's no sentences left, otherwise false.
     * @throws DataNotAvailableException If the sentence index or sentence count
     *             is not available.
     * @throws ParseException If sentence index or count fields contain
     *             unexpected or illegal value.
     */
    boolean isFirst();

    /**
     * Tells if this is the last sentence in RTE sequence.
     * 
     * @return true if there's no sentences left, otherwise false.
     * @throws DataNotAvailableException If the sentence index or sentence count
     *             is not available.
     * @throws ParseException If sentence index or count fields contain
     *             unexpected or illegal value.
     */
    boolean isLast();

    /**
     * Tells if the sentence holds a current working route data.
     * 
     * @return true if working route, otherwise false.
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    boolean isWorkingRoute();

}

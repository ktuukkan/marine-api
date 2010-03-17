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
     * Active route: complete, all waypoints in route order.
     */
    public static final char ACTIVE_ROUTE = 'c';

    /**
     * Working route: the waypoint you just left, the waypoint you're heading to
     * and then all the rest.
     */
    public static final char WORKING_ROUTE = 'w';

    /**
     * Get the number of waypoints IDs in this sentence.
     * 
     * @return integer
     */
    int getWaypointCount();

    /**
     * Get the number or name of the route.
     * 
     * @return String
     */
    String getRouteId();

    /**
     * Get the number of sentences in RTE sequence.
     * 
     * @return integer
     * @see #getSentenceIndex()
     */
    int getSentenceCount();

    /**
     * Get the index of sentence in RTE sequence.
     * 
     * @return integer
     * @see #getSentenceCount()
     */
    int getSentenceIndex();

    /**
     * Get the list of waypoints of route.
     * 
     * @return String array containing waypoint IDs
     */
    String[] getWaypointIds();

    /**
     * Tells if the sentence holds a current active route data.
     * 
     * @return true if active route, otherwise false.
     */
    boolean isActiveRoute();

    /**
     * Tells if this is the first sentence in RTE sequence.
     * 
     * @return true if there's no sentences left, otherwise false.
     */
    boolean isFirst();

    /**
     * Tells if this is the last sentence in RTE sequence.
     * 
     * @return true if there's no sentences left, otherwise false.
     */
    boolean isLast();

    /**
     * Tells if the sentence holds a current working route data.
     * 
     * @return true if working route, otherwise false.
     */
    boolean isWorkingRoute();

}

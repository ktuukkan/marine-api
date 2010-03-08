/* 
 * SentenceRTE.java
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.util.SentenceId;

/**
 * RTE sentence parser. Route data and waypoint list.
 * <p>
 * Example: <code>$GPRTE,1,1,c,0,MELIN,RUSKI,KNUDAN*25</code>
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceRTE extends Sentence {

    /** Active route: complete, all waypoints in route order. */
    public static final char ACTIVE_ROUTE = 'c';

    /**
     * Working route: the waypoint you just left, the waypoint you're heading to
     * and then all the rest.
     */
    public static final char WORKING_ROUTE = 'w';

    // fields indices
    private static final int NUMBER_OF_SENTENCES = 1;
    private static final int SENTENCE_NUMBER = 2;
    private static final int STATUS = 3;
    private static final int ROUTE_ID = 4;
    private static final int FIRST_WPT = 5;
    // waypoint list
    private String[] waypoints = null;

    /**
     * Creates a new instance of RTE parser.
     * 
     * @param nmea RTE sentence string.
     */
    public SentenceRTE(String nmea) {
        super(nmea, SentenceId.RTE);
    }

    /**
     * Get the list of waypoints of route.
     * 
     * @return String array containing waypoint IDs
     */
    public String[] getWaypointIds() {
        if (waypoints == null) {
            parseWaypoints();
        }
        return waypoints;
    }

    /**
     * Get the number of waypoint IDs in this sentence.
     * 
     * @return integer
     */
    public int getNumberOfWaypoints() {
        return getWaypointIds().length;
    }

    /**
     * Tells if the sentence holds a current active route data.
     * 
     * @return true if active route, otherwise false.
     */
    public boolean isActiveRoute() {
        return getCharValue(STATUS) == ACTIVE_ROUTE;
    }

    /**
     * Tells if the sentence holds a current working route data.
     * 
     * @return true if working route, otherwise false.
     */
    public boolean isWorkingRoute() {
        return getCharValue(STATUS) == WORKING_ROUTE;
    }

    /**
     * Get the number of sentences in RTE sequence.
     * 
     * @return integer
     * @see #getSentenceNumber()
     */
    public int getNumberOfSentences() {
        return getIntValue(NUMBER_OF_SENTENCES);
    }

    /**
     * Get the number of this sentence in RTE sequence.
     * 
     * @return integer
     * @see #getNumberOfSentences()
     */
    public int getSentenceNumber() {
        return getIntValue(SENTENCE_NUMBER);
    }

    /**
     * Tells if this sentence is the first one of the RTE sequence.
     * 
     * @return true if there's no sentences left, otherwise false.
     */
    public boolean isFirstInSequence() {
        return (getSentenceNumber() == 1);
    }

    /**
     * Tells if this sentence is the last one of the RTE sequence.
     * 
     * @return true if there's no sentences left, otherwise false.
     */
    public boolean isLastInSequence() {
        return (getSentenceNumber() == getNumberOfSentences());
    }

    /**
     * Get the number or name of the route.
     * 
     * @return String
     */
    public String getRouteId() {
        return getStringValue(ROUTE_ID);
    }

    /**
     * Parse waypoint IDs into String array.
     */
    private void parseWaypoints() {

        int end = toString().indexOf(CHECKSUM_DELIMITER);
        String wp = toString().substring(0, end);

        if (wp == null) {
            waypoints = null;
            throw new ParseException("Unable to parse waypoint list.");
        }

        String[] fields = wp.split(String.valueOf(Sentence.FIELD_DELIMITER));
        waypoints = new String[(fields.length - FIRST_WPT)];

        int j = 0;
        for (int i = FIRST_WPT; i < fields.length; i++) {
            waypoints[j++] = fields[i];
        }
    }
}

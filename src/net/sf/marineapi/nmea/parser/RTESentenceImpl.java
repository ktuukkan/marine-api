/* 
 * RTESentenceImpl.java
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

import net.sf.marineapi.nmea.sentence.RTESentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * RTE sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class RTESentenceImpl extends SentenceImpl implements RTESentence {

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
    public RTESentenceImpl(String nmea) {
        super(nmea, SentenceId.RTE);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#getWaypointIds()
     */
    public String[] getWaypointIds() {
        if (waypoints == null) {
            parseWaypoints();
        }
        return waypoints;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#getNumberOfWaypoints()
     */
    public int getWaypointCount() {
        return getWaypointIds().length;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#isActiveRoute()
     */
    public boolean isActiveRoute() {
        return getCharValue(STATUS) == ACTIVE_ROUTE;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#isWorkingRoute()
     */
    public boolean isWorkingRoute() {
        return getCharValue(STATUS) == WORKING_ROUTE;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#getNumberOfSentences()
     */
    public int getSentenceCount() {
        return getIntValue(NUMBER_OF_SENTENCES);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#getSentenceNumber()
     */
    public int getSentenceIndex() {
        return getIntValue(SENTENCE_NUMBER);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#isFirstInSequence()
     */
    public boolean isFirst() {
        return (getSentenceIndex() == 1);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#isLastInSequence()
     */
    public boolean isLast() {
        return (getSentenceIndex() == getSentenceCount());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RTESentence#getRouteId()
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

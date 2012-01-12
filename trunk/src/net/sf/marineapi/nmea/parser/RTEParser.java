/* 
 * RTEParser.java
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

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.sentence.RTESentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.RouteType;

/**
 * RTE sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
class RTEParser extends SentenceParser implements RTESentence {

    // fields indices
    private static final int NUMBER_OF_SENTENCES = 0;
    private static final int SENTENCE_NUMBER = 1;
    private static final int STATUS = 2;
    private static final int ROUTE_ID = 3;
    private static final int FIRST_WPT = 4;

    /**
     * Creates a new instance of RTE parser.
     * 
     * @param nmea RTE sentence string.
     */
    public RTEParser(String nmea) {
        super(nmea, SentenceId.RTE);
    }

    /**
     * Creates RTE parser with empty sentence. The created RTE sentence contains
     * none waypoint ID fields.
     * 
     * @param talker TalkerId to set
     */
    public RTEParser(TalkerId talker) {
        super(talker, SentenceId.RTE, 4);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RTESentence#addWaypointId(java.lang.String
     * )
     */
    public int addWaypointId(String id) {
        String[] ids = getWaypointIds();
        String[] newIds = new String[ids.length + 1];

        for (int i = 0; i < ids.length; i++) {
            newIds[i] = ids[i];
        }
        newIds[newIds.length - 1] = id;

        setStringValues(FIRST_WPT, newIds);
        return newIds.length;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#getRouteId()
     */
    public String getRouteId() {
        return getStringValue(ROUTE_ID);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#getSentenceCount()
     */
    public int getSentenceCount() {
        return getIntValue(NUMBER_OF_SENTENCES);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#getSentenceIndex()
     */
    public int getSentenceIndex() {
        return getIntValue(SENTENCE_NUMBER);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#getWaypointCount()
     */
    public int getWaypointCount() {
        return getWaypointIds().length;
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#getWaypointIds()
     */
    public String[] getWaypointIds() {

        List<String> temp = new ArrayList<String>();

        for (int i = FIRST_WPT; i < getFieldCount(); i++) {
            try {
                temp.add(getStringValue(i));
            } catch (DataNotAvailableException e) {
                // nevermind empty fields
            }
        }

        return temp.toArray(new String[temp.size()]);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#isActiveRoute()
     */
    public boolean isActiveRoute() {
        return getCharValue(STATUS) == RouteType.ACTIVE.toChar();
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#isFirst()
     */
    public boolean isFirst() {
        return (getSentenceIndex() == 1);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#isLast()
     */
    public boolean isLast() {
        return (getSentenceIndex() == getSentenceCount());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#isWorkingRoute()
     */
    public boolean isWorkingRoute() {
        return getCharValue(STATUS) == RouteType.WORKING.toChar();
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RTESentence#setRouteId(java.lang.String)
     */
    public void setRouteId(String id) {
        setStringValue(ROUTE_ID, id);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RTESentence#setRouteType(net.sf.marineapi
     * .nmea.util.RouteType)
     */
    public void setRouteType(RouteType type) {
        setCharValue(STATUS, type.toChar());
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#setSentenceCount(int)
     */
    public void setSentenceCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        setIntValue(NUMBER_OF_SENTENCES, count);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RTESentence#setSentenceIndex(int)
     */
    public void setSentenceIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        setIntValue(SENTENCE_NUMBER, index);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.sentence.RTESentence#setWaypointIds(java.lang.String
     * [])
     */
    public void setWaypointIds(String[] ids) {
        setStringValues(FIRST_WPT, ids);
    }

}

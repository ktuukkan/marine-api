/* 
 * SentenceRTETest.java
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
package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * SentenceRTETest
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceRTETest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPRTE,1,1,c,0,MELIN,RUSKI,KNUDAN*25";

    private SentenceRTE rte;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            rte = new SentenceRTE(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#getWaypointIds()}.
     */
    @Test
    public void testGetWaypointIds() {
        String[] ids = rte.getWaypointIds();
        assertNotNull(ids);
        assertTrue(ids.length == 3);
        assertEquals("MELIN", ids[0]);
        assertEquals("RUSKI", ids[1]);
        assertEquals("KNUDAN", ids[2]);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#getNumberOfWaypoints()}.
     */
    @Test
    public void testGetNumberOfWaypoints() {
        assertTrue(3 == rte.getNumberOfWaypoints());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#isActiveRoute()}.
     */
    @Test
    public void testIsActiveRoute() {
        assertTrue(rte.isActiveRoute());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#isWorkingRoute()}.
     */
    @Test
    public void testIsWorkingRoute() {
        assertFalse(rte.isWorkingRoute());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#getNumberOfSentences()}.
     */
    @Test
    public void testGetNumberOfSentences() {
        assertTrue(1 == rte.getNumberOfSentences());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#getSentenceNumber()}.
     */
    @Test
    public void testGetSentenceNumber() {
        assertTrue(1 == rte.getSentenceNumber());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#isFirstInSequence()}.
     */
    @Test
    public void testIsFirstInSequence() {
        assertTrue(rte.isFirstInSequence());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#isLastInSequence()}.
     */
    @Test
    public void testIsLastInSequence() {
        assertTrue(rte.isLastInSequence());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRTE#getRouteId()}.
     */
    @Test
    public void testGetRouteId() {
        assertEquals("0", rte.getRouteId());
    }

}

/* 
 * SentenceWPLTest.java
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.Waypoint;

import org.junit.Before;
import org.junit.Test;

/**
 * SentenceWPLTest
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceWPLTest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPWPL,5536.200,N,01436.500,E,RUSKI*1F";

    private SentenceWPL wpl;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            wpl = new SentenceWPL(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceWPL#getWaypoint()}.
     */
    @Test
    public void testGetWaypoint() {
        Waypoint wp = wpl.getWaypoint();
        assertNotNull(wp);
        assertEquals("RUSKI", wp.getId());
        assertEquals(Direction.NORTH, wp.getLatHemisphere());
        assertEquals(Direction.EAST, wp.getLonHemisphere());
        final Double lat = new Double(55 + (36.200 / 60));
        final Double lon = new Double(14 + (36.500 / 60));
        assertEquals(lat, new Double(wp.getLatitude()));
        assertEquals(lon, new Double(wp.getLongitude()));
    }
}

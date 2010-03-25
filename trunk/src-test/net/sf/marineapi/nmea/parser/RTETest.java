package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import net.sf.marineapi.nmea.sentence.RTESentence;

import org.junit.Before;
import org.junit.Test;

/**
 * RTETest
 * 
 * @author Kimmo Tuukkanen
 */
public class RTETest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPRTE,1,1,c,0,MELIN,RUSKI,KNUDAN*25";

    private RTESentence rte;

    @Before
    public void setUp() {
        try {
            rte = new RTEParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#getWaypointIds()}.
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
     * {@link net.sf.marineapi.nmea.parser.RTEParser#getWaypointCount()}.
     */
    @Test
    public void testGetNumberOfWaypoints() {
        assertTrue(3 == rte.getWaypointCount());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#isActiveRoute()}.
     */
    @Test
    public void testIsActiveRoute() {
        assertTrue(rte.isActiveRoute());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#isWorkingRoute()}.
     */
    @Test
    public void testIsWorkingRoute() {
        assertFalse(rte.isWorkingRoute());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#getSentenceCount()}.
     */
    @Test
    public void testGetNumberOfSentences() {
        assertTrue(1 == rte.getSentenceCount());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#getSentenceIndex()}.
     */
    @Test
    public void testGetSentenceNumber() {
        assertTrue(1 == rte.getSentenceIndex());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#isFirst()}.
     */
    @Test
    public void testIsFirstInSequence() {
        assertTrue(rte.isFirst());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#isLast()}.
     */
    @Test
    public void testIsLastInSequence() {
        assertTrue(rte.isLast());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.RTEParser#getRouteId()}.
     */
    @Test
    public void testGetRouteId() {
        assertEquals("0", rte.getRouteId());
    }

}

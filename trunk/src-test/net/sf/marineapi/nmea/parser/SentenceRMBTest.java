/**
 * 
 */
package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.Waypoint;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the RMB sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceRMBTest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58";

    private SentenceRMB rmb;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            rmb = new SentenceRMB(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getStatus()}.
     */
    @Test
    public void testGetStatus() {
        assertEquals(DataStatus.INVALID, rmb.getStatus());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getCrossTrackError()}.
     */
    @Test
    public void testGetCrossTrackError() {
        assertTrue(0.0 == rmb.getCrossTrackError());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getSteerTo()}.
     */
    @Test
    public void testGetSteerTo() {
        assertEquals(Direction.RIGHT, rmb.getSteerTo());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getOriginId()}.
     */
    @Test
    public void testGetOriginId() {
        // FIXME test data should contain ID
        try {
            rmb.getOriginId();
            fail("Did not throw ParseException");
        } catch (Exception e) {
            assertTrue(e instanceof ParseException);
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getDestination()} .
     */
    @Test
    public void testGetDestination() {
        final double lat = 55 + (36.200 / 60);
        final double lon = 14 + (36.500 / 60);
        final String id = "RUSKI";

        Waypoint wp = rmb.getDestination();
        assertNotNull(wp);
        assertEquals(id, wp.getId());
        assertTrue(lat == wp.getLatitude());
        assertTrue(lon == wp.getLongitude());
        assertEquals(Direction.NORTH, wp.getLatHemisphere());
        assertEquals(Direction.EAST, wp.getLonHemisphere());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getRange()}.
     */
    @Test
    public void testGetRange() {
        assertTrue(432.3 == rmb.getRange());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getBearing()} .
     */
    @Test
    public void testGetBearing() {
        assertTrue(234.9 == rmb.getBearing());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getVelocity()}.
     */
    @Test
    public void testGetVelocity() {
        // FIXME test data should contain velocity
        try {
            assertTrue(0.0 == rmb.getVelocity());
            fail("Did not throw ParseException");
        } catch (Exception e) {
            assertTrue(e instanceof ParseException);
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceRMB#getArrivalStatus()}.
     */
    @Test
    public void testGetArrivalStatus() {
        assertEquals(DataStatus.VALID, rmb.getArrivalStatus());
        assertTrue(rmb.hasArrived());
    }

}

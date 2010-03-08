package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.Position;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the GLL sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceGLLTest {

    /**
     * Example sentence
     */
    public static final String EXAMPLE = "$GPGLL,6011.552,N,02501.941,E,120045,A*26";

    private SentenceGLL instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            instance = new SentenceGLL(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGLL#getDataStatus()}.
     */
    @Test
    public void testGetDataStatus() {
        assertEquals(DataStatus.INVALID, instance.getDataStatus());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGLL#getPosition()}.
     */
    @Test
    public void testGetPosition() {
        final double lat = 60 + (11.552 / 60);
        final double lon = 25 + (1.941 / 60);

        Position p = instance.getPosition();
        assertNotNull(p);
        assertTrue(lat == p.getLatitude());
        assertTrue(lon == p.getLongitude());
        assertEquals(Direction.NORTH, p.getLatHemisphere());
        assertEquals(Direction.EAST, p.getLonHemisphere());

        final String invalid1 = "$GPGLL,6111.552,E,02501.941,W,120045,A";
        SentenceGLL fail = new SentenceGLL(invalid1);
        try {
            Position p2 = fail.getPosition();
        } catch (Exception e) {
            // pass
        }

        final String invalid2 = "$GPGLL,6111.552,N,02501.941,S,120045,A";
        fail = new SentenceGLL(invalid2);
        try {
            Position p3 = fail.getPosition();
        } catch (Exception e) {
            // pass
        }

    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGLL#getUtcHours()}.
     */
    @Test
    public void testGetUtcHours() {
        assertEquals(12, instance.getUtcHours());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGLL#getUtcMinutes()}.
     */
    @Test
    public void testGetUtcMinutes() {
        assertEquals(0, instance.getUtcMinutes());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGLL#getUtcSeconds()}.
     */
    @Test
    public void testGetUtcSeconds() {
        assertEquals(45, (int) instance.getUtcSeconds());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGLL#getUtcTime()}.
     */
    @Test
    public void testGetUtcTime() {
        assertEquals("120045", instance.getUtcTime());
    }

}

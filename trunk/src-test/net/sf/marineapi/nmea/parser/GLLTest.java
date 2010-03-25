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
public class GLLTest {

    /**
     * Example sentence
     */
    public static final String EXAMPLE = "$GPGLL,6011.552,N,02501.941,E,120045,A*26";

    private GLLParser instance;

    /**
     * setUp
     */
    @Before
    public void setUp() {
        try {
            instance = new GLLParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GLLParser#getDataStatus()}.
     */
    @Test
    public void testGetDataStatus() {
        assertEquals(DataStatus.INVALID, instance.getDataStatus());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GLLParser#getPosition()}.
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
        GLLParser fail = new GLLParser(invalid1);
        try {
            fail.getPosition();
            fail("Did not throw exception");
        } catch (Exception e) {
            // pass
        }

        final String invalid2 = "$GPGLL,6111.552,N,02501.941,S,120045,A";
        fail = new GLLParser(invalid2);
        try {
            fail.getPosition();
            fail("Did not throw exception");
        } catch (Exception e) {
            // pass
        }

    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GLLParser#getUtcHours()}.
     */
    @Test
    public void testGetUtcHours() {
        assertEquals(12, instance.getUtcHours());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GLLParser#getUtcMinutes()}.
     */
    @Test
    public void testGetUtcMinutes() {
        assertEquals(0, instance.getUtcMinutes());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GLLParser#getUtcSeconds()}.
     */
    @Test
    public void testGetUtcSeconds() {
        assertEquals(45, (int) instance.getUtcSeconds());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GLLParser#getUtcTime()}.
     */
    @Test
    public void testGetUtcTime() {
        assertEquals("120045", instance.getUtcTime());
    }

}

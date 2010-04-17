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
     * {@link net.sf.marineapi.nmea.parser.GLLParser#setDataStatus()}.
     */
    @Test
    public void testSetDataStatus() {
        assertEquals(DataStatus.INVALID, instance.getDataStatus());
        instance.setDataStatus(DataStatus.VALID);
        assertEquals(DataStatus.VALID, instance.getDataStatus());
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
        assertEquals(lat, p.getLatitude(), 0.0000001);
        assertEquals(lon, p.getLongitude(), 0.0000001);
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
     * {@link net.sf.marineapi.nmea.parser.GLLParser#setPosition()}.
     */
    @Test
    public void testSetPosition() {
        final double lat = 60 + (11.552 / 60);
        final double lon = 25 + (1.941 / 60);
        Position p1 = new Position(0.0, Direction.NORTH, 0.0, Direction.EAST);
        Position p2 = new Position(lat, Direction.SOUTH, lon, Direction.WEST);

        instance.setPosition(p1);

        String s1 = instance.toString();
        assertTrue(s1.contains(",0000.000,N,"));
        assertTrue(s1.contains(",00000.000,E,"));

        Position p = instance.getPosition();
        assertNotNull(p);
        assertEquals(0.0, p.getLatitude(), 0.0000001);
        assertEquals(0.0, p.getLongitude(), 0.0000001);

        instance.setPosition(p2);

        String s2 = instance.toString();
        assertTrue(s2.contains(",6011.552,S,"));
        assertTrue(s2.contains(",02501.941,W,"));

        p = instance.getPosition();
        assertNotNull(p);
        assertEquals(lat, p.getLatitude(), 0.0000001);
        assertEquals(lon, p.getLongitude(), 0.0000001);
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

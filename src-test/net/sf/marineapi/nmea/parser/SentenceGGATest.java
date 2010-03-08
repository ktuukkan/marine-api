package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.Datum;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.Units;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the GGA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceGGATest {

    public static final String EXAMPLE = "$GPGGA,120044,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*79";

    private SentenceGGA gga;

    @Before
    public void setUp() throws Exception {
        try {
            gga = new SentenceGGA(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSentenceGGA() {
        SentenceGGA instance = new SentenceGGA(EXAMPLE);
        assertEquals(SentenceId.GGA, instance.getSentenceId());
    }

    @Test
    public void testGetAltitude() {
        assertTrue(28.0 == gga.getAltitude());
    }

    @Test
    public void testGetAltitudeUnits() {
        assertEquals(Units.METER, gga.getAltitudeUnits());
    }

    @Test
    public void testGetDgpsAge() {
        try {
            gga.getDgpsAge();
            fail("Did not throw ParseException");
        } catch (DataNotAvailableException e) {
            // ok
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDgpsStationId() {
        try {
            gga.getDgpsStationId();
            fail("Did not throw ParseException");
        } catch (DataNotAvailableException e) {
            // ok
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga.getFixQuality());
    }

    @Test
    public void testGetGeoidalHeight() {
        assertTrue(19.6 == gga.getGeoidalHeight());
    }

    @Test
    public void testGetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga.getGeoidalHeightUnits());
    }

    @Test
    public void testGetHorizontalDOP() {
        assertTrue(2.0 == gga.getHorizontalDOP());
    }

    @Test
    public void testGetNumberOfSatellites() {
        assertTrue(0 == gga.getSatelliteCount());
    }

    @Test
    public void testGetPosition() {
        Position p = gga.getPosition();
        assertNotNull(p);
        assertTrue(60.19253333333333 == p.getLatitude());
        assertEquals(Direction.NORTH, p.getLatHemisphere());
        assertTrue(25.03235 == p.getLongitude());
        assertEquals(Direction.EAST, p.getLonHemisphere());
        assertEquals(Datum.WGS84, p.getDatum());
    }

    @Test
    public void testGetUtcTime() {
        assertEquals("120044", gga.getUtcTime());
    }

    @Test
    public void testGetUtcHours() {
        assertTrue(12 == gga.getUtcHours());
    }

    @Test
    public void testGetUtcMinutes() {
        assertTrue(0 == gga.getUtcMinutes());
    }

    @Test
    public void testGetUtcSeconds() {
        assertTrue(44 == gga.getUtcSeconds());
    }

}

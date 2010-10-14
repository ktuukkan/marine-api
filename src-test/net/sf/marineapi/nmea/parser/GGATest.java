package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.Datum;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.nmea.util.Units;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the GGA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class GGATest {

    public static final String EXAMPLE = "$GPGGA,120044,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*79";

    private GGAParser gga;

    /**
     * setUp
     */
    @Before
    public void setUp() {
        try {
            gga = new GGAParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetAltitude() {
        assertEquals(28.0, gga.getAltitude(), 0.001);
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
        assertEquals(19.6, gga.getGeoidalHeight(), 0.001);
    }

    @Test
    public void testGetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga.getGeoidalHeightUnits());
    }

    @Test
    public void testGetHorizontalDOP() {
        assertEquals(2.0, gga.getHorizontalDOP(), 0.001);
    }

    @Test
    public void testGetNumberOfSatellites() {
        assertEquals(0, gga.getSatelliteCount());
    }

    @Test
    public void testGetPosition() {
        // expected lat/lon values
        final double lat = 60 + (11.552 / 60);
        final double lon = 25 + (1.941 / 60);

        Position p = gga.getPosition();
        assertNotNull(p);
        assertEquals(lat, p.getLatitude(), 0.0000001);
        assertEquals(CompassPoint.NORTH, p.getLatHemisphere());
        assertEquals(lon, p.getLongitude(), 0.0000001);
        assertEquals(CompassPoint.EAST, p.getLonHemisphere());
        assertEquals(Datum.WGS84, p.getDatum());
    }

    @Test
    public void testGetTime() {
        Time t = gga.getTime();
        assertNotNull(t);
        assertEquals(12, t.getHour());
        assertEquals(0, t.getMinutes());
        assertEquals(44.0, t.getSeconds(), 0.1);
    }

    /**
     * 
     */
    @Test
    public void testGGAParser() {
        GGAParser instance = new GGAParser(EXAMPLE);
        assertEquals(SentenceId.GGA, instance.getSentenceId());
    }

    @Test
    public void testSetAltitude() {
        final double alt = 11.111;
        gga.setAltitude(alt);
        assertEquals(alt, gga.getAltitude(), 0.0001);
    }

    @Test
    public void testSetAltitudeUnits() {
        assertEquals(Units.METER, gga.getAltitudeUnits());
        gga.setAltitudeUnits(Units.FEET);
        assertEquals(Units.FEET, gga.getAltitudeUnits());
    }

    @Test
    public void testSetDgpsAge() {
        final double age = 33.3;
        gga.setDgpsAge(age);
        assertEquals(age, gga.getDgpsAge(), 0.001);
    }

    @Test
    public void testSetDgpsStationId() {
        gga.setDgpsStationId("0001");
        assertEquals("0001", gga.getDgpsStationId());
    }

    @Test
    public void testSetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga.getFixQuality());
        gga.setFixQuality(GpsFixQuality.INVALID);
        assertEquals(GpsFixQuality.INVALID, gga.getFixQuality());
    }

    @Test
    public void testSetGeoidalHeight() {
        gga.setGeoidalHeight(3.14);
        assertEquals(3.14, gga.getGeoidalHeight(), 0.0001);

    }

    @Test
    public void testSetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga.getGeoidalHeightUnits());
        gga.setGeoidalHeightUnits(Units.FEET);
        assertEquals(Units.FEET, gga.getGeoidalHeightUnits());
    }

    @Test
    public void testSetHorizontalDOP() {
        assertEquals(2.0, gga.getHorizontalDOP(), 0.001);
        gga.setHorizontalDOP(0.025);
        assertEquals(0.025, gga.getHorizontalDOP(), 0.001);
    }

    @Test
    public void testSetPosition() {
        final double lat = 61 + (1.111 / 60);
        final double lon = 27 + (7.777 / 60);
        Position p = new Position(lat, CompassPoint.NORTH, lon, CompassPoint.EAST);
        gga.setPosition(p);

        final String str = gga.toString();
        assertTrue(str.contains(",6101.111,N,"));
        assertTrue(str.contains(",02707.777,E,"));

        Position wp = gga.getPosition();
        assertNotNull(wp);
        assertEquals(lat, wp.getLatitude(), 0.0000001);
        assertEquals(lon, wp.getLongitude(), 0.0000001);
        assertEquals(CompassPoint.NORTH, wp.getLatHemisphere());
        assertEquals(CompassPoint.EAST, wp.getLonHemisphere());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.GGAParser#getTime()}.
     */
    @Test
    public void testSetTime() {
        Time t = new Time(1, 2, 3.4);
        gga.setTime(t);
        assertTrue(gga.toString().contains("GPGGA,010203,6011"));
    }

}

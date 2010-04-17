package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.GSASentence;
import net.sf.marineapi.nmea.util.GpsFixStatus;
import net.sf.marineapi.nmea.util.GpsMode;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the GSA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class GSATest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPGSA,A,3,02,,,07,,09,24,26,,,,,1.6,1.6,1.0*3D";

    private GSASentence instance;

    @Before
    public void setUp() {
        try {
            instance = new GSAParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#getFixStatus()} .
     */
    @Test
    public void testGetFixStatus() {
        assertEquals(GpsFixStatus.GPS_3D, instance.getFixStatus());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#getGpsMode()}.
     */
    @Test
    public void testGetGpsMode() {
        assertEquals(GpsMode.AUTOMATIC, instance.getGpsMode());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#getHorizontalDOP()}.
     */
    @Test
    public void testGetHorizontalDOP() {
        double hdop = instance.getHorizontalDOP();
        assertEquals(1.6, hdop, 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#getPositionDOP()}.
     */
    @Test
    public void testGetPositionDOP() {
        double pdop = instance.getPositionDOP();
        assertEquals(1.6, pdop, 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#getSatellitesIds()}.
     */
    @Test
    public void testGetSatelliteIds() {
        String[] satellites = instance.getSatellitesIds();
        assertEquals(5, satellites.length);
        assertEquals("02", satellites[0]);
        assertEquals("07", satellites[1]);
        assertEquals("09", satellites[2]);
        assertEquals("24", satellites[3]);
        assertEquals("26", satellites[4]);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#getVerticalDOP()}.
     */
    @Test
    public void testGetVerticalDOP() {
        double vdop = instance.getVerticalDOP();
        assertEquals(1.0, vdop, 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#setFixStatus(GpsFixStatus)}
     * .
     */
    @Test
    public void testSetFixStatus() {
        instance.setFixStatus(GpsFixStatus.GPS_NA);
        assertTrue(instance.toString().contains(",A,1,"));
        assertEquals(GpsFixStatus.GPS_NA, instance.getFixStatus());

        instance.setFixStatus(GpsFixStatus.GPS_2D);
        assertTrue(instance.toString().contains(",A,2,"));
        assertEquals(GpsFixStatus.GPS_2D, instance.getFixStatus());

        instance.setFixStatus(GpsFixStatus.GPS_3D);
        assertTrue(instance.toString().contains(",A,3,"));
        assertEquals(GpsFixStatus.GPS_3D, instance.getFixStatus());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#setGpsMode(GpsMode)}.
     */
    @Test
    public void testSetGpsMode() {
        instance.setGpsMode(GpsMode.DGPS);
        assertTrue(instance.toString().contains(",D,"));
        assertEquals(GpsMode.DGPS, instance.getGpsMode());

        instance.setGpsMode(GpsMode.SIMULATED);
        assertTrue(instance.toString().contains(",S,"));
        assertEquals(GpsMode.SIMULATED, instance.getGpsMode());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#setHorizontalDOP(double)}.
     */
    @Test
    public void testSetHorizontalDOP() {
        instance.setHorizontalDOP(0.1);
        assertEquals(0.1, instance.getHorizontalDOP(), 0.001);
        instance.setHorizontalDOP(1.2);
        assertEquals(1.2, instance.getHorizontalDOP(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#setPositionDOP(double)}.
     */
    @Test
    public void testSetPositionDOP() {
        instance.setPositionDOP(0.0);
        assertEquals(0.0, instance.getPositionDOP(), 0.001);
        instance.setPositionDOP(1.1);
        assertEquals(1.1, instance.getPositionDOP(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#setSatellitesIds(String[])}
     * .
     */
    @Test
    public void testSetSatelliteIds() {

        String[] ids = { "02", "04", "06", "08", "10", "12" };
        instance.setSatelliteIds(ids);

        String[] satellites = instance.getSatellitesIds();
        assertEquals(ids.length, satellites.length);

        int i = 0;
        for (String id : ids) {
            assertEquals(id, satellites[i++]);
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSAParser#setVerticalDOP(double)}.
     */
    @Test
    public void testSetVerticalDOP() {
        instance.setVerticalDOP(0.3);
        assertEquals(0.3, instance.getVerticalDOP(), 0.001);
        instance.setVerticalDOP(1.3);
        assertEquals(1.3, instance.getVerticalDOP(), 0.001);
    }
}

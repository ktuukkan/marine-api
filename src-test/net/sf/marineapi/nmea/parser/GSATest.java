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

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            instance = new GSASentenceImpl(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSASentenceImpl#getGpsMode()}.
     */
    @Test
    public void testGetGpsMode() {
        assertEquals(GpsMode.AUTOMATIC, instance.getGpsMode());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSASentenceImpl#getFixStatus()} .
     */
    @Test
    public void testGetFixStatus() {
        assertEquals(GpsFixStatus.GPS_3D, instance.getFixStatus());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSASentenceImpl#getPositionDOP()}.
     */
    @Test
    public void testGetPositionDOP() {
        Double expected = new Double(1.6);
        Double pdop = new Double(instance.getPositionDOP());
        assertEquals(expected, pdop);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSASentenceImpl#getHorizontalDOP()}.
     */
    @Test
    public void testGetHorizontalDOP() {
        Double expected = new Double(1.6);
        Double hdop = new Double(instance.getHorizontalDOP());
        assertEquals(expected, hdop);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSASentenceImpl#getVerticalDOP()}.
     */
    @Test
    public void testGetVerticalDOP() {
        Double expected = new Double(1.0);
        Double vdop = new Double(instance.getVerticalDOP());
        assertEquals(expected, vdop);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.GSASentenceImpl#getSatellitesIds()}.
     */
    @Test
    public void testGetSatelliteIds() {
        String[] satellites = instance.getSatellitesIds();
        assertTrue(satellites.length == 5);
        assertEquals("02", satellites[0]);
        assertEquals("07", satellites[1]);
        assertEquals("09", satellites[2]);
        assertEquals("24", satellites[3]);
        assertEquals("26", satellites[4]);
    }

}

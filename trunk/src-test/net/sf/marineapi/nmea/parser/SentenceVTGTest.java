package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.GpsMode;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the VTG sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceVTGTest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K,A";

    private SentenceVTG vtg;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            vtg = new SentenceVTG(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getTrueCourse()}.
     */
    @Test
    public void testGetTrueCourse() {
        assertTrue(360.0 == vtg.getTrueCourse());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getMagneticCourse()}.
     */
    @Test
    public void testGetMagneticCourse() {
        assertTrue(348.7 == vtg.getMagneticCourse());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getSpeedKnots()}.
     */
    @Test
    public void testGetSpeedKnots() {
        assertTrue(16.89 == vtg.getSpeedKnots());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getSpeedKmh()}.
     */
    @Test
    public void testGetSpeedKmh() {
        assertTrue(31.28 == vtg.getSpeedKmh());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getMode()}.
     */
    @Test
    public void testGetMode() {
        assertEquals(GpsMode.AUTOMATIC, vtg.getMode());
    }

}

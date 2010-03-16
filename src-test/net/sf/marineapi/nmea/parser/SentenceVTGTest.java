package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.VTGSentence;
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

    private VTGSentence vtg;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            vtg = new VTGSentenceImpl(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGSentenceImpl#getTrueCourse()}.
     */
    @Test
    public void testGetTrueCourse() {
        assertTrue(360.0 == vtg.getTrueCourse());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGSentenceImpl#getMagneticCourse()}.
     */
    @Test
    public void testGetMagneticCourse() {
        assertTrue(348.7 == vtg.getMagneticCourse());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGSentenceImpl#getSpeedKnots()}.
     */
    @Test
    public void testGetSpeedKnots() {
        assertTrue(16.89 == vtg.getSpeedKnots());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGSentenceImpl#getSpeedKmh()}.
     */
    @Test
    public void testGetSpeedKmh() {
        assertTrue(31.28 == vtg.getSpeedKmh());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGSentenceImpl#getMode()}.
     */
    @Test
    public void testGetMode() {
        assertEquals(GpsMode.AUTOMATIC, vtg.getMode());
    }

}

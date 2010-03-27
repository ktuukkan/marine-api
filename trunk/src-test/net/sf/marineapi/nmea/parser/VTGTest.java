package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
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
public class VTGTest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K,A";

    private VTGSentence vtg;

    @Before
    public void setUp() {
        try {
            vtg = new VTGParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#getTrueCourse()}.
     */
    @Test
    public void testGetTrueCourse() {
        assertEquals(360.0, vtg.getTrueCourse(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#getMagneticCourse()}.
     */
    @Test
    public void testGetMagneticCourse() {
        assertEquals(348.7, vtg.getMagneticCourse(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#getSpeedKnots()}.
     */
    @Test
    public void testGetSpeedKnots() {
        assertEquals(16.89, vtg.getSpeedKnots(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#getSpeedKmh()}.
     */
    @Test
    public void testGetSpeedKmh() {
        assertEquals(31.28, vtg.getSpeedKmh(), 0.001);
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.VTGParser#getMode()}.
     */
    @Test
    public void testGetMode() {
        assertEquals(GpsMode.AUTOMATIC, vtg.getMode());
    }

}

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
public class VTGTest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K,A";

    private VTGSentence empty;
    private VTGSentence vtg;

    @Before
    public void setUp() {
        try {
            empty = new VTGParser();
            vtg = new VTGParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConstructor() {
        assertEquals(9, empty.getFieldCount());
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
     * Test method for {@link net.sf.marineapi.nmea.parser.VTGParser#getMode()}.
     */
    @Test
    public void testGetMode() {
        assertEquals(GpsMode.AUTOMATIC, vtg.getMode());
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
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#getSpeedKnots()}.
     */
    @Test
    public void testGetSpeedKnots() {
        assertEquals(16.89, vtg.getSpeedKnots(), 0.001);
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
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setMagneticCourse(double)}.
     */
    @Test
    public void testSetMagneticCourse() {
        vtg.setMagneticCourse(0.0);
        assertEquals(0.0, vtg.getMagneticCourse(), 0.001);
        vtg.setMagneticCourse(360.0);
        assertEquals(360.0, vtg.getMagneticCourse(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setMagneticCourse(double)}.
     */
    @Test
    public void testSetMagneticCourseWithNegativeValue() {
        try {
            vtg.setMagneticCourse(-0.001);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("0..360"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setMagneticCourse(double)}.
     */
    @Test
    public void testSetMagneticCourseWithValueGreaterThanAllowed() {
        try {
            vtg.setMagneticCourse(360.001);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("0..360"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setMode(GpsMode)}.
     */
    @Test
    public void testSetMode() {
        vtg.setMode(GpsMode.MANUAL);
        assertEquals(GpsMode.MANUAL, vtg.getMode());
        vtg.setMode(GpsMode.SIMULATED);
        assertEquals(GpsMode.SIMULATED, vtg.getMode());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setSpeedKmh(double)}.
     */
    @Test
    public void testSetSpeedKmh() {
        vtg.setSpeedKmh(0.0);
        assertEquals(0.0, vtg.getSpeedKmh(), 0.001);
        vtg.setSpeedKmh(12.3);
        assertEquals(12.3, vtg.getSpeedKmh(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setSpeedKmh(double)}.
     */
    @Test
    public void testSetSpeedKmhWithNegativeValue() {
        try {
            vtg.setSpeedKmh(-0.0001);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("cannot be negative"));
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setSpeedKnots(double)}.
     */
    @Test
    public void testSetSpeedKnots() {
        vtg.setSpeedKnots(0.0);
        assertEquals(0.0, vtg.getSpeedKnots(), 0.001);
        vtg.setSpeedKnots(12.3);
        assertEquals(12.3, vtg.getSpeedKnots(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setSpeedKnots(double)}.
     */
    @Test
    public void testSetSpeedKnotsWithNegativeValue() {
        try {
            vtg.setSpeedKnots(-0.0001);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("cannot be negative"));
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setTrueCourse(double)}.
     */
    @Test
    public void testSetTrueCourse() {

        vtg.setTrueCourse(0.0);
        assertEquals(0.0, vtg.getTrueCourse(), 0.001);

        vtg.setTrueCourse(360.0);
        assertEquals(360.0, vtg.getTrueCourse(), 0.001);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setTrueCourse(double)}.
     */
    @Test
    public void testSetTrueCourseWithNegativeValue() {
        try {
            vtg.setTrueCourse(-0.001);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("0..360"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VTGParser#setTrueCourse(double)}.
     */
    @Test
    public void testSetTrueCourseWithValueGreaterThanAllowed() {
        try {
            vtg.setTrueCourse(360.001);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("0..360"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}

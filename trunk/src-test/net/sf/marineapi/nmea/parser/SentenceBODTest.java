package net.sf.marineapi.nmea.parser;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the BOD sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceBODTest extends TestCase {

    public static final String EXAMPLE = "$GPBOD,234.9,T,228.8,M,RUSKI,*1D";

    private SentenceBOD bod;

    /**
     * setUp
     * 
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception {
        try {
            bod = new SentenceBOD(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * tearDown
     * 
     * @throws java.lang.Exception
     */
    @Override
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceBOD#SentenceBOD(java.lang.String)}
     * .
     */
    @Test
    public void testSentenceBOD() {

        try {
            new SentenceBOD(null);
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new SentenceBOD("$HUBBA,habba,doo,dah,doo");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new SentenceBOD("foobar and haystack");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceBOD#getBearingTrue()}.
     */
    @Test
    public void testGetBearingTrue() {
        try {
            double b = bod.getTrueBearing();
            assertTrue(234.9 == b);
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceBOD#getBearingMagnetic()}.
     */
    @Test
    public void testGetBearingMagnetic() {
        try {
            double b = bod.getMagneticBearing();
            assertTrue(228.8 == b);
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceBOD#getOriginWaypointId()}.
     */
    @Test
    public void testGetOriginWaypointId() {

        try {
            bod.getOriginWaypointId();
        } catch (DataNotAvailableException e) {
            // ok, field is empty
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceBOD#getDestinationWaypointId()}
     * .
     */
    @Test
    public void testGetDestinationWaypointId() {
        try {
            String id = bod.getDestinationWaypointId();
            assertEquals("RUSKI", id);
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }
}

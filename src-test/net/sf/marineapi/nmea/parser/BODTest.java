package net.sf.marineapi.nmea.parser;

import junit.framework.TestCase;

import net.sf.marineapi.nmea.sentence.BODSentence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the BOD sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class BODTest extends TestCase {

    public static final String EXAMPLE = "$GPBOD,234.9,T,228.8,M,RUSKI,*1D";

    private BODSentence bod;

    /**
     * setUp
     * 
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception {
        try {
            bod = new BODParser(EXAMPLE);
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
     * {@link net.sf.marineapi.nmea.parser.BODParser#SentenceBOD(java.lang.String)}
     * .
     */
    @Test
    public void testSentenceBOD() {

        try {
            new BODParser(null);
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new BODParser("$HUBBA,habba,doo,dah,doo");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new BODParser("foobar and haystack");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.BODParser#getBearingTrue()}.
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
     * {@link net.sf.marineapi.nmea.parser.BODParser#getBearingMagnetic()}.
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
     * {@link net.sf.marineapi.nmea.parser.BODParser#getOriginWaypointId()}.
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
     * {@link net.sf.marineapi.nmea.parser.BODParser#getDestinationWaypointId()}
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

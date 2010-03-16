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
public class SentenceBODTest extends TestCase {

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
            bod = new BODSentenceImpl(EXAMPLE);
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
     * {@link net.sf.marineapi.nmea.parser.BODSentenceImpl#SentenceBOD(java.lang.String)}
     * .
     */
    @Test
    public void testSentenceBOD() {

        try {
            new BODSentenceImpl(null);
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new BODSentenceImpl("$HUBBA,habba,doo,dah,doo");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new BODSentenceImpl("foobar and haystack");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.BODSentenceImpl#getBearingTrue()}.
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
     * {@link net.sf.marineapi.nmea.parser.BODSentenceImpl#getBearingMagnetic()}.
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
     * {@link net.sf.marineapi.nmea.parser.BODSentenceImpl#getOriginWaypointId()}.
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
     * {@link net.sf.marineapi.nmea.parser.BODSentenceImpl#getDestinationWaypointId()}
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

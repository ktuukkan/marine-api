package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the base sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceTest {

    private Sentence sentence;

    /**
     * setUp
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        sentence = new Sentence(SentenceRMCTest.EXAMPLE);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.Sentence#Sentence(java.lang.String, net.sf.marineapi.nmea.util.SentenceId)}
     * .
     */
    @Test
    public void testSentence() {

        try {
            new Sentence("this is not a valid sentence");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            // ok
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Sentence("$XZGGA,VALID,BUT,TALKER,NOT,SUPPORTED");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            assertTrue(se.getMessage().contains("[XZ]"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Sentence("$GPXYZ,VALID,BUT,TYPE,NOT,SUPPORTED");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            assertTrue(se.getMessage().contains("[XYZ]"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Sentence(SentenceBODTest.EXAMPLE, SentenceId.GLL);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Sentence(SentenceRMCTest.EXAMPLE, null);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Sentence(null, null);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.Sentence#getSentenceId()}.
     */
    @Test
    public void testGetSentenceId() {
        assertEquals(SentenceId.RMC, sentence.getSentenceId());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.Sentence#getTalkerId()}.
     */
    @Test
    public void testGetTalkerId() {
        assertEquals(TalkerId.GP, sentence.getTalkerId());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.Sentence#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(SentenceRMCTest.EXAMPLE, sentence.toString());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.Sentence#calculateChecksum(java.lang.String)}
     * .
     */
    @Test
    public void testCalcChecksum() {
        assertEquals("1D", Sentence.calculateChecksum(SentenceBODTest.EXAMPLE));
        assertEquals("79", Sentence.calculateChecksum(SentenceGGATest.EXAMPLE));
        assertEquals("26", Sentence.calculateChecksum(SentenceGLLTest.EXAMPLE));
        assertEquals("11", Sentence.calculateChecksum(SentenceRMCTest.EXAMPLE));
        assertEquals("3D", Sentence.calculateChecksum(SentenceGSATest.EXAMPLE));
        assertEquals("73", Sentence.calculateChecksum(SentenceGSVTest.EXAMPLE));
        assertEquals("58", Sentence.calculateChecksum(SentenceRMBTest.EXAMPLE));
        assertEquals("25", Sentence.calculateChecksum(SentenceRTETest.EXAMPLE));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.Sentence#appendChecksum(java.lang.String)}
     * .
     */
    @Test
    public void testAppendChecksum() {

        String a = "$GPGLL,6011.552,N,02501.941,E,120045,A";
        final String expected = a.concat("*26");

        assertEquals(expected, Sentence.appendChecksum(a));

        String b = "$GPGLL,6011.552,N,02501.941,E,120045,A*";
        assertEquals(expected, Sentence.appendChecksum(b));

        String c = "$GPGLL,6011.552,N,02501.941,E,120045,A*00";
        assertEquals(expected, Sentence.appendChecksum(c));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.Sentence#isValid(java.lang.String)} .
     */
    @Test
    public void testIsValid() {

        String a = "$GPGGA,1,2,3,4,5,6,7,8,9";
        assertTrue(Sentence.isValid(a));
        assertTrue(Sentence.isValid(Sentence.appendChecksum(a)));

        String b = "$GPGGA,one,two,three,four,five";
        assertTrue(Sentence.isValid(b));
        assertTrue(Sentence.isValid(Sentence.appendChecksum(b)));

        String c = "$GPGGA,ONE,TWO,THREE,FOUR,FIVE";
        assertTrue(Sentence.isValid(c));
        assertTrue(Sentence.isValid(Sentence.appendChecksum(c)));

        assertTrue(Sentence.isValid(SentenceBODTest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceGGATest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceGLLTest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceGSATest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceGSVTest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceRMBTest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceRMCTest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceRTETest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceVTGTest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceWPLTest.EXAMPLE));
        assertTrue(Sentence.isValid(SentenceZDATest.EXAMPLE));

        assertFalse(Sentence.isValid(null));
        assertFalse(Sentence.isValid(""));
        assertFalse(Sentence.isValid("$"));
        assertFalse(Sentence.isValid("*"));
        assertFalse(Sentence.isValid("foobar"));
        assertFalse(Sentence.isValid("GPGGA,1,2,3,4,5,6,7,8,9"));
        assertFalse(Sentence.isValid("$GpGGA,1,2,3,4,5,6,7,8,9"));
        assertFalse(Sentence.isValid("$GPGGa,1,2,3,4,5,6,7,8,9"));
        assertFalse(Sentence.isValid("$GPGG#,1,2,3,4,5,6,7,8,9"));
        assertFalse(Sentence.isValid("$GPGGA,1,2,#,4,5,6,7,8,9"));
        assertFalse(Sentence.isValid("$GPGGAA,1,2,3,4,5,6,7,8,9"));
        assertFalse(Sentence.isValid("$GPGGA,1,2,3,4,5,6,7,8,9*00"));
        // invalid checksum, otherwise valid
        assertFalse(Sentence
                .isValid("$GPGLL,6011.552,N,02501.941,E,120045,A*00"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.Sentence#getStringValue(int)}.
     */
    @Test
    public void testGetStringValue() {
        String nmea = "$GPGLL,6011.552,N,02501.941,E,120045,A";

        Sentence s = new Sentence(nmea);
        String[] fields = nmea.split(",", -1);
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], s.getStringValue(i));
        }

        // sentence with invalid and missing values
        nmea = "$GPGLL,foobar,N,,EAST,120045,A";

        try {
            Sentence s2 = new Sentence(nmea);
            s2.getDoubleValue(1);
            fail("Did not throw exception");
        } catch (ParseException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            Sentence s3 = new Sentence(nmea);
            s3.getCharValue(4);
            fail("Did not throw exception");
        } catch (ParseException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            Sentence s4 = new Sentence(nmea);
            s4.getStringValue(3);
            fail("Did not throw exception");
        } catch (DataNotAvailableException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testGetFieldIndexOutOfBounds() {

        try {
            sentence.getStringValue(-1);
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // pass
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            String id = sentence.getStringValue(Sentence.ADDRESS_FIELD);
            assertEquals("$GPRMC", id);
        } catch (IndexOutOfBoundsException e) {
            fail("Unexpected IndexOutOfBoundsException");
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            sentence.getStringValue(sentence.getFieldCount() - 1);
            // pass
        } catch (IndexOutOfBoundsException e) {
            fail("Unexpected IndexOutOfBoundsException");
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            sentence.getStringValue(sentence.getFieldCount());
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // pass
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }
}

package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.NMEA;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.util.SentenceId;
import net.sf.marineapi.nmea.util.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the base sentenceImpl parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceTest {

    private SentenceImpl sentenceImpl;

    /**
     * setUp
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        sentenceImpl = new SentenceImpl(SentenceRMCTest.EXAMPLE);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceImpl#Sentence(java.lang.String, net.sf.marineapi.nmea.util.SentenceId)}
     * .
     */
    @Test
    public void testSentence() {

        try {
            new SentenceImpl("this is not a valid sentenceImpl");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            // ok
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new SentenceImpl("$XZGGA,VALID,BUT,TALKER,NOT,SUPPORTED");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            assertTrue(se.getMessage().contains("[XZ]"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new SentenceImpl("$GPXYZ,VALID,BUT,TYPE,NOT,SUPPORTED");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            assertTrue(se.getMessage().contains("[XYZ]"));
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new SentenceImpl(SentenceBODTest.EXAMPLE, SentenceId.GLL);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new SentenceImpl(SentenceRMCTest.EXAMPLE, null);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new SentenceImpl(null, null);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceImpl#getSentenceId()}.
     */
    @Test
    public void testGetSentenceId() {
        assertEquals(SentenceId.RMC, sentenceImpl.getSentenceId());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceImpl#getTalkerId()}.
     */
    @Test
    public void testGetTalkerId() {
        assertEquals(TalkerId.GP, sentenceImpl.getTalkerId());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.SentenceImpl#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(SentenceRMCTest.EXAMPLE, sentenceImpl.toString());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.NMEA#calculateChecksum(java.lang.String)}
     * .
     */
    @Test
    public void testCalcChecksum() {
        assertEquals("1D", NMEA.calculateChecksum(SentenceBODTest.EXAMPLE));
        assertEquals("79", NMEA.calculateChecksum(SentenceGGATest.EXAMPLE));
        assertEquals("26", NMEA.calculateChecksum(SentenceGLLTest.EXAMPLE));
        assertEquals("11", NMEA.calculateChecksum(SentenceRMCTest.EXAMPLE));
        assertEquals("3D", NMEA.calculateChecksum(SentenceGSATest.EXAMPLE));
        assertEquals("73", NMEA.calculateChecksum(SentenceGSVTest.EXAMPLE));
        assertEquals("58", NMEA.calculateChecksum(SentenceRMBTest.EXAMPLE));
        assertEquals("25", NMEA.calculateChecksum(SentenceRTETest.EXAMPLE));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.NMEA#appendChecksum(java.lang.String)}
     * .
     */
    @Test
    public void testAppendChecksum() {

        String a = "$GPGLL,6011.552,N,02501.941,E,120045,A";
        final String expected = a.concat("*26");

        assertEquals(expected, NMEA.appendChecksum(a));

        String b = "$GPGLL,6011.552,N,02501.941,E,120045,A*";
        assertEquals(expected, NMEA.appendChecksum(b));

        String c = "$GPGLL,6011.552,N,02501.941,E,120045,A*00";
        assertEquals(expected, NMEA.appendChecksum(c));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.NMEA#isValid(java.lang.String)} .
     */
    @Test
    public void testIsValid() {

        String a = "$GPGGA,1,2,3,4,5,6,7,8,9";
        assertTrue(NMEA.isValid(a));
        assertTrue(NMEA.isValid(NMEA.appendChecksum(a)));

        String b = "$GPGGA,one,two,three,four,five";
        assertTrue(NMEA.isValid(b));
        assertTrue(NMEA.isValid(NMEA.appendChecksum(b)));

        String c = "$GPGGA,ONE,TWO,THREE,FOUR,FIVE";
        assertTrue(NMEA.isValid(c));
        assertTrue(NMEA.isValid(NMEA.appendChecksum(c)));

        assertTrue(NMEA.isValid(SentenceBODTest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceGGATest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceGLLTest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceGSATest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceGSVTest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceRMBTest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceRMCTest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceRTETest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceVTGTest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceWPLTest.EXAMPLE));
        assertTrue(NMEA.isValid(SentenceZDATest.EXAMPLE));

        assertFalse(NMEA.isValid(null));
        assertFalse(NMEA.isValid(""));
        assertFalse(NMEA.isValid("$"));
        assertFalse(NMEA.isValid("*"));
        assertFalse(NMEA.isValid("foobar"));
        assertFalse(NMEA.isValid("GPGGA,1,2,3,4,5,6,7,8,9"));
        assertFalse(NMEA.isValid("$GpGGA,1,2,3,4,5,6,7,8,9"));
        assertFalse(NMEA.isValid("$GPGGa,1,2,3,4,5,6,7,8,9"));
        assertFalse(NMEA.isValid("$GPGG#,1,2,3,4,5,6,7,8,9"));
        assertFalse(NMEA.isValid("$GPGGA,1,2,#,4,5,6,7,8,9"));
        assertFalse(NMEA.isValid("$GPGGAA,1,2,3,4,5,6,7,8,9"));
        assertFalse(NMEA.isValid("$GPGGA,1,2,3,4,5,6,7,8,9*00"));
        // invalid checksum, otherwise valid
        assertFalse(NMEA
                .isValid("$GPGLL,6011.552,N,02501.941,E,120045,A*00"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceImpl#getStringValue(int)}.
     */
    @Test
    public void testGetStringValue() {
        String nmea = "$GPGLL,6011.552,N,02501.941,E,120045,A";

        SentenceImpl s = new SentenceImpl(nmea);
        String[] fields = nmea.split(",", -1);
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], s.getStringValue(i));
        }

        // sentenceImpl with invalid and missing values
        nmea = "$GPGLL,foobar,N,,EAST,120045,A";

        try {
            SentenceImpl s2 = new SentenceImpl(nmea);
            s2.getDoubleValue(1);
            fail("Did not throw exception");
        } catch (ParseException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            SentenceImpl s3 = new SentenceImpl(nmea);
            s3.getCharValue(4);
            fail("Did not throw exception");
        } catch (ParseException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            SentenceImpl s4 = new SentenceImpl(nmea);
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
            sentenceImpl.getStringValue(-1);
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // pass
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            String id = sentenceImpl.getStringValue(Sentence.ADDRESS_FIELD);
            assertEquals("$GPRMC", id);
        } catch (IndexOutOfBoundsException e) {
            fail("Unexpected IndexOutOfBoundsException");
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            sentenceImpl.getStringValue(sentenceImpl.getFieldCount() - 1);
            // pass
        } catch (IndexOutOfBoundsException e) {
            fail("Unexpected IndexOutOfBoundsException");
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            sentenceImpl.getStringValue(sentenceImpl.getFieldCount());
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // pass
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }
}

package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.Checksum;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.SentenceValidator;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the base instance parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceTest {

    private SentenceParser instance;

    /**
     * setUp
     */
    @Before
    public void setUp() {
        instance = new SentenceParser(RMCTest.EXAMPLE);
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    public void testConstructorForEmptySentence() {
        Sentence s = new SentenceParser(TalkerId.GP, SentenceId.GLL, 5);
        assertEquals("$GPGLL,,,,,*7C", s.toString());
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    public void testConstructorWithInvalidSentence() {
        try {
            String sent = "GPBOD,234.9,T,228.8,M,RUSKI,*1D";
            new SentenceParser(sent);
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            assertTrue(se.getMessage().contains(""));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    public void testConstructorWithNulls() {
        try {
            new SentenceParser(null, null);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    public void testConstructorWithNullType() {

        try {
            new SentenceParser(RMCTest.EXAMPLE, null);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    public void testConstructorWithUnsupportedTalker() {
        try {
            new SentenceParser("$XZGGA,VALID,BUT,TALKER,NOT,SUPPORTED");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            assertTrue(se.getMessage().contains("[XZ]"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    public void testConstructorWithUnsupportedType() {
        try {
            new SentenceParser("$GPXYZ,VALID,BUT,TYPE,NOT,SUPPORTED");
            fail("Did not throw exception");
        } catch (IllegalArgumentException se) {
            assertTrue(se.getMessage().contains("[XYZ]"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    public void testConstructorWithWrongType() {
        try {
            new SentenceParser(BODTest.EXAMPLE, SentenceId.GLL);
            fail("Did not throw exception");
        } catch (IllegalArgumentException iae) {
            // OK
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getCharValue(int)}.
     */
    @Test
    public void testGetCharValueWithEmptyFields() {
        String nmea = "$GPGLL,,,,,,";
        SentenceParser s = new SentenceParser(nmea);
        try {
            s.getCharValue(3);
            fail("Did not throw exception");
        } catch (DataNotAvailableException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getDoubleValue(int)}.
     */
    @Test
    public void testGetDoubleValueWithEmptyFields() {
        String nmea = "$GPGLL,,,,,,";
        SentenceParser s = new SentenceParser(nmea);
        try {
            s.getDoubleValue(2);
            fail("Did not throw exception");
        } catch (DataNotAvailableException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getSentenceId()}.
     */
    @Test
    public void testGetSentenceId() {
        assertEquals(SentenceId.RMC, instance.getSentenceId());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getStringValue(int)}.
     */
    @Test
    public void testGetStringValue() {
        final String nmea = "$GPGLL,6011.552,N,02501.941,E,120045,A";
        final SentenceParser s = new SentenceParser(nmea);
        final String data = "6011.552,N,02501.941,E,120045,A";
        final String[] expected = data.split(",", -1);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], s.getStringValue(i));
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getStringValue(int)}.
     */
    @Test
    public void testGetStringValueWithEmptyFields() {
        String nmea = "$GPGLL,,,,,,";
        SentenceParser s = new SentenceParser(nmea);
        try {
            s.getStringValue(2);
            fail("Did not throw exception");
        } catch (DataNotAvailableException ex) {
            // pass
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getStringValue(int)}.
     */
    @Test
    public void testGetStringValueWithIndexGreaterThanAllowed() {
        try {
            instance.getStringValue(instance.getFieldCount());
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // pass
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getStringValue(int)}.
     */
    @Test
    public void testGetStringValueWithNegativeIndex() {

        try {
            instance.getStringValue(-1);
            fail("Did not throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // pass
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getStringValue(int)}.
     */
    @Test
    public void testGetStringValueWithValidIndex() {
        try {
            String val = instance.getStringValue(0);
            assertEquals("120044", val);
            val = instance.getStringValue(instance.getFieldCount() - 1);
            assertEquals("A", val);
        } catch (IndexOutOfBoundsException e) {
            fail("Unexpected IndexOutOfBoundsException");
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#getTalkerId()}.
     */
    @Test
    public void testGetTalkerId() {
        assertEquals(TalkerId.GP, instance.getTalkerId());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.SentenceValidator#isValid(java.lang.String)} .
     */
    @Test
    public void testIsValid() {
        String a = "$GPGGA,1,2,3,4,5,6,7,8,9";
        assertTrue(SentenceValidator.isValid(a));
        assertTrue(SentenceValidator.isValid(Checksum.add(a)));

        String b = "$GPGGA,1,TWO,three,FOUR,5,6.0,-7.0,Eigth-8,N1N3";
        assertTrue(SentenceValidator.isValid(b));
        assertTrue(SentenceValidator.isValid(Checksum.add(b)));

    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.SentenceValidator#isValid(java.lang.String)} .
     */
    @Test
    public void testIsValidWithInvalidInput() {
        assertFalse(SentenceValidator.isValid(null));
        assertFalse(SentenceValidator.isValid(""));
        assertFalse(SentenceValidator.isValid("$"));
        assertFalse(SentenceValidator.isValid("*"));
        assertFalse(SentenceValidator.isValid("$,*"));
        assertFalse(SentenceValidator.isValid("$GPGSV*"));
        assertFalse(SentenceValidator.isValid("foobar"));
        assertFalse(SentenceValidator.isValid("GPGGA,1,2,3,4,5,6,7,8,9"));
        assertFalse(SentenceValidator.isValid("$GpGGA,1,2,3,4,5,6,7,8,9"));
        assertFalse(SentenceValidator.isValid("$GPGGa,1,2,3,4,5,6,7,8,9"));
        assertFalse(SentenceValidator.isValid("$GPGG#,1,2,3,4,5,6,7,8,9"));
        assertFalse(SentenceValidator.isValid("$GPGGA,1,2,#,4,5,6,7,8,9"));
        assertFalse(SentenceValidator.isValid("$GPGGA,1,$,3,4,5,6,7,8,9"));
        assertFalse(SentenceValidator.isValid("$GPGGAA,1,2,3,4,5,6,7,8,9"));
        assertFalse(SentenceValidator.isValid("$GPGGA,1,2,3,4,5,6,7,8,9*00"));
        // invalid checksum, otherwise valid
        assertFalse(SentenceValidator.isValid("$GPGLL,6011.552,N,02501.941,E,120045,A*00"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.SentenceValidator#isValid(java.lang.String)} .
     */
    @Test
    public void testIsValidWithValidInput() {
        assertTrue(SentenceValidator.isValid(BODTest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(GGATest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(GLLTest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(GSATest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(GSVTest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(RMBTest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(RMCTest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(RTETest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(VTGTest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(WPLTest.EXAMPLE));
        assertTrue(SentenceValidator.isValid(ZDATest.EXAMPLE));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(RMCTest.EXAMPLE, instance.toString());
        assertEquals(instance.toString(), instance.toSentence());
    }
}

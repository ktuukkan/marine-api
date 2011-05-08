package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
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
     * Test method for SentenceParser constructors called from derived custom
     * parsers.
     */
    @Test
    public void testConstructorWithCustomParser() {

        final String foo = "FOO";
        SentenceFactory sf = SentenceFactory.getInstance();
        sf.registerParser(foo, FOOParser.class);

        final String fooSentence = "$GPFOO,B,A,R";
        final FOOParser fp = new FOOParser(fooSentence);
        final Sentence s = sf.createParser(fooSentence);

        assertTrue(s instanceof SentenceParser);
        assertTrue(s instanceof FOOParser);
        assertEquals(foo, s.getSentenceId());
        assertEquals(TalkerId.GP, s.getTalkerId());
        assertEquals(s, fp);
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
            new SentenceParser((String) null, (String) null);
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
            new SentenceParser(RMCTest.EXAMPLE, (String) null);
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
            assertTrue(se.getMessage().endsWith(".TalkerId.XZ"));
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
            new SentenceParser(BODTest.EXAMPLE, SentenceId.GLL.toString());
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
        SentenceId sid = SentenceId.valueOf(instance.getSentenceId());
        assertEquals(SentenceId.RMC, sid);
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
     * {@link net.sf.marineapi.nmea.parser.SentenceParser#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(RMCTest.EXAMPLE, instance.toString());
        assertEquals(instance.toString(), instance.toSentence());
    }
}

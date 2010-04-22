package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.NMEA;
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
	public void testSentence() {

		Sentence s = new SentenceParser(TalkerId.GP, SentenceId.GLL, 5);
		assertEquals("$GPGLL,,,,,*7C", s.toString());

		try {
			new SentenceParser(null, null);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// OK
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			new SentenceParser(RMCTest.EXAMPLE, null);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// OK
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			new SentenceParser(BODTest.EXAMPLE, SentenceId.GLL);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// OK
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			String sent = "GPBOD,234.9,T,228.8,M,RUSKI,*1D";
			new SentenceParser(sent);
			fail("Did not throw exception");
		} catch (IllegalArgumentException se) {
			assertTrue(se.getMessage().contains(""));
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			new SentenceParser("$XZGGA,VALID,BUT,TALKER,NOT,SUPPORTED");
			fail("Did not throw exception");
		} catch (IllegalArgumentException se) {
			assertTrue(se.getMessage().contains("[XZ]"));
		} catch (Exception e) {
			fail(e.getMessage());
		}

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
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#getSentenceId()}.
	 */
	@Test
	public void testGetSentenceId() {
		assertEquals(SentenceId.RMC, instance.getSentenceId());
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
		assertEquals(RMCTest.EXAMPLE, instance.toSentence());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.sentence.NMEA#calculateChecksum(java.lang.String)}
	 * .
	 */
	@Test
	public void testCalcChecksum() {
		assertEquals("1D", NMEA.calculateChecksum(BODTest.EXAMPLE));
		assertEquals("79", NMEA.calculateChecksum(GGATest.EXAMPLE));
		assertEquals("26", NMEA.calculateChecksum(GLLTest.EXAMPLE));
		assertEquals("11", NMEA.calculateChecksum(RMCTest.EXAMPLE));
		assertEquals("3D", NMEA.calculateChecksum(GSATest.EXAMPLE));
		assertEquals("73", NMEA.calculateChecksum(GSVTest.EXAMPLE));
		assertEquals("58", NMEA.calculateChecksum(RMBTest.EXAMPLE));
		assertEquals("25", NMEA.calculateChecksum(RTETest.EXAMPLE));
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

		assertTrue(NMEA.isValid(BODTest.EXAMPLE));
		assertTrue(NMEA.isValid(GGATest.EXAMPLE));
		assertTrue(NMEA.isValid(GLLTest.EXAMPLE));
		assertTrue(NMEA.isValid(GSATest.EXAMPLE));
		assertTrue(NMEA.isValid(GSVTest.EXAMPLE));
		assertTrue(NMEA.isValid(RMBTest.EXAMPLE));
		assertTrue(NMEA.isValid(RMCTest.EXAMPLE));
		assertTrue(NMEA.isValid(RTETest.EXAMPLE));
		assertTrue(NMEA.isValid(VTGTest.EXAMPLE));
		assertTrue(NMEA.isValid(WPLTest.EXAMPLE));
		assertTrue(NMEA.isValid(ZDATest.EXAMPLE));

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
		assertFalse(NMEA.isValid("$GPGLL,6011.552,N,02501.941,E,120045,A*00"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#getStringValue(int)}.
	 */
	@Test
	public void testGetStringValue() {

		String nmea = "$GPGLL,6011.552,N,02501.941,E,120045,A";
		SentenceParser s = new SentenceParser(nmea);

		String data = "6011.552,N,02501.941,E,120045,A";
		String[] expected = data.split(",", -1);

		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], s.getStringValue(i));
		}

		// instance with invalid and missing values
		nmea = "$GPGLL,foobar,N,,EAST,120045,A";
		s = new SentenceParser(nmea);

		try {
			s.getDoubleValue(0);
			fail("Did not throw exception");
		} catch (ParseException ex) {
			// pass
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			s.getStringValue(2);
			fail("Did not throw exception");
		} catch (DataNotAvailableException ex) {
			// pass
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			s.getCharValue(3);
			fail("Did not throw exception");
		} catch (ParseException ex) {
			// pass
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetFieldIndexOutOfBounds() {

		try {
			instance.getStringValue(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// pass
		} catch (Exception e) {
			fail("Unexpected exception was thrown");
		}

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

		try {
			instance.getStringValue(instance.getFieldCount());
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// pass
		} catch (Exception e) {
			fail("Unexpected exception was thrown");
		}
	}
}

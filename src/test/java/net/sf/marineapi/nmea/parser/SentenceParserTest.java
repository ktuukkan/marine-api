package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.test.util.FOOParser;
import net.sf.marineapi.test.util.FOOSentence;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the sentence parser base class.
 *
 * @author Kimmo Tuukkanen
 */
public class SentenceParserTest {

	public static final String VDO_EXAMPLE = "!AIVDO,1,1,,,13:r`R5P1orpG60JeHgRSj4l0000,0*56";
	public static final String VDM_EXAMPLE = "!AIVDM,1,1,,B,177KQJ5000G?tO`K>RA1wUbN0TKH,0*5C";

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
		final FOOSentence fp = new FOOParser(fooSentence);
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
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for SenteceParser constructor.
	 */
	@Test
	public void testConstructorWithAIVDO() {
		Sentence s = new SentenceParser(VDO_EXAMPLE);
		assertTrue(s.isValid());
		assertFalse(s.isProprietary());
		assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR, s.getBeginChar());
		assertEquals(VDO_EXAMPLE, s.toString());
	}

	/**
	 * Test method for SenteceParser constructor.
	 */
	@Test
	public void testConstructorWithAIVDM() {
		Sentence s = new SentenceParser(VDM_EXAMPLE);
		assertTrue(s.isValid());
		assertFalse(s.isProprietary());
		assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR, s.getBeginChar());
		assertEquals(VDM_EXAMPLE, s.toString());
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
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#getDoubleValue(int)}.
	 */
	@Test
	public void testGetDoubleValueWithInvalidValue() {
		String nmea = "$GPGLL,a,b,c,d,e,f";
		SentenceParser s = new SentenceParser(nmea);
		try {
			s.getDoubleValue(2);
			fail("Did not throw exception");
		} catch (ParseException ex) {
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
			assertEquals("120044.567", val);
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
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#isProprietary()}.
	 */
	public void testIsProprietary() {
		assertFalse(instance.isProprietary());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#setBeginChar()}.
	 */
	public void testSetBeginChar() {
		assertEquals(Sentence.BEGIN_CHAR, instance.getBeginChar());
		instance.setBeginChar(Sentence.ALTERNATIVE_BEGIN_CHAR);
		assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR, instance.getBeginChar());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#isValid()}.
	 */
	@Test
	public void testIsValid() {
		assertTrue(instance.isValid());
		instance.setStringValue(0, "\t");
		assertFalse(instance.isValid());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#setDoubleValue(int, double, int, int)}
	 * .
	 */
	@Test
	public void testSetDoubleValue() {
		final int field = 0;
		final double value = 123.456789;
		instance.setDoubleValue(field, value);
		assertEquals(String.valueOf(value), instance.getStringValue(field));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#setDoubleValue(int, double, int, int)}
	 * .
	 */
	@Test
	public void testSetDoubleValueWithPrecision() {

		instance.setDoubleValue(0, 3.14, 0, 0);
		assertEquals("3", instance.getStringValue(0));

		instance.setDoubleValue(0, 3.14, 2, 0);
		assertEquals("03", instance.getStringValue(0));

		instance.setDoubleValue(0, 3.14, 1, 4);
		assertEquals("3.1400", instance.getStringValue(0));

		instance.setDoubleValue(0, 678.910, 3, 3);
		assertEquals("678.910", instance.getStringValue(0));

		instance.setDoubleValue(0, 123.456, 4, 1);
		assertEquals("0123.5", instance.getStringValue(0));

		instance.setDoubleValue(0, 78.910, 1, 1);
		assertEquals("78.9", instance.getStringValue(0));

		instance.setDoubleValue(0, 0.910, 0, 3);
		assertEquals(".910", instance.getStringValue(0));

		instance.setDoubleValue(0, 0.910, 3, 2);
		assertEquals("000.91", instance.getStringValue(0));

		instance.setDoubleValue(0, 0.910, 0, 2);
		assertEquals(".91", instance.getStringValue(0));
	}

	@Test
	public void testSetDoubleValueJDK7RoundingIssue() {

		// 2016-11-06: open-jdk7 has rounding issues that were not caught by this test
		// until now. All good in jdk8, thus testing conditionally.
		// https://bugs.openjdk.java.net/browse/JDK-8029896
		// https://bugs.openjdk.java.net/browse/JDK-8039915

		String version = System.getProperty("java.version");
		org.junit.Assume.assumeTrue(!version.startsWith("1.7."));

		// would fail in jdk7 claiming "12.35" not equal to "12.34"
		instance.setDoubleValue(0, 12.345, 1, 2);
		assertEquals("12.35", instance.getStringValue(0));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#setIntValue(int, int, int)}
	 * .
	 */
	@Test
	public void testSetIntValueWithLeading() {

		instance.setIntValue(0, 0, 0);
		assertEquals("0", instance.getStringValue(0));

		instance.setIntValue(0, 0, 1);
		assertEquals("0", instance.getStringValue(0));

		instance.setIntValue(0, 1, 2);
		assertEquals("01", instance.getStringValue(0));

		instance.setIntValue(0, 1, 3);
		assertEquals("001", instance.getStringValue(0));

		instance.setIntValue(0, 12, 1);
		assertEquals("12", instance.getStringValue(0));

		instance.setIntValue(0, 12, 2);
		assertEquals("12", instance.getStringValue(0));

		instance.setIntValue(0, 12, 3);
		assertEquals("012", instance.getStringValue(0));

		instance.setIntValue(0, -1, 3);
		assertEquals("-01", instance.getStringValue(0));
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

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#toSentence(int)}.
	 */
	@Test
	public void testToSentenceWithMaxLength() {
		int max = instance.toString().length() + 1;
		assertEquals(RMCTest.EXAMPLE, instance.toSentence(max));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#§(int)}.
	 */
	@Test
	public void testToSentenceWithMaxLengthOnLimit() {
		int max = instance.toString().length();
		assertEquals(RMCTest.EXAMPLE, instance.toSentence(max));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#toSentence(int)}.
	 */
	@Test
	public void testToSentenceWithMaxLengthExceeded() {
		try {
			int max = instance.toString().length() - 1;
			assertEquals(RMCTest.EXAMPLE, instance.toSentence(max));
			fail("didn't throw exception");
		} catch (Exception e) {
			// pass
		}
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#equals(Object)
	 */
	@Test
	public void testEquals() {
		assertTrue(instance.equals(new SentenceParser(RMCTest.EXAMPLE)));
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#equals(Object)
	 */
	@Test
	public void testEqualsWithNonEqual() {
		assertFalse(instance.equals(new SentenceParser(RMBTest.EXAMPLE)));
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#equals(Object)
	 */
	@Test
	public void testEqualsWithNull() {
		assertFalse(instance.equals(null));
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceParser#equals(Object)
	 */
	@Test
	public void testEqualsWithSelf() {
		assertTrue(instance.equals(instance));
	}
	
	@Test
	public void testSetFieldCountLowerByOne() {
		
		final int count = instance.getFieldCount() - 1;
		final int lastIndex = instance.getFieldCount() - 2;
		final String value = instance.getStringValue(lastIndex);
		
		instance.setFieldCount(count);
		assertEquals(count, instance.getFieldCount());
		assertEquals(value, instance.getStringValue(lastIndex));
	}
	
	@Test
	public void testSetFieldCountLower() {
		SentenceParser parser = new SentenceParser("$GPGGA,1,2,3,4");
		parser.setFieldCount(2);
		assertEquals(2, parser.getFieldCount());
		assertEquals("1", parser.getStringValue(0));
		assertEquals("2", parser.getStringValue(1));
		assertTrue(parser.toString().startsWith("$GPGGA,1,2*"));
	}
	
	@Test
	public void testSetFieldCountHigherByOne() {
		
		final int count = instance.getFieldCount() + 1;
		final int lastIndex = instance.getFieldCount() - 1;
		final String value = instance.getStringValue(lastIndex);
		
		instance.setFieldCount(count);
		assertEquals(count, instance.getFieldCount());
		assertEquals(value, instance.getStringValue(lastIndex));
	}

	@Test
	public void testSetFieldCountHigher() {
		SentenceParser parser = new SentenceParser("$GPGGA,1,2,3,4");
		parser.setFieldCount(8);
		assertEquals(8, parser.getFieldCount());
		assertTrue(parser.toString().startsWith("$GPGGA,1,2,3,4,,,,*"));
	}
	
	@Test
	public void testSetStringValuesReplaceAll() {
		SentenceParser parser = new SentenceParser("$GPGGA,1,2,3,4");
		String[] values = { "5", "6", "7" };
		parser.setStringValues(0, values);
		assertEquals(3, parser.getFieldCount());
		assertEquals("5", parser.getStringValue(0));
		assertEquals("6", parser.getStringValue(1));
		assertEquals("7", parser.getStringValue(2));
	}
	
	@Test
	public void testSetStringValuesReplaceTail() {
		SentenceParser parser = new SentenceParser("$GPGGA,1,2,3,4");
		String[] values = { "5", "6", "7" };
		parser.setStringValues(1, values);
		assertEquals(4, parser.getFieldCount());
		assertEquals("1", parser.getStringValue(0));
		assertEquals("5", parser.getStringValue(1));
		assertEquals("6", parser.getStringValue(2));
		assertEquals("7", parser.getStringValue(3));
	}
}

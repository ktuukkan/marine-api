package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.test.util.BARParser;
import net.sf.marineapi.test.util.FOOParser;
import net.sf.marineapi.test.util.FOOSentence;
import net.sf.marineapi.test.util.VDMParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class SentenceFactoryTest {

	private final SentenceFactory instance = SentenceFactory.getInstance();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		instance.reset();
	}

	@After
	public void tearDown() throws Exception {
		instance.reset();
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testSupportedTypesRegistered() {
		for (SentenceId id : SentenceId.values()) {
			String msg = "Parser not registered: " + id;
			assertTrue(msg, instance.hasParser(id.toString()));
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateParser() {
		Sentence bod = instance.createParser(BODTest.EXAMPLE);
		assertNotNull(bod);
		assertTrue(bod instanceof Sentence);
		assertTrue(bod instanceof BODSentence);
		assertTrue(bod instanceof BODParser);
		assertEquals(BODTest.EXAMPLE, bod.toSentence());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(TalkerId, SentenceId)
	 * .
	 */
	@Test
	public void testCreateEmptyParserWithSentenceId() {
		for (SentenceId id : SentenceId.values()) {
			Sentence s = instance.createParser(TalkerId.ST, id);
			assertNotNull(s);
			assertTrue(s instanceof Sentence);
			assertTrue(s instanceof SentenceParser);
			assertEquals(TalkerId.ST, s.getTalkerId());
			assertEquals(id.name(), s.getSentenceId());
		}
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateEmptyParserWithSentenceIdStr() {
		for (SentenceId id : SentenceId.values()) {
			Sentence s = instance.createParser(TalkerId.ST, id.name());
			assertNotNull(s);
			assertTrue(s instanceof Sentence);
			assertTrue(s instanceof SentenceParser);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateCustomParser() {

		try {
			instance.registerParser("FOO", FOOParser.class);
			assertTrue(instance.hasParser("FOO"));
		} catch (Exception e) {
			fail("parser registering failed");
		}
		
		Sentence s = null;
		try {
			s = instance.createParser("$IIFOO,aa,bb,cc");
		} catch (Exception e) {
			fail("sentence parsing failed");
		}
		
		assertNotNull(s);
		assertTrue(s instanceof Sentence);
		assertTrue(s instanceof SentenceParser);
		assertTrue(s instanceof FOOParser);
		assertEquals(TalkerId.II, s.getTalkerId());
		assertEquals("FOO", s.getSentenceId());
		assertEquals("aa", ((FOOSentence)s).getValueA());
		assertEquals("bb", ((FOOSentence)s).getValueB());
		assertEquals("cc", ((FOOSentence)s).getValueC());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateEmptyCustomParser() {

		try {
			instance.registerParser("FOO", FOOParser.class);
			assertTrue(instance.hasParser("FOO"));
		} catch (Exception e) {
			fail("parser registering failed");
		}

		Sentence s = instance.createParser(TalkerId.II, "FOO");
		assertNotNull(s);
		assertTrue(s instanceof Sentence);
		assertTrue(s instanceof SentenceParser);
		assertTrue(s instanceof FOOParser);
		assertEquals("FOO", s.getSentenceId());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateParserWithEmptyString() {
		try {
			instance.createParser("");
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateParserWithNull() {
		try {
			instance.createParser(null);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateParserWithRandom() {
		try {
			instance.createParser("asdqas,dwersa,dsdfas,das");
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateParserWithUnregistered() {
		try {
			instance.createParser("$GPXYZ,1,2,3,4,5,6,7,8");
			fail("Did not throw exception");
		} catch (UnsupportedSentenceException e) {
			// pass
		}
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#registerParser(java.lang.String, java.lang.Class)}
	 * .
	 */
	@Test
	public void testRegisterParserWithAlternativeBeginChar() {

		try {
			instance.registerParser("VDM", VDMParser.class);
			assertTrue(instance.hasParser("VDM"));
		} catch (Exception e) {
			fail("parser registering failed");
		}

		Sentence s = instance.createParser("!AIVDM,1,2,3");
		assertNotNull(s);
		assertTrue(s instanceof Sentence);
		assertTrue(s instanceof SentenceParser);
		assertTrue(s instanceof VDMParser);
		instance.unregisterParser(VDMParser.class);
		assertFalse(instance.hasParser("VDM"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#registerParser(java.lang.String, java.lang.Class)}
	 * .
	 */
	@Test
	public void testRegisterInvalidParser() {
		try {
			instance.registerParser("BAR", BARParser.class);
			fail("did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#registerParser(java.lang.String, java.lang.Class)}
	 * .
	 */
	@Test
	public void testUnregisterParser() {
		instance.registerParser("FOO", FOOParser.class);
		assertTrue(instance.hasParser("FOO"));
		instance.unregisterParser(FOOParser.class);
		assertFalse(instance.hasParser("FOO"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#hasParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testHasParser() {
		assertTrue(instance.hasParser("GLL"));
		assertFalse(instance.hasParser("ABC"));
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(TalkerId, SentenceId)
	 * .
	 */
	@Test
	public void testListParsers() {
		List<String> types = instance.listParsers();
		assertEquals(SentenceId.values().length, types.size());
		for (SentenceId id : SentenceId.values()) {
			assertTrue(types.contains(id.name()));
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.SentenceFactory#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(instance);
		assertTrue(instance == SentenceFactory.getInstance());
		assertEquals(instance, SentenceFactory.getInstance());
	}

}

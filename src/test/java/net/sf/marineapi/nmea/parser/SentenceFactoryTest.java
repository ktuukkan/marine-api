package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.test.util.BARParser;
import net.sf.marineapi.test.util.FOOParser;
import net.sf.marineapi.test.util.VDMParser;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class SentenceFactoryTest {

    private SentenceFactory instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        instance = SentenceFactory.getInstance();
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
    }
    
    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceFactory#createParser(java.lang.String)}
     * .
     */
    @Test
    public void testCreateEmptyParser() {
    	for(SentenceId id : SentenceId.values()) {
    		Sentence s = instance.createParser(TalkerId.II, id.name());
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
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceFactory#registerParser(java.lang.String, java.lang.Class)}
     * .
     */
    @Test
    public void testRegisterParser() {

        try {
            instance.registerParser("FOO", FOOParser.class);
            assertTrue(instance.hasParser("FOO"));
        } catch (Exception e) {
            fail("parser registering failed");
        }

        Sentence s = instance.createParser("$IIFOO,1,2,3");
        assertNotNull(s);
        assertTrue(s instanceof Sentence);
        assertTrue(s instanceof SentenceParser);
        assertTrue(s instanceof FOOParser);
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
     * {@link net.sf.marineapi.nmea.parser.SentenceFactory#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        assertNotNull(instance);
        assertEquals(instance, SentenceFactory.getInstance());
    }

}

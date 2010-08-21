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
	 */
	@Override
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#BODParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testConstructorWithInvalidSentence() {
		try {
			new BODParser("$HUBBA,habba,doo,dah,doo");
		} catch (IllegalArgumentException e) {
			// OK
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#BODParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testConstructorWithNull() {
		try {
			new BODParser(null);
		} catch (IllegalArgumentException e) {
			// OK
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#BODParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testConstructorWithRandomString() {
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

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getMagneticBearing()}.
	 */
	@Test
	public void testGetMagneticBearing() {
		try {
			double b = bod.getMagneticBearing();
			assertEquals(228.8, b, 0.001);
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
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getTrueBearing()}.
	 */
	@Test
	public void testGetTrueBearing() {
		try {
			double b = bod.getTrueBearing();
			assertEquals(234.9, b, 0.001);
		} catch (ParseException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getDestinationWaypointId()}
	 * .
	 */
	@Test
	public void testSetDestinationWaypointId() {
		try {
			bod.setDestinationWaypointId("TIISKERI");
			assertEquals("TIISKERI", bod.getDestinationWaypointId());
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
	public void testSetDestinationWaypointIdWithEmptyStr() {
		try {
			bod.setDestinationWaypointId("");
			bod.getDestinationWaypointId();
		} catch (Exception e) {
			assertTrue(e instanceof DataNotAvailableException);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getDestinationWaypointId()}
	 * .
	 */
	@Test
	public void testSetDestinationWaypointIdWithNull() {
		try {
			bod.setDestinationWaypointId(null);
			bod.getDestinationWaypointId();
		} catch (Exception e) {
			assertTrue(e instanceof DataNotAvailableException);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getMagneticBearing()}.
	 */
	@Test
	public void testSetMagneticBearing() {
		final double bearing = 180.0;
		try {
			bod.setMagneticBearing(bearing);
			assertEquals(bearing, bod.getMagneticBearing());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getMagneticBearing()}.
	 */
	@Test
	public void testSetMagneticBearingWithGreaterThanAllowed() {
		try {
			bod.setMagneticBearing(360.01);
			fail("Did not throw exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getMagneticBearing()}.
	 */
	@Test
	public void testSetMagneticBearingWithNegativeValue() {
		try {
			bod.setMagneticBearing(-0.01);
			fail("Did not throw exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getOriginWaypointId()}.
	 */
	@Test
	public void testSetOriginWaypointId() {
		try {
			bod.setOriginWaypointId("TAINIO");
			assertEquals("TAINIO", bod.getOriginWaypointId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getTrueBearing()}.
	 */
	@Test
	public void testSetTrueBearing() {
		final double bearing = 180.0;
		try {
			bod.setTrueBearing(bearing);
			assertEquals(bearing, bod.getTrueBearing());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getTrueBearing()}.
	 */
	@Test
	public void testSetTrueBearingGreaterThanAllowed() {
		try {
			bod.setTrueBearing(360.01);
			fail("Did not throw exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.BODParser#getTrueBearing()}.
	 */
	@Test
	public void testSetTrueBearingWithNegativeValue() {
		try {
			bod.setTrueBearing(-0.01);
			fail("Did not throw exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
}

package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import net.sf.marineapi.nmea.sentence.RTESentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.RouteType;

import org.junit.Before;
import org.junit.Test;

/**
 * RTETest
 * 
 * @author Kimmo Tuukkanen
 */
public class RTETest {

	/** Example sentence */
	public static final String EXAMPLE = "$GPRTE,1,1,c,0,MELIN,RUSKI,KNUDAN*25";

	private RTESentence empty;
	private RTESentence rte;

	@Before
	public void setUp() {
		try {
			empty = new RTEParser(TalkerId.GP);
			rte = new RTEParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(4, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#addWaypointId(String)}.
	 */
	@Test
	public void testAddWaypointId() {
		empty.addWaypointId("1ST");
		assertTrue(empty.toString().contains(",1ST*"));

		empty.addWaypointId("2ND");
		assertTrue(empty.toString().contains(",1ST,2ND*"));

		empty.addWaypointId("3RD");
		assertTrue(empty.toString().contains(",1ST,2ND,3RD*"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#getRouteId()}.
	 */
	@Test
	public void testGetRouteId() {
		assertEquals("0", rte.getRouteId());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#getSentenceCount()}.
	 */
	@Test
	public void testGetSentenceCount() {
		assertEquals(1, rte.getSentenceCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#getSentenceIndex()}.
	 */
	@Test
	public void testGetSentenceIndex() {
		assertEquals(1, rte.getSentenceIndex());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#getWaypointCount()}.
	 */
	@Test
	public void testGetWaypointCount() {
		assertEquals(3, rte.getWaypointCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#getWaypointIds()}.
	 */
	@Test
	public void testGetWaypointIds() {
		String[] ids = rte.getWaypointIds();
		assertNotNull(ids);
		assertEquals(3, ids.length);
		assertEquals("MELIN", ids[0]);
		assertEquals("RUSKI", ids[1]);
		assertEquals("KNUDAN", ids[2]);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#isActiveRoute()}.
	 */
	@Test
	public void testIsActiveRoute() {
		assertTrue(rte.isActiveRoute());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RTEParser#isFirst()}.
	 */
	@Test
	public void testIsFirst() {
		assertTrue(rte.isFirst());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RTEParser#isLast()}.
	 */
	@Test
	public void testIsLast() {
		assertTrue(rte.isLast());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#isWorkingRoute()}.
	 */
	@Test
	public void testIsWorkingRoute() {
		assertFalse(rte.isWorkingRoute());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#getRouteId()}.
	 */
	@Test
	public void testSetRouteId() {
		rte.setRouteId("ID");
		assertEquals("ID", rte.getRouteId());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#isActiveRoute()}.
	 */
	@Test
	public void testSetRouteTypeActive() {
		rte.setRouteType(RouteType.ACTIVE);
		assertTrue(rte.isActiveRoute());
		assertFalse(rte.isWorkingRoute());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#isActiveRoute()}.
	 */
	@Test
	public void testSetRouteTypeWorking() {
		rte.setRouteType(RouteType.WORKING);
		assertTrue(rte.isWorkingRoute());
		assertFalse(rte.isActiveRoute());

	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#setSentenceCount(int)}.
	 */
	@Test
	public void testSetSentenceCount() {
		rte.setSentenceCount(3);
		assertEquals(3, rte.getSentenceCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#setSentenceCount(int)}.
	 */
	@Test
	public void testSetSentenceCountWithNegativeValue() {
		try {
			rte.setSentenceCount(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("cannot be negative"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#setSentenceIndex(int)}.
	 */
	@Test
	public void testSetSentenceIndex() {
		rte.setSentenceIndex(2);
		assertEquals(2, rte.getSentenceIndex());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#setSentenceIndex(int)}.
	 */
	@Test
	public void testSetSentenceIndexWithNegativeValue() {
		try {
			rte.setSentenceIndex(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("cannot be negative"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RTEParser#setWaypointIds(String[])}.
	 */
	@Test
	public void testSetWaypointIds() {
		final String[] ids = { "ONE", "TWO", "THREE", "FOUR", "FIVE" };
		final String expected = "$GPRTE,1,1,c,0,ONE,TWO,THREE,FOUR,FIVE*7F";
		rte.setWaypointIds(ids);
		assertEquals(5, rte.getWaypointCount());
		assertEquals(expected, rte.toString());
		assertTrue(Arrays.equals(ids, rte.getWaypointIds()));

		empty.setWaypointIds(ids);
		assertEquals(5, empty.getWaypointCount());
		assertTrue(empty.toString().startsWith(
				"$GPRTE,,,,,ONE,TWO,THREE,FOUR,FIVE*"));
	}

}

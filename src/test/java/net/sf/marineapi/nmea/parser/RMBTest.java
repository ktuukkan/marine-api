package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.RMBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.Waypoint;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the RMB sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class RMBTest {

	/** Example sentence */
	public static final String EXAMPLE = "$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58";

	private RMBSentence empty;
	private RMBSentence rmb;

	/**
	 * setUp
	 */
	@Before
	public void setUp() {
		try {
			empty = new RMBParser(TalkerId.GP);
			rmb = new RMBParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(13, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getArrivalStatus()}.
	 */
	@Test
	public void testArrivalStatus() {

		assertEquals(DataStatus.VOID, rmb.getArrivalStatus());
		assertFalse(rmb.hasArrived());

		rmb.setArrivalStatus(DataStatus.ACTIVE);
		assertEquals(DataStatus.ACTIVE, rmb.getArrivalStatus());
		assertTrue(rmb.hasArrived());

		rmb.setArrivalStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, rmb.getArrivalStatus());
		assertFalse(rmb.hasArrived());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getBearing()} .
	 */
	@Test
	public void testGetBearing() {
		assertEquals(234.9, rmb.getBearing(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getCrossTrackError()}.
	 */
	@Test
	public void testGetCrossTrackError() {
		assertEquals(0.0, rmb.getCrossTrackError(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getDestination()} .
	 */
	@Test
	public void testGetDestination() {
		final String id = "RUSKI";
		final double lat = 55 + (36.200 / 60);
		final double lon = 14 + (36.500 / 60);

		Waypoint wp = rmb.getDestination();
		assertNotNull(wp);
		assertEquals(id, wp.getId());
		assertEquals(lat, wp.getLatitude(), 0.0000001);
		assertEquals(lon, wp.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, wp.getLatitudeHemisphere());
		assertEquals(CompassPoint.EAST, wp.getLongitudeHemisphere());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getOriginId()}.
	 */
	@Test
	public void testGetOriginId() {
		// FIXME test data should contain ID
		try {
			assertEquals("", rmb.getOriginId());
			fail("Did not throw ParseException");
		} catch (Exception e) {
			assertTrue(e instanceof DataNotAvailableException);
		}
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMBParser#getRange()}
	 * .
	 */
	@Test
	public void testGetRange() {
		assertEquals(432.3, rmb.getRange(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		assertEquals(DataStatus.ACTIVE, rmb.getStatus());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getSteerTo()}.
	 */
	@Test
	public void testGetSteerTo() {
		assertEquals(Direction.RIGHT, rmb.getSteerTo());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#getVelocity()}.
	 */
	@Test
	public void testGetVelocity() {
		// FIXME test data should contain velocity
		try {
			assertEquals(0.0, rmb.getVelocity(), 0.001);
			fail("Did not throw ParseException");
		} catch (Exception e) {
			assertTrue(e instanceof DataNotAvailableException);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setBearing(double)} .
	 */
	@Test
	public void testSetBearing() {
		final double brg = 90.56789;
		rmb.setBearing(brg);
		assertTrue(rmb.toString().contains(",090.6,"));
		assertEquals(brg, rmb.getBearing(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setBearing(double)} .
	 */
	@Test
	public void testSetBearingWithNegativeValue() {
		try {
			rmb.setBearing(-0.001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("0..360"));
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setBearing(double)} .
	 */
	@Test
	public void testSetBearingWithValueGreaterThanAllowed() {
		try {
			rmb.setBearing(360.001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("0..360"));
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setCrossTrackError(double)}
	 * .
	 */
	@Test
	public void testSetCrossTrackError() {
		final double xte = 2.56789;
		rmb.setCrossTrackError(xte);
		assertTrue(rmb.toString().contains(",2.57,"));
		assertEquals(xte, rmb.getCrossTrackError(), 0.2);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setDestination(Waypoint)} .
	 */
	@Test
	public void testSetDestination() {

		final String id = "MYDEST";
		final double lat = 61 + (1.111 / 60);
		final double lon = 27 + (7.777 / 60);
		Waypoint d = new Waypoint(id, lat, lon);

		rmb.setDestination(d);

		String str = rmb.toString();
		Waypoint wp = rmb.getDestination();

		assertTrue(str.contains(",MYDEST,6101.111,N,02707.777,E,"));
		assertNotNull(wp);
		assertEquals(id, wp.getId());
		assertEquals(lat, wp.getLatitude(), 0.0000001);
		assertEquals(lon, wp.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, wp.getLatitudeHemisphere());
		assertEquals(CompassPoint.EAST, wp.getLongitudeHemisphere());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setOriginId(String)}.
	 */
	@Test
	public void testSetOriginId() {
		rmb.setOriginId("ORIGIN");
		assertTrue(rmb.toString().contains(",ORIGIN,RUSKI,"));
		assertEquals("ORIGIN", rmb.getOriginId());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setRange(double)} .
	 */
	@Test
	public void testSetRange() {
		final double range = 12.3456;
		rmb.setRange(range);
		assertTrue(rmb.toString().contains(",12.3,"));
		assertEquals(range, rmb.getRange(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setStatus(DataStatus)}.
	 */
	@Test
	public void testSetStatus() {
		rmb.setStatus(DataStatus.ACTIVE);
		assertEquals(DataStatus.ACTIVE, rmb.getStatus());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setSteerTo(CompassPoint)}.
	 */
	@Test
	public void testSetSteerTo() {
		rmb.setSteerTo(Direction.LEFT);
		assertTrue(rmb.toString().contains(",L,"));
		assertEquals(Direction.LEFT, rmb.getSteerTo());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setSteerTo(CompassPoint)}.
	 */
	@Test
	public void testSetSteerToWithNull() {
		try {
			rmb.setSteerTo(null);
			fail("Did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("LEFT or RIGHT"));
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setVelocity()}.
	 */
	@Test
	public void testSetVelocity() {
		final double v = 40.66666;
		rmb.setVelocity(v);
		assertTrue(rmb.toString().contains(",40.7,"));
		assertEquals(v, rmb.getVelocity(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMBParser#setVelocity()}.
	 */
	@Test
	public void testSetVelocityWithNegativeValue() {
		final double v = -0.123;
		rmb.setVelocity(v);
		assertTrue(rmb.toString().contains(",-0.1,"));
		assertEquals(v, rmb.getVelocity(), 0.1);
	}

}

package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {

	Position instance;

	/**
	 * Setup
	 */
	@Before
	public void setUp() {
		instance = new Position(60.0, CompassPoint.NORTH, 25.0,
				CompassPoint.EAST, Datum.WGS84);
	}

	/**
	 * Test for distanceTo()
	 */
	@Test
	public void testDistanceTo() {

		Position origin = new Position(0.123, CompassPoint.NORTH, 25.0,
				CompassPoint.EAST);

		// distance to "here" must be zero
		assertEquals(0.0, origin.distanceTo(origin), 0.00001);

		for (int n = 0; n < 90; n++) {

			// 1 degree north from instance's position
			Position destination = new Position(0.123 + n, CompassPoint.NORTH,
					25.0, CompassPoint.EAST);

			double distance = origin.distanceTo(destination);

			// By definition, one degree equals 60 NM
			double expected = (60 * n * 1852.0);

			// System.out.println(n + ": exp=" + expected + "\t dist=" +
			// distance);
			assertEquals(expected, distance, 1.0);
		}
	}

	/**
	 * Test for getDatum()
	 */
	@Test
	public void testGetDatum() {
		assertEquals(Datum.WGS84, instance.getDatum());
	}

	/**
	 * Test for getLatHemisphere()
	 */
	@Test
	public void testGetLatHemisphere() {
		assertEquals(CompassPoint.NORTH, instance.getLatHemisphere());
	}

	/**
	 * Test for getLatitude()
	 */
	@Test
	public void testGetLatitude() {
		assertEquals(60.0, instance.getLatitude(), 0.0000001);
	}

	/**
	 * Test for getLongitude()
	 */
	@Test
	public void testGetLongitude() {
		assertEquals(25.0, instance.getLongitude(), 0.0000001);
	}

	/**
	 * Test for getLonHemisphere()
	 */
	@Test
	public void testGetLonHemisphere() {
		assertEquals(CompassPoint.EAST, instance.getLonHemisphere());
	}

	/**
	 * Test for setLatHemisphere()
	 */
	@Test
	public void testSetLatHemisphere() {
		instance.setLatHemisphere(CompassPoint.SOUTH);
		assertEquals(CompassPoint.SOUTH, instance.getLatHemisphere());
		instance.setLatHemisphere(CompassPoint.NORTH);
		assertEquals(CompassPoint.NORTH, instance.getLatHemisphere());
	}

	/**
	 * Test for setLatHemisphere()
	 */
	@Test
	public void testSetLatHemisphereEast() {
		try {
			instance.setLatHemisphere(CompassPoint.EAST);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test for setLatHemisphere()
	 */
	@Test
	public void testSetLatHemisphereWest() {
		try {
			instance.setLatHemisphere(CompassPoint.WEST);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test for setLatitude()
	 */
	@Test
	public void testSetLatitude() {
		assertEquals(60.0, instance.getLatitude(), 0.0000001);
		instance.setLatitude(65.5555);
		assertEquals(65.5555, instance.getLatitude(), 0.0000001);
	}

	/**
	 * Test for setLatitude()
	 */
	@Test
	public void testSetLatitudeWithNegativeValue() {
		try {
			instance.setLatitude(-0.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for setLatitude()
	 */
	@Test
	public void testSetLatitudeWithValueGreaterThanAllowed() {
		try {
			instance.setLatitude(90.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for setLongitude()
	 */
	@Test
	public void testSetLongitude() {
		assertEquals(25.0, instance.getLongitude(), 0.0000001);
		instance.setLongitude(12.34);
		assertEquals(12.34, instance.getLongitude(), 0.0000001);
	}

	/**
	 * Test for setLonHemisphere()
	 */
	@Test
	public void testSetLongitudeHemisphere() {
		instance.setLonHemisphere(CompassPoint.WEST);
		assertEquals(CompassPoint.WEST, instance.getLonHemisphere());
		instance.setLonHemisphere(CompassPoint.EAST);
		assertEquals(CompassPoint.EAST, instance.getLonHemisphere());
	}

	/**
	 * Test for setLonHemisphere()
	 */
	@Test
	public void testSetLongitudeHemisphereNorth() {
		try {
			instance.setLonHemisphere(CompassPoint.NORTH);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test for setLonHemisphere()
	 */
	@Test
	public void testSetLongitudeHemisphereSouth() {
		try {
			instance.setLonHemisphere(CompassPoint.SOUTH);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test for setLongitude()
	 */
	@Test
	public void testSetLongitudeWithNegativeValue() {
		try {
			instance.setLongitude(-0.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for setLongitude()
	 */
	@Test
	public void testSetLongitudeWithValueGreaterThanAllowed() {
		try {
			instance.setLongitude(180.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for toWaypoint()
	 */
	@Test
	public void testToWaypoint() {
		final String name = "TEST";
		final Waypoint wp = instance.toWaypoint(name);
		assertEquals(name, wp.getId());
		assertEquals("", wp.getDescription());
		assertEquals(instance.getLatitude(), wp.getLatitude(), 0.00001);
		assertEquals(instance.getLongitude(), wp.getLongitude(), 0.00001);
		assertEquals(instance.getLatHemisphere(), wp.getLatHemisphere());
		assertEquals(instance.getLonHemisphere(), wp.getLonHemisphere());
		assertEquals(instance.getDatum(), wp.getDatum());
	}
}

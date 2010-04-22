package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
		instance = new Position(60.0, Direction.NORTH, 25.0, Direction.EAST,
				Datum.WGS84);
	}

	/**
	 * Test for getLatitude()
	 */
	@Test
	public void testGetLatitude() {
		assertEquals(60.0, instance.getLatitude(), 0.0000001);
	}

	/**
	 * Test for setLatitude()
	 */
	@Test
	public void testSetLatitude() {
		assertEquals(60.0, instance.getLatitude(), 0.0000001);
		instance.setLatitude(65.5555);
		assertEquals(65.5555, instance.getLatitude(), 0.0000001);
		try {
			instance.setLatitude(-0.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}

		try {
			instance.setLatitude(90.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for getLongitude()
	 */
	@Test
	public void testGetLongitude() {
		assertEquals(25.0, instance.getLongitude(), 0.0000001);
	}

	/**
	 * Test for setLongitude()
	 */
	@Test
	public void testSetLongitude() {

		assertEquals(25.0, instance.getLongitude(), 0.0000001);
		instance.setLongitude(0.0);
		assertEquals(0.0, instance.getLongitude(), 0.0000001);

		try {
			instance.setLongitude(-0.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}

		try {
			instance.setLongitude(180.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for getLatHemisphere()
	 */
	@Test
	public void testGetLatHemisphere() {
		assertEquals(Direction.NORTH, instance.getLatHemisphere());
	}

	/**
	 * Test for setLatHemisphere()
	 */
	@Test
	public void testSetLatHemisphere() {
		instance.setLatHemisphere(Direction.SOUTH);
		assertEquals(Direction.SOUTH, instance.getLatHemisphere());

		try {
			instance.setLatHemisphere(Direction.EAST);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			instance.setLatHemisphere(Direction.WEST);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test for getLonHemisphere()
	 */
	@Test
	public void testGetLonHemisphere() {
		assertEquals(Direction.EAST, instance.getLonHemisphere());
	}

	/**
	 * Test for setLonHemisphere()
	 */
	@Test
	public void testSetLongitudeHemisphere() {
		instance.setLonHemisphere(Direction.WEST);
		assertEquals(Direction.WEST, instance.getLonHemisphere());

		try {
			instance.setLonHemisphere(Direction.NORTH);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			instance.setLonHemisphere(Direction.SOUTH);
			fail("Did not throw IllegalArgumentExcetpion");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
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
	 * Test for distanceTo()
	 */
	@Test
	public void testDistanceTo() {

		Position origin = new Position(0.123, Direction.NORTH, 25.0,
				Direction.EAST);

		// distance to "here" must be zero
		assertEquals(0.0, origin.distanceTo(origin), 0.00001);

		for (int n = 0; n < 90; n++) {

			// 1 degree north from instance's position
			Position destination = new Position(0.123 + n, Direction.NORTH,
					25.0, Direction.EAST);

			double distance = origin.distanceTo(destination);

			// By definition, one degree equals 60 NM
			double expected = (60 * n * 1852.0);
			
			// System.out.println(n + ": exp=" + expected + "\t dist=" + distance);
			assertEquals(expected, distance, 1.0);
		}
	}

	/**
	 * Test for toWaypoint()
	 */
	@Test
	public void testToWaypoint() {

		final String id = "ID";
		final String name = "TEST";
		final String desc = "Description text";
		final Waypoint wp = instance.toWaypoint(name);

		assertEquals(name, wp.getId());
		wp.setId(id);
		assertEquals(id, wp.getId());

		assertEquals("", wp.getDescription());
		wp.setDescription(desc);
		assertEquals(desc, wp.getDescription());

		assertTrue(instance.getLatitude() == wp.getLatitude());
		assertTrue(instance.getLongitude() == wp.getLongitude());
		assertEquals(instance.getLatHemisphere(), wp.getLatHemisphere());
		assertEquals(instance.getLonHemisphere(), wp.getLonHemisphere());
		assertEquals(instance.getDatum(), wp.getDatum());
	}
}

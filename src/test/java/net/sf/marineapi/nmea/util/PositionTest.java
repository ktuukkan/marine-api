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
		instance = new Position(60.0, 25.0, Datum.WGS84);
	}

	/**
	 * Test for distanceTo()
	 */
	@Test
	public void testDistanceTo() {

		// TODO: not exactly correct as 1 degree is *approximately* 60 NM

		Position origin = new Position(0.123, 25.0);

		for (int n = 0; n < 90; n++) {

			// 1 degree north from instance's position
			Position destination = new Position(0.123 + n, 25.0);

			double distance = origin.distanceTo(destination);

			// one degree equals 60 NM
			double expected = (60 * n * 1852.0);

			assertEquals(expected, distance, 1.0);
		}
	}

	/**
	 * Test for distanceTo()
	 */
	@Test
	public void testDistanceToSelf() {
		Position origin = new Position(60.567, 26.123);
		assertEquals(0.0, origin.distanceTo(origin), 0.00001);
	}

	/**
	 * Test for getDatum()
	 */
	@Test
	public void testGetDatum() {
		assertEquals(Datum.WGS84, instance.getDatum());
	}

	/**
	 * Test for getLatitude()
	 */
	@Test
	public void testGetLatitude() {
		assertEquals(60.0, instance.getLatitude(), 0.0000001);
	}

	/**
	 * Test for getLatHemisphere()
	 */
	@Test
	public void testGetLatitudeHemisphere() {
		assertEquals(CompassPoint.NORTH, instance.getLatitudeHemisphere());
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
	public void testGetLongitudeHemisphere() {
		assertEquals(CompassPoint.EAST, instance.getLongitudeHemisphere());
	}

	/**
	 * Test for setLatitude()
	 */
	@Test
	public void testSetIllegalLatitudeNorth() {
		try {
			instance.setLatitude(90.001);
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
	public void testSetIllegalLatitudeSouth() {
		try {
			instance.setLatitude(-90.001);
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
	public void testSetIllegalLongitudeEast() {
		try {
			instance.setLongitude(180.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for setLongitude()
	 */
	@Test
	public void testSetIllegalLongitudeWest() {
		try {
			instance.setLongitude(-180.0001);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test for setLatitude()
	 */
	@Test
	public void testSetLatitudeNorth() {
		assertEquals(60.0, instance.getLatitude(), 0.0000001);
		instance.setLatitude(90.0);
		assertEquals(90.0, instance.getLatitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, instance.getLatitudeHemisphere());
	}

	/**
	 * Test for setLatitude()
	 */
	@Test
	public void testSetLatitudeSouth() {
		assertEquals(60.0, instance.getLatitude(), 0.0000001);
		instance.setLatitude(-90.0);
		assertEquals(-90.0, instance.getLatitude(), 0.0000001);
		assertEquals(CompassPoint.SOUTH, instance.getLatitudeHemisphere());
	}

	/**
	 * Test for setLongitude()
	 */
	@Test
	public void testSetLongitudeEast() {
		assertEquals(25.0, instance.getLongitude(), 0.0000001);
		instance.setLongitude(180.0);
		assertEquals(180, instance.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.EAST, instance.getLongitudeHemisphere());
	}

	/**
	 * Test for setLongitude()
	 */
	@Test
	public void testSetLongitudeWest() {
		assertEquals(25.0, instance.getLongitude(), 0.0000001);
		instance.setLongitude(-180.0);
		assertEquals(-180, instance.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.WEST, instance.getLongitudeHemisphere());
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
		assertEquals(instance.getLatitudeHemisphere(), wp.getLatitudeHemisphere());
		assertEquals(instance.getLongitudeHemisphere(), wp.getLongitudeHemisphere());
		assertEquals(instance.getDatum(), wp.getDatum());
	}
}

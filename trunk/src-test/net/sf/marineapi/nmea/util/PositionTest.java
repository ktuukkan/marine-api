package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {

    Position instance;

    @Before
    public void setUp() throws Exception {
        instance = new Position(60.0, Direction.NORTH, 25.0, Direction.EAST,
                Datum.WGS84);
    }

    @Test
    public void testGetLatitude() {
        assertTrue(60.0 == instance.getLatitude());
    }

    @Test
    public void testSetLatitude() {
        assertTrue(60.0 == instance.getLatitude());
        instance.setLatitude(65.555);
        assertTrue(65.555 == instance.getLatitude());
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

    @Test
    public void testGetLongitude() {
        assertTrue(25.0 == instance.getLongitude());
    }

    @Test
    public void testSetLongitude() {

        assertTrue(25.0 == instance.getLongitude());
        instance.setLongitude(0.0);
        assertTrue(0.0 == instance.getLongitude());

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

    @Test
    public void testGetLatitudeHemisphere() {
        assertEquals(Direction.NORTH, instance.getLatHemisphere());
    }

    @Test
    public void testSetLatitudeHemisphere() {
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

    @Test
    public void testGetLongitudeHemisphere() {
        assertEquals(Direction.EAST, instance.getLonHemisphere());
    }

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

    @Test
    public void testGetDatum() {
        assertEquals(Datum.WGS84, instance.getDatum());
    }

    @Test
    public void testDistanceTo() {

        Position origin = new Position(60.0, Direction.NORTH, 25.0,
                Direction.EAST);

        // distance to "here" must be zero
        assertTrue(0.0 == origin.distanceTo(origin));

        // 1 degree north from instance's position
        Position destination = new Position(61.0, Direction.NORTH, 25.0,
                Direction.EAST);

        double distance = origin.distanceTo(destination);

        // By definition, one degree equals 60 NM
        Double expected = new Double(60 * 1852.0);
        assertEquals(expected, new Double(distance));
    }

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

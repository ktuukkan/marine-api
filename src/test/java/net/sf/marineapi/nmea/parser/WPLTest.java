package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.WPLSentence;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Waypoint;

import org.junit.Before;
import org.junit.Test;

/**
 * WPLTest
 * 
 * @author Kimmo Tuukkanen
 */
public class WPLTest {

	/** Example sentence */
	public static final String EXAMPLE = "$GPWPL,5536.200,N,01436.500,E,RUSKI*1F";

	private WPLSentence empty;
	private WPLSentence wpl;

	@Before
	public void setUp() {
		try {
			empty = new WPLParser(TalkerId.GP);
			wpl = new WPLParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(5, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.WPLParser#getWaypoint()}.
	 */
	@Test
	public void testGetWaypoint() {
		final Double lat = new Double(55 + (36.200 / 60));
		final Double lon = new Double(14 + (36.500 / 60));

		Waypoint wp = wpl.getWaypoint();

		assertNotNull(wp);
		assertEquals("RUSKI", wp.getId());
		assertEquals(CompassPoint.NORTH, wp.getLatitudeHemisphere());
		assertEquals(CompassPoint.EAST, wp.getLongitudeHemisphere());
		assertEquals(lat, new Double(wp.getLatitude()));
		assertEquals(lon, new Double(wp.getLongitude()));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.WPLParser#setWaypoint(Waypoint)}.
	 */
	@Test
	public void testSetWaypointWithNonZeroValues() {

		final double lat = 60 + (11.552 / 60);
		final double lon = 25 + (1.941 / 60);

		Waypoint p2 = new Waypoint("WAYP2", lat, lon);

		wpl.setWaypoint(p2);

		String s2 = wpl.toString();
		assertTrue(s2.contains(",6011.552,N,02501.941,E,WAYP2*"));

		Waypoint p = wpl.getWaypoint();
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(lon, p.getLongitude(), 0.0000001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.WPLParser#setWaypoint(Waypoint)}.
	 */
	@Test
	public void testSetWaypointWithZeroValues() {

		Waypoint p1 = new Waypoint("WAYP1", 0.0, 0.0);
		wpl.setWaypoint(p1);

		String s1 = wpl.toString();
		assertTrue(s1.contains(",0000.000,N,00000.000,E,WAYP1*"));

		Waypoint p = wpl.getWaypoint();
		assertNotNull(p);
		assertEquals(0.0, p.getLatitude(), 0.0000001);
		assertEquals(0.0, p.getLongitude(), 0.0000001);
	}
}

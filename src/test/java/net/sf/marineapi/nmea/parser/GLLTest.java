package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the GLL sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class GLLTest {

	/**
	 * Example sentence
	 */
	public static final String EXAMPLE = "$GPGLL,6011.552,N,02501.941,E,120045,A*26";

	private GLLParser empty;
	private GLLParser instance;

	/**
	 * setUp
	 */
	@Before
	public void setUp() {
		try {
			empty = new GLLParser(TalkerId.GP);
			instance = new GLLParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(7, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link GLLParser#getStatus()}.
	 */
	@Test
	public void testGetDataStatus() {
		assertEquals(DataStatus.ACTIVE, instance.getStatus());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GLLParser#getPosition()}.
	 */
	@Test
	public void testGetPosition() {
		final double lat = 60 + (11.552 / 60);
		final double lon = 25 + (1.941 / 60);

		Position p = instance.getPosition();
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(lon, p.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, p.getLatitudeHemisphere());
		assertEquals(CompassPoint.EAST, p.getLongitudeHemisphere());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.GLLParser#getTime()}.
	 */
	@Test
	public void testGetTime() {
		Time t = instance.getTime();
		assertNotNull(t);
		assertEquals(12, t.getHour());
		assertEquals(0, t.getMinutes());
		assertEquals(45.0, t.getSeconds(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GLLParser#setStatus(DataStatus)}.
	 */
	@Test
	public void testSetStatus() {
		assertEquals(DataStatus.ACTIVE, instance.getStatus());
		instance.setStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, instance.getStatus());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GLLParser#setPosition(Position)}.
	 */
	@Test
	public void testSetPositionWithNonZeroValues() {

		final double lat = 60 + (11.552 / 60);
		final double lon = 25 + (1.941 / 60);
		Position p2 = new Position(lat, lon);
		instance.setPosition(p2);

		final String s2 = instance.toString();
		final Position p = instance.getPosition();

		assertTrue(s2.contains(",6011.552,N,"));
		assertTrue(s2.contains(",02501.941,E,"));
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(lon, p.getLongitude(), 0.0000001);
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GLLParser#setPosition(Position)}.
	 */
	@Test
	public void testSetPositionWithZeroValues() {

		Position p1 = new Position(0.0, 0.0);
		instance.setPosition(p1);

		String s1 = instance.toString();
		Position p = instance.getPosition();

		assertTrue(s1.contains(",0000.000,N,"));
		assertTrue(s1.contains(",00000.000,E,"));
		assertNotNull(p);
		assertEquals(0.0, p.getLatitude(), 0.0000001);
		assertEquals(0.0, p.getLongitude(), 0.0000001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GLLParser#setTime(Time)}.
	 */
	@Test
	public void testSetTime() {
		Time t = new Time(1, 2, 3.4);
		instance.setTime(t);
		assertTrue(instance.toString().contains(",E,010203.400,A*"));
	}

	@Test
	public void testSetMode() {
		empty.setMode(FaaMode.DGPS);
		assertEquals(FaaMode.DGPS, empty.getMode());
		assertTrue(empty.toString().startsWith("$GPGLL,,,,,,,D*"));
	}

	@Test
	public void testSetModeInLegacySentence() {
		instance.setMode(FaaMode.PRECISE);
		assertEquals(FaaMode.PRECISE, instance.getMode());
		assertTrue(instance.toString().startsWith("$GPGLL,6011.552,N,02501.941,E,120045,A,P*"));
	}

}

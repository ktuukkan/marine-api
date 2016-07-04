package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the RMC sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class RMCTest {

	/** Example sentence */
	public static final String EXAMPLE = "$GPRMC,120044.567,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E,A*0B";

	/** Example of legacy format (short by one field) */
	public static final String EXAMPLE_LEGACY = "$GPRMC,183729,A,3907.356,N,12102.482,W,000.0,360.0,080301,015.5,E*6F";

	RMCParser empty;
	RMCParser rmc;
	RMCParser legacy;

	@Before
	public void setUp() {
		try {
			empty = new RMCParser(TalkerId.GP);
			rmc = new RMCParser(EXAMPLE);
			legacy = new RMCParser(EXAMPLE_LEGACY);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(12, empty.getFieldCount());
		assertEquals(11, legacy.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getCourse()} .
	 */
	@Test
	public void testGetCorrectedCourse() {
		double expected = rmc.getCourse() + rmc.getVariation();
		assertEquals(expected, rmc.getCorrectedCourse(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getCourse()} .
	 */
	@Test
	public void testGetCourse() {
		assertEquals(360.0, rmc.getCourse(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getDataStatus()}.
	 */
	@Test
	public void testGetDataStatus() {
		assertEquals(DataStatus.ACTIVE, rmc.getStatus());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getDate()}.
	 */
	@Test
	public void testGetDate() {
		Date expected = new Date(2005, 7, 16);
		Date parsed = rmc.getDate();
		assertEquals(expected, parsed);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getDay()}.
	 */
	@Test
	public void testGetDay() {
		assertEquals(16, rmc.getDate().getDay());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getDirectionOfVariation()}
	 * .
	 */
	@Test
	public void testGetDirectionOfVariation() {
		assertTrue(rmc.getVariation() < 0);
		assertEquals(CompassPoint.EAST, rmc.getDirectionOfVariation());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getMode()}.
	 */
	@Test
	public void testGetFaaMode() {
		assertEquals(FaaMode.AUTOMATIC, rmc.getMode());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getVariation()} .
	 */
	@Test
	public void testGetMagneticVariation() {
		assertEquals(-6.1, rmc.getVariation(), 0.001);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getMonth()}
	 * .
	 */
	@Test
	public void testGetMonth() {
		assertEquals(7, rmc.getDate().getMonth());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getPosition()}.
	 */
	@Test
	public void testGetPosition() {
		final double lat = 60 + (11.552 / 60);
		final double lon = 25 + (1.941 / 60);

		Position p = rmc.getPosition();
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(lon, p.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, p.getLatitudeHemisphere());
		assertEquals(CompassPoint.EAST, p.getLongitudeHemisphere());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getSpeed()}
	 * .
	 */
	@Test
	public void testGetSpeed() {
		assertEquals(0.0, rmc.getSpeed(), 0.001);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getTime()}.
	 */
	@Test
	public void testGetTime() {
		Time t = rmc.getTime();
		assertNotNull(t);
		assertEquals(12, t.getHour());
		assertEquals(0, t.getMinutes());
		assertEquals(44.567, t.getSeconds(), 0.001);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getYear()}.
	 */
	@Test
	public void testGetYear() {
		assertEquals(2005, rmc.getDate().getYear());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#setCourse(double)} .
	 */
	@Test
	public void testSetCourse() {
		final double cog = 90.55555;
		rmc.setCourse(cog);
		assertTrue(rmc.toString().contains(",090.6,"));
		assertEquals(cog, rmc.getCourse(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#setDataStatus(DataStatus)}.
	 */
	@Test
	public void testSetDataStatus() {
		rmc.setStatus(DataStatus.ACTIVE);
		assertEquals(DataStatus.ACTIVE, rmc.getStatus());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.ZDAParser#getTime()}.
	 */
	@Test
	public void testSetDate() {
		rmc.setDate(new Date(2010, 6, 9));
		assertTrue(rmc.toString().contains(",360.0,090610,006.1,"));
		rmc.setDate(new Date(2010, 11, 12));
		assertTrue(rmc.toString().contains(",360.0,121110,006.1,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getDirectionOfVariation()}
	 * .
	 */
	@Test
	public void testSetDirectionOfVariation() {
		rmc.setDirectionOfVariation(CompassPoint.WEST);
		assertEquals(CompassPoint.WEST, rmc.getDirectionOfVariation());
		rmc.setDirectionOfVariation(CompassPoint.EAST);
		assertEquals(CompassPoint.EAST, rmc.getDirectionOfVariation());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#getDirectionOfVariation()}
	 * .
	 */
	@Test
	public void testSetDirectionOfVariationWithInvalidDirection() {
		try {
			rmc.setDirectionOfVariation(CompassPoint.NORTH);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#setFaaMode()}.
	 */
	@Test
	public void testSetFaaMode() {
		rmc.setMode(FaaMode.SIMULATED);
		assertEquals(FaaMode.SIMULATED, rmc.getMode());
		rmc.setMode(FaaMode.ESTIMATED);
		assertEquals(FaaMode.ESTIMATED, rmc.getMode());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#setFaaMode()}.
	 */
	@Test
	public void testSetFaaModeWhenOmitted() {
		RMCParser parser = new RMCParser("$GPRMC,120044.567,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E");
		parser.setMode(FaaMode.SIMULATED);
		assertEquals(FaaMode.SIMULATED, parser.getMode());
		parser.setMode(FaaMode.ESTIMATED);
		assertEquals(FaaMode.ESTIMATED, parser.getMode());
	}
	
	@Test
	public void testSetPosition() {
		final double lat = 61 + (1.111 / 60);
		final double lon = 27 + (7.777 / 60);
		Position p = new Position(lat, lon);
		rmc.setPosition(p);

		String str = rmc.toString();
		Position wp = rmc.getPosition();

		assertTrue(str.contains(",6101.111,N,02707.777,E,"));
		assertNotNull(wp);
		assertEquals(lat, wp.getLatitude(), 0.0000001);
		assertEquals(lon, wp.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, wp.getLatitudeHemisphere());
		assertEquals(CompassPoint.EAST, wp.getLongitudeHemisphere());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#setSpeed(double)} .
	 */
	@Test
	public void testSetSpeed() {
		final double sog = 35.23456;
		rmc.setSpeed(sog);
		assertTrue(rmc.toString().contains(",35.2,"));
		assertEquals(sog, rmc.getSpeed(), 0.1);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.RMCParser#getTime()}.
	 */
	@Test
	public void testSetTime() {
		Time t = new Time(1, 2, 3.456);
		rmc.setTime(t);
		assertTrue(rmc.toString().contains("$GPRMC,010203.456,A,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.RMCParser#setVariation(double)} .
	 */
	@Test
	public void testSetVariation() {
		final double var = 1.55555;
		rmc.setVariation(var);
		rmc.setDirectionOfVariation(CompassPoint.WEST);
		assertTrue(rmc.toString().contains(",001.6,W,"));
		assertEquals(var, rmc.getVariation(), 0.1);
		assertEquals(CompassPoint.WEST, rmc.getDirectionOfVariation());
	}
}

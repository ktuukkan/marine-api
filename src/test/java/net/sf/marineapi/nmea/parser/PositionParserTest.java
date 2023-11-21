/*
 * PositionParserTest.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 *
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Position;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for PositionParser class.
 *
 * @author Kimmo Tuukkanen
 */
public class PositionParserTest {

	private PositionParser instance;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		instance = new PositionParser(GLLTest.EXAMPLE, SentenceId.GLL) {
		};
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#parseHemisphereLat(int)}
	 * .
	 */
	@Test
	public void testParseHemisphereLat() {
		assertEquals(CompassPoint.NORTH, instance.parseHemisphereLat(1));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#parseHemisphereLon(int)}
	 * .
	 */
	@Test
	public void testParseHemisphereLon() {
		assertEquals(CompassPoint.EAST, instance.parseHemisphereLon(3));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#parseDegrees(int)}.
	 */
	@Test
	public void testParseDegreesWithLatitude() {
		// 6011.552 = 60 deg 11.552 min
		final double lat = 60 + (11.552 / 60);
		assertEquals(lat, instance.parseDegrees(0), 0.000001);
	}

	@Test
	public void testParseDegreesLatitudeWithLeadingZero() {
		// 0611.552 = 6 deg 11.552 min
		final double lat = 6 + (11.552 / 60);
		PositionParser pp = new PositionParser("$GPGLL,0611.552,N,0.0,E", SentenceId.GLL) {};
		assertEquals(lat, pp.parseDegrees(0), 0.000001);
	}

	@Test
	public void testParseDegreesLatitudeWithoutLeadingZero() {
		// 611.552 = 6 deg 11.552 min
		final double lat = 6 + (11.552 / 60);
		PositionParser pp = new PositionParser("$GPGLL,611.552,N,0.0,E", SentenceId.GLL) {};
		assertEquals(lat, pp.parseDegrees(0), 0.000001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#parseDegrees(int)}.
	 */
	@Test
	public void testParseDegreesLongitudeWithLeadingZero() {
		// 02501.941 = 25 deg 1.941 min
		final double lon = 25 + (1.941 / 60);
		assertEquals(lon, instance.parseDegrees(2), 0.000001);
	}

	@Test
	public void testParseDegreesWithoutLeadingZero() {
		// one leading zero omitted
		// 2501.941 = 25 deg 1.941 min
		final double lon = 25 + (01.941 / 60);
		PositionParser pp = new PositionParser("$GPGLL,0.0,N,2501.941,E", SentenceId.GLL) {};
		assertEquals(lon, pp.parseDegrees(2), 0.000001);
	}

	@Test
	public void testParseDegreesWithoutLeadingZeros() {
		// two leading zeros omitted
		// 501.941 = 5 deg 1.941 min
		final double lon = 5 + (1.941 / 60);
		PositionParser pp = new PositionParser("$GPGLL,0.0,N,501.941,E", SentenceId.GLL) {};
		assertEquals(lon, pp.parseDegrees(2), 0.000001);
	}

	@Test
	public void testParseDegreesWithoutFullDegrees() {
		// full degrees omitted
		// 28.844957 = 0 deg 28.844957 min
		final double lon = 0 + (28.844957 / 60);
		PositionParser pp = new PositionParser("$GPGLL,0.0,N,28.844957,E", SentenceId.GLL) {};
		assertEquals(lon, pp.parseDegrees(2), 0.000001);
	}

	@Test
	public void testParseDegreesWithoutFullDegreesAndLeadingZero() {
		// full degrees and a leading zero of minutes omitted
		// 8.844957 = 0 deg 8.844957 min
		final double lon = 0 + (8.844957 / 60);
		PositionParser pp = new PositionParser("$GPGLL,0.0,N,8.844957,E", SentenceId.GLL) {};
		assertEquals(lon, pp.parseDegrees(2), 0.000001);
	}

	@Test
	public void testParseDegreesWithoutFullDegreesAndMinutes() {
		// full degrees and full minutes omitted
		// .844957 = 0 deg 0.844957 min
		final double lon = 0 + (0.844957 / 60);
		PositionParser pp = new PositionParser("$GPGLL,0.0,N,.844957,E", SentenceId.GLL) {};
		assertEquals(lon, pp.parseDegrees(2), 0.000001);
	}

	@Test
	public void testParseDegreesWithZeroInt() {
		// 0 = 0 deg 0 min
		PositionParser pp = new PositionParser("$GPGLL,0,N,0,E", SentenceId.GLL) {};
		assertEquals(0, pp.parseDegrees(2), 0.000001);
	}

	@Test
	public void testParseDegreesWithZeroDecimal() {
		// 0.0 = 0 deg 0 min
		PositionParser pp = new PositionParser("$GPGLL,0.0,N,0.0,E", SentenceId.GLL) {};
		assertEquals(0, pp.parseDegrees(2), 0.000001);
	}

    /**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#setLatHemisphere(int, net.sf.marineapi.nmea.util.CompassPoint)}
	 * .
	 */
	@Test
	public void testSetLatHemisphere() {
		instance.setLatHemisphere(1, CompassPoint.SOUTH);
		assertTrue(instance.toString().contains(",S,"));
		assertEquals(CompassPoint.SOUTH, instance.parseHemisphereLat(1));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#setLatitude(int, double)}
	 * .
	 */
	@Test
	public void testSetLatitude() {
		// 2501.941
		final double lat = 25 + (01.941 / 60);
		instance.setLatitude(0, lat);
		assertTrue(instance.toString().contains(",02501.941"));
		assertEquals(lat, instance.parseDegrees(0), 0.000001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#setLongitude(int, double)}
	 * .
	 */
	@Test
	public void testSetLongitude() {
		// 02801.941
		final double lon = 28 + (01.941 / 60);
		instance.setLongitude(2, lon);
		assertTrue(instance.toString().contains(",02801.941"));
		assertEquals(lon, instance.parseDegrees(2), 0.000001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#setLonHemisphere(int, net.sf.marineapi.nmea.util.CompassPoint)}
	 * .
	 */
	@Test
	public void testSetLonHemisphere() {
		instance.setLonHemisphere(3, CompassPoint.WEST);
		assertTrue(instance.toString().contains(",W,"));
		assertEquals(CompassPoint.WEST, instance.parseHemisphereLon(3));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#setPositionValues(Position, int, int, int, int)
	 */
	@Test
	public void testSetPositionValuesNE() {

		final double lat = 60 + (11.552 / 60);
		final double lon = 25 + (1.941 / 60);
		Position p2 = new Position(lat, lon);
		instance.setPositionValues(p2, 0, 1, 2, 3);

		final String s2 = instance.toString();
		final Position p = instance.parsePosition(0, 1, 2, 3);

		assertTrue(s2.contains(",6011.552,N,"));
		assertTrue(s2.contains(",02501.941,E,"));
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(lon, p.getLongitude(), 0.0000001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#setPositionValues(Position, int, int, int, int)
	 */
	@Test
	public void testSetPositionValuesSW() {

		final double lat = -60 - (11.552 / 60);
		final double lon = -25 - (1.941 / 60);
		Position p2 = new Position(lat, lon);
		instance.setPositionValues(p2, 0, 1, 2, 3);

		final String s2 = instance.toString();
		final Position p = instance.parsePosition(0, 1, 2, 3);

		assertTrue(s2.contains(",6011.552,S,"));
		assertTrue(s2.contains(",02501.941,W,"));
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(lon, p.getLongitude(), 0.0000001);
	}
}

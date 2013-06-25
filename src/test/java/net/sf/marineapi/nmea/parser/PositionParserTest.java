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
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.CompassPoint;

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
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#parseLatitude(int)}.
	 */
	@Test
	public void testParseLatitude() {
		// 6011.552
		final double lat = 60 + (11.552 / 60);
		assertEquals(lat, instance.parseLatitude(0), 0.000001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.PositionParser#parseLongitude(int)}.
	 */
	@Test
	public void testParseLongitude() {
		// 02501.941
		final double lat = 25 + (01.941 / 60);
		assertEquals(lat, instance.parseLongitude(2), 0.000001);
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
		assertEquals(lat, instance.parseLatitude(0), 0.000001);
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
		assertEquals(lon, instance.parseLongitude(2), 0.000001);
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

}

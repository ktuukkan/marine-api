/* 
 * VHWTest.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class VHWTest {

	public static final String EXAMPLE = "$VWVHW,000.0,T,001.5,M,1.0,N,1.85,K";

	private VHWParser vhw;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		vhw = new VHWParser(EXAMPLE);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#VHWParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testConstructorString() {
		assertTrue(vhw.getTalkerId() == TalkerId.VW);
		assertTrue(SentenceId.valueOf(vhw.getSentenceId()) == SentenceId.VHW);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#VHWParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testConstructorTalkerId() {
		VHWParser empty = new VHWParser(TalkerId.II);
		assertEquals(8, empty.getFieldCount());
		assertTrue('T' == empty.getCharValue(1));
		assertTrue('M' == empty.getCharValue(3));
		assertTrue('N' == empty.getCharValue(5));
		assertTrue('K' == empty.getCharValue(7));
		assertEquals("VHW", empty.getSentenceId());
		assertTrue(empty.getTalkerId() == TalkerId.II);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#getHeading()}.
	 */
	@Test
	public void testGetHeading() {
		assertEquals(0.0, vhw.getHeading(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#getMagneticHeading()}.
	 */
	@Test
	public void testGetMagneticHeading() {
		assertEquals(1.5, vhw.getMagneticHeading(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#getSpeedKmh()}.
	 */
	@Test
	public void testGetSpeedKilometres() {
		assertEquals(1.85, vhw.getSpeedKmh(), 0.01);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#getSpeedKnots()}.
	 */
	@Test
	public void testGetSpeedKnots() {
		assertEquals(1.0, vhw.getSpeedKnots(), 0.1);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.VHWParser#isTrue()}.
	 */
	@Test
	public void testIsTrue() {
		// should always return true
		assertTrue(vhw.isTrue());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#setHeading(double)}.
	 */
	@Test
	public void testSetHeading() {
		vhw.setHeading(90.456);
		assertEquals(90.5, vhw.getHeading(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#setMagneticHeading()}.
	 */
	@Test
	public void testSetMagneticHeading() {
		vhw.setMagneticHeading(123.4567);
		assertEquals(123.5, vhw.getMagneticHeading(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#setSpeedKilometres()}.
	 */
	@Test
	public void testSetSpeedKilometres() {
		vhw.setSpeedKmh(5.5555);
		assertEquals(5.6, vhw.getSpeedKmh(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VHWParser#setSpeedKnots()}.
	 */
	@Test
	public void testSetSpeedKnots() {
		vhw.setSpeedKnots(12.155);
		assertEquals(12.2, vhw.getSpeedKnots(), 0.1);
	}

}

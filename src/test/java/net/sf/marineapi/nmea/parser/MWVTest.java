/* 
 * MWVTest.java
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.MWVSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Units;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class MWVTest {

	public static final String EXAMPLE = "$IIMWV,125.1,T,5.5,M,A";

	private MWVSentence mwv;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mwv = new MWVParser(EXAMPLE);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#MWVParser(net.sf.marineapi.nmea.sentence.TalkerId)}
	 * .
	 */
	@Test
	public void testMWVParserTalkerId() {
		MWVParser mwvp = new MWVParser(TalkerId.II);
		assertEquals(TalkerId.II, mwvp.getTalkerId());
		assertEquals(SentenceId.MWV.toString(), mwvp.getSentenceId());
		assertEquals(DataStatus.VOID, mwvp.getStatus());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.MWVParser#getAngle()}
	 * .
	 */
	@Test
	public void testGetAngle() {
		assertEquals(125.1, mwv.getAngle(), 0.1); // "$IIMWV,125.1,T,5.5,A"
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.MWVParser#getSpeed()}
	 * .
	 */
	@Test
	public void testGetSpeed() {
		assertEquals(5.5, mwv.getSpeed(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#getSpeedUnit()}.
	 */
	@Test
	public void testGetSpeedUnit() {
		assertEquals(Units.METER, mwv.getSpeedUnit());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#getStatus()}.
	 */
	@Test
	public void testGetStatus() {
		assertEquals(DataStatus.ACTIVE, mwv.getStatus());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.MWVParser#isTrue()}.
	 */
	@Test
	public void testIsTrue() {
		assertTrue(mwv.isTrue());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setAngle(double)}.
	 */
	@Test
	public void testSetAngle() {
		final double angle = 88.123;
		mwv.setAngle(angle);
		assertEquals(angle, mwv.getAngle(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setAngle(double)}.
	 */
	@Test
	public void testSetNegativeAngle() {
		final double angle = -0.1;
		try {
			mwv.setAngle(angle);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setAngle(double)}.
	 */
	@Test
	public void testSetAngleOutOfRange() {
		final double angle = 360.1;
		try {
			mwv.setAngle(angle);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setSpeed(double)}.
	 */
	@Test
	public void testSetSpeed() {
		final double speed = 7.75;
		mwv.setSpeed(speed);
		assertEquals(speed, mwv.getSpeed(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setSpeed(double)}.
	 */
	@Test
	public void testSetNegativeSpeed() {
		final double speed = -0.01;
		try {
			mwv.setSpeed(speed);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setSpeedUnit(net.sf.marineapi.nmea.util.Units)}
	 * .
	 */
	@Test
	public void testSetSpeedUnit() {
		mwv.setSpeedUnit(Units.KMH);
		assertEquals(Units.KMH, mwv.getSpeedUnit());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setSpeedUnit(net.sf.marineapi.nmea.util.Units)}
	 * .
	 */
	@Test
	public void testSetInvalidSpeedUnit() {
		try {
			mwv.setSpeedUnit(Units.FATHOMS);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setStatus(net.sf.marineapi.nmea.util.DataStatus)}
	 * .
	 */
	@Test
	public void testSetStatus() {
		mwv.setStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, mwv.getStatus());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MWVParser#setTrue(boolean)}.
	 */
	@Test
	public void testSetTrue() {
		assertTrue(mwv.isTrue());
		mwv.setTrue(false);
		assertFalse(mwv.isTrue());
	}

}

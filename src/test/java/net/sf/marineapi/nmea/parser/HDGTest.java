/* 
 * HDGTest.java
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
import net.sf.marineapi.nmea.sentence.HDGSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class HDGTest {

	public static final String EXAMPLE = "$HCHDG,123.4,1.2,E,1.2,W";

	HDGSentence hdg;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		hdg = new HDGParser(EXAMPLE);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.HDGParser(TalkerId)}.
	 */
	@Test
	public void testConstructor() {
		HDGSentence empty = new HDGParser(TalkerId.HC);
		assertEquals(TalkerId.HC, empty.getTalkerId());
		assertEquals(SentenceId.HDG.toString(), empty.getSentenceId());
		try {
			empty.getHeading();
		} catch (DataNotAvailableException e) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.HDTParser#isTrue()}.
	 */
	@Test
	public void testIsTrue() {
		assertFalse(hdg.isTrue());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#HDGParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testHDGParserString() {
		assertTrue(hdg.isValid());
		assertEquals(TalkerId.HC, hdg.getTalkerId());
		assertEquals("HDG", hdg.getSentenceId());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#HDGParser(net.sf.marineapi.nmea.sentence.TalkerId)}
	 * .
	 */
	@Test
	public void testHDGParserTalkerId() {
		HDGParser hdgp = new HDGParser(TalkerId.HC);
		assertTrue(hdgp.isValid());
		assertEquals(TalkerId.HC, hdgp.getTalkerId());
		assertEquals("HDG", hdgp.getSentenceId());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#getDeviation()}.
	 */
	@Test
	public void testGetDeviation() {
		assertEquals(1.2, hdg.getDeviation(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#getHeading()}.
	 */
	@Test
	public void testGetHeading() {
		assertEquals(123.4, hdg.getHeading(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#getVariation()}.
	 */
	@Test
	public void testGetVariation() {
		// 1.2 degrees west -> -1.2
		assertEquals(-1.2, hdg.getVariation(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setDeviation(double)}.
	 */
	@Test
	public void testSetDeviationWest() {
		final double dev = -5.5;
		hdg.setDeviation(dev);
		assertEquals(dev, hdg.getDeviation(), 0.1);
		assertTrue(hdg.toString().contains(",005.5,W,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setDeviation(double)}.
	 */
	@Test
	public void testSetDeviationEast() {
		final double dev = 5.5;
		hdg.setDeviation(dev);
		assertEquals(dev, hdg.getDeviation(), 0.1);
		assertTrue(hdg.toString().contains(",005.5,E,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setDeviation(double)}.
	 */
	@Test
	public void testSetDeviationTooHigh() {
		final double value = 180.000001;
		try {
			hdg.setDeviation(value);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setDeviation(double)}.
	 */
	@Test
	public void testSetDeviationTooLow() {
		final double value = -180.000001;
		try {
			hdg.setHeading(value);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setHeading(double)}.
	 */
	@Test
	public void testSetHeading() {
		final double value = 359.9;
		hdg.setHeading(value);
		assertEquals(value, hdg.getHeading(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setHeading(double)}.
	 */
	@Test
	public void testSetHeadingTooHigh() {
		final double value = 360.000001;
		try {
			hdg.setHeading(value);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setHeading(double)}.
	 */
	@Test
	public void testSetHeadingTooLow() {
		final double value = -0.000001;
		try {
			hdg.setHeading(value);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setVariation(double)}.
	 */
	@Test
	public void testSetVariationEast() {
		final double var = 179.9;
		hdg.setVariation(var);
		assertEquals(var, hdg.getVariation(), 0.1);
		assertTrue(hdg.toString().contains(",179.9,E*"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setVariation(double)}.
	 */
	@Test
	public void testSetVariationWest() {
		final double var = -0.1;
		hdg.setVariation(var);
		assertEquals(var, hdg.getVariation(), 0.1);
		assertTrue(hdg.toString().contains(",000.1,W*"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setVariation(double)}.
	 */
	@Test
	public void testSetVariationTooHigh() {
		final double var = 180.00001;
		try {
			hdg.setVariation(var);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.HDGParser#setVariation(double)}.
	 */
	@Test
	public void testSetVariationTooLow() {
		final double var = -180.00001;
		try {
			hdg.setVariation(var);
			fail("Did not throw exception");
		} catch (IllegalArgumentException iae) {
			// pass
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}

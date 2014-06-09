package net.sf.marineapi.nmea.sentence;

/* 
 * TalkerIdTest.java
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class TalkerIdTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.sentence.TalkerId#parse(java.lang.String)}.
	 */
	@Test
	public void testParse() {
		assertEquals(TalkerId.GP, TalkerId.parse("$GPGLL,,,,,,,"));
		assertEquals(TalkerId.GL, TalkerId.parse("$GLGSV,,,,,,,"));
		assertEquals(TalkerId.GN, TalkerId.parse("$GNGSV,,,,,,,"));
		assertEquals(TalkerId.II, TalkerId.parse("$IIDPT,,,,,,,"));
	}

	@Test
	public void testParseProprietary() {
		assertEquals(TalkerId.P, TalkerId.parse("$PRWIILOG,GGA,A,T,1,0"));
	}

	@Test
	public void testParseAIS() {
		assertEquals(TalkerId.AI, TalkerId.parse("!AIVDM,,,,,,,"));	
		assertEquals(TalkerId.AB, TalkerId.parse("!ABVDM,,,,,,,"));	
		assertEquals(TalkerId.BS, TalkerId.parse("!BSVDM,,,,,,,"));	
	}

	@Test
	public void testParseUnknown() {
		try {
			TalkerId.parse("$XXXXX,,,,,,");
			fail("Did not throw exception");
		} catch (Exception e) {
			// pass
		}		
	}

}

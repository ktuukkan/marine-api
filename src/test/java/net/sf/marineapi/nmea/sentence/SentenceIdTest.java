/* 
 * SentenceIdTest.java
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
package net.sf.marineapi.nmea.sentence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class SentenceIdTest {

	@Test
	public void testParseKnownId() {
		SentenceId s = SentenceId.parse("$GPGLL,,,,,,,");
		assertEquals(SentenceId.GLL, s);
	}

	@Test
	public void testParseUnknownId() {
		try {
			SentenceId.parse("$ABCDE,,,,,,");
			fail("Did not throw exception");
		} catch (Exception e) {
			// pass
		}
	}

	@Test
	public void testParseStrStandardId() {
		String s = SentenceId.parseStr("$GPGLL,,,,,,,");
		assertEquals("GLL", s);
	}

	@Test
	public void testParseStrNormalLengthProprietaryId() {
		String s = SentenceId.parseStr("$PGRMZ,,,,,,,");
		assertEquals("GRMZ", s);
	}

	@Test
	public void testParseStrShortProprietaryId() {
		String s = SentenceId.parseStr("$PBVE,,,,,,,");
		assertEquals("BVE", s);
	}

	@Test
	public void testParseStrShortestPossibleProprietaryId() {
		String s = SentenceId.parseStr("$PAB,,,,,,,");
		assertEquals("AB", s);
	}

	@Test
	public void testParseStrLongestPossibleProprietaryId() {
		String s = SentenceId.parseStr("$PABCDEFGHI,,,,,,,");
		assertEquals("ABCDEFGHI", s);
	}
}

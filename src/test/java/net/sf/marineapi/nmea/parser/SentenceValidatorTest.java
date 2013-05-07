/* 
 * SentenceValidatorTest.java
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.Checksum;
import net.sf.marineapi.nmea.sentence.SentenceValidator;

import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class SentenceValidatorTest {

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.sentence.SentenceValidator#isValid(java.lang.String)}
	 * .
	 */
	@Test
	public void testIsValid() {

		// "normal"
		String a = "$ABCDE,1,2,3,4,5,6,7,8,9";
		assertTrue(SentenceValidator.isValid(a));
		assertTrue(SentenceValidator.isValid(Checksum.add(a)));

		// '!' as begin char
		String b = "!ABCDE,1,2,3,4,5,6,7,8,9";
		assertTrue(SentenceValidator.isValid(b));
		assertTrue(SentenceValidator.isValid(Checksum.add(b)));

		String c = "$ABCDE,1,TWO,three,FOUR?,5,6.0,-7.0,Eigth-8,N1N3,#T3n";
		assertTrue(SentenceValidator.isValid(c));
		assertTrue(SentenceValidator.isValid(Checksum.add(c)));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.sentence.SentenceValidator#isValid(java.lang.String)}
	 * .
	 */
	@Test
	public void testIsValidWithInvalidInput() {
		// invalid checksum, otherwise valid
		assertFalse(SentenceValidator.isValid("$ABCDE,1,2,3,4,5,6,7,8,9*00"));
		// something weird
		assertFalse(SentenceValidator.isValid(null));
		assertFalse(SentenceValidator.isValid(""));
		assertFalse(SentenceValidator.isValid("$"));
		assertFalse(SentenceValidator.isValid("*"));
		assertFalse(SentenceValidator.isValid("$,*"));
		assertFalse(SentenceValidator.isValid("$GPGSV*"));
		assertFalse(SentenceValidator.isValid("foobar"));
		assertFalse(SentenceValidator.isValid("GPGGA,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GpGGA,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGGa,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGG#,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGG,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGGAA,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGGA,1,2,3,4,5,6,7,8,9*00"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.sentence.SentenceValidator#isValid(java.lang.String)}
	 * .
	 */
	@Test
	public void testIsValidWithValidInput() {
		assertTrue(SentenceValidator.isValid(BODTest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(GGATest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(GLLTest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(GSATest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(GSVTest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(RMBTest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(RMCTest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(RTETest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(VTGTest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(WPLTest.EXAMPLE));
		assertTrue(SentenceValidator.isValid(ZDATest.EXAMPLE));
	}

}

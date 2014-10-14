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
package net.sf.marineapi.nmea.sentence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.GLLTest;
import net.sf.marineapi.nmea.parser.GSATest;
import net.sf.marineapi.nmea.parser.GSVTest;
import net.sf.marineapi.nmea.parser.RMBTest;
import net.sf.marineapi.nmea.parser.RMCTest;
import net.sf.marineapi.nmea.parser.RTETest;
import net.sf.marineapi.nmea.parser.VTGTest;
import net.sf.marineapi.nmea.parser.WPLTest;
import net.sf.marineapi.nmea.parser.ZDATest;

import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class SentenceValidatorTest {

	@Test
	public void testIsValid() {

		// "normal"
		String a = "$ABCDE,1,2,3,4,5,6,7,8,9";
		assertTrue(SentenceValidator.isValid(a));
		assertTrue(SentenceValidator.isValid(Checksum.add(a)));

		// empty sentence, single field
		String b = "$ABCDE,";
		assertTrue(SentenceValidator.isValid(b));
		assertTrue(SentenceValidator.isValid(Checksum.add(b)));

		// empty sentence, multiple fields
		String c = "$ABCDE,,,,,,";
		assertTrue(SentenceValidator.isValid(c));
		assertTrue(SentenceValidator.isValid(Checksum.add(c)));

		String d = "$ABCDE,1,TWO,three,FOUR?,5,6.0,-7.0,Eigth-8,N1N3,#T3n";
		assertTrue(SentenceValidator.isValid(d));
		assertTrue(SentenceValidator.isValid(Checksum.add(d)));

		// '!' begin char
		String e = "!ABCDE,1,2,3,4,5,6,7,8,9";
		assertTrue(SentenceValidator.isValid(e));
		assertTrue(SentenceValidator.isValid(Checksum.add(e)));
	}

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
		assertFalse(SentenceValidator.isValid("$gpgga,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("GPGGA,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GpGGA,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGGa,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGG#,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$AB,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$ABCDEFGHIJK,1,2,3,4,5,6,7,8,9"));
		assertFalse(SentenceValidator.isValid("$GPGGA,1,2,3,4,5,6,7,8,9*00"));
	}

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

	@Test
	public void testIsValidWithLongProprietaryId() {
		String str = "$PRWIILOG,GGA,A,T,1,0";
		assertTrue(SentenceValidator.isSentence(str));
		assertTrue(SentenceValidator.isValid(str));
	}

	@Test
	public void testIsValidWithShortProprietaryId() {
		String str = "$PUBX,03,GT{,ID,s,AZM,EL,SN,LK},";
		assertTrue(SentenceValidator.isSentence(str));
		assertTrue(SentenceValidator.isValid(str));
	}

	@Test
	public void testIsSentenceWithChecksum() {
		
		String nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*20";
		assertTrue(SentenceValidator.isSentence(nmea));
		
		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*20xy";
		assertFalse(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*201";
		assertFalse(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*2";
		assertFalse(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*";
		assertFalse(SentenceValidator.isSentence(nmea));
	}
	
	@Test
	public void testIsSentenceWithoutChecksum() {
		String nmea = "$GPRMC,142312.000,V,,,,,,,080514,,";
		assertTrue(SentenceValidator.isSentence(nmea));
	}
	
	@Test
	public void testIsSentenceWithChecksumAndNewline() {
		
		String nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n";
		assertTrue(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*20\n\r";
		assertTrue(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*20\r";
		assertTrue(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*20\n";
		assertTrue(SentenceValidator.isSentence(nmea));
		
		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n\r\n";
		assertFalse(SentenceValidator.isSentence(nmea));
	}	
	
	@Test
	public void testIsSentenceNoChecksumWithNewline() {
		
		String nmea = "$GPRMC,142312.000,V,,,,,,,080514,,\r\n";
		assertTrue(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,\n\r";
		assertTrue(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,\r";
		assertTrue(SentenceValidator.isSentence(nmea));

		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,\n";
		assertTrue(SentenceValidator.isSentence(nmea));
		
		nmea = "$GPRMC,142312.000,V,,,,,,,080514,,\r\n\r\n";
		assertFalse(SentenceValidator.isSentence(nmea));
	}
}

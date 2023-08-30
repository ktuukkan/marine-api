/*
 * ChecksumTest.java
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
import net.sf.marineapi.nmea.sentence.Checksum;

import org.junit.Test;

/**
 * Tests the Checksum class.
 *
 * @author Kimmo Tuukkanen
 */
public class ChecksumTest {

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.sentence.Checksum#add(java.lang.String)}.
	 */
	@Test
	public void testAdd() {
		String a = "$GPGLL,6011.552,N,02501.941,E,120045,A";
		String b = "$GPGLL,6011.552,N,02501.941,E,120045,A*";
		String c = "$GPGLL,6011.552,N,02501.941,E,120045,A*00";
		final String expected = a.concat("*26");
		assertEquals(expected, Checksum.add(a));
		assertEquals(expected, Checksum.add(b));
		assertEquals(expected, Checksum.add(c));
	}

	@Test
	public void testAddAIS() {
		assertEquals("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B",
				Checksum.add("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0"));

		assertEquals("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D",
				Checksum.add("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2"));

		assertEquals("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0*2C",
				Checksum.add("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0"));

		assertEquals("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2*03",
				Checksum.add("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2"));

		assertEquals("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0*51",
				Checksum.add("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0"));

		assertEquals("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58",
				Checksum.add("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.sentence.Checksum#calculate(java.lang.String)}
	 * .
	 */
	@Test
	public void testCalculate() {
		assertEquals("1D", Checksum.calculate(BODTest.EXAMPLE));
		assertEquals("63", Checksum.calculate(GGATest.EXAMPLE));
		assertEquals("26", Checksum.calculate(GLLTest.EXAMPLE));
		assertEquals("74", Checksum.calculate(RMCTest.EXAMPLE));
		assertEquals("3D", Checksum.calculate(GSATest.EXAMPLE));
		assertEquals("73", Checksum.calculate(GSVTest.EXAMPLE));
		assertEquals("58", Checksum.calculate(RMBTest.EXAMPLE));
		assertEquals("25", Checksum.calculate(RTETest.EXAMPLE));
	}

	@Test
	public void testDelimiterIndex() {
		assertEquals(13, Checksum.index("$GPGGA,,,,,,,"));
		assertEquals(13, Checksum.index("$GPGGA,,,,,,,*00"));
	}

	@Test
	public void testCalculateAIS() {
		assertEquals("7B", Checksum.calculate("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0"));
		assertEquals("7B", Checksum.calculate("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B"));

		assertEquals("5D", Checksum.calculate("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2"));
		assertEquals("5D", Checksum.calculate("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D"));

		assertEquals("2C", Checksum.calculate("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0"));
		assertEquals("2C", Checksum.calculate("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0*2C"));

		assertEquals("03", Checksum.calculate("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2"));
		assertEquals("03", Checksum.calculate("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2*03"));

		assertEquals("51", Checksum.calculate("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0"));
		assertEquals("51", Checksum.calculate("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0*51"));

		assertEquals("58", Checksum.calculate("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0"));
		assertEquals("58", Checksum.calculate("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58"));
	}

	@Test
	public void testCalculateIssue134() {
		assertEquals("3F", Checksum.calculate("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0"));
		assertEquals("3F", Checksum.calculate("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0*3F"));

		assertEquals("4F", Checksum.calculate("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0"));
		assertEquals("4F", Checksum.calculate("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0*4F"));

		assertEquals("6D", Checksum.calculate("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0"));
		assertEquals("6D", Checksum.calculate("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0*6D"));
	}

	@Test
	public void testAddIssue134() {
		assertEquals("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0*3F",
				Checksum.add("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0"));

		assertEquals("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0*4F",
				Checksum.add("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0"));

		assertEquals("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0*6D",
				Checksum.add("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0"));
	}
}

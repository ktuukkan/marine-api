/* 
 * ChecksumTest.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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
 * @version $Revision$
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

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.Checksum#calculate(java.lang.String)}
     * .
     */
    @Test
    public void testCalculate() {
        assertEquals("1D", Checksum.calculate(BODTest.EXAMPLE));
        assertEquals("79", Checksum.calculate(GGATest.EXAMPLE));
        assertEquals("26", Checksum.calculate(GLLTest.EXAMPLE));
        assertEquals("11", Checksum.calculate(RMCTest.EXAMPLE));
        assertEquals("3D", Checksum.calculate(GSATest.EXAMPLE));
        assertEquals("73", Checksum.calculate(GSVTest.EXAMPLE));
        assertEquals("58", Checksum.calculate(RMBTest.EXAMPLE));
        assertEquals("25", Checksum.calculate(RTETest.EXAMPLE));
    }

}

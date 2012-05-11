/* 
 * SentenceIdTest.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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
package net.sf.marineapi.sentence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.SentenceId;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceIdTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.SentenceId#parse(java.lang.String)}
     * .
     */
    @Test
    public void testParse() {
        SentenceId a = SentenceId.parse("$GPGLL,,,,,,,");
        assertEquals(SentenceId.GLL, a);

        SentenceId b = SentenceId.parse("$IIDPT,,,,,,,");
        assertEquals(SentenceId.DPT, b);

        try {
            SentenceId.parse("$ABCDE,,,,,,");
            fail("Did not throw exception");
        } catch (Exception e) {
            // pass
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.sentence.SentenceId#parseStr(java.lang.String)}
     * .
     */
    @Test
    public void testParseStr() {
        String a = SentenceId.parseStr("$GPGLL,,,,,,,");
        assertEquals("GLL", a);

        String b = SentenceId.parseStr("$IIDPT,,,,,,,");
        assertEquals("DPT", b);

        String c = SentenceId.parseStr("$ABCDE,,,,,,,");
        assertEquals("CDE", c);

        String d = SentenceId.parseStr("$PGRMZ,,,,,,,");
        assertEquals("GRMZ", d);
    }

}

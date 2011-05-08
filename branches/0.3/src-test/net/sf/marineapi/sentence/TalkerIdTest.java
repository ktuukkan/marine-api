package net.sf.marineapi.sentence;
/* 
 * TalkerIdTest.java
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 * @version $Revision$
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
        TalkerId a = TalkerId.parse("$GPGLL,,,,,,,");
        assertEquals(TalkerId.GP, a);

        TalkerId b = TalkerId.parse("$IIDPT,,,,,,,");
        assertEquals(TalkerId.II, b);

        TalkerId c = TalkerId.parse("$PGRMZ,,,,,,,");
        assertEquals(TalkerId.P, c);

        try {
            TalkerId.parse("$ABCDE,,,,,,");
            fail("Did not throw exception");
        } catch (Exception e) {
            // pass
        }
    }

}

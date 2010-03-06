/* 
 * SentenceVTGTest.java
 * Copyright 2010 Kimmo Tuukkanen
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.GpsMode;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the VTG sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceVTGTest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K,A";

    private SentenceVTG vtg;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            vtg = new SentenceVTG(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getTrueCourse()}.
     */
    @Test
    public void testGetTrueCourse() {
        assertTrue(360.0 == vtg.getTrueCourse());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getMagneticCourse()}.
     */
    @Test
    public void testGetMagneticCourse() {
        assertTrue(348.7 == vtg.getMagneticCourse());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getSpeedKnots()}.
     */
    @Test
    public void testGetSpeedKnots() {
        assertTrue(16.89 == vtg.getSpeedKnots());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getSpeedKmh()}.
     */
    @Test
    public void testGetSpeedKmh() {
        assertTrue(31.28 == vtg.getSpeedKmh());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceVTG#getMode()}.
     */
    @Test
    public void testGetMode() {
        assertEquals(GpsMode.AUTOMATIC, vtg.getMode());
    }

}

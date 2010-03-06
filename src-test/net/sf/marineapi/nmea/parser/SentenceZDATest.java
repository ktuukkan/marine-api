/* 
 * SentenceZDATest.java
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

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ZDA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceZDATest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPZDA,032915,07,08,2004,00,00*4D";

    private SentenceZDA zda;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            zda = new SentenceZDA(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getUtcTime()}.
     */
    @Test
    public void testGetUtcTime() {
        assertEquals("032915", zda.getUtcTime());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getUtcHours()}.
     */
    @Test
    public void testGetUtcHours() {
        assertTrue(3 == zda.getUtcHours());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getUtcMinutes()}.
     */
    @Test
    public void testGetUtcMinutes() {
        assertTrue(29 == zda.getUtcMinutes());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getUtcSeconds()}.
     */
    @Test
    public void testGetUtcSeconds() {
        assertTrue(15 == zda.getUtcSeconds());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getUtcDay()}.
     */
    @Test
    public void testGetUtcDay() {
        assertTrue(7 == zda.getUtcDay());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getUtcMonth()}.
     */
    @Test
    public void testGetUtcMonth() {
        assertTrue(8 == zda.getUtcMonth());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getUtcYear()}.
     */
    @Test
    public void testGetUtcYear() {
        assertTrue(2004 == zda.getUtcYear());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getLocalZoneHours()}.
     */
    @Test
    public void testGetLocalZoneHours() {
        assertTrue(0 == zda.getLocalZoneHours());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getLocalZoneMinutes()}.
     */
    @Test
    public void testGetLocalZoneMinutes() {
        assertTrue(0 == zda.getLocalZoneMinutes());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceZDA#getDate()}.
     */
    @Test
    public void testGetDate() {
        GregorianCalendar cal = new GregorianCalendar(2004, 7, 7, 3, 29, 15);
        final Date expected = cal.getTime();
        Date parsed = zda.getDate();

        assertEquals(expected, parsed);
        assertTrue(expected.getTime() == parsed.getTime());
    }

}

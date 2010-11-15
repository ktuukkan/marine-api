/* 
 * DateTest.java
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
package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class DateTest {

    private Date instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        instance = new Date();
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#Date()}.
     */
    @Test
    public void testDate() {
        assertEquals(1970, instance.getYear());
        assertEquals(1, instance.getMonth());
        assertEquals(1, instance.getDay());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.util.Date#Date(int, int, int)}.
     */
    @Test
    public void testDateIntIntInt() {
        Date d = new Date(2010, 6, 15);
        assertEquals(2010, d.getYear());
        assertEquals(6, d.getMonth());
        assertEquals(15, d.getDay());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.util.Date#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsAfterInit() {
        Date d = new Date();
        assertTrue(d.equals(instance));
        Date one = new Date(2010, 6, 15);
        Date two = new Date(2010, 6, 15);
        assertTrue(one.equals(two));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.util.Date#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsItself() {
        assertTrue(instance.equals(instance));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.util.Date#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsWhenChanged() {

        Date d = new Date();
        d.setDay(15);
        assertFalse(d.equals(instance));

        instance.setDay(15);
        assertTrue(d.equals(instance));

        d.setMonth(6);
        assertFalse(d.equals(instance));

        instance.setMonth(6);
        assertTrue(d.equals(instance));

        d.setYear(10);
        assertFalse(d.equals(instance));

        instance.setYear(10);
        assertTrue(d.equals(instance));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.util.Date#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsWrongType() {
        Object str = new String("foobar");
        Object dbl = new Double(123);
        assertFalse(instance.equals(str));
        assertFalse(instance.equals(dbl));
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#getDay()}.
     */
    @Test
    public void testGetDay() {
        assertEquals(1, instance.getDay());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#getMonth()}.
     */
    @Test
    public void testGetMonth() {
        assertEquals(1, instance.getMonth());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#getYear()}.
     */
    @Test
    public void testGetYear() {
        assertEquals(1970, instance.getYear());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setDay(int)}.
     */
    @Test
    public void testSetDay() {
        final int day = 10;
        instance.setDay(day);
        assertEquals(day, instance.getDay());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setDay(int)}.
     */
    @Test
    public void testSetDayOutOfBounds() {
        int day = 0;
        try {
            instance.setDay(day);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            // pass
        }

        day = 32;
        try {
            instance.setDay(day);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setMonth(int)}.
     */
    @Test
    public void testSetMonth() {
        final int month = 10;
        instance.setMonth(month);
        assertEquals(month, instance.getMonth());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setMonth(int)}.
     */
    @Test
    public void testSetMonthOutOfBounds() {
        int month = 0;
        try {
            instance.setMonth(month);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            // pass
        }

        month = 32;
        try {
            instance.setMonth(month);
            fail("Did not throw exception");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setYear(int)}.
     */
    @Test
    public void testSetYearFiveDigits() {
        try {
            instance.setYear(10000);
            fail("Did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setYear(int)}.
     */
    @Test
    public void testSetYearFourDigit() {
        for (int year = 1000; year < 10000; year++) {
            instance.setYear(year);
            assertEquals(year, instance.getYear());
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setYear(int)}.
     */
    @Test
    public void testSetYearNegative() {
        try {
            instance.setYear(-1);
            fail("Did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setYear(int)}.
     */
    @Test
    public void testSetYearThreeDigits() {
        try {
            instance.setYear(100);
            fail("Did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }

        try {
            instance.setYear(999);
            fail("Did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.util.Date#setYear(int)}.
     */
    @Test
    public void testSetYearTwoDigit() {
        int century = 2000;
        for (int year = 0; year < 100; year++) {
            instance.setYear(year);
            assertEquals((century + year), instance.getYear());
            if (year == Date.PIVOT_YEAR) {
                century = 1900;
            }
        }
    }

}

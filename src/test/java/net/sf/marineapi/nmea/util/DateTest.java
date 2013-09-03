/*
 * DateTest.java
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
package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class DateTest {

	private Date instance;

	private GregorianCalendar cal;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		instance = new Date();
		cal = new GregorianCalendar();
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Date#Date()}.
	 */
	@Test
	public void testConstructor() {
		assertEquals(cal.get(Calendar.YEAR), instance.getYear());
		assertEquals(cal.get(Calendar.MONTH) + 1, instance.getMonth());
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), instance.getDay());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Date#Date(int, int, int)}.
	 */
	@Test
	public void testConstructorWithValues() {
		Date d = new Date(2010, 6, 15);
		assertEquals(2010, d.getYear());
		assertEquals(6, d.getMonth());
		assertEquals(15, d.getDay());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Date#Date(String)}.
	 */
	@Test
	public void testConstructorWithString() {
		Date d = new Date("150610");
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

		final int y = 2011;
		final int m = 6;
		final int d = 15;
		final Date a = new Date(y, m, d);
		final Date b = new Date(y, m, d);

		a.setDay(b.getDay() - 1);
		assertFalse(a.equals(b));

		b.setDay(a.getDay());
		assertTrue(a.equals(b));

		a.setMonth(b.getMonth() - 1);
		assertFalse(a.equals(b));

		b.setMonth(a.getMonth());
		assertTrue(a.equals(b));

		a.setYear(b.getYear() - 1);
		assertFalse(a.equals(b));

		b.setYear(a.getYear());
		assertTrue(a.equals(b));
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
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), instance.getDay());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Date#getMonth()}.
	 */
	@Test
	public void testGetMonth() {
		assertEquals(cal.get(Calendar.MONTH) + 1, instance.getMonth());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Date#getYear()}.
	 */
	@Test
	public void testGetYear() {
		assertEquals(cal.get(Calendar.YEAR), instance.getYear());
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

	@Test
	public void testToStringTwoDigitYear() {
		Date d = new Date(13, 9, 2);
		assertEquals("020913", d.toString());
	}

	@Test
	public void testToStringFourDigitYear() {
		Date d = new Date(2013, 9, 2);
		assertEquals("020913", d.toString());
	}

	@Test
	public void testToISO8601TwoDigitYear() {
		Date d = new Date(13, 9, 2);
		assertEquals("2013-09-02", d.toISO8601());
	}

	@Test
	public void testToISO8601FourDigitYear() {
		Date d = new Date(2013, 9, 2);
		assertEquals("2013-09-02", d.toISO8601());
	}

	@Test
	public void testToISO8601WithTime() {
		Date d = new Date(2013, 9, 2);
		Time t = new Time(2, 7, 9);
		assertEquals("2013-09-02T02:07:09+00:00", d.toISO8601(t));
	}

	@Test
	public void testToISO8601WithTimeAndZeroZone() {
		Date d = new Date(2013, 9, 2);
		Time t = new Time(2, 7, 9, 0, 0);
		assertEquals("2013-09-02T02:07:09+00:00", d.toISO8601(t));
	}

	@Test
	public void testToISO8601WithTimeAndPositiveOffset() {
		Date d = new Date(2013, 9, 2);
		Time t = new Time(2, 7, 9, 2, 0);
		assertEquals("2013-09-02T02:07:09+02:00", d.toISO8601(t));
	}

	@Test
	public void testToISO8601WithTimeAndNegativeOffset() {
		Date d = new Date(2013, 9, 2);
		Time t = new Time(2, 7, 9, -2, 5);
		assertEquals("2013-09-02T02:07:09-02:05", d.toISO8601(t));
	}
}

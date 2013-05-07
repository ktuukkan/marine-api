/* 
 * TimeTest.java
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
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class TimeTest {

	private Time time;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		time = new Time(1, 2, 3.4);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#getHour()}.
	 */
	@Test
	public void testGetHour() {
		assertEquals(1, time.getHour());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#getMilliseconds()}
	 * .
	 */
	@Test
	public void testGetMilliseconds() {
		time.setHour(12);
		time.setMinutes(1);
		time.setSeconds(1.0);
		assertEquals(43261000, time.getMilliseconds());
		time.setHour(18);
		time.setMinutes(1);
		time.setSeconds(1.0);
		assertEquals(64861000, time.getMilliseconds());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#getMinutes()}.
	 */
	@Test
	public void testGetMinutes() {
		assertEquals(2, time.getMinutes());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#getSeconds()}.
	 */
	@Test
	public void testGetSeconds() {
		assertEquals(3.4, time.getSeconds(), 0.1);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#setHour(int)}.
	 */
	@Test
	public void testSetHour() {
		time.setHour(12);
		assertEquals(12, time.getHour());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#setHour(int)}.
	 */
	@Test
	public void testSetInvalidHour() {
		try {
			time.setHour(60);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#setMinutes(int)}.
	 */
	@Test
	public void testSetInvalidMinutes() {
		try {
			time.setMinutes(60);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#setSeconds(double)}.
	 */
	@Test
	public void testSetInvalidSeconds() {
		try {
			time.setSeconds(60.0);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#setMinutes(int)}.
	 */
	@Test
	public void testSetMinutes() {
		time.setMinutes(30);
		assertEquals(30, time.getMinutes());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#setHour(int)}.
	 */
	@Test
	public void testSetNegativeHour() {
		try {
			time.setHour(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#setMinutes(int)}.
	 */
	@Test
	public void testSetNegativeMinutes() {
		try {
			time.setMinutes(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#setSeconds(double)}.
	 */
	@Test
	public void testSetNegativeSeconds() {
		try {
			time.setSeconds(-0.01);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time#setSeconds(int)}.
	 */
	@Test
	public void testSetSeconds() {
		time.setSeconds(45.1);
		assertEquals(45.1, time.getSeconds(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#setTime(java.util.Date)}.
	 */
	@Test
	public void testSetTime() {
		Date now = new Date();
		time.setTime(now);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(now);
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), time.getHour());
		assertEquals(cal.get(Calendar.MINUTE), time.getMinutes());
		assertEquals(cal.get(Calendar.SECOND), time.getSeconds(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#toDate(java.util.Date)}.
	 */
	@Test
	public void testToDate() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Date date = time.toDate(cal.getTime());
		Calendar result = new GregorianCalendar();
		result.setTime(date);

		assertEquals(time.getHour(), result.get(Calendar.HOUR_OF_DAY));
		assertEquals(time.getMinutes(), result.get(Calendar.MINUTE));
		assertEquals((int) time.getSeconds(), result.get(Calendar.SECOND));
		assertEquals(2010, result.get(Calendar.YEAR));
		assertEquals(Calendar.JANUARY, result.get(Calendar.MONTH));
		assertEquals(1, result.get(Calendar.DAY_OF_YEAR));
	}
}

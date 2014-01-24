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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
		time = new Time(1, 2, 3.4, 0, 0);
	}

	/**
	 * Test method for setTime() and toDate() round-trip.
	 */
	@Test
	public void testDateRoundTrip() {

		Date now = new Date();
		time.setTime(now);

		Date result = time.toDate(now);

		assertEquals(now, result);
		assertEquals(now.getTime(), result.getTime());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#toString(net.sf.marineapi.nmea.util.Time)}
	 * .
	 */
	@Test
	public void testFormatTimeNoDecimals() {
		Time t = new Time(1, 2, 3);
		assertEquals("010203.000", t.toString());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#toString(net.sf.marineapi.nmea.util.Time)}
	 * .
	 */
	@Test
	public void testFormatTimeWithDecimals() {
		Time t = new Time(1, 2, 3.456);
		assertEquals("010203.456", t.toString());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#toString(net.sf.marineapi.nmea.util.Time)}
	 * .
	 */
	@Test
	public void testFormatTimeWithOneDecimal() {
		Time t = new Time(1, 2, 3.4);
		assertEquals("010203.400", t.toString());
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
		time.setSeconds(1.123);
		assertEquals(64861123, time.getMilliseconds());
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
		assertEquals(3.4, time.getSeconds(), 0.001);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time(java.lang.String)}
	 * .
	 */
	@Test
	public void testParseTimeWithDecimals() {
		Time t = new Time("010203.456");
		assertEquals(1, t.getHour());
		assertEquals(2, t.getMinutes());
		assertEquals(3.456, t.getSeconds(), 0.001);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time(java.lang.String)}
	 * .
	 */
	@Test
	public void testParseTimeWithOneDecimal() {
		Time t = new Time("010203.4");
		assertEquals(1, t.getHour());
		assertEquals(2, t.getMinutes());
		assertEquals(3.4, t.getSeconds(), 0.001);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.util.Time(java.lang.String)}
	 * .
	 */
	@Test
	public void testParseTimeWithoutDecimals() {
		Time t = new Time("010203");
		assertEquals(1, t.getHour());
		assertEquals(2, t.getMinutes());
		assertEquals(3.0, t.getSeconds(), 0.001);
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
			time.setSeconds(-0.001);
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
		time.setSeconds(45.12345);
		assertEquals(45.12345, time.getSeconds(), 0.001);
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
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		int fullSeconds = cal.get(Calendar.SECOND);
		int milliSeconds = cal.get(Calendar.MILLISECOND);
		double seconds = fullSeconds + (milliSeconds / 1000.0);

		assertEquals(hours, time.getHour());
		assertEquals(minutes, time.getMinutes());
		assertEquals(seconds, time.getSeconds(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Time#toDate(java.util.Date)}.
	 */
	@Test
	public void testToDate() {

		Calendar cal = new GregorianCalendar();
		Calendar result = new GregorianCalendar();

		// set cal to reference date/time (date portion not significant)
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 3);
		cal.set(Calendar.MILLISECOND, 400);

		// convert Time to Date and insert to result Calendar for comparison
		Date d = time.toDate(cal.getTime());
		result.setTime(d);

		int resultHour = result.get(Calendar.HOUR_OF_DAY);
		int resultMinute = result.get(Calendar.MINUTE);
		int resultFullSeconds = result.get(Calendar.SECOND);
		int resultMilliseconds = result.get(Calendar.MILLISECOND);
		double resultSeconds = resultFullSeconds + (resultMilliseconds / 1000.0);

		// Time portion, should match values in Time exactly
		assertEquals(time.getHour(), resultHour);
		assertEquals(time.getMinutes(), resultMinute);
		assertEquals(time.getSeconds(), resultSeconds, 0.001);

		// Date portion should not have changed
		assertEquals(cal.get(Calendar.YEAR), result.get(Calendar.YEAR));
		assertEquals(cal.get(Calendar.MONTH), result.get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.DAY_OF_YEAR),
			result.get(Calendar.DAY_OF_YEAR));
	}

	@Test
	public void testEquals() {

		Time a = new Time(1, 2, 3.456);
		Time b = new Time(1, 2, 3.456);
		Time c = new Time(2, 3, 4.567);

		assertTrue(a.equals(a));
		assertTrue(a.equals(b));
		assertFalse(a.equals(c));
		assertFalse(a.equals(new Object()));
		assertEquals(a.hashCode(), b.hashCode());
	}
}

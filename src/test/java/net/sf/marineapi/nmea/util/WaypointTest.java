/* 
 * WaypointTest.java
 * Copyright 2010 Kimmo Tuukkanen
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

import org.junit.Before;
import org.junit.Test;

/**
 * WaypointTest
 * 
 * @author Kimmo Tuukkanen
 */
public class WaypointTest {

	private final String id1 = "FOO";
	private final String id2 = "BAR";
	private final String desc = "Description text";
	Waypoint point;

	@Before
	public void setUp() {
		point = new Waypoint(id1, 60.0, 25.0, Datum.WGS84);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Waypoint#setDescription(java.lang.String)}
	 * .
	 */
	@Test
	public void testDescription() {
		assertEquals("", point.getDescription());
		point.setDescription(desc);
		assertEquals(desc, point.getDescription());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.util.Waypoint#setId(java.lang.String)}.
	 */
	@Test
	public void testId() {
		assertEquals(id1, point.getId());
		point.setId(id2);
		assertEquals(id2, point.getId());
	}

}

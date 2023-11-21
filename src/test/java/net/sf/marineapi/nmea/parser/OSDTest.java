/* 
 * OSDTest.java
 * Copyright (C) 2020 Joshua Sweaney
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
package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import net.sf.marineapi.nmea.sentence.OSDSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.ReferenceSystem;
import net.sf.marineapi.nmea.util.Units;

/**
 * OSDTest
 * 
 * @author Joshua Sweaney
 */
public class OSDTest {

    public static final String EXAMPLE = "$RAOSD,35.1,A,36.0,P,10.2,P,15.3,0.1,N*41";
    public OSDSentence example, empty;

    @Before
    public void setUp() {
        example = new OSDParser(EXAMPLE);
        empty = new OSDParser(TalkerId.RA);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getHeading()}.
     */
    @Test
    public void testGetHeading() {
        assertEquals(35.1, example.getHeading(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getHeadingStatus()}.
     */
    @Test
    public void testGetHeadingStatus() {
        assertEquals(DataStatus.ACTIVE, example.getHeadingStatus());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getCourse()}.
     */
    @Test
    public void testGetCourse() {
        assertEquals(36.0, example.getCourse(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getCourseReference()}.
     */
    @Test
    public void testGetCourseReference() {
        assertEquals(ReferenceSystem.POSITIONING_SYSTEM_GROUND_REFERENCE,
                        example.getCourseReference());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getSpeed()}.
     */
    @Test
    public void testGetSpeed() {
        assertEquals(10.2, example.getSpeed(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getSpeedReference()}.
     */
    @Test
    public void testGetSpeedReference() {
        assertEquals(ReferenceSystem.POSITIONING_SYSTEM_GROUND_REFERENCE,
                        example.getSpeedReference());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getVesselSet()}.
     */
    @Test
    public void testGetVesselSet() {
        assertEquals(15.3, example.getVesselSet(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getVesselDrift()}.
     */
    @Test
    public void testGetVesselDrift() {
        assertEquals(0.1, example.getVesselDrift(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#getSpeedUnits()}.
     */
    @Test
    public void testGetSpeedUnits() {
        assertEquals(Units.NAUTICAL_MILES, example.getSpeedUnits());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setHeading(double)}.
     */
    @Test
    public void testSetHeading() {
        double newHeading = 275.2;
        empty.setHeading(newHeading);
        assertEquals(newHeading, empty.getHeading(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setHeadingStatus(DataStatus)}.
     */
    @Test
    public void testSetHeadingStatus() {
        DataStatus newStatus = DataStatus.VOID;
        empty.setHeadingStatus(newStatus);
        assertEquals(newStatus, empty.getHeadingStatus());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setCourse(double)}.
     */
    @Test
    public void testSetCourse() {
        double newCourse = 95.3;
        empty.setCourse(newCourse);
        assertEquals(newCourse, empty.getCourse(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setCourseReference(ReferenceSystem)}.
     */
    @Test
    public void testSetCourseReference() {
        ReferenceSystem newReference = ReferenceSystem.BOTTOM_TRACKING_LOG;
        empty.setCourseReference(newReference);
        assertEquals(newReference, empty.getCourseReference());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setSpeed(double)}.
     */
    @Test
    public void testSetSpeed() {
        double newSpeed = 11.2;
        empty.setSpeed(newSpeed);
        assertEquals(newSpeed, empty.getSpeed(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setSpeedReference(ReferenceSystem)}.
     */
    @Test
    public void testSetSpeedReference() {
        ReferenceSystem newReference = ReferenceSystem.RADAR_TRACKING;
        empty.setSpeedReference(newReference);
        assertEquals(newReference, empty.getSpeedReference());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setVesselSet(double)}.
     */
    @Test
    public void testSetVesselSet() {
        double newSet = 13.9;
        empty.setVesselSet(newSet);
        assertEquals(newSet, empty.getVesselSet(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setVesselDrift(double)}.
     */
    @Test
    public void testSetVesselDrift() {
        double newDrift = 365.4;
        empty.setVesselDrift(newDrift);
        assertEquals(newDrift, empty.getVesselDrift(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.OSDParser#setSpeedUnits(Units)}.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSetSpeedUnits() {
        Units newUnits = Units.NAUTICAL_MILES;
        empty.setSpeedUnits(newUnits);
        assertEquals(newUnits, empty.getSpeedUnits());

        // An invalid speed unit. Should throw IllegalArgumentException.
        empty.setSpeedUnits(Units.CELSIUS);
    }

    
}
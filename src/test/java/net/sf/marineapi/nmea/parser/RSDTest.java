/* 
 * RSDTest.java
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

import net.sf.marineapi.nmea.sentence.RSDSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DisplayRotation;
import net.sf.marineapi.nmea.util.Units;

/**
 * RSDTest
 * 
 * @author Joshua Sweaney
 */
public class RSDTest {

    public static final String EXAMPLE = "$RARSD,12,90,24,45,6,270,12,315,6.5,118,96,N,N*5A";
    public RSDSentence example, empty;

    @Before
    public void setUp() {
        example = new RSDParser(EXAMPLE);
        empty = new RSDParser(TalkerId.RA);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getOriginOneRange()}.
     */
    @Test
    public void testGetOriginOneRange() {
        assertEquals(12.0, example.getOriginOneRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getOriginOneBearing()}.
     */
    @Test
    public void testGetOriginOneBearing() {
        assertEquals(90.0, example.getOriginOneBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getVRMOneRange()}.
     */
    @Test
    public void testGetVRMOneRange() {
        assertEquals(24.0, example.getVRMOneRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getEBLOneBearing()}.
     */
    @Test
    public void testGetEBLOneBearing() {
        assertEquals(45.0, example.getEBLOneBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getOriginTwoRange()}.
     */
    @Test
    public void testGetOriginTwoRange() {
        assertEquals(6.0, example.getOriginTwoRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getOriginTwoBearing()}.
     */
    @Test
    public void testGetOriginTwoBearing() {
        assertEquals(270.0, example.getOriginTwoBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getVRMTwoRange()}.
     */
    @Test
    public void testGetVRMTwoRange() {
        assertEquals(12.0, example.getVRMTwoRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getEBLTwoBearing()}.
     */
    @Test
    public void testGetEBLTwoBearing() {
        assertEquals(315.0, example.getEBLTwoBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getCursorRange()}.
     */
    @Test
    public void testGetCursorRange() {
        assertEquals(6.5, example.getCursorRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getCursorBearing()}.
     */
    @Test
    public void testGetCursorBearing() {
        assertEquals(118.0, example.getCursorBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getRangeScale()}.
     */
    @Test
    public void testGetRangeScale() {
        assertEquals(96.0, example.getRangeScale(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getRangeUnits()}.
     */
    @Test
    public void testGetRangeUnits() {
        assertEquals(Units.NAUTICAL_MILES, example.getRangeUnits());
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#getDisplayRotation()}.
     */
    @Test
    public void testGetDisplayRotation() {
        assertEquals(DisplayRotation.NORTH_UP, example.getDisplayRotation());
    }
    
    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setOriginOneRange(double)}.
     */
    @Test
    public void testSetOriginOneRange() {
        double newRange = 0.75;
        empty.setOriginOneRange(newRange);
        assertEquals(newRange, empty.getOriginOneRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setOriginOneBearing(double)}.
     */
    @Test
    public void testSetOriginOneBearing() {
        double newBearing = 93.2;
        empty.setOriginOneBearing(newBearing);
        assertEquals(newBearing, empty.getOriginOneBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setVRMOneRange(double)}.
     */
    @Test
    public void testSetVRMOneRange() {
        double newRange = 12.5;
        empty.setVRMOneRange(newRange);
        assertEquals(newRange, empty.getVRMOneRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setEBLOneBearing(double)}.
     */
    @Test
    public void testSetEBLOneBearing() {
        double newBearing = 147.0;
        empty.setEBLOneBearing(newBearing);
        assertEquals(newBearing, empty.getEBLOneBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setOriginTwoRange(double)}.
     */
    @Test
    public void testSetOriginTwoRange() {
        double newRange = 0.75;
        empty.setOriginTwoRange(newRange);
        assertEquals(newRange, empty.getOriginTwoRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setOriginTwoBearing(double)}.
     */
    @Test
    public void testSetOriginTwoBearing() {
        double newBearing = 93.2;
        empty.setOriginTwoBearing(newBearing);
        assertEquals(newBearing, empty.getOriginTwoBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setVRMTwoRange(double)}.
     */
    @Test
    public void testSetVRMTwoRange() {
        double newRange = 12.5;
        empty.setVRMTwoRange(newRange);
        assertEquals(newRange, empty.getVRMTwoRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setEBLTwoBearing(double)}.
     */
    @Test
    public void testSetEBLTwoBearing() {
        double newBearing = 147.0;
        empty.setEBLTwoBearing(newBearing);
        assertEquals(newBearing, empty.getEBLTwoBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setCursorRange(double)}.
     */
    @Test
    public void testSetCursorRange() {
        double newRange = 48.32;
        empty.setCursorRange(newRange);
        assertEquals(newRange, empty.getCursorRange(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setCursorBearing(double)}.
     */
    @Test
    public void testSetCursorBearing() {
        double newBearing = 300.4;
        empty.setCursorBearing(newBearing);
        assertEquals(newBearing, empty.getCursorBearing(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setRangeScale(double)}.
     */
    @Test
    public void testSetRangeScale() {
        double newScale = 0.75;
        empty.setRangeScale(newScale);
        assertEquals(newScale, empty.getRangeScale(), 0.0);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setRangeUnits(Units)}.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSetRangeUnits() {
        Units newUnits = Units.KILOMETERS;
        empty.setRangeUnits(newUnits);
        assertEquals(newUnits, empty.getRangeUnits());

        // Invalid range unit. Should throw IllegalArgumentException
        Units invalidUnits = Units.FATHOMS;
        empty.setRangeUnits(invalidUnits);
    }

    /**
     * Test for
     * {@link net.sf.marineapi.nmea.parser.RSDParser#setDisplayRotation(DisplayRotation)}.
     */
    @Test
    public void testSetDisplayRotation() {
        DisplayRotation newRotation = DisplayRotation.COURSE_UP;
        empty.setDisplayRotation(newRotation);
        assertEquals(newRotation, empty.getDisplayRotation());
    }

}
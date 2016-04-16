/*
 * VDOTest.java
 * Copyright (C) 2016 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.AISSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * VDOTest
 *
 * @author Kimmo Tuukkanen
 */
public class VDOTest {

    public static final String EXAMPLE = "!AIVDO,1,1,,B,H1c2;qA@PU>0U>060<h5=>0:1Dp,2*7D";
    public static final String PART1 = "!AIVDO,2,1,5,B,E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5,0*7B";
    public static final String PART2 = "!AIVDO,2,2,5,B,1CQ1A83PCAH0,0*60";

    private AISSentence vdo;
    private AISSentence frag1;
    private AISSentence frag2;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        vdo = new VDOParser(EXAMPLE);
        frag1 = new VDOParser(PART1);
        frag2 = new VDOParser(PART2);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#VDOParser(net.sf.marineapi.nmea.sentence.TalkerId)}
     * .
     */
    @Test
    public void testVDOParserTalkerId() {
        AISSentence empty = new VDOParser(TalkerId.AI);
        assertEquals('!', empty.getBeginChar());
        assertEquals(TalkerId.AI, empty.getTalkerId());
        assertEquals("VDO", empty.getSentenceId());
        assertEquals(6, empty.getFieldCount());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#getNumberOfFragments()}.
     */
    @Test
    public void testGetNumberOfFragments() {
        assertEquals(1, vdo.getNumberOfFragments());
        assertEquals(2, frag1.getNumberOfFragments());
        assertEquals(2, frag2.getNumberOfFragments());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#getFragmentNumber()}.
     */
    @Test
    public void testGetFragmentNumber() {
        assertEquals(1, vdo.getFragmentNumber());
        assertEquals(1, frag1.getFragmentNumber());
        assertEquals(2, frag2.getFragmentNumber());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#getMessageId()}.
     */
    @Test
    public void testGetMessageId() {
        assertEquals("5", frag1.getMessageId());
        assertEquals("5", frag2.getMessageId());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#getRadioChannel()}.
     */
    @Test
    public void testGetRadioChannel() {
        assertEquals("B", vdo.getRadioChannel());
        assertEquals("B", frag1.getRadioChannel());
        assertEquals("B", frag2.getRadioChannel());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#getPayload()}.
     */
    @Test
    public void testGetPayload() {
        final String pl = "H1c2;qA@PU>0U>060<h5=>0:1Dp";
        final String f1 = "E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5";
        final String f2 = "1CQ1A83PCAH0";
        assertEquals(pl, vdo.getPayload());
        assertEquals(f1, frag1.getPayload());
        assertEquals(f2, frag2.getPayload());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#getFillBits()}.
     */
    @Test
    public void testGetFillBits() {
        assertEquals(2, vdo.getFillBits());
        assertEquals(0, frag1.getFillBits());
        assertEquals(0, frag2.getFillBits());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#isFragmented()}.
     */
    @Test
    public void testIsFragmented() {
        assertFalse(vdo.isFragmented());
        assertTrue(frag1.isFragmented());
        assertTrue(frag2.isFragmented());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#isFirstFragment()}.
     */
    @Test
    public void testIsFirstFragment() {
        assertTrue(vdo.isFirstFragment());
        assertTrue(frag1.isFirstFragment());
        assertFalse(frag2.isFirstFragment());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#isLastFragment()}.
     */
    @Test
    public void testIsLastFragment() {
        assertTrue(vdo.isLastFragment());
        assertFalse(frag1.isLastFragment());
        assertTrue(frag2.isLastFragment());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.VDOParser#isPartOfMessage(net.sf.marineapi.nmea.sentence.AISSentence)}
     * .
     */
    @Test
    public void testIsPartOfMessage() {
        assertFalse(vdo.isPartOfMessage(frag1));
        assertFalse(vdo.isPartOfMessage(frag2));
        assertFalse(frag1.isPartOfMessage(vdo));
        assertFalse(frag2.isPartOfMessage(vdo));
        assertTrue(frag1.isPartOfMessage(frag2));
        assertFalse(frag2.isPartOfMessage(frag1));
    }

    @Test
    public void testToStringWithAIS() {
        AISSentence example = new VDOParser(EXAMPLE);
        AISSentence empty = new VDOParser(TalkerId.AI);
        assertEquals(EXAMPLE, example.toString());
        assertEquals("!AIVDO,,,,,,*55", empty.toString());
    }
}

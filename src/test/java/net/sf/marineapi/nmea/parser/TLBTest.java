/* 
 * TLBTest.java
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import net.sf.marineapi.nmea.sentence.TLBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * TLBTest
 * 
 * @author Joshua Sweaney
 */
public class TLBTest {

    public static final String EXAMPLE = "$RATLB,1,SHIPONE,2,SHIPTWO,3,SHIPTHREE*3D";

    private TLBSentence empty, threeTargets;

    @Before
    public void setUp() {
        empty = new TLBParser(TalkerId.RA);
        threeTargets = new TLBParser(EXAMPLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTLBParser() {
        // Target 5 has odd number of fields (missing lable for target 5). This should throw an exception on construct.
        new TLBParser("$RATLB,3,SHIPTHREE,5*2F"); 
    }

    /**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLBParser#addTargetLabel(int, String)}.
	 */
    @Test
    public void testAddTargetLabel() {
        empty.addTargetLabel(3, "SHIPTHREE");
        assertTrue(empty.toString().contains("3,SHIPTHREE*"));
        empty.addTargetLabel(5,"SHIPFIVE");
        assertTrue(empty.toString().contains("3,SHIPTHREE,5,SHIPFIVE*")); // SHIPFIVE is now at the end
        empty.addTargetLabel(99, "SHIP99");
        assertTrue(empty.toString().contains("3,SHIPTHREE,5,SHIPFIVE,99,SHIP99*"));

        // Adding to existing sentence
        threeTargets.addTargetLabel(4, "SHIPFOUR");
        assertTrue(threeTargets.toString().contains("1,SHIPONE,2,SHIPTWO,3,SHIPTHREE,4,SHIPFOUR*"));
    }

    /**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLBParser#setTargetPairs(int, String)}.
	 */
    @Test(expected = IllegalArgumentException.class)
    public void testSetTargetPairs() {
        int[] ids = {1,2,3};
        String[] labels = {"SHIPONE", "SHIPTWO", "SHIPTHREE"};
        empty.setTargetPairs(ids, labels);

        assertTrue(empty.toString().contains("1,SHIPONE,2,SHIPTWO,3,SHIPTHREE*"));

        int[] ids_two = {5,6};
        String[] labels_two = {"SHIPFIVE", "SHIPSIX", "SHIPSEVEN"}; // Intentionally larger than ids_two[]
        empty.setTargetPairs(ids_two, labels_two); // Will throw exception
    }

    /**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLBParser#getTargetIds()}.
	 */
    @Test
    public void testGetTargetIds() {
        int[] ids = {1,2,3};
        assertArrayEquals(ids, threeTargets.getTargetIds());
        
        int[] ids_empty = {};
        assertArrayEquals(ids_empty, empty.getTargetIds());
    }

    /**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLBParser#getTargetLabels()}.
	 */
    @Test
    public void testGetTargetLabels() {
        String[] labels = {"SHIPONE", "SHIPTWO", "SHIPTHREE"};
        assertArrayEquals(labels, threeTargets.getTargetLabels());

        String[] labels_empty = {};
        assertArrayEquals(labels_empty, empty.getTargetLabels());
    }
    
}
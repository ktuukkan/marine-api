/*
 * MHUTest.java
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

import net.sf.marineapi.nmea.sentence.MHUSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * MHU parser tests.
 *
 * @author Kimmo Tuukkanen
 */
public class MHUTest {

    public static final String EXAMPLE = "$IIMHU,66.0,5.0,3.0,C";

    private MHUSentence mhu;
    private MHUSentence empty;

    @Before
    public void setUp() {
        mhu = new MHUParser(EXAMPLE);
        empty = new MHUParser(TalkerId.II);
        assertEquals(4, mhu.getFieldCount());
    }

    @Test
    public void testEmptySentenceConstructor() {
        assertEquals(TalkerId.II, empty.getTalkerId());
        assertEquals(SentenceId.MHU.toString(), empty.getSentenceId());
        assertEquals(4, empty.getFieldCount());
        assertEquals('C', empty.getDewPointUnit());
    }

    @Test
    public void testGetRelativeHumidity() throws Exception {
        assertEquals(66.0, mhu.getRelativeHumidity(), 0.1);
    }

    @Test
    public void testGetAbsoluteHumidity() throws Exception {
        assertEquals(5.0, mhu.getAbsoluteHumidity(), 0.1);
    }

    @Test
    public void testGetDewPoint() throws Exception {
        assertEquals(3.0, mhu.getDewPoint(), 0.1);
    }

    @Test
    public void testGetDewPointUnit() throws Exception {
        assertEquals('C', mhu.getDewPointUnit());
    }

    @Test
    public void testSetRelativeHumidity() throws Exception {
        mhu.setRelativeHumidity(55.55555);
        assertEquals(55.6, mhu.getRelativeHumidity(), 0.1);
    }

    @Test
    public void testSetAbsoluteHumidity() throws Exception {
        mhu.setAbsoluteHumidity(6.1234);
        assertEquals(6.1, mhu.getAbsoluteHumidity(), 0.1);
    }

    @Test
    public void testSetDewPoint() throws Exception {
        mhu.setDewPoint(1.2356);
        assertEquals(1.2, mhu.getDewPoint(), 0.1);
    }

    @Test
    public void testSetDewPointUnit() throws Exception {
        mhu.setDewPointUnit('F');
        assertEquals('F', mhu.getDewPointUnit());
    }
}
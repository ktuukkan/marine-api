/*
 * GNSTest.java
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

import net.sf.marineapi.nmea.sentence.GNSSentence;
import net.sf.marineapi.nmea.sentence.GNSSentence.Mode;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * GNS parser tests
 *
 * @author Kimmo Tuukkanen
 */
public class GNSTest {

    public static final String EXAMPLE = "$GNGNS,014035.00,4332.69262,S,17235.48549,E,RR,13,0.9,25.63,11.24,,*70";

    GNSSentence gns;
    GNSSentence empty;

    @Before
    public void setUp() throws Exception {

        gns = new GNSParser(EXAMPLE);
        assertEquals(TalkerId.GN, gns.getTalkerId());
        assertEquals(SentenceId.GNS.name(), gns.getSentenceId());
        assertEquals(12, gns.getFieldCount());

        empty = new GNSParser(TalkerId.GP);
        assertEquals(TalkerId.GP, empty.getTalkerId());
        assertEquals(SentenceId.GNS.name(), empty.getSentenceId());
        assertEquals(12, empty.getFieldCount());
    }

    @Test
    public void getTime() throws Exception {
        Time t = gns.getTime();
        assertEquals(1, t.getHour());
        assertEquals(40, t.getMinutes());
        assertEquals(35.00, t.getSeconds(), 0.001);
    }

    @Test
    public void setTime() throws Exception {
        gns.setTime(new Time(10, 20, 30));
        assertEquals(10, gns.getTime().getHour());
        assertEquals(20, gns.getTime().getMinutes());
        assertEquals(30.0, gns.getTime().getSeconds(), 0.1);
    }

    @Test
    public void getPosition() throws Exception {

        // 4332.69262,S,17235.48549,E
        final double LAT = -(43 + 32.69262 / 60);
        final double LON = 172 + 35.48549 / 60;
        Position p = gns.getPosition();

        assertEquals(LAT, p.getLatitude(), 0.00001);
        assertEquals(CompassPoint.SOUTH, p.getLatitudeHemisphere());
        assertEquals(LON, p.getLongitude(), 0.00001);
        assertEquals(CompassPoint.EAST, p.getLongitudeHemisphere());
    }

    @Test
    public void setPosition() throws Exception {

        final double LAT = 61.23456;
        final double LON = 21.23456;

        empty.setPosition(new Position(LAT, LON));
        Position p = empty.getPosition();

        assertEquals(LAT, p.getLatitude(), 0.00001);
        assertEquals(CompassPoint.NORTH, p.getLatitudeHemisphere());
        assertEquals(LON, p.getLongitude(), 0.00001);
        assertEquals(CompassPoint.EAST, p.getLongitudeHemisphere());
    }

    @Test
    public void getGpsMode() throws Exception {
        assertEquals(Mode.RTK, gns.getGpsMode());
        assertEquals(Mode.NONE, empty.getGpsMode());
    }

    @Test
    public void setGpsMode() throws Exception {
        gns.setGpsMode(Mode.DGPS);
        assertTrue(gns.toString().contains(",DR,"));
        assertEquals(Mode.DGPS, gns.getGpsMode());
        assertEquals(Mode.RTK, gns.getGlonassMode());
        assertEquals(0, gns.getAdditionalModes().length);
    }

    @Test
    public void getGlonassMode() throws Exception {
        assertEquals(Mode.RTK, gns.getGlonassMode());
        assertEquals(Mode.NONE, empty.getGlonassMode());
    }

    @Test
    public void setGlonassMode() throws Exception {
        gns.setGlonassMode(Mode.FRTK);
        assertTrue(gns.toString().contains(",RF,"));
        assertEquals(Mode.FRTK, gns.getGlonassMode());
        assertEquals(Mode.RTK, gns.getGpsMode());
        assertEquals(0, gns.getAdditionalModes().length);
    }

    @Test
    public void setAdditionalModes() throws Exception {
        gns.setAdditionalModes(Mode.AUTOMATIC, Mode.ESTIMATED);
        assertTrue(gns.toString().contains(",RRAE,"));
        assertEquals(Mode.RTK, gns.getGpsMode());
        assertEquals(Mode.RTK, gns.getGlonassMode());
    }

    @Test
    public void getAdditionalModes() throws Exception {
        gns.setAdditionalModes(Mode.AUTOMATIC, Mode.ESTIMATED);
        Mode[] additional = gns.getAdditionalModes();
        assertEquals(2, additional.length);
        assertEquals(Mode.AUTOMATIC, additional[0]);
        assertEquals(Mode.ESTIMATED, additional[1]);
    }

    @Test
    public void getSatelliteCount() throws Exception {
        assertEquals(13, gns.getSatelliteCount());
    }

    @Test
    public void setSatelliteCount() throws Exception {
        gns.setSatelliteCount(8);
        assertTrue(gns.toString().contains(",08,"));
        assertEquals(8, gns.getSatelliteCount());
    }

    @Test
    public void getHorizontalDOP() throws Exception {
        assertEquals(0.9, gns.getHorizontalDOP(), 0.001);
    }

    @Test
    public void setHorizontalDOP() throws Exception {
        gns.setHorizontalDOP(0.123);
        assertEquals(0.12, gns.getHorizontalDOP(), 0.001);
    }

    @Test
    public void getOrthometricHeight() throws Exception {
        assertEquals(25.63, gns.getOrthometricHeight(), 0.001);
    }

    @Test
    public void setOrthometricHeight() throws Exception {
        gns.setOrthometricHeight(12.342);
        assertEquals(12.34, gns.getOrthometricHeight(), 0.0001);
    }

    @Test
    public void getGeoidalSeparation() throws Exception {
        assertEquals(11.24, gns.getGeoidalSeparation(), 0.001);
    }

    @Test
    public void setGeoidalSeparation() throws Exception {
        gns.setGeoidalSeparation(1.234);
        assertEquals(1.23, gns.getGeoidalSeparation(), 0.001);
    }

    @Test
    public void testDgpsAge() throws Exception {
        empty.setDgpsAge(10);
        assertTrue(empty.toString().contains(",10.0,*"));
        assertEquals(10, empty.getDgpsAge(), 0.1);
    }

    @Test
    public void testDgpsStationId() throws Exception {
        gns.setDgpsStationId("1234");
        assertTrue(gns.toString().contains(",1234*"));
        assertEquals("1234", gns.getDgpsStationId());
    }

}
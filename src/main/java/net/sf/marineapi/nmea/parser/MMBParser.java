/*
 * MMBParser.java
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

import net.sf.marineapi.nmea.sentence.MMBSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * MMBParser - Barometer.
 *
 * $--MMB,x.x,I,x.x,B*hh<CR><LF>
 *
 * @author Kimmo Tuukkanen
 */
class MMBParser extends SentenceParser implements MMBSentence {

    private static final int PRESSURE_INHG = 0;
    private static final int UNIT_INHG = 1;
    private static final int PRESSURE_BARS = 2;
    private static final int UNIT_BARS = 3;

    /**
     * Constructor for parsing MMB.
     *
     * @param nmea MMB sentence String.
     */
    public MMBParser(String nmea) {
        super(nmea, SentenceId.MMB);
    }

    /**
     * Constructs a fresh MMB parser.
     *
     * @param tid TalkerId to use in sentence.
     */
    public MMBParser(TalkerId tid) {
        super(tid, SentenceId.MMB, 4);
        setCharValue(UNIT_INHG, 'I');
        setCharValue(UNIT_BARS, 'B');
    }

    @Override
    public double getInchesOfMercury() {
        return getDoubleValue(PRESSURE_INHG);
    }

    @Override
    public double getBars() {
        return getDoubleValue(PRESSURE_BARS);
    }

    @Override
    public void setInchesOfMercury(double inHg) {
        setDoubleValue(PRESSURE_INHG, inHg);
    }

    @Override
    public void setBars(double bars) {
        setDoubleValue(PRESSURE_BARS, bars);
    }
}

/*
 * VBWParser.java
 * Copyright (C) 2013-2014 ESRG LLC.
 * 
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
 * along with NMEA Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.parser;



import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VBWSentence;

class VBWParser extends SentenceParser implements VBWSentence {

    public static final int LONG_WATERSPEED = 0;
    public static final int TRAV_WATERSPEED = 1;
    public static final int LONG_GROUNDSPEED = 3;
    public static final int TRAV_GROUNDSPEED = 4;

    public VBWParser(String nmea) {
        super(nmea, SentenceId.VBW);
    }

    public VBWParser(TalkerId talker) {
        super(talker, SentenceId.VBW, 6);
    }

    public double getLongWaterSpeed() {
        return getDoubleValue(LONG_WATERSPEED);
    }

    public double getLongGroundSpeed() {
        return getDoubleValue(LONG_GROUNDSPEED);
    }

    public double getTravWaterSpeed() {
        return getDoubleValue(TRAV_WATERSPEED);
    }

    public double getTravGroundSpeed() {
        return getDoubleValue(TRAV_GROUNDSPEED);
    }
}

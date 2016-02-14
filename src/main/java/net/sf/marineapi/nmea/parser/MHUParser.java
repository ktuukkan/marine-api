/*
 * MHUParser.java
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

/**
 * MHUParser - Humidity & dew point.
 *
 * $--MHU,x.x,x.x,x.x,C*hh<CR><LF>
 *
 * @author Kimmo Tuukkanen
 */
class MHUParser extends SentenceParser implements MHUSentence {

    private static final int RELATIVE_HUMIDITY = 0;
    private static final int ABSOLUTE_HUMIDITY = 1;
    private static final int DEW_POINT = 2;
    private static final int DEW_POINT_UNIT = 3;

    /**
     * Constructor for parsing MHU sentence.
     *
     * @param nmea MHU sentence String
     */
    public MHUParser(String nmea) {
        super(nmea, SentenceId.MHU);
    }

    /**
     * Constructor for fresh MHU sentence.
     *
     * @param tid Talker ID to be used.
     */
    public MHUParser(TalkerId tid) {
        super(tid, SentenceId.MHU, 4);
        setDewPointUnit('C');
    }

    @Override
    public double getRelativeHumidity() {
        return getDoubleValue(RELATIVE_HUMIDITY);
    }

    @Override
    public double getAbsoluteHumidity() {
        return getDoubleValue(ABSOLUTE_HUMIDITY);
    }

    @Override
    public double getDewPoint() {
        return getDoubleValue(DEW_POINT);
    }

    @Override
    public char getDewPointUnit() {
        return getCharValue(DEW_POINT_UNIT);
    }

    @Override
    public void setRelativeHumidity(double humidity) {
        setDoubleValue(RELATIVE_HUMIDITY, humidity, 1, 1);
    }

    @Override
    public void setAbsoluteHumidity(double humidity) {
        setDoubleValue(ABSOLUTE_HUMIDITY, humidity, 1, 1);
    }

    @Override
    public void setDewPoint(double dewPoint) {
        setDoubleValue(DEW_POINT, dewPoint, 1, 1);
    }

    @Override
    public void setDewPointUnit(char unit) {
        setCharValue(DEW_POINT_UNIT, unit);
    }
}

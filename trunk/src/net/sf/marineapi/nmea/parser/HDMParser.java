/* 
 * HDMParser.java
 * Copyright (C) 2010-2011 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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

import net.sf.marineapi.nmea.sentence.HDMSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * HDM sentence parser.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class HDMParser extends SentenceParser implements HDMSentence {

    private static final int HEADING = 0;
    private static final int MAGN_INDICATOR = 1;

    /**
     * Creates a new HDM parser.
     * 
     * @param nmea HDM sentence String
     */
    public HDMParser(String nmea) {
        super(nmea, SentenceId.HDM);
    }

    /**
     * Creates a new empty HDM sentence.
     * 
     * @param talker Talker id to set
     */
    public HDMParser(TalkerId talker) {
        super(talker, SentenceId.HDM, 2);
        setCharValue(MAGN_INDICATOR, 'M');
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.HDMSentence#getHeading()
     */
    public double getHeading() {
        return getDoubleValue(HEADING);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.HDMSentence#setHeading(double)
     */
    public void setHeading(double hdm) {
        if (hdm < 0 || hdm > 360) {
            throw new IllegalArgumentException("Heading out of range [0..360]");
        }
        setDoubleValue(HEADING, hdm, 3, 1);
    }
}

/* 
 * VHWParser.java
 * Copyright (C) 2011 Warren Zahra
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

import net.sf.marineapi.nmea.sentence.VHWSentence;

/**
 * @author Warren Zahra
 * @version $Revision$
 */
class VHWParser extends SentenceParser implements VHWSentence {

    private static final int TRUE = 0;
    private static final int DEGREES_TRUE = 1;
    private static final int MAGNETIC = 2;
    private static final int DEGREES_MAGNETIC = 3;
    private static final int KNOTS = 4;
    private static final int SPEED_KNOTS = 5;
    private static final int KILOMETRES = 6;
    private static final int SPEED_KILOMETRES = 7;

    public VHWParser(String nmea) {
        super(nmea);
    }

    public double getDegreesTrue() {
        return getDoubleValue(TRUE);
    }

    public double getDegreesMagnetic() {
        return getDoubleValue(MAGNETIC);
    }

    public double getSpeedKnots() {
        return getDoubleValue(KNOTS);
    }

    public double getSpeedKilometres() {
        return getDoubleValue(KILOMETRES);
    }

}

/* 
 * MTWParser.java
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

import net.sf.marineapi.nmea.sentence.MTWSentence;

/**
 * @author Warren Zahra
 * @version $Revision$
 */
class MTWParser extends SentenceParser implements MTWSentence {

    private static final int TEMPERATURE = 0;

    public MTWParser(String nmea) {
        super(nmea);
    }

    public double getTemperature() {
        return getDoubleValue(TEMPERATURE);
    }

}

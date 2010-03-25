/* 
 * SentenceFactory.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * Factory for creating NMEA sentence parsers.
 * 
 * TODO Add create method for "empty" sentences.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SentenceFactory {

    private static SentenceFactory instance;

    private SentenceFactory() {
    }

    /**
     * Returns the singleton instance of SentenceFactory.
     * 
     * @return SentenceFactory instance
     */
    public static SentenceFactory getInstance() {
        if (instance == null) {
            instance = new SentenceFactory();
        }
        return instance;
    }
    
    /**
     * Creates a parser for specified NMEA sentence String.
     * 
     * @param nmea NMEA 0183 sentence
     * @return Sentence parser
     */
    public Sentence createParser(String nmea) {

        Sentence s = null;
        SentenceId type = SentenceParser.parseSentenceId(nmea);

        switch (type) {
            case BOD:
                s = new BODParser(nmea);
                break;
            case GGA:
                s = new GGAParser(nmea);
                break;
            case GLL:
                s = new GLLParser(nmea);
                break;
            case GSA:
                s = new GSAParser(nmea);
                break;
            case GSV:
                s = new GSVParser(nmea);
                break;
            case RMB:
                s = new RMBParser(nmea);
                break;
            case RMC:
                s = new RMCParser(nmea);
                break;
            case RTE:
                s = new RTEParser(nmea);
                break;
            case VTG:
                s = new VTGParser(nmea);
                break;
            case WPL:
                s = new WPLParser(nmea);
                break;
            case ZDA:
                s = new ZDAParser(nmea);
                break;
            default:
                s = new SentenceParser(nmea);
        }

        return s;
    }
}

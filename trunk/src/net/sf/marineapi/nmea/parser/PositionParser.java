/* 
 * PositionParser.java
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

import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.SentenceId;

/**
 * Abstract base class for sentence parsers that provide geographic position or
 * waypoint data, and thus need to parse lat/lon values. However, notice that
 * <code>PositionParser</code> does not implement <code>PositionSentence</code>
 * interface because the extending parser may not provide current location.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public abstract class PositionParser extends SentenceParser {

    /**
     * Constructor.
     * 
     * @param nmea NMEA 0183 sentence
     * @param type Assumed type of sentence in <code>nmea</code>
     */
    protected PositionParser(String nmea, SentenceId type) {
        super(nmea, type);
    }

    /**
     * Parses the latitude degrees from the specified field. The assumed String
     * format for latitude is <code>ddmm.mmm</code>.
     * 
     * @param index Index of field containing the latitude value.
     */
    protected double parseLatitude(int index) {
        String field = getStringValue(index);
        int deg = Integer.parseInt(field.substring(0, 2));
        double min = Double.parseDouble(field.substring(2));
        return deg + (min / 60);
    }

    /**
     * Parses the longitude degrees from the specified field. The assumed String
     * format for longitude is <code>dddmm.mmm</code>.
     * 
     * @param index Index of field containing the longitude value.
     */
    protected double parseLongitude(int index) {
        String field = getStringValue(index);
        int deg = Integer.parseInt(field.substring(0, 3));
        double min = Double.parseDouble(field.substring(3));
        return deg + (min / 60);
    }

    /**
     * Parses the hemisphere of latitude from specified field.
     * 
     * @param index Index of field that contains the latitude hemisphere value.
     */
    protected Direction parseHemisphereLat(int index) {
        char ch = getCharValue(index);
        Direction d = Direction.valueOf(ch);
        if (Direction.NORTH.equals(d) || Direction.SOUTH.equals(d)) {
            return d;
        }
        throw new ParseException("Invalid latitude hemisphere '" + ch + "'");
    }

    /**
     * Parses the hemisphere of longitude from the specified field.
     * 
     * @param index Field index for longitude hemisphere indicator
     */
    protected Direction parseHemisphereLon(int index) {
        char ch = getCharValue(index);
        Direction d = Direction.valueOf(ch);
        if (Direction.EAST.equals(d) || Direction.WEST.equals(d)) {
            return d;
        }
        throw new ParseException("Invalid longitude hemisphere " + ch + "'");
    }
}

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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.util.Direction;

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
     * Parses the hemisphere of latitude from specified field.
     * 
     * @param index Index of field that contains the latitude hemisphere value.
     * @return Hemisphere of latitude
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
     * @return Hemisphere of longitude
     */
    protected Direction parseHemisphereLon(int index) {
        char ch = getCharValue(index);
        Direction d = Direction.valueOf(ch);
        if (Direction.EAST.equals(d) || Direction.WEST.equals(d)) {
            return d;
        }
        throw new ParseException("Invalid longitude hemisphere " + ch + "'");
    }

    /**
     * Parses the latitude degrees from the specified field. The assumed String
     * format for latitude is <code>ddmm.mmm</code>.
     * 
     * @param index Index of field containing the latitude value.
     * @return Latitude value in degrees
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
     * @return Longitude value in degrees
     */
    protected double parseLongitude(int index) {
        String field = getStringValue(index);
        int deg = Integer.parseInt(field.substring(0, 3));
        double min = Double.parseDouble(field.substring(3));
        return deg + (min / 60);
    }

    /**
     * Sets the latitude value in specified field, formatted in "ddmm.mmm".
     * 
     * @param index Field index
     * @param lat Latitude value in degrees
     */
    protected void setLatitude(int index, double lat) {

        int deg = (int) Math.floor(lat);
        double min = (lat - deg) * 60;

        DecimalFormat df = new DecimalFormat("00.000");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        String result = String.format("%02d%s", deg, df.format(min));
        setStringValue(index, result);
    }

    /**
     * Set the hemisphere of latitude in specified field.
     * 
     * @param field Field index
     * @param hem Direction.NORTH or Direction.SOUTH
     */
    protected void setLatHemisphere(int field, Direction hem) {
        setCharValue(field, hem.toChar());
    }

    /**
     * Sets the longitude value in specified field, formatted in "dddmm.mmm".
     * 
     * @param index Field index
     * @param lon Longitude value in degrees
     */
    protected void setLongitude(int index, double lon) {

        int deg = (int) Math.floor(lon);
        double min = (lon - deg) * 60;

        DecimalFormat nf = new DecimalFormat("00.000");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        nf.setDecimalFormatSymbols(dfs);

        String result = String.format("%03d%s", deg, nf.format(min));
        setStringValue(index, result);
    }

    /**
     * Set the hemisphere of longitude in specified field.
     * 
     * @param field Field index
     * @param hem Direction.EAST or Direction.WEST
     */
    protected void setLonHemisphere(int field, Direction hem) {
        setCharValue(field, hem.toChar());
    }
}

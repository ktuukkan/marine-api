/* 
 * RSDParser.java
 * Copyright (C) 2020 Joshua Sweaney
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

import java.util.Arrays;

import net.sf.marineapi.nmea.sentence.RSDSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DisplayRotation;
import net.sf.marineapi.nmea.util.Units;

/**
 * RSD sentence parser
 * 
 * @author Joshua Sweaney
 */
public class RSDParser extends SentenceParser implements RSDSentence {

    private static final int ORIGIN_ONE_RANGE = 0;
    private static final int ORIGIN_ONE_BEARING = 1;
    private static final int VRM_ONE_RANGE = 2;
    private static final int EBL_ONE_BEARING = 3;
    private static final int ORIGIN_TWO_RANGE = 4;
    private static final int ORIGIN_TWO_BEARING = 5;
    private static final int VRM_TWO_RANGE = 6;
    private static final int EBL_TWO_BEARING = 7;
    private static final int CURSOR_RANGE = 8;
    private static final int CURSOR_BEARING = 9;
    private static final int RANGE_SCALE = 10;
    private static final int RANGE_UNITS = 11;
    private static final int DISPLAY_ROTATION = 12;

    private static final Units[] VALID_RANGE_UNITS = {Units.KILOMETERS, Units.NAUTICAL_MILES, Units.STATUTE_MILES};

    /**
	 * Creates a new instance of RSD parser
	 * 
	 * @param nmea RSD sentence string.
	 */
	public RSDParser(String nmea) {
        super(nmea, SentenceId.RSD);
    }

    /**
	 * Creates RSD parser with empty sentence.
	 * 
	 * @param talker TalkerId to set
	 */
	public RSDParser(TalkerId talker) {
		super(talker, SentenceId.RSD, 13);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getOriginOneRange()
     */
    public double getOriginOneRange() {
        return getDoubleValue(ORIGIN_ONE_RANGE);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getOriginOneBearing()
     */
    public double getOriginOneBearing() {
        return getDoubleValue(ORIGIN_ONE_BEARING);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getVRMOneRange()
     */
    public double getVRMOneRange() {
        return getDoubleValue(VRM_ONE_RANGE);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getEBLOneBearing()
     */
    public double getEBLOneBearing() {
        return getDoubleValue(EBL_ONE_BEARING);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getOriginTwoRange()
     */
    public double getOriginTwoRange() {
        return getDoubleValue(ORIGIN_TWO_RANGE);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getOriginTwoBearing()
     */
    public double getOriginTwoBearing() {
        return getDoubleValue(ORIGIN_TWO_BEARING);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getVRMTwoRange()
     */
    public double getVRMTwoRange() {
        return getDoubleValue(VRM_TWO_RANGE);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getEBLTwoBearing()
     */
    public double getEBLTwoBearing() {
        return getDoubleValue(EBL_TWO_BEARING);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getCursorRange()
     */
    public double getCursorRange() {
        return getDoubleValue(CURSOR_RANGE);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getCursorBearing()
     */
    public double getCursorBearing() {
        return getDoubleValue(CURSOR_BEARING);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getRangeScale()
     */
    public double getRangeScale() {
        return getDoubleValue(RANGE_SCALE);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getRangeUnits()
     */
    public Units getRangeUnits() {
        return Units.valueOf(getCharValue(RANGE_UNITS));
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#getDisplayRotation()
     */
    public DisplayRotation getDisplayRotation() {
        return DisplayRotation.valueOf(getCharValue(DISPLAY_ROTATION));
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setOriginOneRange(double)
     */
    public void setOriginOneRange(double range) {
        setDoubleValue(ORIGIN_ONE_RANGE, range);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setOriginOneBearing(double)
     */
    public void setOriginOneBearing(double bearing) {
        setDoubleValue(ORIGIN_ONE_BEARING, bearing);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setVRMOneRange(double)
     */
    public void setVRMOneRange(double range) {
        setDoubleValue(VRM_ONE_RANGE, range);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setEBLOneBearing(double)
     */
    public void setEBLOneBearing(double bearing) {
        setDoubleValue(EBL_ONE_BEARING, bearing);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setOriginTwoRange(double)
     */
    public void setOriginTwoRange(double range) {
        setDoubleValue(ORIGIN_TWO_RANGE, range);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setOriginTwoBearing(double)
     */
    public void setOriginTwoBearing(double bearing) {
        setDoubleValue(ORIGIN_TWO_BEARING, bearing);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setVRMTwoRange(double)
     */
    public void setVRMTwoRange(double range) {
        setDoubleValue(VRM_TWO_RANGE, range);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setEBLTwoBearing(double)
     */
    public void setEBLTwoBearing(double bearing) {
        setDoubleValue(EBL_TWO_BEARING, bearing);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setCursorRange(double)
     */
    public void setCursorRange(double range) {
        setDoubleValue(CURSOR_RANGE, range);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setCursorBearing(double)
     */
    public void setCursorBearing(double bearing) {
        setDoubleValue(CURSOR_BEARING, bearing);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setRangeScale(double)
     */
    public void setRangeScale(double scale) {
        setDoubleValue(RANGE_SCALE, scale);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setRangeUnits(Units)
     */
    public void setRangeUnits(Units units) {
        if (Arrays.asList(VALID_RANGE_UNITS).contains(units)) {
            setCharValue(RANGE_UNITS, units.toChar());
        } else {
            String err = "Range units must be ";
            for (int i = 0; i<VALID_RANGE_UNITS.length; i++) {
                Units u = VALID_RANGE_UNITS[i];
                err += u.name() + "(" + u.toChar() + ")";
                if (i != VALID_RANGE_UNITS.length-1) {
                    err += ", ";
                }
            }
            throw new IllegalArgumentException(err);
        }
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.RSDSentence#setDisplayRotation(DisplayRotation)
     */
    public void setDisplayRotation(DisplayRotation rotation) {
        setCharValue(DISPLAY_ROTATION, rotation.toChar());
    }
    
}
/* 
 * RSDSentence.java
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
package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.nmea.util.DisplayRotation;
import net.sf.marineapi.nmea.util.Units;

/**
 * Radar system data<br>
 * Contains information about variable range markers (VRM), electronic bearing lines (EBL),
 * the range scale in use, the display rotation, and the current cursor position.
 * Example:<br>
 * {@code $RARSD,12,90,24,45,6,270,12,315,6.5,118,96,N,N*5A<CR><LF> }
 * 
 * @author Joshua Sweaney
 */
public interface RSDSentence {

    /**
     * Get the range of the origin for VRM1 and EBL1.
     * @return double the range of Origin 1
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getOriginOneRange();

    /**
     * Get the bearing of the origin for VRM1 and EBL1 (in degrees), from 0 degrees.
     * @return double the bearing of Origin 1
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getOriginOneBearing();

    /**
     * Get the range of variable range marker 1
     * @return double VRM 1 range
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getVRMOneRange();

    /**
     * Get the bearing of electronic bearing line 1, from 0 degrees
     * @return double EBL 1 bearing
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getEBLOneBearing();

    /**
     * Get the range of the origin for VRM2 and EBL2.
     * @return double the range of Origin 2
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getOriginTwoRange();

    /**
     * Get the bearing of the origin for VRM2 and EBL2 (in degrees), from 0 degrees.
     * @return double the bearing of Origin 2
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getOriginTwoBearing();

    /**
     * Get the range of variable range marker 2
     * @return double VRM 2 range
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getVRMTwoRange();

    /**
     * Get the bearing of electronic bearing line 2, from 0 degrees
     * @return double EBL 2 bearing
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getEBLTwoBearing();

    /**
     * Get current cursor position range
     * @return double current cursor range
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getCursorRange();

    /**
     * Get current cursor position bearing
     * @return double current cursor bearing
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getCursorBearing();

    /**
     * Get the current selected range scale
     * @return double current range scale
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    double getRangeScale();

    /**
     * Get the units used for range measurements in this sentence
     * @return Units the units used for the ranges (either kilometres, nautical miles, or statute miles)
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    Units getRangeUnits();

    /**
     * Get the selected display rotation of the radar system
     * @return DisplayRotation the display rotation
     * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
     */
    DisplayRotation getDisplayRotation();

    /**
     * Set the range for origin one
     * @param range the range
     */
    void setOriginOneRange(double range);

    /**
     * Set the bearing for origin one
     * @param bearing the bearing
     */
    void setOriginOneBearing(double bearing);
    
    /**
     * Set the range of VRM one
     * @param range the range
     */
    void setVRMOneRange(double range);

    /**
     * Set the bearing of EBL one
     * @param bearing the bearing
     */
    void setEBLOneBearing(double bearing);

    /**
     * Set the range for origin two
     * @param range the range
     */
    void setOriginTwoRange(double range);

    /**
     * Set the bearing for origin two
     * @param bearing double the bearing
     */
    void setOriginTwoBearing(double bearing);
    
    /**
     * Set the range of VRM two
     * @param range the range
     */
    void setVRMTwoRange(double range);

    /**
     * Set the bearing of EBL two
     * @param bearing the bearing
     */
    void setEBLTwoBearing(double bearing);

    /**
     * Set the cursor range
     * @param range the range
     */
    void setCursorRange(double range);

    /**
     * Set the cursor bearing
     * @param bearing the bearing
     */
    void setCursorBearing(double bearing);

    /**
     * Set the range scale
     * @param rangeScale the range scale
     */
    void setRangeScale(double rangeScale);

    /**
     * Set the range units
     * @param units the units used for the ranges (either kilometres, nautical miles, or statute miles)
     */
    void setRangeUnits(Units units);

    /**
     * Set the display rotation
     * @param rotation the display rotation
     */
    void setDisplayRotation(DisplayRotation rotation);
}
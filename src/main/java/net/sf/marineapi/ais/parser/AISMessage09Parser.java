/*
 * AISMessage09Parser.java
 * Copyright (C) 2016 Henri Laurent
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
package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage09;
import net.sf.marineapi.ais.util.AISRuleViolation;
import net.sf.marineapi.ais.util.Angle12;
import net.sf.marineapi.ais.util.Latitude27;
import net.sf.marineapi.ais.util.Longitude28;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.ais.util.SpeedOverGround;
import net.sf.marineapi.ais.util.TimeStamp;

import java.text.DecimalFormat;

/**
 * AIS Message 9 implementation: Standard SAR Aircraft Position Report
 *
 * <pre>
 * Field  Name                                      Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	  messageID                               	   6	(   1,   6)
 *  2	  repeatIndicator                         	   2	(   7,   8)
 *  3	  userID                                  	  30	(   9,  38)
 *  4	  Altitude                               	  12	(  39,  50)
 *  5	  speedOverGround                         	  10	(  51,  60)
 *  6	  positionAccuracy                        	   1	(  61,  61)
 *  7	  longitude                               	  28	(  62,  89)
 *  8	  latitude                                	  27	(  90, 116)
 * 9	  courseOverGround                        	  12	( 117, 128)
 * 10	  timeStamp                               	   6	( 129, 134)
 * 11     regional                                     8    ( 135, 142)
 * 12	  dte                                    	   1	( 143, 143)
 * 13     spare                                        3    ( 144, 146)
 * 14     assigned                                     1    ( 147, 147)
 * 15     raim                                         1    ( 148, 148)
 * 16     radio                                       20    ( 149, 168)
 *                                                  ---- +
 *                                               sum 168
 * </pre>
 *
 * @author Henri Laurent
 */
class AISMessage09Parser extends AISMessageParser implements AISMessage09 {

    private final static String SEPARATOR			    = "\n\t";
    private static final int    ALTITUDE                = 0;
    private final static int	SPEEDOVERGROUND			= 1;
    private final static int	POSITIONACCURACY		= 2;
    private final static int	LONGITUDE				= 3;
    private final static int	LATITUDE				= 4;
    private final static int	COURSEOVERGROUND		= 5;
    private final static int	TIMESTAMP				= 6;
    private final static int	REGIONAL				= 7; // spare 1
    private final static int	DTE		        		= 8;
    @SuppressWarnings("unused")
    private static final int    SPARE	                = 9; // spare 2
    private static final int    ASSIGNEDMODEFLAG	    = 10;
    private static final int    RAIMFLAG	            = 11;
    private static final int    RADIOSTATUS	            = 12;

    private static final int[] FROM = new int[]{38,50,60,61,89,116,128,134,142,43,146,147,149};
    private static final int[] TO =   new int[]{50,60,61,89,116,128,134,142,43,146,147,149,167};

    private int fAltitude;
    private int		fSOG;
    private boolean	fPositionAccuracy;
    private int		fLongitude;
    private int		fLatitude;
    private int		fCOG;
    private int		fTimeStamp;
    private int	    fRegional;
    private boolean	fDTE;
    private boolean	fAssignedModeFlag;
    private boolean	fRAIMFlag;
    private int	fRadioStatus;

    /**
     * Constructor.
     *
     * @param content Sib-bit message content
     */
    public AISMessage09Parser(Sixbit content) {
        super(content, 168);
        fAltitude = content.getInt(FROM[ALTITUDE], TO[ALTITUDE]);
        fSOG = content.getInt(FROM[SPEEDOVERGROUND], TO[SPEEDOVERGROUND]);
        fPositionAccuracy = content.getBoolean(TO[POSITIONACCURACY]);
        fLongitude = content.getAs28BitInt(FROM[LONGITUDE], TO[LONGITUDE]);
        if (!Longitude28.isCorrect(fLongitude))
            addViolation(new AISRuleViolation("LongitudeInDegrees", fLongitude, Longitude28.RANGE));
        fLatitude = content.getAs27BitInt(FROM[LATITUDE], TO[LATITUDE]);
        if (!Latitude27.isCorrect(fLatitude))
            addViolation(new AISRuleViolation("LatitudeInDegrees", fLatitude, Latitude27.RANGE));
        fCOG = content.getInt(FROM[COURSEOVERGROUND], TO[COURSEOVERGROUND]);
        if (!Angle12.isCorrect(fCOG))
            addViolation(new AISRuleViolation("CourseOverGround", fCOG, Angle12.RANGE));
        fTimeStamp = content.getInt(FROM[TIMESTAMP], TO[TIMESTAMP]);
        fRegional = content.getInt(FROM[REGIONAL], TO[REGIONAL]);
        fDTE = content.getBoolean(TO[DTE]);
        fAssignedModeFlag = content.getBoolean(TO[ASSIGNEDMODEFLAG]);
        fRAIMFlag = content.getBoolean(TO[RAIMFLAG]);
        fRadioStatus = content.getInt(FROM[RADIOSTATUS], TO[RADIOSTATUS]);
    }

    public int getAltitude() {
        return fAltitude;
    }

    public int getSpeedOverGround() { return fSOG; }

    /**
     * Returns the String representation of speed over ground.
     *
     * @return formatted value, "no SOG" or ">=1022"
     */
    public String getSOGString() {
        String msg;
        if (fSOG == 1023)
            msg = "no SOG";
        else if (fSOG == 1022)
            msg = ">=1022";
        else
            msg = new DecimalFormat("##0.0").format(fSOG / 10.0);
        return msg;
    }

    public boolean isAccurate() { return fPositionAccuracy; }

    public double getLongitudeInDegrees() { return Longitude28.toDegrees(fLongitude); }

    public double getLatitudeInDegrees() { return Latitude27.toDegrees(fLatitude); }

    public double getCourseOverGround() { return Angle12.toDegrees(fCOG); }

    public int getTimeStamp() { return fTimeStamp; }

    /**
     * Regional reserved (spare)
     *
     * @return Int value
     */
    public int getRegional() {
        return fRegional;
    }

    public boolean getDTEFlag() {
        return fDTE;
    }

    public boolean getAssignedModeFlag() {
        return fAssignedModeFlag;
    }

    public boolean getRAIMFlag() {
        return fRAIMFlag;
    }

    public int getRadioStatus() {
        return fRadioStatus;
    }

    @Override
    public boolean hasLongitude() {
        return Longitude28.isAvailable(fLongitude);
    }

    @Override
    public boolean hasLatitude() {
        return Latitude27.isAvailable(fLatitude);
    }

    public String toString() {
        String result = "\tAlt:      " + fAltitude;
        result += SEPARATOR + "SOG:     " + SpeedOverGround.toString(fSOG);
        result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
        result += SEPARATOR + "Lon:     " + Longitude28.toString(fLongitude);
        result += SEPARATOR + "Lat:     " + Latitude27.toString(fLatitude);
        result += SEPARATOR + "COG:     " + Angle12.toString(fCOG);
        result += SEPARATOR + "Time:    " + TimeStamp.toString(fTimeStamp);
        result += SEPARATOR + "Regional:     " + getRegional();
        result += SEPARATOR + "DTE: " + (fDTE ? "yes" : "no");
        result += SEPARATOR + "Assigned Mode Flag: " + (fAssignedModeFlag ? "yes" : "no");
        result += SEPARATOR + "RAIM Flag: " + (fRAIMFlag ? "yes" : "no");
        result += SEPARATOR + "RadioStatus:     " + getRadioStatus();
        return result;
    }
}

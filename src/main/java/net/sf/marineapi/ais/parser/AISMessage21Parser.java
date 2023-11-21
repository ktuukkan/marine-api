/*
 * AISMessage21Parser.java
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

import net.sf.marineapi.ais.message.AISMessage21;
import net.sf.marineapi.ais.util.AISRuleViolation;
import net.sf.marineapi.ais.util.Latitude27;
import net.sf.marineapi.ais.util.Longitude28;
import net.sf.marineapi.ais.util.NavAidType;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * AIS Message 21 implementation: Aid-to-Navigation Report.
 *
 * This message is unusual in that it varies in length depending on the presence
 * and size of the Name Extension field. May vary between 272 and 360 bits.
 *
 * <pre>
 * Field  Name                                      Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	  messageID                               	   6	(   1,   6)
 *  2	  repeatIndicator                         	   2	(   7,   8)
 *  3	  userID                                  	  30	(   9,  38)
 *  4	  aid_type                               	   5	(  39,  43)
 *  5	  name                                    	 120	( 44,  163)
 *  6	  positionAccuracy                        	   1	( 164, 164)
 *  7	  longitude                               	  28	( 165, 192)
 *  8	  latitude                                	  27	( 193, 219)
 *  9	  dimension                               	  30	( 220, 249)
 * 10	  typeOfElectronicPositionFixingDevice    	   4	( 250, 253)
 * 12	  timeStamp                               	   6	( 254, 259)
 * 13     off_position                                 1	( 260, 260)
 * 14     regional                                     8    ( 261, 268)
 * 15     raim                                         1    ( 269, 269)
 * 16     virtual_aid                                  1    ( 270, 270)
 * 17     assigned                                     1    ( 271, 271)
 * 18     spare                                        1    ( 272, 272)
 * 19	  name extension                          	  88	( 273, 360)
  *                                                  ---- +
 *                                               sum 360
 * </pre>
 *
 * @author Henri Laurent
 */
class AISMessage21Parser extends AISMessageParser implements AISMessage21 {

    private final static String SEPARATOR			= "\n\t";
    private static final int AIDTYPE = 0;
    private static final int NAME = 1;
    private final static int POSITIONACCURACY		= 2;
    private final static int LONGITUDE				= 3;
    private final static int LATITUDE				= 4;
    private static final int BOW = 5;
    private static final int STERN = 6;
    private static final int PORT = 7;
    private static final int STARBOARD = 8;
    private static final int TYPEOFEPFD = 9;
    private static final int UTC_SECOND	= 10;
    private static final int OFFPOSITIONINDICATOR	= 11;
    private static final int REGIONAL	= 12;
    private static final int RAIMFLAG	= 13;
    private static final int VIRTUALAIDFLAG	= 14;
    private static final int ASSIGNEDMODEFLAG	= 15;
    @SuppressWarnings("unused")
    private static final int SPARE	= 16;
    private static final int NAMEEXTENSION	= 17;

    private static final int[] FROM = new int[]{38, 43,163,164,192,219,228,237,243,249,253,259,260,268,269,270,271,272};
    private static final int[] TO =   new int[]{43,163,164,192,219,228,237,243,249,253,259,260,268,269,270,271,272,360};

    private int fAidType;
    private String fName;
    private boolean	fPositionAccuracy;
    private int	fLongitude;
    private int	fLatitude;
    private int fBow;
    private int fStern;
    private int fPort;
    private int fStarboard;
    private int	fTypeOfEPFD;
    private int	fUTCSecond;
    private boolean	fOffPositionIndicator;
    private int	fRegional;
    private boolean	fRAIMFlag;
    private boolean	fVirtualAidFlag;
    private boolean	fAssignedModeFlag;
    private String fNameExtension;

    /**
     * Constructor.
     *
     * @param content Six-bit message content.
     */
    public AISMessage21Parser(Sixbit content) {
        super(content, 272, 361);
        fAidType = content.getInt(FROM[AIDTYPE], TO[AIDTYPE]);
        fName = content.getString(FROM[NAME], TO[NAME]);
        fPositionAccuracy = content.getBoolean(TO[POSITIONACCURACY]);
        fLongitude = content.getAs28BitInt(FROM[LONGITUDE], TO[LONGITUDE]);
        if (!Longitude28.isCorrect(fLongitude))
            addViolation(new AISRuleViolation("LongitudeInDegrees", fLongitude, Longitude28.RANGE));
        fLatitude = content.getAs27BitInt(FROM[LATITUDE], TO[LATITUDE]);
        if (!Latitude27.isCorrect(fLatitude))
            addViolation(new AISRuleViolation("LatitudeInDegrees", fLatitude, Latitude27.RANGE));
        fBow = content.getInt(FROM[BOW], TO[BOW]);
        fStern = content.getInt(FROM[STERN], TO[STERN]);
        fPort = content.getInt(FROM[PORT], TO[PORT]);
        fStarboard = content.getInt(FROM[STARBOARD], TO[STARBOARD]);
        fTypeOfEPFD = content.getInt(FROM[TYPEOFEPFD], TO[TYPEOFEPFD]);
        fUTCSecond = content.getInt(FROM[UTC_SECOND], TO[UTC_SECOND]);
        fOffPositionIndicator = content.getBoolean(TO[OFFPOSITIONINDICATOR]);
        fRegional = content.getInt(FROM[REGIONAL], TO[REGIONAL]);
        fRAIMFlag = content.getBoolean(TO[RAIMFLAG]);
        fVirtualAidFlag = content.getBoolean(TO[VIRTUALAIDFLAG]);
        fAssignedModeFlag = content.getBoolean(TO[ASSIGNEDMODEFLAG]);
        fNameExtension = content.getString(FROM[NAMEEXTENSION], TO[NAMEEXTENSION]).trim();
    }

    public int getAidType() {
        return fAidType;
    }

    public String getName() {
        return this.fName;
    }

    public boolean isAccurate() {
        return fPositionAccuracy;
    }

    public double getLongitudeInDegrees() { return Longitude28.toDegrees(fLongitude); }

    public double getLatitudeInDegrees() { return Latitude27.toDegrees(fLatitude); }

    public int getBow() {
        return this.fBow;
    }

    public int getStern() {
        return this.fStern;
    }

    public int getPort() {
        return this.fPort;
    }

    public int getStarboard() {
        return this.fStarboard;
    }

    public int getTypeOfEPFD() { return fTypeOfEPFD; }

    public int getUtcSecond() { return fUTCSecond; }

    public boolean getOffPositionIndicator() {
        return fOffPositionIndicator;
    }

    public int getRegional() {
        return fRegional;
    }

    public boolean getRAIMFlag() {
        return fRAIMFlag;
    }

    public boolean getVirtualAidFlag() {
        return fVirtualAidFlag;
    }

    public boolean getAssignedModeFlag() {
        return fAssignedModeFlag;
    }

    public String getNameExtension() {
        return fNameExtension;
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
        String result = "\tAid Type:      " + NavAidType.toString(fAidType);
        result += SEPARATOR + "Name:      " + this.fName;
        result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
        result += SEPARATOR + "Lon:     " + Longitude28.toString(fLongitude);
        result += SEPARATOR + "Lat:     " + Latitude27.toString(fLatitude);
        String dim = "Bow: " + this.fBow + ", Stern: " + this.fStern + ", Port: " + this.fPort + ", Starboard: " + this.fStarboard + " [m]";
        result += SEPARATOR + "Dim:       " + dim;
        result += SEPARATOR + "Sec:     " + getUtcSecond();
        result += SEPARATOR + "Off Position Indicator: " + (fOffPositionIndicator ? "yes" : "no");
        result += SEPARATOR + "Regional:     " + getRegional();
        result += SEPARATOR + "RAIM Flag: " + (fRAIMFlag ? "yes" : "no");
        result += SEPARATOR + "Virtual Aid Flag: " + (fVirtualAidFlag ? "yes" : "no");
        result += SEPARATOR + "Assigned Mode Flag: " + (fAssignedModeFlag ? "yes" : "no");
        result += SEPARATOR + "Name Extension:      " + this.fNameExtension;
        return result;
    }
}

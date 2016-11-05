/*
 * AISUTCParser.java
 * Copyright (C) 2015 Lázár József
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

import net.sf.marineapi.ais.message.AISUTCReport;
import net.sf.marineapi.ais.util.AISRuleViolation;
import net.sf.marineapi.ais.util.Latitude27;
import net.sf.marineapi.ais.util.Longitude28;
import net.sf.marineapi.ais.util.PositionInfo;
import net.sf.marineapi.ais.util.PositioningDevice;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * AIS Base station and Mobile Station UTC reporting
 * 
 * <pre>
 * Field  Name                                          Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	  messageID                        		       	   6	(   1,   6)
 *  2	  repeatIndicator                         		   2	(   7,   8)
 *  3	  userID                                  		  30	(   9,  38)
 *  4	  utcYear                        		          14	(  39,  52)
 *  5	  utcMonth                            	    	   4	(  53,  56)
 *  6	  utcDay                                  		   5	(  57,  61)
 *  7	  utcHour                                 		   5	(  62,  66)
 *  8	  utcMinute                              		   6	(  67,  72)
 *  9	  utcSecond                          	    	   6	(  73,  78)
 * 10	  positionAccuracy                    	    	   1	(  79,  79)
 * 11	  longitude                               		  28	(  80, 107)
 * 12	  latitude                              		  27	( 108, 134)
 * 13	  typeOfElectronicPositionFixingDevice    		   4	( 135, 138)
 * 14	  transmissionControlForLongRangeBroadcastMessage  1	( 139, 139)
 * 15	  spare                                   	 	   9	( 140, 148)
 * 16	  raimFlag                              		   1	( 149, 149)
 * 17	  communicationState                      		  19	( 150, 168)
 *                                                      ---- +
 *                                                  sum  168
 * </pre>
 * 
 * @author Lázár József
 */
class AISUTCParser extends AISMessageParser implements AISUTCReport {

	private final static String	SEPARATOR			= "\n\t";
	private static final int	UTC_YEAR			= 0;
	private static final int	UTC_MONTH			= 1;
	private static final int	UTC_DAY				= 2;
	private static final int	UTC_HOUR			= 3;
	private static final int 	UTC_MINUTE			= 4;
	private static final int 	UTC_SECOND			= 5;
	private static final int 	POSITIONACCURACY	= 6;
	private static final int	LONGITUDE			= 7;
	private static final int 	LATITUDE			= 8;
	private static final int 	FIXING_DEV_TYPE		= 9;
	private final static int[]	FROM				= {
		38, 52, 56, 61, 66, 72, 78,  79, 107, 134};
	private final static int[]	TO   				= {
		52, 56, 61, 66, 72, 78, 79, 107, 134, 138};

	private int		fUTCYear;
	private int		fUTCMonth;
	private int		fUTCDay;
	private int		fUTCHour;
	private int		fUTCMinute;
	private int		fUTCSecond;
	private boolean	fPositionAccuracy;
	private double	fLongitude;
	private double	fLatitude;
	private int		fTypeOfEPFD;

	public AISUTCParser(Sixbit content) {
		super(content);
		if (content.length() != 168)
			throw new IllegalArgumentException("Wrong message length");
		
	    fUTCYear = content.getInt(FROM[UTC_YEAR], TO[UTC_YEAR]);
	    fUTCMonth = content.getInt(FROM[UTC_MONTH], TO[UTC_MONTH]);
	    fUTCDay = content.getInt(FROM[UTC_DAY], TO[UTC_DAY]);
	    fUTCHour = content.getInt(FROM[UTC_HOUR], TO[UTC_HOUR]);
	    fUTCMinute = content.getInt(FROM[UTC_MINUTE],TO[UTC_MINUTE]);
	    fUTCSecond = content.getInt(FROM[UTC_SECOND], TO[UTC_SECOND]);
	    fPositionAccuracy = content.getBoolean(FROM[POSITIONACCURACY]);
	    fLongitude = Longitude28.toDegrees(content.getAs28BitInt(FROM[LONGITUDE], TO[LONGITUDE]));
	    if (!PositionInfo.isLongitudeCorrect(fLongitude))
	    	fViolations.add(new AISRuleViolation("LongitudeInDegrees", fLongitude, PositionInfo.LONGITUDE_RANGE));
	    fLatitude = Latitude27.toDegrees(content.getAs27BitInt(FROM[LATITUDE], TO[LATITUDE]));
	    if (!PositionInfo.isLatitudeCorrect(fLatitude))
	    	fViolations.add(new AISRuleViolation("LatitudeInDegrees", fLatitude, PositionInfo.LATITUDE_RANGE));
	    fTypeOfEPFD = content.getInt(FROM[FIXING_DEV_TYPE], TO[FIXING_DEV_TYPE]);
	}

	public int getUtcYear() { return fUTCYear; }

	public int getUtcMonth() { return fUTCMonth; }

	public int getUtcDay() { return fUTCDay; }

	public int getUtcHour() { return fUTCHour; }

	public int getUtcMinute() { return fUTCMinute; }

	public int getUtcSecond() { return fUTCSecond; }

	public boolean getPositionAccuracy() { return fPositionAccuracy; }

	public double getLongitudeInDegrees() { return fLongitude; }

	public double getLatitudeInDegrees() { return fLatitude; }

	public int getTypeOfEPFD() { return fTypeOfEPFD; }
	
	@Override
	public String toString() {
		String result =     "\tYear:    " + getUtcYear();
		result += SEPARATOR + "Month:   " + getUtcMonth();
		result += SEPARATOR + "Day:     " + getUtcDay();
		result += SEPARATOR + "Hour:    " + getUtcHour();
		result += SEPARATOR + "Minute:  " + getUtcMinute();
		result += SEPARATOR + "Sec:     " + getUtcSecond();
		result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
		result += SEPARATOR + "Lon:     " + PositionInfo.longitudeToString(fLongitude);
		result += SEPARATOR + "Lat:     " + PositionInfo.latitudeToString(fLatitude);
		result += SEPARATOR + "EPFD:    " + PositioningDevice.toString(fTypeOfEPFD);
		return result;
	}
}

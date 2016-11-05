/*
 * AISPositionReportParser.java
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

import java.text.DecimalFormat;

import net.sf.marineapi.ais.message.AISPositionReport;
import net.sf.marineapi.ais.util.AISRuleViolation;
import net.sf.marineapi.ais.util.Angle12;
import net.sf.marineapi.ais.util.Angle9;
import net.sf.marineapi.ais.util.Latitude27;
import net.sf.marineapi.ais.util.Longitude28;
import net.sf.marineapi.ais.util.ManeuverIndicator;
import net.sf.marineapi.ais.util.NavigationalStatus;
import net.sf.marineapi.ais.util.PositionInfo;
import net.sf.marineapi.ais.util.RateOfTurn;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.ais.util.TimeStamp;

/**
 * Parser for all position report messages.
 * 
 * <pre>
 * Field Name                                    Bits    (from,  to)
 * ------------------------------------------------------------------------
 *  1    messageID                                  6    (   1,   6)
 *  2    repeatIndicator                            2    (   7,   8)
 *  3    userID                                    30    (   9,  38)
 *  4    navigationalStatus                         4    (  39,  42)
 *  5    rateOfTurn                                 8    (  43,  50)
 *  6    speedOverGround                           10    (  51,  60)
 *  7    positionAccuracy                           1    (  61,  61)
 *  8    longitude                                 28    (  62,  89)
 *  9    latitude                                  27    (  90, 116)
 * 10    courseOverGround                          12    ( 117, 128)
 * 11    trueHeading                                9    ( 129, 137)
 * 12    timeStamp                                  6    ( 138, 143)
 * 13    specialManoeuvre                           2    ( 144, 145)
 * 14    spare                                      3    ( 146, 148)
 * 15    raimFlag                                   1    ( 149, 149)
 * 16    communicationState                        19    ( 150, 168)
 *                                               ---- +
 *                                          sum   168
 * </pre>
 * 
 * @author Lázár József
 */
class AISPositionReportParser extends AISMessageParser implements AISPositionReport {

	private final static String	SEPARATOR				= "\n\t";
	private final static int	NAVIGATIONALSTATUS		= 0;
	private final static int	RATEOFTURN				= 1;
	private final static int	SPEEDOVERGROUND			= 2;
	private final static int	POSITIONACCURACY		= 3;
	private final static int	LONGITUDE				= 4;
	private final static int	LATITUDE				= 5;
	private final static int	COURSEOVERGROUND		= 6;
	private final static int	TRUEHEADING				= 7;
	private final static int	TIMESTAMP				= 8;
	private final static int	MANOEUVER				= 9;
	private final static int[]	FROM					= {
		38, 42, 50, 60, 61,  89, 116, 128, 137, 143};
	private final static int[]	TO   					= {
		42, 50, 60, 61, 89, 116, 128, 137, 143, 145};

	private int		fNavigationalStatus;
	private int		fRateOfTurn;
	private int		fSOG;
	private boolean	fPositionAccuracy;
	private double	fLongitude;
	private double	fLatitude;
	private int		fCOG;
	private int		fTrueHeading;
	private int		fTimeStamp;
	private int		fManouverIndicator;

	/**
	 * Constructs an AIS Message Position Report parser.
	 */
	public AISPositionReportParser(Sixbit content) {
		super(content);
		if (content.length() != 168)
			throw new IllegalArgumentException("Wrong message length");
		
	    fNavigationalStatus = content.getInt(FROM[NAVIGATIONALSTATUS], TO[NAVIGATIONALSTATUS]);
	    if (!NavigationalStatus.isCorrect(fNavigationalStatus))
	    	fViolations.add(new AISRuleViolation("NavigationalStatus", fNavigationalStatus, NavigationalStatus.RANGE));
	    fRateOfTurn = content.getAs8BitInt(FROM[RATEOFTURN], TO[RATEOFTURN]);
	    fSOG = content.getInt(FROM[SPEEDOVERGROUND], TO[SPEEDOVERGROUND]);
	    fPositionAccuracy = content.getBoolean(FROM[POSITIONACCURACY]);
	    fLongitude = Longitude28.toDegrees(content.getAs28BitInt(FROM[LONGITUDE], TO[LONGITUDE]));
	    if (!PositionInfo.isLongitudeCorrect(fLongitude))
	    	fViolations.add(new AISRuleViolation("LongitudeInDegrees", fLongitude, PositionInfo.LONGITUDE_RANGE));
	    fLatitude = Latitude27.toDegrees(content.getAs27BitInt(FROM[LATITUDE], TO[LATITUDE]));
	    if (!PositionInfo.isLatitudeCorrect(fLatitude))
	    	fViolations.add(new AISRuleViolation("LatitudeInDegrees", fLatitude, PositionInfo.LATITUDE_RANGE));
	    fCOG = content.getInt(FROM[COURSEOVERGROUND], TO[COURSEOVERGROUND]);
	    if (!Angle12.isCorrect(fCOG))
	    	fViolations.add(new AISRuleViolation("CourseOverGround", fCOG, Angle12.RANGE));
	    fTrueHeading = content.getInt(FROM[TRUEHEADING], TO[TRUEHEADING]);
	    if(!Angle9.isCorrect(fTrueHeading))
	    	fViolations.add(new AISRuleViolation("TrueHeading", fTrueHeading, Angle9.RANGE));
	    fTimeStamp = content.getInt(FROM[TIMESTAMP], TO[TIMESTAMP]);
	    fManouverIndicator = content.getInt(FROM[MANOEUVER], TO[MANOEUVER]);
	    if (!ManeuverIndicator.isCorrect(fManouverIndicator))
	    	fViolations.add(new AISRuleViolation("ManouverIndicator", fManouverIndicator, ManeuverIndicator.RANGE));
	}

	public int getNavigationalStatus() { return fNavigationalStatus; }

	public int getRateOfTurn() { return fRateOfTurn; }

	public int getSpeedOverGround() { return fSOG; }

	public String getSOGString() {
		String msg;
		if (fSOG == 1023)
			msg = "no SOG";
		else if (fSOG == 1022)
			msg = ">=102.2";
		else
			msg = new DecimalFormat("##0.0").format(fSOG / 10.0);
		return msg;
	}

	public boolean getPositionAccuracy() { return fPositionAccuracy; }

	public double getLongitudeInDegrees() { return fLongitude; }

	public double getLatitudeInDegrees() { return fLatitude; }

	public int getCourseOverGround() { return fCOG; }

	public int getTrueHeading() { return fTrueHeading; }

	public int getTimeStamp() { return fTimeStamp; }

	public int getManouverIndicator() { return fManouverIndicator; }

	public String toString() {
		String result =     "\tNav st:  " + NavigationalStatus.toString(fNavigationalStatus);
		result += SEPARATOR + "ROT:     " + RateOfTurn.toString(fRateOfTurn);
		result += SEPARATOR + "SOG:     " + getSOGString();
		result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
		result += SEPARATOR + "Lat:     " + PositionInfo.longitudeToString(fLongitude);
		result += SEPARATOR + "Lon:     " + PositionInfo.latitudeToString(fLatitude);
		result += SEPARATOR + "COG:     " + Angle12.toString(fCOG);
		result += SEPARATOR + "Heading: " + Angle9.getTrueHeadingString(fTrueHeading);
		result += SEPARATOR + "Time:    " + TimeStamp.toString(fTimeStamp);
		result += SEPARATOR + "Man ind: " + ManeuverIndicator.toString(fManouverIndicator);
		return result;
	}
}

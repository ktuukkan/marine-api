/*
 * AISPositionReportBParser.java
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

import net.sf.marineapi.ais.message.AISPositionReportB;
import net.sf.marineapi.ais.util.AISRuleViolation;
import net.sf.marineapi.ais.util.Angle12;
import net.sf.marineapi.ais.util.Angle9;
import net.sf.marineapi.ais.util.Latitude27;
import net.sf.marineapi.ais.util.Longitude28;
import net.sf.marineapi.ais.util.PositionInfo;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.ais.util.TimeStamp;

/**
 * Implementation for AIS Message 18 and 19:  Class B Equipment Position Report.
 * 
 * <pre>
 * Field    Name                                    Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1       messageID                                  6    (   1,   6)
 *  2       repeatIndicator                            2    (   7,   8)
 *  3       userID                                    30    (   9,  38)
 *  4       spare1                                     8    (  39,  46)
 *  5       speedOverGround                           10    (  47,  56)
 *  6       positionAccuracy                           1    (  57,  57)
 *  7       longitude                                 28    (  58,  85)
 *  8       latitude                                  27    (  86, 112)
 *  9       courseOverGround                          12    ( 113, 124)
 * 10       trueHeading                                9    ( 125, 133)
 * 11       timeStamp                                  6    ( 134, 139)
 * </pre>
 * 
 * @author Lázár József
 */
class AISPositionReportBParser extends AISMessageParser implements AISPositionReportB {

	protected final static String	SEPARATOR			= "\n\t";

	private final static int		SPEEDOVERGROUND			= 0;
	private final static int		POSITIONACCURACY		= 1;
	private final static int		LONGITUDE				= 2;
	private final static int		LATITUDE				= 3;
	private final static int		COURSEOVERGROUND		= 4;
	private final static int		TRUEHEADING				= 5;
	private final static int		TIMESTAMP				= 6;
	private final static int[]		FROM				= {
		46, 56, 57,  85, 112, 124, 133};
	private final static int[]		TO					= {
		56, 57, 85, 112, 124, 133, 139};

	private int		fSOG;
	private boolean	fPositionAccuracy;
	private double	fLongitude;
	private double	fLatitude;
	private int		fCOG;
	private int		fTrueHeading;
	private int		fTimeStamp;

	public AISPositionReportBParser(Sixbit content) {
		super(content);
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
	    	fViolations.add(new AISRuleViolation("getCourseOverGround", fCOG, Angle12.RANGE));
	    fTrueHeading = content.getInt(FROM[TRUEHEADING], TO[TRUEHEADING]);
	    if (!Angle9.isCorrect(fTrueHeading))
	    	fViolations.add(new AISRuleViolation("getTrueHeading",fTrueHeading, Angle9.RANGE));
	    fTimeStamp = content.getInt(FROM[TIMESTAMP], TO[TIMESTAMP]);
	}

	public int getSpeedOverGround() { return fSOG; }

	public boolean getPositionAccuracy() { return fPositionAccuracy; }

	public double getLongitudeInDegrees() { return fLongitude; }

	public double getLatitudeInDegrees() { return fLatitude; }

	public int getCourseOverGround() { return fCOG; }

	public int getTrueHeading() { return fTrueHeading; }

	public int getTimeStamp() { return fTimeStamp; }

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

	public String toString() {
		String result =     "\tSOG:     " + getSOGString();
		result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
		result += SEPARATOR + "Lat:     " + PositionInfo.longitudeToString(fLongitude);
		result += SEPARATOR + "Lon:     " + PositionInfo.latitudeToString(fLatitude);
		result += SEPARATOR + "COG:     " + Angle12.toString(fCOG);
		result += SEPARATOR + "Heading: " + Angle9.getTrueHeadingString(fTrueHeading);
		result += SEPARATOR + "Time:    " + TimeStamp.toString(fTimeStamp);
		return result;		
	}
}

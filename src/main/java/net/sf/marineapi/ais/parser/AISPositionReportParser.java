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

import net.sf.marineapi.ais.message.AISPositionReport;
import net.sf.marineapi.ais.util.AISRuleViolation;
import net.sf.marineapi.ais.util.Angle12;
import net.sf.marineapi.ais.util.Angle9;
import net.sf.marineapi.ais.util.Latitude27;
import net.sf.marineapi.ais.util.Longitude28;
import net.sf.marineapi.ais.util.ManeuverIndicator;
import net.sf.marineapi.ais.util.NavigationalStatus;
import net.sf.marineapi.ais.util.RateOfTurn;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.ais.util.SpeedOverGround;
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
	private int		fLongitude;
	private int		fLatitude;
	private int		fCOG;
	private int		fTrueHeading;
	private int		fTimeStamp;
	private int		fManouverIndicator;

	/**
	 * Constructs an AIS Message Position Report parser.
	 *
	 * @param content Six-bit message content.
	 */
	public AISPositionReportParser(Sixbit content) {
		super(content, 168, 204);

	    fNavigationalStatus = content.getInt(FROM[NAVIGATIONALSTATUS], TO[NAVIGATIONALSTATUS]);
	    if (!NavigationalStatus.isCorrect(fNavigationalStatus))
	    	addViolation(new AISRuleViolation("NavigationalStatus", fNavigationalStatus, NavigationalStatus.RANGE));
	    fRateOfTurn = content.getAs8BitInt(FROM[RATEOFTURN], TO[RATEOFTURN]);
	    fSOG = content.getInt(FROM[SPEEDOVERGROUND], TO[SPEEDOVERGROUND]);

	    // FIXME check indices, should be 61-61?
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
	    fTrueHeading = content.getInt(FROM[TRUEHEADING], TO[TRUEHEADING]);
	    if(!Angle9.isCorrect(fTrueHeading))
	    	addViolation(new AISRuleViolation("TrueHeading", fTrueHeading, Angle9.RANGE));
	    fTimeStamp = content.getInt(FROM[TIMESTAMP], TO[TIMESTAMP]);
	    fManouverIndicator = content.getInt(FROM[MANOEUVER], TO[MANOEUVER]);
	    if (!ManeuverIndicator.isCorrect(fManouverIndicator))
	    	addViolation(new AISRuleViolation("ManouverIndicator", fManouverIndicator, ManeuverIndicator.RANGE));
	}

	public int getNavigationalStatus() {
	    return fNavigationalStatus;
	}

	public double getRateOfTurn() {
	    return RateOfTurn.toDegreesPerMinute(fRateOfTurn);
	}

	public double getSpeedOverGround() {
		return SpeedOverGround.toKnots(fSOG);
	}

	public boolean isAccurate() {
	    return fPositionAccuracy;
	}

	public double getLongitudeInDegrees() {
	    return Longitude28.toDegrees(fLongitude);
	}

	public double getLatitudeInDegrees() {
	    return Latitude27.toDegrees(fLatitude);
	}

	public double getCourseOverGround() {
	    return Angle12.toDegrees(fCOG);
	}

	public int getTrueHeading() {
	    return fTrueHeading;
	}

	public int getTimeStamp() {
	    return fTimeStamp;
	}

	public int getManouverIndicator() {
	    return fManouverIndicator;
	}

	@Override
	public boolean hasRateOfTurn() {
		return RateOfTurn.isTurnIndicatorAvailable(fRateOfTurn);
	}

	@Override
	public boolean hasSpeedOverGround() {
		return SpeedOverGround.isAvailable(fSOG);
	}

	@Override
	public boolean hasCourseOverGround() {
		return Angle12.isAvailable(fCOG);
	}

	@Override
	public boolean hasTrueHeading() {
		return Angle9.isAvailable(fTrueHeading);
	}

	@Override
	public boolean hasTimeStamp() {
		return TimeStamp.isAvailable(fTimeStamp);
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
		String result =     "\tNav st:  " + NavigationalStatus.toString(fNavigationalStatus);
		result += SEPARATOR + "ROT:     " + RateOfTurn.toString(fRateOfTurn);
		result += SEPARATOR + "SOG:     " + SpeedOverGround.toString(fSOG);
		result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
		result += SEPARATOR + "Lon:     " + Longitude28.toString(fLongitude);
		result += SEPARATOR + "Lat:     " + Latitude27.toString(fLatitude);
		result += SEPARATOR + "COG:     " + Angle12.toString(fCOG);
		result += SEPARATOR + "Heading: " + Angle9.getTrueHeadingString(fTrueHeading);
		result += SEPARATOR + "Time:    " + TimeStamp.toString(fTimeStamp);
		result += SEPARATOR + "Man ind: " + ManeuverIndicator.toString(fManouverIndicator);
		return result;
	}
}

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

import net.sf.marineapi.ais.message.AISPositionReportB;
import net.sf.marineapi.ais.util.AISRuleViolation;
import net.sf.marineapi.ais.util.Angle12;
import net.sf.marineapi.ais.util.Angle9;
import net.sf.marineapi.ais.util.Latitude27;
import net.sf.marineapi.ais.util.Longitude28;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.ais.util.SpeedOverGround;
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
 * TODO: missing "Class B" flags 13 - 20.
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
	private int		fLongitude;
	private int		fLatitude;
	private int		fCOG;
	private int		fTrueHeading;
	private int		fTimeStamp;

	/**
	 * Constructor.
	 *
	 * @param content Six-bit message content.
	 */
	public AISPositionReportBParser(Sixbit content) {
		super(content);
		this.parse(content);
	}

	/**
	 * Constructor with message length validation.
	 *
	 * @param content Six-bit message content.
	 * @param len Expected content length (bits)
	 * @throws IllegalArgumentException If content length is not as expected.
	 */
	public AISPositionReportBParser(Sixbit content, int len) {
		super(content, len);
		this.parse(content);
	}

	private void parse(Sixbit content) {
		fSOG = content.getInt(FROM[SPEEDOVERGROUND], TO[SPEEDOVERGROUND]);
		fPositionAccuracy = content.getBoolean(FROM[POSITIONACCURACY]);
		fLongitude = content.getAs28BitInt(FROM[LONGITUDE], TO[LONGITUDE]);
		if (!Longitude28.isCorrect(fLongitude))
			addViolation(new AISRuleViolation("LongitudeInDegrees", fLongitude, Longitude28.RANGE));
		fLatitude = content.getAs27BitInt(FROM[LATITUDE], TO[LATITUDE]);
		if (!Latitude27.isCorrect(fLatitude))
			addViolation(new AISRuleViolation("LatitudeInDegrees", fLatitude, Latitude27.RANGE));
		fCOG = content.getInt(FROM[COURSEOVERGROUND], TO[COURSEOVERGROUND]);
		if (!Angle12.isCorrect(fCOG))
			addViolation(new AISRuleViolation("getCourseOverGround", fCOG, Angle12.RANGE));
		fTrueHeading = content.getInt(FROM[TRUEHEADING], TO[TRUEHEADING]);
		if (!Angle9.isCorrect(fTrueHeading))
			addViolation(new AISRuleViolation("getTrueHeading",fTrueHeading, Angle9.RANGE));
		fTimeStamp = content.getInt(FROM[TIMESTAMP], TO[TIMESTAMP]);
	}

	public double getSpeedOverGround() { return SpeedOverGround.toKnots(fSOG); }

	public boolean isAccurate() { return fPositionAccuracy; }

	public double getLongitudeInDegrees() { return Longitude28.toDegrees(fLongitude); }

	public double getLatitudeInDegrees() { return Latitude27.toDegrees(fLatitude); }

	public double getCourseOverGround() { return Angle12.toDegrees(fCOG); }

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

	public int getTrueHeading() { return fTrueHeading; }

	public int getTimeStamp() { return fTimeStamp; }

	public String toString() {
		String result =     "\tSOG:     " + SpeedOverGround.toString(fSOG);
		result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
		result += SEPARATOR + "Lon:     " + Longitude28.toString(fLongitude);
		result += SEPARATOR + "Lat:     " + Latitude27.toString(fLatitude);
		result += SEPARATOR + "COG:     " + Angle12.toString(fCOG);
		result += SEPARATOR + "Heading: " + Angle9.getTrueHeadingString(fTrueHeading);
		result += SEPARATOR + "Time:    " + TimeStamp.toString(fTimeStamp);
		return result;		
	}
}

/*
 * AISMessage08DAC200FID10Parser.java
 * Copyright (C) 2018 Paweł Kozioł
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

import net.sf.marineapi.ais.message.AISMessage08DAC200FID10;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * AIS Message 8 subtype, DAC = 200, FID = 10:
 * Inland ship static and voyage related data (Inland AIS).
 *
 * <pre>
 * Field  Name                                      Bits    (from, to )
 * -------------------------------------------------------------------------
 *  1     messageID                                    6    (   1,   6)
 *  2     repeatIndicator                              2    (   7,   8)
 *  3     userID                                      30    (   9,  38)
 *  4     spare                                        2    (  39,  40)
 *  5     designatedAreaCode                          10    (  41,  50)
 *  6     functionalID                                 6    (  51,  56)
 *  7     European Vessel ID                          48    (  57, 104)
 *  7     Length of ship                              13    (  58, 117)
 *  7     Beam of ship                                10    ( 118, 127)
 *  7     Ship/combination type                       14    ( 128, 141)
 *  7     Hazardous cargo                              3    ( 142, 144)
 *  7     Draught                                     11    ( 145, 155)
 *  7     Loaded/Unloaded                              2    ( 156, 157)
 *  7     Speed inf. quality                           1    ( 158, 158)
 *  7     Course inf. quality                          1    ( 159, 159)
 *  7     Heading inf. quality                         1    ( 160, 160)
 *  7     spare2                                       8    ( 161, 168)
 *                                                  ---- +
 *                                               sum 168
 * </pre>
 *
 * @author Paweł Kozioł
 */
class AISMessage08DAC200FID10Parser extends AISMessage08Parser implements AISMessage08DAC200FID10 {

	private final static int MSG_TYPE = 8;
	private final static int MSG_DAC = 200;
	private final static int MSG_FID = 10;
	private final static int MSG_LEN = 168;

	private final static String	SEPARATOR				= "\n\t";
	private final static int	VIN			            = 0;
	private final static int	SHIP_LENGTH		        = 1;
	private final static int	SHIP_BEAM				= 2;
	private final static int	SHIP_TYPE				= 3;
	private final static int	HAZARD_CODE        		= 4;
	private final static int	DRAUGHT				    = 5;
	private final static int	LOAD_STATUS				= 6;
	private final static int	SPEED_QUALITY			= 7;
	private final static int	COURSE_QUALITY			= 8;
	private final static int	HEADING_QUALITY			= 9;
	private final static int[]	FROM					= {
			 56, 104, 117, 127, 141, 144, 155, 157, 158, 159};
	private final static int[]	TO   					= {
			104, 117, 127, 141, 144, 155, 157, 158, 159, 160};

	public static class Factory extends AISMessage08Parser.Factory {

		@Override
		public boolean canCreate(AISMessageParser parser) {
			return parser.getMessageType() == MSG_TYPE
					&& parser.getSixbit().length() == MSG_LEN
					&& getDAC(parser.getSixbit()) == MSG_DAC
					&& getFID(parser.getSixbit()) == MSG_FID;
		}

		@Override
		public AISMessage08DAC200FID10Parser create(AISMessageParser parser) {
			return new AISMessage08DAC200FID10Parser(parser.getSixbit());
		}
	}

	private String vin;
	private double shipLength;
	private double shipBeam;
	private int shipType;
	private int hazardCode;
	private double draught;
	private int loadStatus;
	private boolean speedQuality;
	private boolean courseQuality;
	private boolean headingQuality;

	public AISMessage08DAC200FID10Parser(Sixbit content) {
		super(content);
		if (content.length() != 168)
			throw new IllegalArgumentException("Wrong message length");

		vin = content.getString(FROM[VIN], TO[VIN]);
		shipLength = 0.1 * content.getInt(FROM[SHIP_LENGTH], TO[SHIP_LENGTH]);
		shipBeam = 0.1 * content.getInt(FROM[SHIP_BEAM], TO[SHIP_BEAM]);
		shipType = content.getInt(FROM[SHIP_TYPE], TO[SHIP_TYPE]);
		hazardCode = content.getInt(FROM[HAZARD_CODE], TO[HAZARD_CODE]);
		draught = 0.01 * content.getInt(FROM[DRAUGHT], TO[DRAUGHT]);
		loadStatus = content.getInt(FROM[LOAD_STATUS], TO[LOAD_STATUS]);
		speedQuality = content.getBoolean(FROM[SPEED_QUALITY]);
		courseQuality = content.getBoolean(FROM[COURSE_QUALITY]);
		headingQuality = content.getBoolean(FROM[HEADING_QUALITY]);
	}

	@Override
	public String getENI() {
		return vin;
	}

	@Override
	public double getShipLength() {
		return shipLength;
	}

	@Override
	public double getShipBeam() {
		return shipBeam;
	}

	@Override
	public int getShipType() {
		return shipType;
	}

	@Override
	public int getHazardCode() {
		return hazardCode;
	}

	@Override
	public double getDraught() {
		return draught;
	}

	@Override
	public int getLoadStatus() {
		return loadStatus;
	}

	@Override
	public boolean getSpeedQuality() {
		return speedQuality;
	}

	@Override
	public boolean getCourseQuality() {
		return courseQuality;
	}

	@Override
	public boolean getHeadingQuality() {
		return headingQuality;
	}

	public String toString() {
		String result =     "\tDAC:             " + getDAC();
		result += SEPARATOR + "FID:             " + getFID();
		result += SEPARATOR + "VIN:             " + getENI();
		result += SEPARATOR + "Ship length:     " + getShipLength();
		result += SEPARATOR + "Ship beam:       " + getShipBeam();
		result += SEPARATOR + "Ship type:       " + getShipType();
		result += SEPARATOR + "Hazard code:     " + getHazardCode();
		result += SEPARATOR + "Draught:         " + getDraught();
		result += SEPARATOR + "Load status:     " + getLoadStatus();
		result += SEPARATOR + "Speed quality:   " + (speedQuality ? "high" : "low");
		result += SEPARATOR + "Course quality:  " + (courseQuality ? "high" : "low");
		result += SEPARATOR + "Heading quality: " + (headingQuality ? "high" : "low");
		return result;
	}
}

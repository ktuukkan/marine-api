/*
 * AISMessage19Parser.java
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

import net.sf.marineapi.ais.message.AISMessage19;
import net.sf.marineapi.ais.util.PositioningDevice;
import net.sf.marineapi.ais.util.ShipType;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * AIS Message 19 implementation: Extended Class B Equipment Position Report.
 * 
 * The first part of the message is handled by AISPositionReportBParser.
 * 
 * <pre>
 * Field   Name                                    Bits    (from, to )
 * ------------------------------------------------------------------------
 * 12      spare2                                     4    ( 140, 143)
 * 13      name                                     120    ( 144, 263)
 * 14      typeOfShipAndCargoType                     8    ( 264, 271)
 * 15      dimension                                 30    ( 272, 301)
 * 16      typeOfElectronicPositionFixingDevice       4    ( 302, 305)
 * 17      raimFlag                                   1    ( 306, 306)
 * 18      dte                                        1    ( 307, 307)
 * 19      assignedModeFlag                           1    ( 308, 308)
 * 20      spare3                                     4    ( 309, 312)
 *                                                ---- +
 *                                             sum  312
 * </pre>
 * 
 * @author Lázár József
 */
class AISMessage19Parser extends AISPositionReportBParser implements AISMessage19 {

	private static final int	NAME				= 0;
	private static final int	TYPEOFSHIPANDCARGO	= 1;
	private static final int	BOW					= 2;
	private static final int	STERN				= 3;
	private static final int	PORT				= 4;
	private static final int	STARBOARD			= 5;
	private static final int	TYPEOFEPFD			= 6;
	private final static int[]	FROM				= {
		143, 263, 271, 280, 289, 295, 301};
	private final static int[]	TO					= {
		263, 271, 280, 289, 295, 301, 305};

	private String		fName;
	private int			fShipAndCargoType;
	private int			fBow;
	private int			fStern;
	private int			fPort;
	private int			fStarboard;
	private int			fTypeOfEPFD;

	public AISMessage19Parser(Sixbit content) {
		super(content);
		if (content.length() != 312)
			throw new IllegalArgumentException("Wrong message length");

		fName = content.getString(FROM[NAME], TO[NAME]);
		fShipAndCargoType = content.getInt(FROM[TYPEOFSHIPANDCARGO], TO[TYPEOFSHIPANDCARGO]);

		fBow = content.getInt(FROM[BOW], TO[BOW]); 
		fStern = content.getInt(FROM[STERN], TO[STERN]);
		fPort = content.getInt(FROM[PORT], TO[PORT]);
		fStarboard = content.getInt(FROM[STARBOARD], TO[STARBOARD]);

		fTypeOfEPFD = content.getInt(FROM[TYPEOFEPFD], TO[TYPEOFEPFD]);
	}

	public String getName() { return fName; }

	public int getTypeOfShipAndCargoType() { return fShipAndCargoType; }

	public int getBow() { return fBow; }

	public int getStern() { return fStern; }

	public int getPort() { return fPort; }

	public int getStarboard() { return fStarboard; }

	public int getTypeOfEPFD() { return fTypeOfEPFD; }

	public String toString () {
		String result = super.toString();
		result += SEPARATOR + "Name:    " + fName;
		result += SEPARATOR + "Type:    " + ShipType.shipTypeToString(fShipAndCargoType);
		String dim = "Bow: " + fBow + ", Stern: " + fStern +
				", Port: " + fPort + ", Starboard: " + fStarboard + " [m]";
		result += SEPARATOR + "Dim:     " + dim;
		result += SEPARATOR + "EPFD:    " + PositioningDevice.toString(fTypeOfEPFD);
		return result;		
	}
}

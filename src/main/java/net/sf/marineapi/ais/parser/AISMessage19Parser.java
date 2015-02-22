package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.sentence.AISMessage19;
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
public class AISMessage19Parser extends AISPositionReportBParser implements AISMessage19 {

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

	public AISMessage19Parser(Sixbit content) throws Exception {
		super(content);
		if (content.length() != 312)
			throw new Exception("Wrong message length");

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

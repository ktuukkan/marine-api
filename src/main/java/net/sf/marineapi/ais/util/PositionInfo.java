package net.sf.marineapi.ais.util;

import java.text.DecimalFormat;

/**
 * Checks the position information for validity.
 * 
 * @author Lázár József
 */
public class PositionInfo {

	private static final DecimalFormat	COORD_FORMAT = new DecimalFormat("##0.000000;-##0.000000");
	private static final int			LONGITUDE_NOTAVAILABLE = 181;
	public static final String			LONGITUDE_RANGE			=
			"[-180.0, 180.0] + {" + LONGITUDE_NOTAVAILABLE + "}";

	private static final int			LATITUDE_NOTAVAILABLE = 91;
	public static final String 			LATITUDE_RANGE =
			"[-90.0, 90.0] + {" + LATITUDE_NOTAVAILABLE + "}";

	/**
	 * @return true true if the value is correct
	 */
	public static boolean isLongitudeCorrect(double value) {
		return isLongitudeAvailable(value) || (value == LONGITUDE_NOTAVAILABLE);
	}
	
	/**
	 * Checks if the longitude value is available.
	 * @return true if the angular is not the default value
	 */
	public static boolean isLongitudeAvailable(double value) {
		return (-180 <= value) && (value <= 180); 
	}
	
	/**
	 * Returns the longitude as a string.
	 * @return a string representing the longitude
	 */
	public static String longitudeToString(double value) {
		String msg;
		if (isLongitudeCorrect(value)) {
			if (isLongitudeAvailable(value)) {
				msg = COORD_FORMAT.format(value);
			}
			else
				msg = "longitude not available";	
		}
		else
			msg = "invalid longitude";
		return msg;
	}

	/**
	 * @return true if the latitude value is correct
	 */
	public static boolean isLatitudeCorrect(double value){
		return isLatitudeAvailable(value) || (value == LATITUDE_NOTAVAILABLE);
	}
	
	/**
	 * Checks if the latitude value is available.
	 * @return true if the latitude is available
	 */
	public static boolean isLatitudeAvailable(double value) {
		return (-90 <= value) && (value <= 90); 
	}

	/**
	 * Returns the latitude as a string.
     * @return a string representing the latitude
	 */
	public static String latitudeToString(double value) {
		String msg;
		if (isLatitudeCorrect(value)) {
			if (isLatitudeAvailable(value))
				msg = COORD_FORMAT.format(value);
			else
				msg = "latitude not available";	
		}
		else
			msg = "invalid latitude";
		return msg;
	}
}
package net.sf.marineapi.ais.util;

/**
 * Checks a 2-bit signed integer maneuver value for validity.
 * 
 * @author Lázár József
 */
public class ManeuverIndicator {

	private static final int	DEFAULTVALUE	= 0;
	private static final int	MINVALUE		= 1 ;
	private static final int	MAXVALUE		= 2;
	public static final String	RANGE =
			"[" + MINVALUE + "," + MAXVALUE + "] + {" + DEFAULTVALUE + "}";

	/**
	 * Checks if the value is in the correct range.
	 * @return true if the value is correct
	 */
	public static boolean isCorrect(int value) {
		return (MINVALUE <= value && value <= MAXVALUE) || (value == DEFAULTVALUE);
	}
	
	/**
	 * Checks if the maneuver value is available.
	 * @return true if the value is not the default value
	 */
	public static boolean isAvailable(int value) {
		return value != DEFAULTVALUE;
	}
	
	/**
	 * @return a string representing the maneuvre indicator value
	 */
	public static String toString(int value) {
		switch (value) {
			case 0:
				return "no special maneuver indicator";
			case 1:
				return "not in special maneuver";
			case 2:
				return "in special maneuver";
			default:
				return "invalid special maneuver indicator";
		}
	}
}
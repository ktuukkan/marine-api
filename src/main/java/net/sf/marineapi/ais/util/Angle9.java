package net.sf.marineapi.ais.util;

/**
 * Checks a 9-bit signed integer angular value for validity.
 * 
 * @author Lázár József
 */
public class Angle9 {

	private static final int	DEFAULTVALUE	= 511;
	private static final int	MINVALUE		= 0 ;
	private static final int	MAXVALUE		= 359;
	public static final String	RANGE			=
			"[" + MINVALUE + "," + MAXVALUE + "] + {" + DEFAULTVALUE + "}";
	
	/**
	 * @return true if the value is in the correct range
	 */
	public static boolean isCorrect(int value) {
		return (MINVALUE <= value && value <= MAXVALUE) || (value == DEFAULTVALUE);
	}
	
	/**
	 * Checks if the angular value is available.
	 * @return true if the angular is not the default value
	 */
	public static boolean isAvailable(int value) {
		return value != DEFAULTVALUE;
	}
	
	public static String getTrueHeadingString(int value) {
		String headingString;
		if (value == DEFAULTVALUE)
			headingString = "no heading";
		else if (value > MAXVALUE)
			headingString = "invalid heading";
		else
			headingString = Integer.toString(value);
		return headingString;
	}

	/**
	 * @return a string representing the angular value
	 */
	public static String toString(int value) {
		String msg;
		if (isCorrect(value)) {
			if (isAvailable(value))
				msg = Integer.toString(value);
			else 
				msg = "not available";
		}
		else
			msg = "illegal value";
		return msg;
	}
}
package net.sf.marineapi.ais.util;

/**
 * Checks a rate-of-turn value for validity.
 * 
 * @author Lázár József
 */
public class RateOfTurn {

	private static final int DEFAULTVALUE = -0x80;
	private static final int MINVALUE = -126;
	private static final int MAXVALUE = 126;

	/**
	 * Checks if the ROT value is available.
	 * @return true if the ROT is not the default value
	 */
	public static boolean isTurnInformationAvailable(int value) {
		return value != DEFAULTVALUE;
	}

	/**
	 * Checks if a turn indicator is available.
	 * @return true if the turn indicator is available
	 */
	public static boolean isTurnIndicatorAvailable(int value) {
		return (MINVALUE <= value) && (value <= MAXVALUE);
	}

	/**
	 * Converts the rate-of-turn value to a estimate degrees/minute value.
	 * @return degrees/minute value (positive sign indicates turning right)
	 */
	public static double toDegreesPerMinute(int value) {
		if (isTurnIndicatorAvailable(value)) {
			double v = value / 4.733;
			double v2 = v * v;
			if (value < 0)
				return -v2;
			else
				return v2;
		}
		else
			return 0d;
	}		
	
	/**
	 * @return string representation of the ROT information
	 */
	public static String toString(int value) {
		String direction;
		if (value < 0)
			direction = "left";
		else
			direction = "right";
		
		switch (Math.abs(value)) {
			case 128:
				return "no turn information available (default)";
			case 127:
				return "turning " + direction + " at more than 5 degrees per 30 s (No TI available)";
			case 126:
				return "turning " + direction + " at 708 degrees per min or higher";
			case 0:
				return "not turning";
			default : 
				return "turning " + direction + " at " +
						Math.abs(toDegreesPerMinute(value)) + " degrees per min";
		}
	}
}
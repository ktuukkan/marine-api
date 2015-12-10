package net.sf.marineapi.ais.util;

/**
 * Checks a 6-bit integer time stamp value for validity.
 * 
 * @author Lázár József
 */
public class TimeStamp {

	private static final int MINVALUE = 0;
	private static final int MAXVALUE = 59;

	/**
	 * Checks if the time stamp value is available.
	 * @return true if the time stamp falls within a range
	 */
	public static boolean isAvailable(int value) {
		return (value >= MINVALUE && value <= MAXVALUE); 
	}
	
	/**
	 * Return a string representing the time stamp value.
	 * @return a string representing the time stamp
	 */
	public static String toString(int value) {
		switch (value) {
		case 60:
			return "no time stamp";
		case 61: 
			return "positioning system manual";
		case 62:
			return "dead reckoning";
		case 63:
			return "positioning system inoperative";
		default:
			return Integer.toString(value);
		}
	}
}

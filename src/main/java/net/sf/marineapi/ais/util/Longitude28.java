package net.sf.marineapi.ais.util;

/**
 * Checks a 28-bit signed integer longitude value for validity.
 * 
 * @author Lázár József
 */
public class Longitude28 {

	private static final double	TO_DEGREES	= 1.0 / (60.0 * 10000.0);

	/**
	 * Converts the longitude value (in 1/10000 minutes) to degrees.
	 * @return the longitude value in degrees
	 */
	public static double toDegrees(int value) {
		return value * TO_DEGREES; 
	}
}
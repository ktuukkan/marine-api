package net.sf.marineapi.ais.util;

/**
 * Checks a 27-bit signed integer latitude value for validity.
 * 
 * @author Lázár József
 */
public class Latitude27 {

	private static final double TO_DEGREES = 1.0 / (60.0 * 10000.0);

	/**
	 * Converts the latitude value (in 1/10000 minutes) to degrees.
	 * @return the latitude value in degrees
	 */
	public static double toDegrees(int value) {
		return value * TO_DEGREES; 
	}
}
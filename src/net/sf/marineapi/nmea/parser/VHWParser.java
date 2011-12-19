/**
 * 
 */
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.VHWSentence;

/**
 * @author Warren Zahra
 * 
 */
public class VHWParser extends SentenceParser implements VHWSentence {

	/**
	 * 
	 */
	private static final int TRUE = 0;
	private static final int DEGREES_TRUE = 1;
	private static final int MAGNETIC = 2;
	private static final int DEGREES_MAGNETIC = 3;
	private static final int KNOTS = 4;
	private static final int SPEED_KNOTS = 5;
	private static final int KILOMETRES = 6;
	private static final int SPEED_KILOMETRES = 7;

	public VHWParser(String nmea) {
		super(nmea);
	}

	public double getDegreesTrue() {
		return getDoubleValue(TRUE);
	}

	public double getDegreesMagnetic() {
		return getDoubleValue(MAGNETIC);
	}

	public double getSpeedKnots() {
		return getDoubleValue(KNOTS);
	}

	public double getSpeedKilometres() {
		return getDoubleValue(KILOMETRES);
	}

}

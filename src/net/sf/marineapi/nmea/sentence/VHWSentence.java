/**
 * 
 */
package net.sf.marineapi.nmea.sentence;

/**
 * @author Warren Zahra
 *
 */
public interface VHWSentence {
	
	public double getDegreesTrue();
	public double getDegreesMagnetic();
	public double getSpeedKnots();
	public double getSpeedKilometres();

}

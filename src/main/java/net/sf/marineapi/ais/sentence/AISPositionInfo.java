package net.sf.marineapi.ais.sentence;

/**
 * Interface for all position information.
 * 
 * @author Lázár József
 */
public interface AISPositionInfo extends AISMessage {
	
    /**
	 * Returns the position accuracy.
	 */
	public boolean getPositionAccuracy();

	/**
	 * Returns the longitude in degrees.
	 */
	public double getLongitudeInDegrees();
	
	/**
	 * Returns the latitude in degrees.
	 */
	public double getLatitudeInDegrees();
}

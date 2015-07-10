package net.sf.marineapi.ais.message;

/**
 * Interface for all position information.
 * 
 * @author Lázár József
 */
interface AISPositionInfo extends AISMessage {
	
    /**
	 * Returns the position accuracy.
	 */
	boolean getPositionAccuracy();

	/**
	 * Returns the longitude in degrees.
	 */
	double getLongitudeInDegrees();
	
	/**
	 * Returns the latitude in degrees.
	 */
	double getLatitudeInDegrees();
}

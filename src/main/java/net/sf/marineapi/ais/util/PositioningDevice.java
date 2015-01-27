package net.sf.marineapi.ais.util;

/**
 * Checks the positioning device type for validity.
 * 
 * @author Lázár József
 */
public class PositioningDevice {
	
	/**
	 * Returns a text string for the EPFD.
	 * @return a text string describing the positioning device type
	 */
	static public String toString (int deviceType) {
		switch (deviceType) {
		case 0:
			return "undefined device";
		case 1:
			return "GPS";
		case 2:
			return "GLONASS";
		case 3:
			return "combined GPS/GLONASS";
		case 4:
			return "Loran-C";
		case 5:
			return "Chayka";
		case 6:
			return "integrated navigation system";
		case 7:
			return "surveyed";
		case 8:
			return "Galileo";
		case 15:
			return "internal GNSS";
		default:
			return "not used";
		}
	}
}

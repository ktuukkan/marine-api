package net.sf.marineapi.ais.util;

/**
 * Checks the navigational status for validity.
 * 
 * @author Lázár József
 */
public class NavigationalStatus {

	public static final String		RANGE = "[0,8] + [14,15]";
	final static private String[]	VALUES = {
		"under way using engine",				// 0
		"at anchor",							// 1
		"not under command",					// 2
		"restricted manoeuvrability",			// 3
		"constrained by her draught",			// 4
		"moored",								// 5
		"aground",								// 6
		"engaged in fishing",					// 7
		"under way sailing",					// 8
		"reserved for future amendment",		// 9
		"reserved for future amendment",		//10
		"reserved for future use",				//11
		"reserved for future use",				//12
		"reserved for future use",				//13
		"AIS-SART (active)",					//14
		"not defined"							//15
	};
	
	/**
	 * @return text string describing the navigational status
	 */
	public static String toString(int value) {
		if (value >= 0 && value <=15)
			return VALUES[value];
		else
			return VALUES[15];
	}
	
	/**
	 * @return true if the status falls within the range
	 */
	public static boolean isCorrect(int value) {
		return (0 <= value) && (value <= 15) &&
			   !((9 <= value) && (value <= 13));
	}
}

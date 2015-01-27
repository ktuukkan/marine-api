package net.sf.marineapi.ais.util;

/**
 * Class holding information about a violation against an AIS rule.
 * 
 * @author jol
 */
public class AISRuleViolation implements Violation {

	private String	fPlaceOfViolation;
	private Object	fCurrentValue;
	private String	fValidRange;

	public AISRuleViolation(String where, Object value, String range) {
		fPlaceOfViolation = where;
		fCurrentValue = value;
		fValidRange = range;
	}
	
	public String toString() {
		return "Violation: Value " + fCurrentValue.toString() +
				" in " + fPlaceOfViolation +
				" is outside the allowed range (" + fValidRange + ")";
	}
}

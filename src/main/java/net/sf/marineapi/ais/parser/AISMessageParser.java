package net.sf.marineapi.ais.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.ais.util.Violation;

/**
 * Base class for all AIS messages.
 * 
 * It contains only the rule violation messages.
 * 
 * @author Lázár József
 */
public class AISMessageParser {

	protected List<Violation> fViolations = new ArrayList<Violation>();
	
	/**
	 * Add a new rule violation to this message
	 */
	public void addViolation(Violation v) {
		fViolations.add(v);
	}

	public int getNrOfViolations() {
		return fViolations.size();
	}

	public List<Violation> getViolations() {
		return fViolations;
	}
}

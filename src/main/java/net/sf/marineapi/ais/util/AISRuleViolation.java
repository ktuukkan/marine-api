/*
 * AISRuleViolation.java
 * Copyright (C) 2015 Lázár József
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 *
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.ais.util;

/**
 * Class holding information about a violation against an AIS rule.
 * 
 * @author Lázár József
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

/*
 * ShipType.java
 * Copyright (C) 2015-2020 Lázár József, Joshua Sweaney
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
 * Checks the ship type for validity.
 * 
 * @author Lázár József, Joshua Sweaney
 */
public class ShipType {

	final static private String[] SPECIAL = {
			"Pilot vessel",
			"Search and rescue vessel",
			"Tug",
			"Port tender",
			"Vessel with anti-pollution capability",
			"Law enforcement vessel",
			"Spare - for local vessels",
			"Spare - for local vessels",
			"Medical transport",
			"Ship not party to an armed conflict"
	};

	final static private String[] FIRST_DIGIT = {
			"Not specified",
			"Reserved for future use",
			"WIG",
			"Vessel",
			"HSC",
			"See above",
			"Passenger ship",
			"Cargo ship",
			"Tanker",
			"Other types of ship"
	};
	
	final static private String[] SECOND_DIGIT = {
			"General",
			"Category X",
			"Category Y",
			"Category Z",
			"Category OS",
			"Reserved for future use",
			"Reserved for future use",
			"Reserved for future use",
			"Reserved for future use",
			"No additional information"
	};

	final static private String[] VESSEL = {
			"Fishing",
			"Towing",
			"Towing and exceeding",
			"Engaged in dredging or underwater operations",
			"Engaged in diving operations",
			"Engaged in military operations",
			"Sailing",
			"Pleasure craft",
			"Reserved for future use",
			"Reserved for future use"
	};
	
	/**
	 * Return string describing the ship and cargo type.
	 *
	 * @param type Ship and cargo type indicator
	 * @return a text string describing the ship and cargo type.
	 */
	static public String shipTypeToString (int type) {
		String typeStr ="N/A";
		if (0 <= type && type <= 255) {
			if (type >= 200)
				return "Reserved for future use";
			if (type >= 100)
				return "Reserved for regional use";
			if (type == 0)
				return "Not available / no ship";

			int d1 = type / 10;
			int d2 = type % 10;
			if (0 <= d1 && d1 <= 9 && 0 <= d2 && d2 <= 9) {
				switch(d1) {
					case 0:
						return FIRST_DIGIT[0] + " " + Integer.toString(type);
					case 3:
						return FIRST_DIGIT[3] + ", " + VESSEL[d2];
					case 5: 
						return SPECIAL[d2];
					default:
						return FIRST_DIGIT[d1] + ", " + SECOND_DIGIT[d2]; 
				}
			}
		}
		return typeStr;
	}

	/**
	 * Returns a string describing the first digit. Describes the type of vessel.
	 * @param type Ship and cargo type indicator
	 * @return String a text string describing the ship type digit
	 */
	static public String shipTypeToVesselString(int type) {
		String typeStr ="N/A";
		if (0 <= type && type <= 255) {
			if (type >= 200)
				return "Reserved for future use";
			if (type >= 100)
				return "Reserved for regional use";
			if (type == 0)
				return "Not available / no ship";

			int d1 = type / 10;
			int d2 = type % 10;
			if (0 <= d1 && d1 <= 9 && 0 <= d2 && d2 <= 9) {
				switch(d1) {
					case 0:
						return FIRST_DIGIT[0] + " " + Integer.toString(type); // Ship type not specified, so just print whole type number
					case 3:
						return FIRST_DIGIT[3]; // Vessel
					case 5: 
						return SPECIAL[d2]; // Describes special vessel types for vessels in U.S. waters
					default:
						return FIRST_DIGIT[d1]; // Ship type
				}
			}
		}
		return typeStr;		
	}

	/**
	 * Returns a string describing the second digit. Usually describes the type
	 * of cargo being carried, but may also describe the activity the vessel is engaged in.
	 * @param type Ship and cargo type indicator
	 * @return String a text string describing the cargo type digit
	 */
	static public String shipTypeToCargoString(int type) {
		String typeStr ="N/A";
		if (0 <= type && type <= 255) {
			if (type >= 200)
				return "Reserved for future use";
			if (type >= 100)
				return "Reserved for regional use";
			if (type == 0)
				return "Not available / no ship";

			int d1 = type / 10;
			int d2 = type % 10;
			if (0 <= d1 && d1 <= 9 && 0 <= d2 && d2 <= 9) {
				switch(d1) {
					case 0:
						return FIRST_DIGIT[0] + " " + Integer.toString(type); // Cargo not specified, so just print whole type number
					case 3:
						return VESSEL[d2]; // Describes an activity the vessel is engaged in
					case 5: 
						return SPECIAL[d2]; // Describes special vessel types for vessels in U.S. waters
					default:
						return SECOND_DIGIT[d2]; // Describes the cargo being carried
				}
			}
		}
		return typeStr;
	}
}
/*
 * NavAidType.java
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
 * Checks the NavAid type for validity.
 * 
 * @author Henri Laurent
 */
public class NavAidType {
	
	/**
	 * Returns a text string for the NavAid.
	 *
	 * @param deviceType Device type value to Stringify.
	 * @return a text string describing the Nav Aid type
	 */
	static private String[] navAidTypes = {
	        "Default, Type of Aid to Navigation not specified",
	        "Reference point",
	        "RACON (radar transponder marking a navigation hazard)",
	        "Fixed structure off shore, such as oil platforms, wind farms, rigs",
	        "Spare, Reserved for future use",
	        "Light, without sectors",
	        "Light, with sectors",
	        "Leading Light Front",
	        "Leading Light Rear",
	        "Beacon, Cardinal N",
	        "Beacon, Cardinal E",
	        "Beacon, Cardinal S",
	        "Beacon, Cardinal W",
	        "Beacon, Port hand",
	        "Beacon, Starboard hand",
	        "Beacon, Preferred Channel port hand",
	        "Beacon, Preferred Channel starboard hand",
	        "Beacon, Isolated danger",
	        "Beacon, Safe water",
	        "Beacon, Special mark",
	        "Cardinal Mark N",
	        "Cardinal Mark E",
	        "Cardinal Mark S",
	        "Cardinal Mark W",
	        "Port hand Mark",
	        "Starboard hand Mark",
	        "Preferred Channel Port hand",
	        "Preferred Channel Starboard hand",
	        "Isolated danger",
	        "Safe Water",
	        "Special Mark",
	        "Light Vessel / LANBY / Rigs",
	        "not used"
	    };

	    /**
	     * Returns a text string for the NavAid.
	     *
	     * @param deviceType Device type value to Stringify.
	     * @return a text string describing the Nav Aid type
	     */
	    static public String toString(int deviceType) {
	        if (deviceType >= 0 && deviceType < navAidTypes.length) {
	            return navAidTypes[deviceType];
	        }
	        return "not used";
	    }
}

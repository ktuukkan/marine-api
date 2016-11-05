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
	 * @return a text string describing the Nav Aid type
	 */
	static public String toString (int deviceType) {
		switch (deviceType) {
			case 0:
				return "Default, Type of Aid to Navigation not specified";
			case 1:
				return "Reference point";
			case 2:
				return "RACON (radar transponder marking a navigation hazard)";
			case 3:
				return "Fixed structure off shore, such as oil platforms, wind farms, rigs";
			case 4:
				return "Spare, Reserved for future use";
			case 5:
				return "Light, without sectors";
			case 6:
				return "Light, with sectors";
			case 7:
				return "Leading Light Front";
			case 8:
				return "Leading Light Rear";
			case 9:
				return "Beacon, Cardinal N";
			case 10:
				return "Beacon, Cardinal E";
			case 11:
				return "Beacon, Cardinal S";
			case 12:
				return "Beacon, Cardinal W";
			case 13:
				return "Beacon, Port hand";
			case 14:
				return "Beacon, Starboard hand";
			case 15:
				return "Beacon, Preferred Channel port hand";
			case 16:
				return "Beacon, Preferred Channel starboard hand";
			case 17:
				return "Beacon, Isolated danger";
			case 18:
				return "Beacon, Safe water";
			case 19:
				return "Beacon, Special mark";
			case 20:
				return "Cardinal Mark N";
			case 21:
				return "Cardinal Mark E";
			case 22:
				return "Cardinal Mark S";
			case 23:
				return "Cardinal Mark W";
			case 24:
				return "Port hand Mark";
			case 25:
				return "Starboard hand Mark";
			case 26:
				return "Preferred Channel Port hand";
			case 27:
				return "Preferred Channel Starboard hand";
			case 28:
				return "Isolated danger";
			case 29:
				return "Safe Water";
			case 30:
				return "Special Mark";
			case 31:
				return "Light Vessel / LANBY / Rigs";
			default:
				return "not used";
		}
	}
}

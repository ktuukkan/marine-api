/* 
 * NMEA.java
 * Copyright (C) 2010 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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
package net.sf.marineapi.nmea.sentence;

/**
 * Utility methods for general sentence operations.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public final class NMEA {

    // no instantiation
    private NMEA() {
    }

    /**
     * Validates of the specified String against NMEA 0183 sentence format.
     * <p>
     * Sentence is considered valid if it (1) begins with '$' followed by 5 char
     * ID, (2) is at most 80 characters long (without &lt;CR&gt;&lt;LF&gt;), (3)
     * contains no illegal characters and (4) has a correct checksum.
     * <p>
     * Sentences without a checksum are validated by checking the general
     * sentence characteristics.
     * 
     * @param nmea NMEA sentence String
     * @return <code>true</code> if valid, otherwise <code>false</code>.
     */
    public static boolean isValid(String nmea) {
        if (nmea == null || "".equals(nmea)) {
            return false;
        }

        final String re;
        int chkd = nmea.indexOf(String.valueOf(Sentence.CHECKSUM_DELIMITER));

        if (chkd < 0) {
            re = "^[$]{1}[A-Z0-9]{5}[,][A-Za-z0-9,. -]{0,73}$";
            return nmea.matches(re);
        }

        re = "^[$]{1}[A-Z0-9]{5}[,][A-Za-z0-9,. -]{0,70}[*][A-F0-9]{2}$";
        int start = nmea.indexOf(Sentence.CHECKSUM_DELIMITER) + 1;
        String chk = nmea.substring(start, nmea.length());

        return nmea.matches(re) && chk.equals(Checksum.calculate(nmea));
    }

}

/* 
 * SentenceValidator.java
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
 * <p>
 * SentenceValidator checks any String against NMEA 0183 format.
 * <p>
 * Any String is considered as a valid NMEA sentence if it meets the following
 * criteria:
 * <ul>
 * <li>String begins with '$' character
 * <li>Begin character is followed by 5 upper-case chars (sentence type id)
 * <li>String is max. 80 chars long (without &lt;CR&gt;&lt;LF&gt;)
 * <li>String does not contain any illegal characters
 * <li>Has a correct checksum, separated by '*' character (unless omitted)
 * </ul>
 * <p>
 * Sentences without checksum are validated only by checking the general
 * sentence characteristics.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public final class SentenceValidator {

    private SentenceValidator() {
    }

    /**
     * Validates of the specified String against NMEA 0183 sentence format.
     * 
     * @param nmea String to validate
     * @return <code>true</code> if valid, otherwise <code>false</code>.
     */
    public static boolean isValid(String nmea) {
        if (nmea == null || "".equals(nmea)) {
            return false;
        }

        final String re;
        int chkd = nmea.indexOf(String.valueOf(Sentence.CHECKSUM_DELIMITER));

		// printable ASCII chars 0x20 to 0x7E

        if (chkd < 0) {
			re = "^[$|!]{1}[A-Z0-9]{5}[,][\\x20-\\x7F]{0,75}$";
            return nmea.matches(re);
        }

		re = "^[$|!]{1}[A-Z0-9]{5}[,][\\x20-\\x7F]{0,72}[*][A-F0-9]{2}$";
        int start = nmea.indexOf(Sentence.CHECKSUM_DELIMITER) + 1;
        String chk = nmea.substring(start, nmea.length());

        return nmea.matches(re) && chk.equals(Checksum.calculate(nmea));
    }

}

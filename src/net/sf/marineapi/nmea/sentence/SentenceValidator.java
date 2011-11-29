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
 * SentenceValidator checks any String against NMEA 0183 format.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public final class SentenceValidator {

    private SentenceValidator() {
    }

    /**
     * Tells if the specified String matches the NMEA 0183 sentence format.
     * <p>
     * String is considered as a sentence if it meets the following criteria:
     * <ul>
     * <li>Starts with '$' character
     * <li>'$' is followed by 5 upper-case chars and a comma (sentence type id)
     * <li>Length is max. 80 chars long (excluding &lt;CR&gt;&lt;LF&gt;)
     * <li>String contains only printable ASCII characters
     * <li>Checksum is correct and separated by '*' char (unless omitted)
     * </ul>
     * 
     * @param nmea String to inspect
     * @return true if recognized as sentence, otherwise false.
     */
    public static boolean isSentence(String nmea) {

        if (nmea == null || "".equals(nmea)) {
            return false;
        }

        int i = nmea.indexOf(Sentence.CHECKSUM_DELIMITER);

        // printable ASCII chars 0x20 to 0x7E
        String re = "^[$|!]{1}[A-Z0-9]{5}[,][\\x20-\\x7F]{0,72}[*][A-F0-9]{2}$";
        if (i < 0) {
            re = "^[$|!]{1}[A-Z0-9]{5}[,][\\x20-\\x7F]{0,75}$";
        }

        return nmea.matches(re);
    }

    /**
     * Tells if the specified String is a valid NMEA 0183 sentence. String is
     * considered as valid sentence if it passes the {@link #isSentence(String)}
     * test and contains a valid checksum. Sentences without checksum are
     * validated only by checking the general sentence characteristics.
     * 
     * @param nmea String to validate
     * @return <code>true</code> if valid, otherwise <code>false</code>.
     */
    public static boolean isValid(String nmea) {

        boolean isValid = false;

        if (SentenceValidator.isSentence(nmea)) {
            int i = nmea.indexOf(Sentence.CHECKSUM_DELIMITER);
            if (i > 0) {
                String sum = nmea.substring(++i, nmea.length());
                isValid = sum.equals(Checksum.calculate(nmea));
            } else {
                // no checksum
                isValid = true;
            }
        }

        return isValid;
    }
}

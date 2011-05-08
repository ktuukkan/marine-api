/* 
 * Checksum.java
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
 * Provides Sentence checksum calculation and utilities.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public final class Checksum {

    private Checksum() {
    }

    /**
     * Calculates and adds a checksum to specified sentence String. Existing
     * checksum will be replaced with the new one.
     * <p>
     * For example, <br>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A</code><br>
     * results in <br>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*26</code>
     * <p>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*00</code><br>
     * results in <br>
     * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*26</code>
     * 
     * @param sentence Sentence in String representation
     * @return The specified String with checksum added.
     */
    public static String add(String sentence) {

        String str = sentence;

        int i = str.indexOf(Sentence.CHECKSUM_DELIMITER);
        if (i != -1) {
            str = str.substring(0, i);
        }

        return str + Sentence.CHECKSUM_DELIMITER + calculate(str);
    }

    /**
     * Calculates the checksum of sentence String. Checksum is a XOR of each
     * character between, but not including, the $ and * characters. The
     * resulting hex value is returned as a String in two digit format, padded
     * with a leading zero if necessary. The method will calculate the checksum
     * for any given String and the sentence validity is not checked.
     * 
     * @param nmea NMEA Sentence with or without checksum.
     * @return Checksum hex value, padded with leading zero if necessary.
     */
    public static String calculate(String nmea) {
        char ch;
        int sum = 0;
        for (int i = 0; i < nmea.length(); i++) {
            ch = nmea.charAt(i);
            if (ch == Sentence.BEGIN_CHAR) {
                continue;
            } else if (ch == Sentence.CHECKSUM_DELIMITER) {
                break;
            } else if (sum == 0) {
                sum = (byte) ch;
            } else {
                sum ^= (byte) ch;
            }
        }
        return String.format("%02X", sum);
    }

}

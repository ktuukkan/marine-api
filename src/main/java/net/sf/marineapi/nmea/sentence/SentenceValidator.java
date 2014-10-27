/*
 * SentenceValidator.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.sentence;

import java.util.regex.Pattern;

/**
 * SentenceValidator for detecting and validation of sentence Strings.
 *
 * @author Kimmo Tuukkanen
 */
public final class SentenceValidator {

	private static final Pattern reChecksum = Pattern.compile(
		"^[$|!]{1}[A-Z0-9]{3,10}[,][\\x20-\\x7F]*[*][A-F0-9]{2}(\\r|\\n|\\r\\n|\\n\\r){0,1}$");

	private static final Pattern reNoChecksum = Pattern.compile(
		"^[$|!]{1}[A-Z0-9]{3,10}[,][\\x20-\\x7F]*(\\r|\\n|\\r\\n|\\n\\r){0,1}$");

	private SentenceValidator() {
	}

	/**
	 * <p>
	 * Tells if the specified String matches the NMEA 0183 sentence format.</p>
	 * <p>
	 * String is considered as a sentence if it meets the following criteria:
	 * <ul>
	 * <li>First character is '$' or '!'
	 * <li>Begin char is followed by upper-case sentence ID (3 to 10 chars)
	 * <li>Sentence ID is followed by a comma and an arbitrary number of
	 *     printable ASCII characters (payload data)
	 * <li>Data is followed by '*' and a two-char hex checksum (may be omitted)
	 * </ul>
	 * <p>
	 * Notice that format matching is not strict; although NMEA 0183 defines a
	 * maximum length of 80 chars, the sentence length is not checked. This is
	 * due to fact that it seems quite common that devices violate this rule,
	 * some perhaps deliberately, some by mistake. Thus, assuming the formatting
	 * is otherwise valid, it is not feasible to strictly validate length and
	 * discard sentences that just exceed the 80 chars limit.
	 * </p>
	 *
	 * @param nmea String to inspect
	 * @return true if recognized as sentence, otherwise false.
	 */
	public static boolean isSentence(String nmea) {

		if (nmea == null || "".equals(nmea)) {
			return false;
		}

		if (Checksum.index(nmea) == nmea.length()) {
			return reNoChecksum.matcher(nmea).matches();
		}

		return reChecksum.matcher(nmea).matches();
	}

	/**
	 * Tells if the specified String is a valid NMEA 0183 sentence. String is
	 * considered as valid sentence if it passes the {@link #isSentence(String)}
	 * test and contains correct checksum. Sentences without checksum are
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

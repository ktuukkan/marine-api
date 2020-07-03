/*
 * Copyright (C) 2020 Gunnar Hillert
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

import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.sentence.Sentence;

/**
 *
 * @author Gunnar Hillert
 *
 */
public interface UBXSentence extends Sentence {

	/**
	 * @return The numeric u-blox proprietary message identifier
	 */
	Integer getMessageId();

	/**
	 * Parse integer value from the specified sentence field.
	 *
	 * @param index Field index in sentence
	 * @return Field parsed by {@link SentenceParser}
	 */
	Integer getUBXFieldIntValue(int index);

	/**
	 * Parse {@link String} value from the specified sentence field.
	 *
	 * @param index Field index in sentence
	 * @return Field parsed by {@link SentenceParser}
	 */
	String getUBXFieldStringValue(int index);

	/**
	 * Parse char value from the specified sentence field.
	 *
	 * @param index Field index in sentence
	 * @return Field parsed by {@link SentenceParser}
	 */
	char getUBXFieldCharValue(int index);

	/**
	 * Parse double value from the specified sentence field.
	 *
	 * @param index Field index in sentence
	 * @return Field parsed by {@link SentenceParser}
	 */
	double getUBXFieldDoubleValue(int index);

	/**
	 * @return the number of data fields in the sentence, excluding ID field
	 * and checksum.
	 *
	 * @see SentenceParser
	 */
	int getUBXFieldCount();
}

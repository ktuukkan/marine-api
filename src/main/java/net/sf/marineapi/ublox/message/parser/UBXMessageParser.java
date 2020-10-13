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
package net.sf.marineapi.ublox.message.parser;

import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.sentence.UBXSentence;
import net.sf.marineapi.ublox.message.UBXMessage;

/**
 * This is the base class for all UBX message parser implementations and contains
 * common logic. In a sense it wraps the given {@link UBXSentence}. The {@link UBXMessageParser}
 * extends {@link SentenceParser} to access the fields from the NMEA message (UBXSentence).
 *
 * @author Gunnar Hillert
 *
 * @see UBXMessage00Parser
 * @see UBXMessage03Parser
 *
 */
public class UBXMessageParser extends SentenceParser implements UBXMessage {

	protected final UBXSentence sentence;

	/**
	 * Construct a parser with the given {@link UBXSentence}.
	 *
	 * @param sentence UBXSentence
	 */
	public UBXMessageParser(UBXSentence sentence) {
		super(sentence.toString());
		this.sentence = sentence;
	}

	/**
	 * @see UBXMessage#getMessageType()
	 */
	@Override
	public int getMessageType() {
		return this.sentence.getMessageId();
	}

}

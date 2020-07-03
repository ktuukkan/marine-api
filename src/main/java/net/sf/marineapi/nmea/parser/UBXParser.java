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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.UBXSentence;
import net.sf.marineapi.ublox.message.parser.UBXMessage00Parser;
import net.sf.marineapi.ublox.message.parser.UBXMessage03Parser;
import net.sf.marineapi.ublox.message.parser.UBXMessageParser;

/**
 * Common UBX sentence parser. These messages are often referred to as PUBX messages,
 * consisting of {@link TalkerId#P } + {@link SentenceId#UBX}.
 *
 * This parser only handles the NMEA layer. The actual payload message is parsed
 * by the {@link UBXMessageParser} and its sub-classes.
 *
 * @author Gunnar Hillert
 *
 * @see UBXSentence
 * @see UBXMessageParser
 * @see UBXMessage00Parser
 * @see UBXMessage03Parser
 */
public class UBXParser extends SentenceParser implements UBXSentence {

	public UBXParser(String nmea) {
		super(nmea, SentenceId.UBX);
	}

	/**
	 * Creates a new empty UBX Parser.
	 *
	 * @param talker TalkerId to set
	 */
	public UBXParser(TalkerId talker) {
		super(talker, SentenceId.UBX, 6);
	}

	public UBXParser(String nmea, String type) {
		super(nmea);
		if (type == null || "".equals(type)) {
			throw new IllegalArgumentException(
				"Sentence type must be specified.");
		}
		String sid = getSentenceId();
		if (!sid.equals(type)) {
			String ptrn = "Sentence id mismatch; expected [%s], found [%s].";
			String msg = String.format(ptrn, type, sid);
			throw new IllegalArgumentException(msg);
		}
	}

	@Override
	public Integer getMessageId() {
		return super.getIntValue(0);
	}

	@Override
	public Integer getUBXFieldIntValue(int index) {
		return super.getIntValue(index);
	}

	@Override
	public String getUBXFieldStringValue(int index) {
		return super.getStringValue(index);
	}

	@Override
	public char getUBXFieldCharValue(int index) {
		return super.getCharValue(index);
	}

	@Override
	public double getUBXFieldDoubleValue(int index) {
		return super.getDoubleValue(index);
	}

	@Override
	public int getUBXFieldCount() {
		return super.getFieldCount();
	}

}

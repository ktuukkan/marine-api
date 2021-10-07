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
package net.sf.marineapi.ublox.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.UBXSentence;
import net.sf.marineapi.ublox.message.UBXMessage03;
import net.sf.marineapi.ublox.util.UbloxSatelliteInfo;

/**
 * Test the parsing of messages of type {@link UBXMessage03}.
 *
 * @author Gunnar Hillert
 */
public class UBXMessage03Test {

	private final String message03 =
			"$PUBX,03,31,5,U,063,15,18,000,12,U,100,36,40,064,14,-,257,05,,000,18,-,219,67,20,000,20,e,180,19,21,000,21,e,223,25,21,000,24,e,161,10,29,052,25,U,067,61,36,023,26,-,317,07,,000,29,U,005,43,41,064,31,-,303,35,16,000,32,-,236,04,19,000,214,-,126,-2,,000,215,-,218,00,,000,219,-,172,03,23,000,221,U,332,72,21,000,222,U,032,27,33,064,224,e,355,51,39,064,234,U,094,70,28,024,235,U,334,45,32,064,241,U,130,22,41,064,47,U,116,15,39,064,57,U,040,21,18,000,65,-,072,-2,,000,66,-,127,01,,000,77,e,149,23,,000,78,U,128,78,25,000,79,U,336,43,36,000,81,e,325,52,,000,82,-,256,32,15,000,88,U,023,21,37,004*33";

	@Test
	public void testParsinfOfUBXMessage03() {

		final SentenceFactory sf = SentenceFactory.getInstance();
		final Sentence sentence = (Sentence) sf.createParser(message03);

		assertTrue("Not a UBXSentence.", sentence instanceof UBXSentence);

		final String sentenceId = sentence.getSentenceId();
		final int messageId = ((UBXSentence) sentence).getMessageId();

		assertEquals("UBX", sentenceId);
		assertEquals("Wrong messageId.", 3, messageId);

		final UBXMessage03Parser pubx03MessageParser = new UBXMessage03Parser((UBXSentence) sentence);

		assertEquals(31, pubx03MessageParser.getNumberOfTrackedSatellites());

		final List<UbloxSatelliteInfo> satellites = pubx03MessageParser.getSatellites();

		assertEquals(pubx03MessageParser.getNumberOfTrackedSatellites(), satellites.size());

	}

}

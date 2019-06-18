/* 
 * DTBParser.java
 * Copyright (C) 2019 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.DTBSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * DTB sentence parser.
 * 
 * @author Bob Schwarz
 * @see <a href="https://github.com/LoadBalanced/marine-api">marine-api fork</a>
 * 
 */
class DTBParser extends DTAParser implements DTBSentence {

	public static final String DTB_SENTENCE_ID = "DTB";
	
    /**
     * Creates a new instance of DTBParser with 8 data fields.
     * 
     * @param talker - DTB talkerId
     */
	public DTBParser(TalkerId talker) {
		super(talker, SentenceId.DTB, 8);
	}
	
    /**
     * Creates a new instance of DTBParser with specified data fields.
     * 
     * @param talker - DTB talkerId
     * @param size - number of data fields in DTBSentence (not counting header and checksum).
     */
	public DTBParser(TalkerId talker, int size) {
		super(talker, SentenceId.DTB, size);
	}	
	
    /**
     * Creates a new instance of DTBParser with specified type and data fields.
     * 
     * @param talker - DTB talkerId
     * @param type - SentenceId enum
     * @param size - number of data fields in DTBSentence (not counting header and checksum).
     * 
     */
	public DTBParser(TalkerId talker, SentenceId type, int size) {
		super(talker, type, size);
	}
		
    /**
     * Creates a new instance of DTBParser.
     * 
     * @param nmea - DTB sentence String
     */
	public DTBParser(String nmea) {
		super(nmea, SentenceId.DTB);
	}

    /**
     * Creates a new instance of DTBParser.
     * 
     * @param nmea - DTB sentence String
     * @param type - SentenceId enum
     */
	public DTBParser(String nmea, SentenceId type) {
		super(nmea, type);
	}
	
	/**
	 * Gets the hard-coded channel from Sentence for GasFinder2.  
	 * Since only GasFinder2 will send a DTB sentence, and since only
	 * GasFinderMC has channels, there will normally be no channel for DTBSentence.
	 * 
	 * @return Channel number 2.
	 */
	@Override
	public int getChannelNumber() {
		return 2;
	}
}

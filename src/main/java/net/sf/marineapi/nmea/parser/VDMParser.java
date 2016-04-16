package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.AISSentence;

/**
 * AIS VDM sentence parser, contains only the NMEA layer. The actual payload
 * message is parsed by AIS message parsers.
 * 
 * @author Lázár József
 * @see AISSentence
 * @see AISParser
 */
class VDMParser extends AISParser {

	/**
	 * Creates a new instance of VDMParser.
	 * 
	 * @param nmea NMEA sentence String.
	 */
	public VDMParser(String nmea) {
		super(nmea, SentenceId.VDM);
	}

	/**
	 * Creates a new empty VDMParser.
	 * 
	 * @param talker TalkerId to set
	 */
	public VDMParser(TalkerId talker) {
		super(talker, SentenceId.VDM);
	}
}

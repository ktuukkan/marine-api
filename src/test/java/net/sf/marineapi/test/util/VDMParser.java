package net.sf.marineapi.test.util;

import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Dummy parser simulating AIVDM parser with alternative begin character, for
 * testing the inheritance of SentenceParser and custom parsers registering in
 * SentenceFactory.
 */
public class VDMParser extends SentenceParser {

	public VDMParser(String s) {
		// just like any other parser, begin char comes in String param
		super(s, "VDM");
	}

	public VDMParser(TalkerId tid) {
		// alternative begin char is set here for empty sentences
		super('!', tid, "VDM", 3);
	}

	public String getValueA() {
		return getStringValue(0);
	}

	public String getValueB() {
		return getStringValue(1);
	}

	public String getValueC() {
		return getStringValue(2);
	}
}

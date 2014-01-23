package net.sf.marineapi.test.util;

import net.sf.marineapi.nmea.parser.SentenceParser;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Dummy parser imaginary sentence type, for testing the inheritance of
 * SentenceParser.
 */
public class FOOParser extends SentenceParser implements FOOSentence {

	public FOOParser(String s) {
		super(s, "FOO");
	}

	public FOOParser(TalkerId tid) {
		super(tid, "FOO", 3);
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

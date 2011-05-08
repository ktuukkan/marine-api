package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Dummy parser imaginary sentence type, for testing the inheritance of
 * SentenceParser.
 */
public class FOOParser extends SentenceParser {

    public FOOParser(String s) {
        super(s, "FOO");
    }

    public FOOParser() {
        super(TalkerId.GP, "FOO", 3);
    }

    public String getValueA() {
        return getStringValue(0);
    }

    public String getValueB() {
        return getStringValue(1);
    }

    public String getValueC() {
        return getStringValue(1);
    }
}

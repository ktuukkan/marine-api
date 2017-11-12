package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.STALKSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SeaTalk $STALK sentence parser.
 *
 * $STALK,cc,p1,p2..,pn*xx
 */
class STALKParser extends SentenceParser implements STALKSentence {

    private static int COMMAND = 0;
    private static int FIRST_PARAM = 1;

    /**
     * Construct with sentence.
     *
     * @param nmea <code>$STALK</code> sentence String.
     */
    public STALKParser(String nmea) {
        super(nmea, SentenceId.ALK);
    }

    /**
     * Constructor with TalkerId, mostly for compatibility with SentenceFactory.
     * Does not set given talker id, but uses the STALK default 'ST'.
     * Creates a sentence with two fields, command and one parameter.
     *
     * @param tid Any TalkerId may given, but does not affect the resulting
     *            "talker id" as sentence identifier is always <code>$STALK</code>.
     */
    public STALKParser(TalkerId tid) {
        super(TalkerId.ST, SentenceId.ALK,2);
        if (!tid.equals(TalkerId.ST)) {
            throw new IllegalArgumentException("$STALK talker id 'ST' is mandatory (got " + tid + ")");
        }
    }

    @Override
    public String getCommand() {
        return getStringValue(COMMAND);
    }

    @Override
    public void setCommand(String cmd) {
        setStringValue(COMMAND, cmd);
    }

    @Override
    public String[] getParameters() {
        return getStringValues(FIRST_PARAM);
    }

    @Override
    public void setParameters(String... params) {
        setStringValues(FIRST_PARAM, params);
    }

    @Override
    public void addParameter(String param) {
        List<String> params = new ArrayList<String>(Arrays.asList(getParameters()));
        params.add(param);
        setParameters(params.toArray(new String[params.size()]));
    }
}

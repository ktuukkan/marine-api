package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.ROTSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * Created by SJK on 22/01/14.
 */
public class ROTParser extends SentenceParser implements ROTSentence {

    private static final int RATE_OF_TURN = 0;
    private static final int STATUS = 1;
    /**
     * Creates a new ROT parser.
     *
     * @param nmea ROT sentence String to parse.
     */
    public ROTParser(String nmea) {
        super(nmea, SentenceId.ROT);
    }

    /**
     * Creates a new empty ROT sentence.
     *
     * @param talker Talker id to set
     */
    public ROTParser(TalkerId talker) {
        super(talker, SentenceId.ROT, 2);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.parser.RateOfTurnSentance#getRateOfTurn()
     */
    public double getRateOfTurn() {
        return getDoubleValue(RATE_OF_TURN);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.sentence.RateOfTurnSentance#getStatus()
     */
    public boolean getStatus() {
        return getCharValue(STATUS) == 'A';
    }

    public void setStatus(boolean status) {
        setCharValue(STATUS,status ? 'A' : 'V');
    }

    public void setRateOfTurn(double ROT) {
        setDegreesValue(RATE_OF_TURN, ROT);
    }
}

package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.HDTSentence;
import net.sf.marineapi.nmea.sentence.ROTSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SJK on 22/01/14.
 */
public class ROTTest {

    public static final String EXAMPLE = "$HCROT,-0.3,A";
    public static final String INVALID_EXAMPLE = "$HCROT,-0.3,V";
    ROTSentence rot;
    ROTSentence irot;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        rot = new ROTParser(EXAMPLE);
        irot = new ROTParser(INVALID_EXAMPLE);
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.ROTParser( net.sf.marineapi.nmea.sentence.TalkerId )}.
     */
    @Test
    public void testConstructor() {
        ROTSentence empty = new ROTParser(TalkerId.HE);
        assertEquals(TalkerId.HE, empty.getTalkerId());
        assertEquals(SentenceId.ROT.toString(), empty.getSentenceId());
        try {
            empty.getRateOfTurn();
        } catch (DataNotAvailableException e) {
            // pass
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.ROTParser#getStatus()}.
     */
    @Test
    public void testIsStatusValid() {
        assertTrue(rot.getStatus());
    }

    @Test
    public void testIsStatusInvalidData() {
       assertFalse(irot.getStatus());
    }

    /**
     * Test method for
     * {@link ROTParser#getRateOfTurn()}.
     */
    @Test
    public void testGetRateOfTurn() {
        double value = rot.getRateOfTurn();
        assertEquals(-0.3, value, 0.1);
    }
}

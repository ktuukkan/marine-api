package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.ROTSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by SJK on 22/01/14.
 */
public class ROTTest {

    public static final String EXAMPLE = "$HCROT,-0.3,A";
    public static final String INVALID_EXAMPLE = "$HCROT,-0.3,V";
    ROTSentence rot;
    ROTSentence irot;
    
    @Before
    public void setUp() throws Exception {
        rot = new ROTParser(EXAMPLE);
        irot = new ROTParser(INVALID_EXAMPLE);
    }

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

    @Test
    public void testGetStatus() {
        assertEquals(DataStatus.ACTIVE, rot.getStatus());
        assertEquals(DataStatus.VOID, irot.getStatus());
    }

    @Test
    public void testSetStatus() {
    	rot.setStatus(DataStatus.VOID);
        assertEquals(DataStatus.VOID, rot.getStatus());
    }
    
    @Test
    public void testGetRateOfTurn() {
        double value = rot.getRateOfTurn();
        assertEquals(-0.3, value, 0.1);
    }
    
    @Test
    public void testSetRateOfTurn() {
    	final double newValue = 0.5;
    	rot.setRateOfTurn(newValue);
    	assertEquals(newValue, rot.getRateOfTurn(), 0.1);
    }
}

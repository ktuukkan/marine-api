package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.STALKSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * $STALK parser test
 */
public class STALKTest {

    public static final String EXAMPLE = "$STALK,52,A1,00,00*36";

    private STALKSentence stalk;
    private STALKSentence empty;

    @Before
    public void setUp() {
        try {
            stalk = new STALKParser(EXAMPLE);
            empty = new STALKParser(TalkerId.ST);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConstructor() {

        assertEquals(4, stalk.getFieldCount());
        assertEquals(TalkerId.ST, stalk.getTalkerId());
        assertEquals(SentenceId.ALK.name(), stalk.getSentenceId());

        assertEquals(2, empty.getFieldCount());
        assertEquals(TalkerId.ST, empty.getTalkerId());
        assertEquals(SentenceId.ALK.name(), empty.getSentenceId());
    }

    @Test
    public void testConstructorWithWrongTalkerId() {
        try {
            new STALKParser(TalkerId.GP);
            fail("STALK parser did not throw exception on invalid talker-id");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetCommand() {
        assertEquals("52", stalk.getCommand());
    }

    @Test
    public void testSetCommand() {
        empty.setCommand("25");
        assertEquals("25", empty.getCommand());
    }

    @Test
    public void testGetParameters() {
        String[] params = stalk.getParameters();
        assertEquals(3, params.length);
        assertEquals("A1", params[0]);
        assertEquals("00", params[1]);
        assertEquals("00", params[2]);
    }

    @Test
    public void testSetParameters() {
        empty.setParameters("A1", "A2", "A3", "A4");
        String[] params = empty.getParameters();

        assertEquals(5, empty.getFieldCount());
        assertEquals(4, params.length);
        assertEquals("A1", params[0]);
        assertEquals("A2", params[1]);
        assertEquals("A3", params[2]);
        assertEquals("A4", params[3]);
    }

    @Test
    public void testAddParameter() {
        stalk.addParameter("B1");
        String[] params = stalk.getParameters();
        assertEquals("B1", params[params.length-1]);
    }

}

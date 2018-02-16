package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TXTTest {

    public static final String EXAMPLE = "$GPTXT,01,01,TARG1,Message*35";

    private TXTParser txt;
    private TXTParser empty;

    @Before
    public void setUp() {
        try {
            txt = new TXTParser(EXAMPLE);
            empty = new TXTParser(TalkerId.II);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testStringConstructor() {
        assertEquals(TalkerId.GP, txt.getTalkerId());
        assertEquals("TXT", txt.getSentenceId());
        assertEquals(4, txt.getFieldCount());
    }

    @Test
    public void testTalkerIdConstructor() {
        assertEquals(TalkerId.II, empty.getTalkerId());
        assertEquals("TXT", empty.getSentenceId());
        assertEquals(4, empty.getFieldCount());
    }

    @Test
    public void testGetMessageIndex() {
        assertEquals(1, txt.getMessageIndex());
    }

    @Test
    public void testSetMessageIndex() {
        empty.setMessageIndex(1);
        assertEquals(1, empty.getMessageIndex());
    }

    @Test
    public void testSetMessageIndexThrows() {
        try {
            empty.setMessageIndex(-1);
            fail("setMessageIndex didn't throw on -1");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetMessageCount() {
        assertEquals(1, txt.getMessageCount());
    }

    @Test
    public void testSetMessageCount() {
        empty.setMessageCount(1);
        assertEquals(1, empty.getMessageCount());
    }

    @Test
    public void testSetMessageCountThrows() {
        try {
            empty.setMessageCount(0);
            fail("setMessageIndex didn't throw on 0");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetIndentifier() {
        assertEquals("TARG1", txt.getIdentifier());
    }

    @Test
    public void testSetIndentifier() {
        empty.setIdentifier("FOOBAR");
        assertEquals("FOOBAR", empty.getIdentifier());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Message", txt.getMessage());
    }

    @Test
    public void testSetMessage() {
        empty.setMessage("xyzzy");
        assertEquals("xyzzy", empty.getMessage());
    }

    @Test
    public void testSetMessageNonASCII() {
        try {
            empty.setMessage("€€ääööåå");
            fail("setMessage() did not throw on non-ASCII input");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}



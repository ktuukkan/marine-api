package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test common AIS message parser.
 */
public class AISMessageParserTest {

    private final String payload = "13aEOK?P00PD2wVMdLDRhgvL289?";
    private final Sixbit sixbit = new Sixbit(payload, 0);
    private final AISMessageParser parser = new AISMessageParser(sixbit);

    @Test
    public void testGetMessageType() {
        assertEquals(1, parser.getMessageType());
    }

    @Test
    public void testGetMMSI() {
        assertEquals(244670316, parser.getMMSI());
    }

    @Test
    public void testGetRepeatIndicator() {
        assertEquals(0, parser.getRepeatIndicator());
    }

    @Test
    public void testGetMessageBody() {
        Sixbit decoder = parser.getSixbit();
        assertEquals(sixbit.getPayload(), decoder.getPayload());
    }

    @Test
    public void testAppend() {
        AISMessageParser msg = new AISMessageParser();
        msg.append(payload, 1, 0);
        assertEquals(1, msg.getMessageType());
        assertEquals(0, msg.getRepeatIndicator());
        assertEquals(244670316, msg.getMMSI());
        assertEquals(sixbit.getPayload(), msg.getSixbit().getPayload());
    }

    @Test
    public void testAppendInvalidTail() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.append(payload, 1, 0);
            msg.append(payload, 1, 0);
            fail("AISMessageParser.append() did not throw exception");
        } catch (IllegalArgumentException iae) {
            assertEquals("Incorrect order of message fragments", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown from AISMessageParser.append()");
        }

    }
}

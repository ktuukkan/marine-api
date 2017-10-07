package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        Sixbit body = parser.getMessageBody();
        assertEquals(sixbit.getPayload(), body.getPayload());
    }

    @Test
    public void testAppend() {

    }

    @Test
    public void testAppendInvalidTail() {

    }
}

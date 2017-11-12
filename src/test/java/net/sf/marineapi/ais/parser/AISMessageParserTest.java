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
    public void testGetSixbit() {
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
        assertEquals(payload, msg.getSixbit().getPayload());
    }

    @Test
    public void testAppendIncorrectOrder() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.append(payload, 2, 0);
        } catch (IllegalArgumentException iae) {
            assertEquals("Invalid fragment index or sequence order", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception was thrown; " + e.getMessage());
        }
    }


    @Test
    public void testAppendInvalidTail() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.append(payload, 1, 0);
            msg.append(payload, 1, 0);
            fail("AISMessageParser.append() did not throw exception");
        } catch (IllegalArgumentException iae) {
            assertEquals("Invalid fragment index or sequence order", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown from AISMessageParser.append()");
        }

    }

    @Test
    public void testAppendEmptyString() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.append("", 1, 0);
            fail("AISMessageParser.append() did not throw exception");
        } catch (IllegalArgumentException iae) {
            assertEquals("Message fragment cannot be null or empty", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown from AISMessageParser.append()");
        }
    }

    @Test
    public void testAppendNull() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.append(null, 1, 0);
            fail("AISMessageParser.append() did not throw exception");
        } catch (IllegalArgumentException iae) {
            assertEquals("Message fragment cannot be null or empty", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown from AISMessageParser.append()");
        }
    }

    @Test
    public void testAppendNegativeFillBits() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.append(payload, 1, -1);
            fail("AISMessageParser.append() did not throw exception");
        } catch (IllegalArgumentException iae) {
            assertEquals("Fill bits cannot be negative", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown from AISMessageParser.append()");
        }
    }

    @Test
    public void testAppendInvalidIndex() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.append(payload, 0, 0);
            fail("AISMessageParser.append() did not throw exception");
        } catch (IllegalArgumentException iae) {
            assertEquals("Invalid fragment index or sequence order", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown from AISMessageParser.append()");
        }
    }

    @Test
    public void testGetWithoutMessage() {
        try {
            AISMessageParser msg = new AISMessageParser();
            msg.getMMSI();
            fail("Getter did not throw exception");
        } catch (IllegalStateException ise) {
            assertEquals("Message is empty!", ise.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}

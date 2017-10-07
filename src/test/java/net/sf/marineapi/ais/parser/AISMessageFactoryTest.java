package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.ais.message.AISMessage01;
import net.sf.marineapi.ais.message.AISMessage05;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.AISSentence;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test AISMessageFactory
 */
public class AISMessageFactoryTest {

    private final SentenceFactory sf = SentenceFactory.getInstance();
    private final AISMessageFactory amf = AISMessageFactory.getInstance();

    private String s1 = "!AIVDM,1,1,,A,13aEOK?P00PD2wVMdLDRhgvL289?,0*26";
    private String s2_1 = "!AIVDM,2,1,9,B,53nFBv01SJ<thHp6220H4heHTf2222222222221?50:454o<`9QSlUDp,0*09";
    private String s2_2 = "!AIVDM,2,2,9,B,888888888888880,2*2E";

    private final AISSentence single = (AISSentence) sf.createParser(s1);
    private final AISSentence split1 = (AISSentence) sf.createParser(s2_1);
    private final AISSentence split2 = (AISSentence) sf.createParser(s2_2);

    @Test
    public void testCreate() {
        AISMessage msg = amf.create(single);
        assertTrue(msg instanceof AISMessage01);
        assertEquals(1, msg.getMessageType());
    }

    @Test
    public void testCreateWithTwo() {
        AISMessage msg = amf.create(split1, split2);
        assertTrue(msg instanceof AISMessage05);
        assertEquals(5, msg.getMessageType());
    }

    @Test
    public void testCreateWithIncorrectOrder() {
        try {
            amf.create(split2, split1);
            fail("AISMessageFactory didn't throw on incorrect order");
        } catch (IllegalArgumentException iae) {
            assertEquals("Incorrect order of AIS sentences", iae.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception thrown from AISMessageFactory");
        }
    }

}

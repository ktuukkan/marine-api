package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.MMBSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MMBTest
 *
 * @author Kimmo Tuukkanen
 */
public class MMBTest {

    public static final String EXAMPLE = "$IIMMB,29.9870,I,1.0154,B*75";

    MMBSentence mmb;
    MMBSentence empty;

    @Before
    public void setUp() throws Exception {
        mmb = new MMBParser(EXAMPLE);
        empty = new MMBParser(TalkerId.WI);
    }

    @Test
    public void testConstructors() {
        assertEquals(4, mmb.getFieldCount());
        assertEquals(4, empty.getFieldCount());
        assertEquals(TalkerId.II, mmb.getTalkerId());
        assertEquals(TalkerId.WI, empty.getTalkerId());
        assertEquals(SentenceId.MMB.name(), empty.getSentenceId());
    }

    @Test
    public void testGetInchesOfMercury() throws Exception {
        assertEquals(29.9870, mmb.getInchesOfMercury(), 0.0001);
    }

    @Test
    public void testGetBars() throws Exception {
        assertEquals(1.0154, mmb.getBars(), 0.0001);
    }

    @Test
    public void testSetInchesOfMercury() throws Exception {
        mmb.setInchesOfMercury(29.9999);
        assertEquals(29.9999, mmb.getInchesOfMercury(), 0.0001);
    }

    @Test
    public void testSetBars() throws Exception {
        mmb.setBars(1.1234);
        assertEquals(1.1234, mmb.getBars(), 0.0001);
    }
}
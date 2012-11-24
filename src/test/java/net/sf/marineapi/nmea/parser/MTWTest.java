package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.MTWSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * MTW parser tests.
 * 
 * @author Kimmo Tuukkanen
 */
public class MTWTest {

	public static final String EXAMPLE = "$IIMTW,17.75,C";

	private MTWSentence mtw;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mtw = new MTWParser(EXAMPLE);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MTWParser#MTWParser(java.lang.String)}
	 * .
	 */
	@Test
	public void testMTWParserString() {
		assertEquals("MTW", mtw.getSentenceId());
		assertEquals(TalkerId.II, mtw.getTalkerId());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MTWParser#MTWParser(net.sf.marineapi.nmea.sentence.TalkerId)}
	 * .
	 */
	@Test
	public void testMTWParserTalkerId() {
		MTWParser empty = new MTWParser(TalkerId.II);
		assertEquals("MTW", empty.getSentenceId());
		assertEquals(TalkerId.II, empty.getTalkerId());
		assertEquals(2, empty.getFieldCount());
		assertEquals('C', empty.getCharValue(1));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MTWParser#getTemperature()}.
	 */
	@Test
	public void testGetTemperature() {
		assertEquals(17.75, mtw.getTemperature(), 0.01);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.MTWParser#setTemperature(double)}.
	 */
	@Test
	public void testSetTemperature() {
		mtw.setTemperature(12.345);
		assertEquals(12.345, mtw.getTemperature(), 0.01);
	}

}

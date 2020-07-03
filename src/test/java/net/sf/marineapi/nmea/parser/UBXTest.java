package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.UBXSentence;

/**
 * @author Gunnar Hillert
 */
public class UBXTest {

	final String message00 = "$PUBX,00,202920.00,1932.33821,N,15555.72641,W,451.876,G3,3.3,4.0,0.177,0.00,-0.035,,1.11,1.39,1.15,17,0,0*62";

	private UBXSentence ubxSentence;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ubxSentence = new UBXParser(message00);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.VDMParser#VDMParser(net.sf.marineapi.nmea.sentence.TalkerId)}
	 * .
	 */
	@Test
	public void testUBXParserTalkerId() {
		final UBXSentence empty = new UBXParser(TalkerId.P);
		assertEquals(TalkerId.P, empty.getTalkerId());
		assertEquals("UBX", empty.getSentenceId());
		assertEquals(6, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.UBXParser#getMessageId()}.
	 */
	@Test
	public void testGetMessageId() {
		assertEquals(Integer.valueOf(0), ubxSentence.getMessageId());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.UBXParser#getUBXFieldCount()}.
	 */
	@Test
	public void testGetUBXFieldCount() {
		assertEquals(20, ubxSentence.getUBXFieldCount());
	}

}

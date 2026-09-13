package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.CURSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CURTest {

	public static final String EXAMPLE = "$IICUR,A,0,,,123.4,T,2.5,,,M,B*41";

	private CURSentence cur;

	@Before
	public void setUp() throws Exception {
		cur = new CURParser(EXAMPLE);
	}

	@Test
	public void testConstructor() {
		CURParser empty = new CURParser(TalkerId.II);
		assertEquals(11, empty.getFieldCount());
		// direction/heading references default to "True"
		assertEquals("T", empty.getCurrentDirectionReference());
		assertEquals("T", empty.getCurrentHeadingReference());
	}

	@Test
	public void testGetCurrentSpeed() {
		assertEquals(2.5, cur.getCurrentSpeed(), 0.1);
	}

	@Test
	public void testGetCurrentDirection() {
		assertEquals(123.4, cur.getCurrentDirection(), 0.1);
	}

	@Test
	public void testGetCurrentDirectionReference() {
		assertEquals("T", cur.getCurrentDirectionReference());
	}

	@Test
	public void testGetCurrentHeadingReference() {
		assertEquals("M", cur.getCurrentHeadingReference());
	}
}

package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.XTESentence;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.FaaMode;

import org.junit.Before;
import org.junit.Test;

public class XTETest {

	public static final String EXAMPLE = "$IIXTE,A,A,5.36,R,N*67";
	
	private XTESentence empty;
	private XTESentence instance;
	
	@Before
	public void setUp() throws Exception {
		instance = new XTEParser(EXAMPLE);
		empty = new XTEParser(TalkerId.GP);
	}

	@Test
	public void testXTEParserString() {
		assertEquals(TalkerId.II, instance.getTalkerId());
		assertEquals(SentenceId.XTE.name(), instance.getSentenceId());
		assertEquals(6, instance.getFieldCount());
		assertTrue(instance.isValid());
	}

	@Test
	public void testXTEParserTalkerId() {
		assertEquals(TalkerId.GP, empty.getTalkerId());
		assertEquals(SentenceId.XTE.name(), empty.getSentenceId());
		assertEquals(6, empty.getFieldCount());
		assertTrue(empty.isValid());
	}

	@Test
	public void testGetCycleLockStatus() {
		assertEquals(DataStatus.ACTIVE, instance.getCycleLockStatus());
	}

	@Test
	public void testGetMagnitude() {
		assertEquals(5.36, instance.getMagnitude(), 0.001);
	}

	@Test
	public void testGetMode() {
		assertEquals(FaaMode.NONE, empty.getMode());
	}

	@Test
	public void testGetStatus() {
		assertEquals(DataStatus.VOID, empty.getStatus());
	}

	@Test
	public void testGetSteerTo() {
		assertEquals(Direction.RIGHT, instance.getSteerTo());
	}

	@Test
	public void testSetCycleLockStatus() {
		instance.setCycleLockStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, instance.getCycleLockStatus());
	}

	@Test
	public void testSetMagnitude() {
		final double distance = 1.234;
		empty.setMagnitude(1.234);
		assertEquals(distance, empty.getMagnitude(), 0.01);
	}

	@Test
	public void testSetMode() {
		instance.setMode(FaaMode.DGPS);
		assertEquals(FaaMode.DGPS, instance.getMode());
	}

	@Test
	public void testSetStatus() {
		instance.setStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, instance.getStatus());
	}

	@Test
	public void testSetSteerTo() {
		instance.setSteerTo(Direction.LEFT);
		assertEquals(Direction.LEFT, instance.getSteerTo());
	}

}

package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.RPMSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;

import org.junit.Before;
import org.junit.Test;

public class RPMTest {

	public static final String EXAMPLE = "$IIRPM,E,1,2418.2,10.5,A";
	
	RPMSentence rpm;
	RPMSentence empty;
	
	@Before
	public void setUp() throws Exception {
		rpm = new RPMParser(EXAMPLE);
		empty = new RPMParser(TalkerId.II);
	}

	@Test
	public void testRPMParserString() {
		assertEquals(TalkerId.II, rpm.getTalkerId());
		assertEquals("RPM", rpm.getSentenceId());
		assertEquals(5, rpm.getFieldCount());
	}

	@Test
	public void testRPMParserTalkerId() {
		assertEquals(TalkerId.II, empty.getTalkerId());
		assertEquals("RPM", empty.getSentenceId());
		assertEquals(5, empty.getFieldCount());
	}

	@Test
	public void testGetId() {
		assertEquals(1, rpm.getId());
	}

	@Test
	public void testGetPitch() {
		assertEquals(10.5, rpm.getPitch(), 0.1);
	}

	@Test
	public void testGetRPM() {
		assertEquals(2418.2, rpm.getRPM(), 0.1);
	}

	@Test
	public void testGetSource() {
		assertEquals('E', rpm.getSource());
	}

	@Test
	public void testGetStatus() {
		assertEquals(DataStatus.ACTIVE, rpm.getStatus());
	}

	@Test
	public void testIsEngine() {
		assertTrue(rpm.isEngine());
	}

	@Test
	public void testIsShaft() {
		assertFalse(rpm.isShaft());
	}

	@Test
	public void testSetId() {
		empty.setId(2);
		assertEquals(2, empty.getId());
	}

	@Test
	public void testSetPitch() {
		empty.setPitch(3.14);
		assertEquals(3.1, empty.getPitch(), 0.1);
	}

	@Test
	public void testSetSource() {
		empty.setSource(RPMSentence.SHAFT);
		assertTrue(empty.isShaft());
		assertEquals(RPMSentence.SHAFT, empty.getSource());
	}
	
	@Test
	public void testSetInvalidSource() {
		try {
			empty.setSource('A');
			fail("Didn't throw exception");
		} catch (Exception e) {
			// pass
		}
	}
	
	@Test
	public void testSetStatus() {
		empty.setStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, empty.getStatus());
	}

}

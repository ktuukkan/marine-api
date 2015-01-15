package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VBWSentence;
import net.sf.marineapi.nmea.util.DataStatus;

import org.junit.Before;
import org.junit.Test;

public class VBWTest {

	public static final String EXAMPLE = "$IIVBW,11.0,02.0,A,07.5,13.3,A,06.65,A,12.3,A";

	private VBWSentence vbw;
	private VBWSentence empty;

	@Before
	public void setUp() throws Exception {
		vbw = new VBWParser(EXAMPLE);
		empty = new VBWParser(TalkerId.II);
	}

	@Test
	public void testVBWParserString() {
		assertEquals(TalkerId.II, vbw.getTalkerId());
		assertEquals(SentenceId.VBW.name(), vbw.getSentenceId());
		assertEquals(10, vbw.getFieldCount());
	}

	@Test
	public void testVBWParserTalkerId() {
		assertEquals(TalkerId.II, empty.getTalkerId());
		assertEquals(SentenceId.VBW.name(), empty.getSentenceId());
		assertEquals(10, empty.getFieldCount());
		assertTrue(empty.toString().startsWith("$IIVBW,"));
	}

	@Test
	public void testGetLongWaterSpeed() {
		assertEquals(11.0, vbw.getLongWaterSpeed(), 0.1);
	}

	@Test
	public void testGetTravWaterSpeed() {
		assertEquals(2.0, vbw.getTravWaterSpeed(), 0.1);
	}

	@Test
	public void testGetWaterSpeedStatus() {
		assertEquals(DataStatus.ACTIVE, vbw.getWaterSpeedStatus());
	}

	@Test
	public void testGetLongGroundSpeed() {
		assertEquals(07.5, vbw.getLongGroundSpeed(), 0.1);
	}

	@Test
	public void testGetTravGroundSpeed() {
		assertEquals(13.3, vbw.getTravGroundSpeed(), 0.1);
	}

	@Test
	public void testGetGroundSpeedStatus() {
		assertEquals(DataStatus.ACTIVE, vbw.getGroundSpeedStatus());
	}

	@Test
	public void testGetSternWaterSpeed() {
		assertEquals(06.65, vbw.getSternWaterSpeed(), 0.1);
	}

	@Test
	public void testGetSternWaterSpeedStatus() {
		assertEquals(DataStatus.ACTIVE, vbw.getSternWaterSpeedStatus());
	}

	@Test
	public void testGetSternGroundSpeed() {
		assertEquals(12.3, vbw.getSternGroundSpeed(), 0.1);
	}

	@Test
	public void testGetSternGroundSpeedStatus() {
		assertEquals(DataStatus.ACTIVE, vbw.getSternGroundSpeedStatus());
	}

	@Test
	public void testSetLongWaterSpeed() {
		final double dir = 23.3;
		empty.setLongWaterSpeed(dir);
		assertEquals(dir, empty.getLongWaterSpeed(), 0.1);
	}

	@Test
	public void testSetTravWaterSpeed() {
		final double dir = 23.3;
		empty.setTravWaterSpeed(dir);
		assertEquals(dir, empty.getTravWaterSpeed(), 0.1);
	}

	@Test
	public void testSetWaterSpeedStatus() {
		empty.setWaterSpeedStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, empty.getWaterSpeedStatus());
	}

	@Test
	public void testSetLongGroundSpeed() {
		final double dir = 23.3;
		empty.setLongGroundSpeed(dir);
		assertEquals(dir, empty.getLongGroundSpeed(), 0.1);
	}

	@Test
	public void testSetTravGroundSpeed() {
		final double dir = 23.3;
		empty.setTravGroundSpeed(dir);
		assertEquals(dir, empty.getTravGroundSpeed(), 0.1);
	}

	@Test
	public void testSetGroundSpeedStatus() {
		empty.setGroundSpeedStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, empty.getGroundSpeedStatus());
	}

	@Test
	public void testSetSternWaterSpeed() {
		final double dir = 23.3;
		empty.setSternWaterSpeed(dir);
		assertEquals(dir, empty.getSternWaterSpeed(), 0.1);
	}

	@Test
	public void testSetSternWaterSpeedStatus() {
		empty.setSternWaterSpeedStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, empty.getSternWaterSpeedStatus());
	}

	@Test
	public void testSetSternGroundSpeed() {
		final double dir = 23.3;
		empty.setSternGroundSpeed(dir);
		assertEquals(dir, empty.getSternGroundSpeed(), 0.1);
	}

	@Test
	public void testSetSternGroundSpeedStatus() {
		empty.setSternGroundSpeedStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, empty.getSternGroundSpeedStatus());
	}
}

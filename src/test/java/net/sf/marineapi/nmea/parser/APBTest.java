package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.APBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;

import org.junit.Before;
import org.junit.Test;

public class APBTest {

	public static final String EXAMPLE = "$GPAPB,A,A,0.10,R,N,V,V,011,M,DEST,011,M,011,M";

	private APBSentence apb;
	private APBSentence empty;

	@Before
	public void setUp() throws Exception {
		apb = new APBParser(EXAMPLE);
		empty = new APBParser(TalkerId.AG);
	}

	@Test
	public void testAPBParserString() {
		assertEquals(TalkerId.GP, apb.getTalkerId());
		assertEquals("APB", apb.getSentenceId());
		assertEquals(14, apb.getFieldCount());
	}

	@Test
	public void testAPBParserTalkerId() {
		assertEquals(TalkerId.AG, empty.getTalkerId());
		assertEquals("APB", empty.getSentenceId());
		assertEquals(14, empty.getFieldCount());
	}

	@Test
	public void testGetBearingPositionToDestination() {
		empty.setBearingPositionToDestination(123.45);
		assertEquals(123.5, empty.getBearingPositionToDestination(), 0.1);
	}

	@Test
	public void testGetBearingOriginToDestination() {
		empty.setBearingOriginToDestination(234.56);
		assertEquals(234.6, empty.getBearingOriginToDestination(), 0.1);
	}

	@Test
	public void testGetCrossTrackError() {
		empty.setCrossTrackError(12.345);
		assertEquals(12.3, empty.getCrossTrackError(), 0.1);
	}

	@Test
	public void testGetCrossTrackUnits() {
		empty.setCrossTrackUnits(APBSentence.NM);
		assertEquals(APBSentence.NM, empty.getCrossTrackUnits());
	}

	@Test
	public void testGetCycleLockStatus() {
		empty.setCycleLockStatus(DataStatus.ACTIVE);
		assertEquals(DataStatus.ACTIVE, empty.getCycleLockStatus());
	}

	@Test
	public void testGetDestionationWaypointId() {
		empty.setDestinationWaypointId("WP001");
		assertEquals("WP001", empty.getDestionationWaypointId());
	}

	@Test
	public void testGetHeadingToDestionation() {
		empty.setHeadingToDestination(98.765);
		assertEquals(98.8, empty.getHeadingToDestionation(), 0.1);
	}

	@Test
	public void testGetStatus() {
		empty.setStatus(DataStatus.VOID);
		assertEquals(DataStatus.VOID, empty.getStatus());
	}

	@Test
	public void testGetSteerTo() {
		empty.setSteerTo(Direction.LEFT);
		assertEquals(Direction.LEFT, empty.getSteerTo());
	}

	@Test
	public void testIsArrivalCircleEntered() {
		empty.setArrivalCircleEntered(true);
		assertTrue(empty.isArrivalCircleEntered());
	}

	@Test
	public void testIsBearingOriginToDestionationTrue() {
		empty.setBearingOriginToDestionationTrue(true);
		assertTrue(empty.isBearingOriginToDestionationTrue());
	}

	@Test
	public void testIsBearingPositionToDestinationTrue() {
		empty.setBearingPositionToDestinationTrue(false);
		assertFalse(empty.isBearingPositionToDestinationTrue());
	}

	@Test
	public void testIsHeadingToDestinationTrue() {
		empty.setHeadingToDestinationTrue(true);
		assertTrue(empty.isHeadingToDestinationTrue());
	}

	@Test
	public void testIsPerpendicularPassed() {
		empty.setPerpendicularPassed(false);
		assertFalse(empty.isPerpendicularPassed());
	}

	@Test
	public void testSetArrivalCircleEntered() {
		empty.setArrivalCircleEntered(true);
		assertTrue(empty.isArrivalCircleEntered());
	}
}

package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.SteeringMode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class HTCTest {

	private static final String EXAMPLE = "$AGHTC,V,0.1,R,M,,15.0,15.0,,,90.3,,,T*53";

	private HTCParser htc;

	@Before
	public void setUp() throws Exception {
		htc = new HTCParser(EXAMPLE);
	}

	@Test
	public void testConstructor() {
		HTCParser empty = new HTCParser(TalkerId.AG);
		assertEquals(13, empty.getFieldCount());
	}

	@Test
	public void testGetOverride() {
		assertEquals(DataStatus.VOID, htc.getOverride());
	}

	@Test
	public void testGetRudderAngle() {
		assertEquals(0.1, htc.getRudderAngle(), 0.01);
	}

	@Test
	public void testGetRudderDirection() {
		assertEquals(Direction.RIGHT, htc.getRudderDirection());
	}

	@Test
	public void testGetSteeringMode() {
		assertEquals(SteeringMode.MANUAL, htc.getSteeringMode());
	}

	@Test
	public void testGetTurnMode() {
		// field left empty in EXAMPLE
		assertNull(htc.getTurnMode());
	}

	@Test
	public void testGetRudderLimit() {
		assertEquals(15.0, htc.getRudderLimit(), 0.1);
	}

	@Test
	public void testGetOffHeadingLimit() {
		assertEquals(15.0, htc.getOffHeadingLimit(), 0.1);
	}

	@Test
	public void testGetRadiusOfTurn() {
		assertTrue(Double.isNaN(htc.getRadiusOfTurn()));
	}

	@Test
	public void testGetRateOfTurn() {
		assertTrue(Double.isNaN(htc.getRateOfTurn()));
	}

	@Test
	public void testGetHeadingToSteer() {
		assertEquals(90.3, htc.getHeadingToSteer(), 0.1);
	}

	@Test
	public void testGetOffTrackLimit() {
		assertTrue(Double.isNaN(htc.getOffTrackLimit()));
	}

	@Test
	public void testGetTrack() {
		assertTrue(Double.isNaN(htc.getTrack()));
	}

	@Test
	public void testIsHeadingTrue() {
		assertTrue(htc.isHeadingTrue());
	}
}

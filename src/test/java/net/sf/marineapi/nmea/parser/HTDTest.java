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

public class HTDTest {

	private static final String EXAMPLE = "$AGHTD,V,0.1,R,M,,15.0,15.0,,,,,,T,A,A,A,90.3,*39";

	private HTDParser htd;

	@Before
	public void setUp() throws Exception {
		htd = new HTDParser(EXAMPLE);
	}

	@Test
	public void testConstructor() throws Exception {
		HTDParser empty = new HTDParser(TalkerId.AG);
		assertEquals(17, empty.getFieldCount());
	}

	@Test
	public void testGetOverride() throws Exception {
		assertEquals(DataStatus.VOID, htd.getOverride());
	}

	@Test
	public void testGetCommandedRudderAngle() throws Exception {
		assertEquals(0.1, htd.getCommandedRudderAngle(), 0.01);
	}

	@Test
	public void testGetRudderDirection() throws Exception {
		assertEquals(Direction.RIGHT, htd.getCommandedRudderDirection());
	}

	@Test
	public void testGetSteeringMode() throws Exception {
		assertEquals(SteeringMode.MANUAL, htd.getSelectedSteeringMode());
	}

	@Test
	public void testGetTurnMode() throws Exception {
		assertNull(htd.getTurnMode());
	}

	@Test
	public void testGetCommandedRudderLimit() throws Exception {
		assertEquals(15, htd.getCommandedRudderLimit(), 0.1);
	}

	@Test
	public void testGetCommandedOffHeadingLimit() throws Exception {
		assertEquals(15, htd.getCommandedOffHeadingLimit(), 0.1);
	}

	@Test
	public void testGetCommandedRadiusOfTurnForHEadingChanges() throws Exception {
		assertTrue(Double.isNaN(htd.getCommandedRadiusOfTurnForHeadingChanges()));
	}

	@Test
	public void testGetCommandedRateOfTurnForHeadingChanges() throws Exception {
		assertTrue(Double.isNaN(htd.getCommandedRateOfTurnForHeadingChanges()));
	}

	@Test
	public void testGetCommandedHeadingToSteer() throws Exception {
		assertTrue(Double.isNaN(htd.getCommandedHeadingToSteer()));
	}

	@Test
	public void testGetCommandedOffTrackLimit() throws Exception {
		assertTrue(Double.isNaN(htd.getCommandedOffTrackLimit()));
	}

	@Test
	public void testGetCommandedTrack() throws Exception {
		assertTrue(Double.isNaN(htd.getCommandedTrack()));
	}

	@Test
	public void testIsHeadingReferenceInUseTrue() throws Exception {
		assertTrue(htd.isHeadingReferenceInUseTrue());
	}

	@Test
	public void testGetRudderStatus() throws Exception {
		assertEquals(DataStatus.ACTIVE, htd.getRudderStatus());
	}

	@Test
	public void testGetOffHeadinStatus() throws Exception {
		assertEquals(DataStatus.ACTIVE, htd.getOffHeadinStatus());
	}

	@Test
	public void testGetOffTrackStatus() throws Exception {
		assertEquals(DataStatus.ACTIVE, htd.getOffTrackStatus());
	}

	@Test
	public void testGetHeading() throws Exception {
		assertEquals(90.3, htd.getHeading(), 0.1);
	}

	@Test
	public void testIsTrue() throws Exception {
		assertTrue(htd.isTrue());
	}
}

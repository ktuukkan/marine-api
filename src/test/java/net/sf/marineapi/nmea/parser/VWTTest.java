package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VWTSentence;
import net.sf.marineapi.nmea.util.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class VWTTest {

	public static final String EXAMPLE = "$IIVWT,33.0,R,8.0,N,4.1,M,14.8,K*4F";

	private VWTSentence vwt;

	@Before
	public void setUp() throws Exception {
		vwt = new VWTParser(EXAMPLE);
	}

	@Test
	public void testConstructor() {
		VWTParser empty = new VWTParser(TalkerId.II);
		assertEquals(9, empty.getFieldCount());
	}

	@Test
	public void testGetWindAngle() {
		assertEquals(33.0, vwt.getWindAngle(), 0.1);
	}

	@Test
	public void testGetDirectionLeftRight() {
		assertEquals(Direction.RIGHT, vwt.getDirectionLeftRight());
	}

	@Test
	public void testGetSpeedKnots() {
		assertEquals(8.0, vwt.getSpeedKnots(), 0.1);
	}

	@Test
	public void testGetSpeedKmh() {
		assertEquals(14.8, vwt.getSpeedKmh(), 0.1);
	}

	@Test
	public void testSetWindAngle() {
		vwt.setWindAngle(77.2);
		assertEquals(77.2, vwt.getWindAngle(), 0.1);
	}

	@Test
	public void testSetWindAngleOutOfRange() {
		try {
			vwt.setWindAngle(360.1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	@Test
	public void testSetDirectionLeftRight() {
		vwt.setDirectionLeftRight(Direction.LEFT);
		assertEquals(Direction.LEFT, vwt.getDirectionLeftRight());
	}

	@Test
	public void testSetSpeedKnots() {
		vwt.setSpeedKnots(9.9);
		assertEquals(9.9, vwt.getSpeedKnots(), 0.1);
	}

	@Test
	public void testSetNegativeSpeedKnots() {
		try {
			vwt.setSpeedKnots(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	@Test
	public void testSetSpeedKmh() {
		vwt.setSpeedKmh(16.6);
		assertEquals(16.6, vwt.getSpeedKmh(), 0.1);
	}

	@Test
	public void testSetNegativeSpeedKmh() {
		try {
			vwt.setSpeedKmh(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
}

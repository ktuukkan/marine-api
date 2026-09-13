package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VWRSentence;
import net.sf.marineapi.nmea.util.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class VWRTest {

	public static final String EXAMPLE = "$IIVWR,45.0,L,10.5,N,5.4,M,19.4,K*6F";

	private VWRSentence vwr;

	@Before
	public void setUp() throws Exception {
		vwr = new VWRParser(EXAMPLE);
	}

	@Test
	public void testConstructor() {
		VWRParser empty = new VWRParser(TalkerId.II);
		assertEquals(9, empty.getFieldCount());
	}

	@Test
	public void testGetWindAngle() {
		assertEquals(45.0, vwr.getWindAngle(), 0.1);
	}

	@Test
	public void testGetDirectionLeftRight() {
		assertEquals(Direction.LEFT, vwr.getDirectionLeftRight());
	}

	@Test
	public void testGetSpeedKnots() {
		assertEquals(10.5, vwr.getSpeedKnots(), 0.1);
	}

	@Test
	public void testGetSpeedKmh() {
		assertEquals(19.4, vwr.getSpeedKmh(), 0.1);
	}

	@Test
	public void testSetWindAngle() {
		vwr.setWindAngle(88.5);
		assertEquals(88.5, vwr.getWindAngle(), 0.1);
	}

	@Test
	public void testSetWindAngleOutOfRange() {
		try {
			vwr.setWindAngle(360.1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	@Test
	public void testSetDirectionLeftRight() {
		vwr.setDirectionLeftRight(Direction.RIGHT);
		assertEquals(Direction.RIGHT, vwr.getDirectionLeftRight());
	}

	@Test
	public void testSetSpeedKnots() {
		vwr.setSpeedKnots(12.3);
		assertEquals(12.3, vwr.getSpeedKnots(), 0.1);
	}

	@Test
	public void testSetNegativeSpeedKnots() {
		try {
			vwr.setSpeedKnots(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	@Test
	public void testSetSpeedKmh() {
		vwr.setSpeedKmh(20.0);
		assertEquals(20.0, vwr.getSpeedKmh(), 0.1);
	}

	@Test
	public void testSetNegativeSpeedKmh() {
		try {
			vwr.setSpeedKmh(-1);
			fail("Did not throw exception");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
}

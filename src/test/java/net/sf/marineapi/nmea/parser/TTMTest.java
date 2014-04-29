package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.AcquisitionType;
import net.sf.marineapi.nmea.util.TargetStatus;
import net.sf.marineapi.nmea.util.Time;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the RMC sentence parser.
 * 
 * @author Johan Bergkvist
 */
public class TTMTest {

	/** Example sentence */
	public static final String EXAMPLE = "$RATTM,11,25.3,13.7,T,7.0,20.0,T,10.1,20.2,N,NAME,Q,,175550.24,A*34";

	TTMParser empty;
	TTMParser ttm;

	@Before
	public void setUp() {
		try {
			empty = new TTMParser(TalkerId.RA);
			ttm = new TTMParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(15, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getNumber()} .
	 */
	@Test
	public void testGetNumber() {
		assertEquals(11, ttm.getNumber());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getDistance()} .
	 */
	@Test
	public void testGetDistance() {
		assertEquals(25.3, ttm.getDistance(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getBearing()} .
	 */
	@Test
	public void testGetBearing() {
		assertEquals(13.7, ttm.getBearing(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getSpeed()} .
	 */
	@Test
	public void testGetSpeed() {
		assertEquals(7.0, ttm.getSpeed(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getCourse()} .
	 */
	@Test
	public void testGetCourse() {
		assertEquals(20.0, ttm.getCourse(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getDistanceOfCPA()} .
	 */
	@Test
	public void testGetDistanceOfCPA() {
		assertEquals(10.1, ttm.getDistanceOfCPA(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getTimeToCPA()} .
	 */
	@Test
	public void testGetTimeToCPA() {
		assertEquals(20.2, ttm.getTimeToCPA(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getTimeToCPA()} .
	 */
	@Test
	public void testGetName() {
		assertEquals("NAME", ttm.getName());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getStatus()} .
	 */
	@Test
	public void testGetStatus() {
		assertEquals(TargetStatus.QUERY, ttm.getStatus());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getTime()} .
	 */
	@Test
	public void testGetTime() {
		Time t = ttm.getTime();
		assertNotNull(t);
		assertEquals(17, t.getHour());
		assertEquals(55, t.getMinutes());
		assertEquals(50.24, t.getSeconds(), 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#getAcquisitionType()} .
	 */
	@Test
	public void testGetAcquisitionType() {
		assertEquals(AcquisitionType.AUTO, ttm.getAcquisitionType());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setNumber()} .
	 */
	@Test
	public void testSetNumber() {
		final int number = 90;
		ttm.setNumber(number);
		assertTrue(ttm.toString().contains(",90,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setDistance()} .
	 */
	@Test
	public void testSetDistance() {
		ttm.setDistance(56.4);
		assertTrue(ttm.toString().contains(",56.4,"));
		assertTrue(ttm.toString().contains(",N,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setBearing()} .
	 */
	@Test
	public void testSetBearing() {
		ttm.setBearing(34.1);
		assertTrue(ttm.toString().contains(",34.1,T,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setSpeed()} .
	 */
	@Test
	public void testSetSpeed() {
		ttm.setBearing(44.1);
		assertTrue(ttm.toString().contains(",44.1,"));
		assertTrue(ttm.toString().contains(",N,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setCourse()} .
	 */
	@Test
	public void testSetCourse() {
		ttm.setCourse(234.9);
		assertTrue(ttm.toString().contains(",234.9,T,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setDistanceOfCPA()} .
	 */
	@Test
	public void testSetDistanceOfCPA() {
		ttm.setDistanceOfCPA(55.2);
		assertTrue(ttm.toString().contains(",55.2,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setTimeToCPA()} .
	 */
	@Test
	public void testSetTimeToCPA() {
		ttm.setTimeToCPA(15.0);
		assertTrue(ttm.toString().contains(",15.0,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setName()} .
	 */
	@Test
	public void testSetName() {
		ttm.setName("FRED");
		assertTrue(ttm.toString().contains(",FRED,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setStatus()} .
	 */
	@Test
	public void testSetStatus() {
		ttm.setStatus(TargetStatus.LOST);
		assertTrue(ttm.toString().contains(",T,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setStatus()} .
	 */
	@Test
	public void testSetReferenceTrue() {
		ttm.setReference(true);
		assertTrue(ttm.toString().contains(",R,"));
	}
	@Test
	public void testSetReferenceFalse() {
		ttm.setReference(false);
		assertTrue(!ttm.toString().contains(",R,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setTime()} .
	 */
	@Test
	public void testSetTime() {
		Time t = new Time(1, 2, 3.45);
		ttm.setTime(t);
		assertTrue(ttm.toString().contains(",010203.45,"));
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setAcquisitionType()} .
	 */
	@Test
	public void testSetAcquisitionType() {
		ttm.setAcquisitionType(AcquisitionType.MANUAL);
		assertTrue(ttm.toString().contains(",M*"));
	}

}

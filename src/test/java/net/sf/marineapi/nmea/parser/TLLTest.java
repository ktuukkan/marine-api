package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;

import org.junit.Before;
import org.junit.Test;



/**
 * This is a test class for the TLL NMEA Sentence
 * @author Epameinondas Pantzopoulos
 *
 */
public class TLLTest {
	
	/** Example sentence */
	public static final String EXAMPLE = "$RATLL,01,3731.51205,N,02436.00000,E,ANDROS,163700.86,T,*25";
	
	TLLParser tll;
	TLLParser empty;
	
	@Before
	public void setUp() {
		try {
			empty = new TLLParser(TalkerId.RA);
			tll = new TLLParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConstructor() {
		assertEquals(9, empty.getFieldCount());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLLParser#getNumber()} .
	 */
	@Test
	public void testGetNumber() {
		assertEquals(1, tll.getNumber());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLLParser#getName()} .
	 */
	@Test
	public void testGetName() {
		assertEquals("ANDROS", tll.getName());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLLParser#getPosition()} .
	 */
	@Test
	public void testGetPosition() {
		Position p = tll.getPosition();
		
		final double lat = 37 + (31.51205 / 60);
		final double lon = 24 + (36.0/ 60);
		
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, p.getLatitudeHemisphere());
		assertEquals(lon, p.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.EAST, p.getLongitudeHemisphere());
	}
	
	@Test
	public void testGetTime() {
		Time t = tll.getTime();
		assertNotNull(t);
		assertEquals(16, t.getHour());
		assertEquals(37, t.getMinutes());
		assertEquals(00.86, t.getSeconds(), 0.001);
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setNumber()} .
	 */
	@Test
	public void testSetNumber() {
		final int number = 999;
		tll.setNumber(number);
		assertTrue(tll.toString().contains(",999,"));
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setName()} .
	 */
	@Test
	public void testSetName() {
		tll.setName("VRACHNOU");
		assertTrue(tll.toString().contains(",VRACHNOU,"));
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TTMParser#setTime()} .
	 */
	@Test
	public void testSetTime() {
		Time t = new Time(16, 7, 19.27);
		tll.setTime(t);
		assertTrue(tll.toString().contains(",160719.27,"));
	}

}

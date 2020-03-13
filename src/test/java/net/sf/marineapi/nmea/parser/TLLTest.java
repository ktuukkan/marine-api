package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.util.Position;
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
	
	TLLParser mytll;
	
	@Before
	public void setUp() {
		try {		
			mytll = new TLLParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConstructor() {
		assertEquals(9, mytll.getFieldCount());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLLParser#getNumber()} .
	 */
	@Test
	public void testGetNumber() {
		assertEquals(1, mytll.getNumber());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLLParser#getName()} .
	 */
	@Test
	public void testGetName() {
		assertEquals("ANDROS", mytll.getName());
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.TLLParser#getPosition()} .
	 */
	@Test
	public void testGetPosition() {
		Position pos = new Position(37.5252008,24.6);
		
		assertEquals(pos, mytll.getPosition());
	}

}

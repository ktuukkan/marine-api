package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VLWSentence;

import org.junit.Before;
import org.junit.Test;

public class VLWTest {

	public static final String EXAMPLE = "$VWVLW,2.8,N,0.8,N";
	
	VLWSentence vlw;
	VLWSentence empty;
	
	@Before
	public void setUp() throws Exception {
		vlw = new VLWParser(EXAMPLE);
		empty = new VLWParser(TalkerId.VD);
	}

	@Test
	public void testVLWParserString() {
		assertEquals(TalkerId.VW, vlw.getTalkerId());
		assertEquals("VLW", vlw.getSentenceId());
		assertEquals(4, vlw.getFieldCount());
	}

	@Test
	public void testVLWParserTalkerId() {
		assertEquals(TalkerId.VD, empty.getTalkerId());
		assertEquals("VLW", empty.getSentenceId());
		assertEquals(4, empty.getFieldCount());
	}

	@Test
	public void testGetTotal() {
		assertEquals(2.8, vlw.getTotal(), 0.1);
	}

	@Test
	public void testGetTotalUnits() {
		assertEquals('N', vlw.getTotalUnits());
	}

	@Test
	public void testGetTrip() {
		assertEquals(0.8, vlw.getTrip(), 0.1);
	}

	@Test
	public void testGetTripUnits() {
		assertEquals('N', vlw.getTripUnits());
	}

	@Test
	public void testSetTotal() {
		empty.setTotal(3.14);
		assertEquals(3.1, empty.getTotal(), 0.1);
	}

	@Test
	public void testSetTotalUnits() {
		empty.setTotalUnits(VLWSentence.KM);
		assertEquals(VLWSentence.KM, empty.getTotalUnits());
	}

	@Test
	public void testSetTrip() {
		empty.setTrip(0.0);
		assertEquals(0.0, empty.getTrip(), 0.1);
	}

	@Test
	public void testSetTripUnits() {
		empty.setTripUnits(VLWSentence.NM);
		assertEquals(VLWSentence.NM, empty.getTripUnits());
	}

}

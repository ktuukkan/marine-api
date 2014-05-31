package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.DTMSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

public class DTMTest {
	
	public static final String EXAMPLE = "$GPDTM,W84,,0.000000,N,0.000000,E,0.0,W84*6F";

	private DTMSentence dtm;
	private DTMSentence empty;
	
	@Before
	public void setUp() throws Exception {
		dtm = new DTMParser(EXAMPLE);
		empty = new DTMParser(TalkerId.GP);
	}

	@Test
	public void testDTMParserString() {
		assertEquals("DTM", dtm.getSentenceId());
		assertEquals(TalkerId.GP, dtm.getTalkerId());
		assertEquals(8, dtm.getFieldCount());
	}

	@Test
	public void testDTMParserTalkerId() {
		assertEquals("DTM", empty.getSentenceId());
		assertEquals(TalkerId.GP, empty.getTalkerId());
		assertEquals(8, empty.getFieldCount());
	}

	@Test
	public void testGetAltitudeOffset() {
		assertEquals(0.0, dtm.getAltitudeOffset(), 0.1);
	}

	@Test
	public void testGetDatumCode() {
		assertEquals("W84", dtm.getDatumCode());
	}

	@Test
	public void testGetDatumSubCode() {
		try {
			dtm.getDatumSubCode();
			fail("didn't throw exception");
		} catch (Exception e) {
			// pass
		}
	}

	@Test
	public void testGetLatitudeOffset() {
		assertEquals(0.0, dtm.getLatitudeOffset(), 0.1);
	}

	@Test
	public void testGetLongitudeOffset() {
		assertEquals(0.0, dtm.getLongitudeOffset(), 0.1);
	}

	@Test
	public void testGetName() {
		assertEquals("W84", dtm.getName());
	}

	@Test
	public void testSetDatumCode() {
		dtm.setDatumCode("W72");
		assertEquals("W72", dtm.getDatumCode());
	}

	@Test
	public void testSetDatumSubCode() {
		dtm.setDatumSubCode("123");
		assertEquals("123", dtm.getDatumSubCode());
	}

	@Test
	public void testSetLatitudeOffset() {
		dtm.setLatitudeOffset(0.12345678);
		assertEquals(0.1235, dtm.getLatitudeOffset(), 0.0001);
	}

	@Test
	public void testSetLongitudeOffset() {
		dtm.setLongitudeOffset(1.23456789);
		assertEquals(1.2346, dtm.getLongitudeOffset(), 0.0001);
	}

	@Test
	public void testSetName() {
		dtm.setName("S83");
		assertEquals("S83", dtm.getName());
	}

}

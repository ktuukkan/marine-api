package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.sentence.GSVSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.SatelliteInfo;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the GSV sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class GSVTest {

	/** Example sentence */
	public static final String EXAMPLE = "$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,63,058,50,21,53,329,47*73";

	private GSVSentence empty;
	private GSVSentence gsv;

	@Before
	public void setUp() {
		try {
			empty = new GSVParser(TalkerId.GP);
			gsv = new GSVParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(19, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#getSatelliteCount()}.
	 */
	@Test
	public void testGetSatelliteCount() {
		assertEquals(12, gsv.getSatelliteCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#getSatelliteInfo()}.
	 */
	@Test
	public void testGetSatelliteInfo() {
		List<SatelliteInfo> sat = gsv.getSatelliteInfo();
		assertEquals(4, sat.size());
		testSatelliteInfo(sat.get(0), "15", 56, 182, 51);
		testSatelliteInfo(sat.get(1), "17", 38, 163, 47);
		testSatelliteInfo(sat.get(2), "18", 63, 58, 50);
		testSatelliteInfo(sat.get(3), "21", 53, 329, 47);
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#getSatelliteInfo()}.
	 */
	@Test
	public void testGetSatelliteInfoWithEmptyFields() {
		
		GSVSentence g = new GSVParser("$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,,,,21,53,329,47");
		List<SatelliteInfo> sat = g.getSatelliteInfo();
		
		assertEquals(3, sat.size());
		testSatelliteInfo(sat.get(0), "15", 56, 182, 51);
		testSatelliteInfo(sat.get(1), "17", 38, 163, 47);
		testSatelliteInfo(sat.get(2), "21", 53, 329, 47);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#getSatelliteInfo()}.
	 */
	@Test
	public void testGetSatelliteInfoWithShortSentence() {
		
		GSVSentence g = new GSVParser("$GPGSV,3,2,12,15,56,182,51,17,38,163,47");
		List<SatelliteInfo> sat = g.getSatelliteInfo();
		
		assertEquals(2, sat.size());
		testSatelliteInfo(sat.get(0), "15", 56, 182, 51);
		testSatelliteInfo(sat.get(1), "17", 38, 163, 47);
	}
	
	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#getSentenceCount()}.
	 */
	@Test
	public void testGetSentenceCount() {
		assertEquals(3, gsv.getSentenceCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#getSentenceIndex()}.
	 */
	@Test
	public void testGetSentenceIndex() {
		assertEquals(2, gsv.getSentenceIndex());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.GSVParser#isFirst()}.
	 */
	@Test
	public void testIsFirst() {
		assertFalse(gsv.isFirst());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.GSVParser#isLast()} .
	 */
	@Test
	public void testIsLast() {
		assertFalse(gsv.isLast());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#setSatelliteCount(int)}.
	 */
	@Test
	public void testSetSatelliteCount() {
		gsv.setSatelliteCount(5);
		assertEquals(5, gsv.getSatelliteCount());
		gsv.setSatelliteCount(10);
		assertEquals(10, gsv.getSatelliteCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#getSatelliteInfo()}.
	 */
	@Test
	public void testSetSatelliteInfo() {
		List<SatelliteInfo> si = new ArrayList<SatelliteInfo>();
		si.add(new SatelliteInfo("01", 11, 12, 13));
		si.add(new SatelliteInfo("02", 21, 22, 23));
		si.add(new SatelliteInfo("03", 31, 32, 33));
		gsv.setSatelliteInfo(si);

		assertTrue(gsv.toString().contains(",03,31,032,33,,,,*"));
		List<SatelliteInfo> sat = gsv.getSatelliteInfo();
		assertEquals(3, sat.size());
		testSatelliteInfo(sat.get(0), "01", 11, 12, 13);
		testSatelliteInfo(sat.get(1), "02", 21, 22, 23);
		testSatelliteInfo(sat.get(2), "03", 31, 32, 33);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSVParser#setSentenceCount(int)}.
	 */
	@Test
	public void testSetSentenceCount() {
		gsv.setSentenceCount(1);
		assertEquals(1, gsv.getSentenceCount());
		gsv.setSentenceCount(2);
		assertEquals(2, gsv.getSentenceCount());
	}

	@Test
	public void testParserGlonassGSV() {
		GSVParser gl = new GSVParser("$GLGSV,2,1,07,70,28,145,44,71,67,081,46,72,34,359,40,77,16,245,35,1*76");
		assertEquals(TalkerId.GL, gl.getTalkerId());
	}

	/**
	 * Tests the given SatelliteInfo against specified values.
	 */
	private void testSatelliteInfo(SatelliteInfo si, String id, int elevation,
			int azimuth, int noise) {
		assertEquals(id, si.getId());
		assertEquals(elevation, si.getElevation(), 0.1);
		assertEquals(azimuth, si.getAzimuth(), 0.1);
		assertEquals(noise, si.getNoise(), 0.1);
	}
}

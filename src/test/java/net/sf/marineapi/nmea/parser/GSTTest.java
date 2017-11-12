package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Datum;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.nmea.util.Units;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the GST sentence parser.
 * 
 * @author Tero Laitinen
 */
public class GSTTest {

	public static final String EXAMPLE = "$GPGST,172814.0,0.006,0.023,0.020,273.6,0.023,0.020,0.031*6A";

	private GSTParser gst;
	private GSTParser empty;

	@Before
	public void setUp() {
		try {
			empty = new GSTParser(TalkerId.GP);
			gst = new GSTParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(8, empty.getFieldCount());
	}

	@Test
	public void testGetPseudoRangeResidualsRMS() {
		assertEquals(0.006, gst.getPseudoRangeResidualsRMS(), 0.001);
	}

	@Test
	public void testGetSemiMajorError() {
		assertEquals(0.023, gst.getSemiMajorError(), 0.001);
	}

	@Test
	public void testGetSemiMinorError() {
		assertEquals(0.020, gst.getSemiMinorError(), 0.001);
	}

	@Test
	public void testGetErrorEllipseOrientation() {
		assertEquals(273.6, gst.getErrorEllipseOrientation(), 0.001);
	}

	@Test
	public void testGetLatitudeError() {
		assertEquals(0.023, gst.getLatitudeError(), 0.001);
	}

	@Test
	public void testGetLongitudeError() {
		assertEquals(0.020, gst.getLongitudeError(), 0.001);
	}


	@Test
	public void testGetAltitudeError() {
		assertEquals(0.031, gst.getAltitudeError(), 0.001);
	}

	@Test
	public void testGetTime() {
		Time t = gst.getTime();
		assertNotNull(t);
		assertEquals(17, t.getHour());
		assertEquals(28, t.getMinutes());
		assertEquals(14.0, t.getSeconds(), 0.001);
	}

	@Test
	public void testGSTParser() {
		GSTParser instance = new GSTParser(EXAMPLE);
		SentenceId sid = SentenceId.valueOf(instance.getSentenceId());
		assertEquals(SentenceId.GST, sid);
	}
	@Test
	public void testSetPseudoRangeResidualsRMS() {
		gst.setPseudoRangeResidualsRMS(0.012);
		assertEquals(0.012, gst.getPseudoRangeResidualsRMS(), 0.001);
	}

	@Test
	public void testSetSemiMajorError() {
		gst.setSemiMajorError(0.015);
		assertEquals(0.015, gst.getSemiMajorError(), 0.001);
	}

	@Test
	public void testSetSemiMinorError() {
		gst.setSemiMinorError(0.032);
		assertEquals(0.032, gst.getSemiMinorError(), 0.001);
	}

	@Test
	public void testSetErrorEllipseOrientation() {
		gst.setErrorEllipseOrientation(121.3);
		assertEquals(121.3, gst.getErrorEllipseOrientation(), 0.001);
	}

	@Test
	public void testSetLatitudeError() {
		gst.setLatitudeError(0.068);
		assertEquals(0.068, gst.getLatitudeError(), 0.001);
	}

	@Test
	public void testSetLongitudeError() {
		gst.setLongitudeError(0.011);
		assertEquals(0.011, gst.getLongitudeError(), 0.001);
	}


	@Test
	public void testSetAltitudeError() {
		gst.setAltitudeError(0.013);
		assertEquals(0.013, gst.getAltitudeError(), 0.001);
	}

	@Test
	public void testSetTime() {
		gst.setTime(new Time(1, 2, 3.456));
		Time t = gst.getTime();
		assertNotNull(t);
		assertEquals(1, t.getHour());
		assertEquals(2, t.getMinutes());
		assertEquals(3.456, t.getSeconds(), 0.001);
	}


}

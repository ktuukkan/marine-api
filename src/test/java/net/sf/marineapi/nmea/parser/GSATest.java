package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.GSASentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.GpsFixStatus;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the GSA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class GSATest {

	/** Example sentence */
	public static final String EXAMPLE = "$GPGSA,A,3,02,,,07,,09,24,26,,,,,1.6,1.6,1.0*3D";

	private GSASentence empty;
	private GSASentence instance;

	@Before
	public void setUp() {
		try {
			empty = new GSAParser(TalkerId.GP);
			instance = new GSAParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(17, empty.getFieldCount());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#getFixStatus()} .
	 */
	@Test
	public void testGetFixStatus() {
		assertEquals(GpsFixStatus.GPS_3D, instance.getFixStatus());
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.GSAParser#getMode()}.
	 */
	@Test
	public void testGetFaaMode() {
		assertEquals(FaaMode.AUTOMATIC, instance.getMode());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#getHorizontalDOP()}.
	 */
	@Test
	public void testGetHorizontalDOP() {
		double hdop = instance.getHorizontalDOP();
		assertEquals(1.6, hdop, 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#getPositionDOP()}.
	 */
	@Test
	public void testGetPositionDOP() {
		double pdop = instance.getPositionDOP();
		assertEquals(1.6, pdop, 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#getSatelliteIds()}.
	 */
	@Test
	public void testGetSatelliteIds() {
		String[] satellites = instance.getSatelliteIds();
		assertEquals(5, satellites.length);
		assertEquals("02", satellites[0]);
		assertEquals("07", satellites[1]);
		assertEquals("09", satellites[2]);
		assertEquals("24", satellites[3]);
		assertEquals("26", satellites[4]);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#getVerticalDOP()}.
	 */
	@Test
	public void testGetVerticalDOP() {
		double vdop = instance.getVerticalDOP();
		assertEquals(1.0, vdop, 0.001);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#setFixStatus(GpsFixStatus)}
	 * .
	 */
	@Test
	public void testSetFixStatus() {
		instance.setFixStatus(GpsFixStatus.GPS_NA);
		assertTrue(instance.toString().contains(",A,1,"));
		assertEquals(GpsFixStatus.GPS_NA, instance.getFixStatus());

		instance.setFixStatus(GpsFixStatus.GPS_2D);
		assertTrue(instance.toString().contains(",A,2,"));
		assertEquals(GpsFixStatus.GPS_2D, instance.getFixStatus());

		instance.setFixStatus(GpsFixStatus.GPS_3D);
		assertTrue(instance.toString().contains(",A,3,"));
		assertEquals(GpsFixStatus.GPS_3D, instance.getFixStatus());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#setMode(FaaMode)}.
	 */
	@Test
	public void testSetFaaMode() {
		instance.setMode(FaaMode.DGPS);
		assertTrue(instance.toString().contains(",D,"));
		assertEquals(FaaMode.DGPS, instance.getMode());

		instance.setMode(FaaMode.SIMULATED);
		assertTrue(instance.toString().contains(",S,"));
		assertEquals(FaaMode.SIMULATED, instance.getMode());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#setHorizontalDOP(double)}.
	 */
	@Test
	public void testSetHorizontalDOP() {
		final double hdop = 1.98765;
		instance.setHorizontalDOP(hdop);
		assertEquals(hdop, instance.getHorizontalDOP(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#setPositionDOP(double)}.
	 */
	@Test
	public void testSetPositionDOP() {
		final double pdop = 1.56788;
		instance.setPositionDOP(pdop);
		assertEquals(pdop, instance.getPositionDOP(), 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#setSatellitesIds(String[])}
	 * .
	 */
	@Test
	public void testSetSatelliteIds() {

		String[] ids = { "02", "04", "06", "08", "10", "12" };
		instance.setSatelliteIds(ids);

		String[] satellites = instance.getSatelliteIds();
		assertEquals(ids.length, satellites.length);

		int i = 0;
		for (String id : ids) {
			assertEquals(id, satellites[i++]);
		}
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.nmea.parser.GSAParser#setVerticalDOP(double)}.
	 */
	@Test
	public void testSetVerticalDOP() {
		final double vdop = 1.56789;
		instance.setVerticalDOP(vdop);
		assertEquals(vdop, instance.getVerticalDOP(), 0.1);
	}
}

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
 * Test the GGA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class GGATest {

	public static final String EXAMPLE = "$GPGGA,120044.567,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*63";

	private GGAParser gga;
	private GGAParser empty;

	@Before
	public void setUp() {
		try {
			empty = new GGAParser(TalkerId.GP);
			gga = new GGAParser(EXAMPLE);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		assertEquals(14, empty.getFieldCount());
	}

	@Test
	public void testGetAltitude() {
		assertEquals(28.0, gga.getAltitude(), 0.001);
	}

	@Test
	public void testGetAltitudeUnits() {
		assertEquals(Units.METER, gga.getAltitudeUnits());
	}

	@Test
	public void testGetDgpsAge() {
		try {
			gga.getDgpsAge();
			fail("Did not throw ParseException");
		} catch (DataNotAvailableException e) {
			// ok
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetDgpsStationId() {
		try {
			gga.getDgpsStationId();
			fail("Did not throw ParseException");
		} catch (DataNotAvailableException e) {
			// ok
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetFixQuality() {
		assertEquals(GpsFixQuality.NORMAL, gga.getFixQuality());
	}

	@Test
	public void testGetGeoidalHeight() {
		assertEquals(19.6, gga.getGeoidalHeight(), 0.001);
	}

	@Test
	public void testGetGeoidalHeightUnits() {
		assertEquals(Units.METER, gga.getGeoidalHeightUnits());
	}

	@Test
	public void testGetHorizontalDOP() {
		assertEquals(2.0, gga.getHorizontalDOP(), 0.001);
	}

	@Test
	public void testGetNumberOfSatellites() {
		assertEquals(0, gga.getSatelliteCount());
	}

	@Test
	public void testGetPosition() {
		// expected lat/lon values
		final double lat = 60 + (11.552 / 60);
		final double lon = 25 + (1.941 / 60);
		final double alt = 28.0;

		Position p = gga.getPosition();
		assertNotNull(p);
		assertEquals(lat, p.getLatitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, p.getLatitudeHemisphere());
		assertEquals(lon, p.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.EAST, p.getLongitudeHemisphere());
		assertEquals(Datum.WGS84, p.getDatum());
		assertEquals(alt, p.getAltitude(), 0.01);
	}

	@Test
	public void testGetTime() {
		Time t = gga.getTime();
		assertNotNull(t);
		assertEquals(12, t.getHour());
		assertEquals(0, t.getMinutes());
		assertEquals(44.567, t.getSeconds(), 0.001);
	}

	/**
     * 
     */
	@Test
	public void testGGAParser() {
		GGAParser instance = new GGAParser(EXAMPLE);
		SentenceId sid = SentenceId.valueOf(instance.getSentenceId());
		assertEquals(SentenceId.GGA, sid);
	}

	@Test
	public void testSetAltitude() {
		final double alt = 11.11111;
		gga.setAltitude(alt);
		assertEquals(alt, gga.getAltitude(), 0.1);
	}

	@Test
	public void testSetAltitudeUnits() {
		assertEquals(Units.METER, gga.getAltitudeUnits());
		gga.setAltitudeUnits(Units.FEET);
		assertEquals(Units.FEET, gga.getAltitudeUnits());
	}

	@Test
	public void testSetDgpsAge() {
		final double age = 33.333333;
		gga.setDgpsAge(age);
		assertEquals(age, gga.getDgpsAge(), 0.1);
	}

	@Test
	public void testSetDgpsStationId() {
		gga.setDgpsStationId("0001");
		assertEquals("0001", gga.getDgpsStationId());
	}

	@Test
	public void testSetFixQuality() {
		assertEquals(GpsFixQuality.NORMAL, gga.getFixQuality());
		gga.setFixQuality(GpsFixQuality.INVALID);
		assertEquals(GpsFixQuality.INVALID, gga.getFixQuality());
	}

	@Test
	public void testSetGeoidalHeight() {
		final double height = 3.987654;
		gga.setGeoidalHeight(height);
		assertEquals(height, gga.getGeoidalHeight(), 0.1);
	}

	@Test
	public void testSetGeoidalHeightUnits() {
		assertEquals(Units.METER, gga.getGeoidalHeightUnits());
		gga.setGeoidalHeightUnits(Units.FEET);
		assertEquals(Units.FEET, gga.getGeoidalHeightUnits());
	}

	@Test
	public void testSetHorizontalDOP() {
		final double hdop = 0.123456;
		gga.setHorizontalDOP(hdop);
		assertEquals(hdop, gga.getHorizontalDOP(), 0.1);
	}

	@Test
	public void testSetPosition() {
		final double lat = 61 + (1.111 / 60);
		final double lon = 27 + (7.777 / 60);
		final double alt = 11.1;
		Position p = new Position(lat, lon);
		p.setAltitude(alt);
		gga.setPosition(p);

		final String str = gga.toString();
		assertTrue(str.contains(",6101.111,N,"));
		assertTrue(str.contains(",02707.777,E,"));

		Position wp = gga.getPosition();
		assertNotNull(wp);
		assertEquals(lat, wp.getLatitude(), 0.0000001);
		assertEquals(lon, wp.getLongitude(), 0.0000001);
		assertEquals(CompassPoint.NORTH, wp.getLatitudeHemisphere());
		assertEquals(CompassPoint.EAST, wp.getLongitudeHemisphere());
		assertEquals(alt, wp.getAltitude(), 0.01);
	}

	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.GGAParser#getTime()}.
	 */
	@Test
	public void testSetTime() {
		Time t = new Time(1, 2, 3.456);
		gga.setTime(t);
		assertTrue(gga.toString().contains("GPGGA,010203.456,6011"));
	}

	@Test
	public void testSetNumberOfSatellites() {
		gga.setSatelliteCount(5);
		assertEquals(5, gga.getSatelliteCount());
		assertTrue(gga.toString().contains(",E,1,05,2.0,28.0,"));
	}

	@Test
	public void testSetNumberOfSatellitesThrows() {
		try {
			gga.setSatelliteCount(-1);
			fail("setSatelliteCount() did not throw exception");
		} catch(IllegalArgumentException e) {
			assertEquals(0, gga.getSatelliteCount());
			assertEquals("Satelite count cannot be negative", e.getMessage());
		}
	}
}

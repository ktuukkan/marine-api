package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.GBSSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.Time;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GBSTest {

    public static final String EXAMPLE =
            "$GPGBS,015509.00,-0.031,-0.186,0.219,19,0.000,-0.354,6.972*4D";

    private GBSSentence gbs;
    private GBSSentence empty;

    @Before
    public void setUp() throws Exception {
        gbs = new GBSParser(EXAMPLE);
        empty = new GBSParser(TalkerId.GP);
    }

    @Test
    public void getLatitudeError() throws Exception {
        assertEquals(-0.031, gbs.getLatitudeError(), 0.001);
    }

    @Test
    public void setLatitudeError() throws Exception {
        empty.setLatitudeError(-0.123);
        assertEquals(-0.123, empty.getLatitudeError(), 0.001);
    }

    @Test
    public void getLongitudeError() throws Exception {
        assertEquals(-0.186, gbs.getLongitudeError(), 0.001);
    }

    @Test
    public void setLongitudeError() throws Exception {
        empty.setLongitudeError(-0.456);
        assertEquals(-0.456, empty.getLongitudeError(), 0.001);
    }

    @Test
    public void getAltitudeError() throws Exception {
        assertEquals(0.219, gbs.getAltitudeError(), 0.001);
    }

    @Test
    public void setAltitudeError() throws Exception {
        empty.setAltitudeError(-0.456);
        assertEquals(-0.456, empty.getAltitudeError(), 0.001);
    }

    @Test
    public void getSatelliteId() throws Exception {
        assertEquals("19", gbs.getSatelliteId());
    }

    @Test
    public void setSatelliteId() throws Exception {
        empty.setSatelliteId("07");
        assertEquals("07", empty.getSatelliteId());
    }

    @Test
    public void getProbability() throws Exception {
        assertEquals(0.000, gbs.getProbability(), 0.001);
    }

    @Test
    public void setProbability() throws Exception {
        empty.setProbability(0.123);
        assertEquals(0.123, empty.getProbability(), 0.001);
    }

    @Test
    public void getEstimate() throws Exception {
        assertEquals(-0.354, gbs.getEstimate(), 0.001);
    }

    @Test
    public void setEstimate() throws Exception {
        empty.setEstimate(-0.234);
        assertEquals(-0.234, empty.getEstimate(), 0.001);
    }

    @Test
    public void getDeviation() throws Exception {
        assertEquals(6.972, gbs.getDeviation(), 0.001);
    }

    @Test
    public void setDeviation() throws Exception {
        empty.setDeviation(1.234);
        assertEquals(1.234, empty.getDeviation(), 0.001);
    }

    @Test
    public void getTime() throws Exception {
        assertEquals("015509.000", gbs.getTime().toString());
    }

    @Test
    public void setTime() throws Exception {
        empty.setTime(new Time(10, 31, 12.345));
        assertEquals("103112.345", empty.getTime().toString());
    }
}
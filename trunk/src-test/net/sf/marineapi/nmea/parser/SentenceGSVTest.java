/**
 * 
 */
package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import net.sf.marineapi.nmea.util.SatelliteInfo;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kimmot
 */
public class SentenceGSVTest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,63,058,50,21,53,329,47*73";

    private SentenceGSV gsv;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            gsv = new SentenceGSV(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGSV#getSentenceCount()}.
     */
    @Test
    public void testGetSentenceCount() {
        assertTrue(3 == gsv.getSentenceCount());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGSV#getSentenceIndex()}.
     */
    @Test
    public void testGetSentenceNumber() {
        assertTrue(2 == gsv.getSentenceIndex());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGSV#isFirst()}.
     */
    @Test
    public void testIsFirstInSequence() {
        assertFalse(gsv.isFirst());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.SentenceGSV#isLast()}
     * .
     */
    @Test
    public void testIsLastInSequence() {
        assertFalse(gsv.isLast());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGSV#getSatelliteCount()}.
     */
    @Test
    public void testGetSatelliteCount() {
        assertTrue(12 == gsv.getSatelliteCount());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.SentenceGSV#getSatelliteInfo()}.
     */
    @Test
    public void testGetSatelliteInfo() {
        List<SatelliteInfo> sat = gsv.getSatelliteInfo();
        assertTrue(sat.size() == 4);
        testSatelliteInfo(sat.get(0), "15", 56, 182, 51);
        testSatelliteInfo(sat.get(1), "17", 38, 163, 47);
        testSatelliteInfo(sat.get(2), "18", 63, 58, 50);
        testSatelliteInfo(sat.get(3), "21", 53, 329, 47);
    }

    /**
     * Tests the given SatelliteInfo against specified values.
     */
    private void testSatelliteInfo(SatelliteInfo si, String id, int elevation,
            int azimuth, int noise) {
        assertEquals(id, si.getId());
        assertTrue(elevation == si.getElevation());
        assertTrue(azimuth == si.getAzimuth());
        assertTrue(noise == si.getSignalNoiseRatio());
    }
}

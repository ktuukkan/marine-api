package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.MDASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Richard van Nieuwenhoven
 *
 */
public class MDATest {

    public static final String EXAMPLE = "$IIMDA,99700.0,P,1.00,B,3.2,C,,C,,,,C,295.19,T,,M,5.70,N,2.93,M*08";

    private MDASentence mda;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mda = new MDAParser(EXAMPLE);
    }

    @Test
    public void testMDAParserTalkerId() {
        MDAParser mwdp = new MDAParser(TalkerId.II);
        assertEquals(TalkerId.II, mwdp.getTalkerId());
        assertEquals(SentenceId.MDA.toString(), mwdp.getSentenceId());
    }

    @Test
    public void testGetMagneticWindDirection() {
        assertTrue(Double.isNaN(mda.getMagneticWindDirection()));
    }

    @Test
    public void testGetTrueWindDirection() {
        assertEquals(295.19, mda.getTrueWindDirection(), 0.1);
    }

    @Test
    public void testGetWindSpeed() {
        assertEquals(2.93, mda.getWindSpeed(), 0.1);
    }

    @Test
    public void testGetWindSpeedKnots() {
        assertEquals(5.7, mda.getWindSpeedKnots(), 0.1);
    }

    @Test
    public void testGetAbsoluteHumidity() {
        assertTrue(Double.isNaN(mda.getAbsoluteHumidity()));
    }

    @Test
    public void testGetAirTemperature() {
        assertEquals(3.2, mda.getAirTemperature(), 0.1);
    }

    @Test
    public void testGetBarometricPressure() {
        assertEquals(1.0, mda.getBarometricPressure(), 0.1);
    }

    @Test
    public void testGetBarometricPressureInHg() {
        assertTrue(Double.isNaN(mda.getBarometricPressureInHg()));
    }

    @Test
    public void testGetRelativeHumidity() {
        assertTrue(Double.isNaN(mda.getRelativeHumidity()));
    }

    @Test
    public void testGetDewPoint() {
        assertTrue(Double.isNaN(mda.getDewPoint()));
    }

    @Test
    public void testGetWaterTemperature() {
        assertTrue(Double.isNaN(mda.getWaterTemperature()));
    }
}

package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.MDASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Richard van Nieuwenhoven
 */
public class MDATest {

    public static final String EXAMPLE = "$IIMDA,99700.0,P,1.00,B,3.2,C,,C,,,,C,295.19,T,,M,5.70,N,2.93,M*08";

    public static final String EXAMPLE2 = "$IIMDA,30.0,I,1.0149,B,26.8,C,,C,64.2,16.4,19.5,C,,T,38.7,M,10.88,N,5.60,M*36";

    private MDASentence mda;

    private MDASentence mda2;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mda = new MDAParser(EXAMPLE);
        mda2 = new MDAParser(EXAMPLE2);
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
        assertEquals(38.7, mda2.getMagneticWindDirection(), 0.1);
    }

    @Test
    public void testGetTrueWindDirection() {
        assertEquals(295.19, mda.getTrueWindDirection(), 0.1);
        assertTrue(Double.isNaN(mda2.getTrueWindDirection()));
    }

    @Test
    public void testGetWindSpeed() {
        assertEquals(2.93, mda.getWindSpeed(), 0.1);
        assertEquals(5.6, mda2.getWindSpeed(), 0.1);
    }

    @Test
    public void testGetWindSpeedKnots() {
        assertEquals(5.7, mda.getWindSpeedKnots(), 0.1);
        assertEquals(10.88, mda2.getWindSpeedKnots(), 0.1);
    }

    @Test
    public void testGetAbsoluteHumidity() {
        assertTrue(Double.isNaN(mda.getAbsoluteHumidity()));
        assertEquals(16.4, mda2.getAbsoluteHumidity(), 0.1);
    }

    @Test
    public void testGetAirTemperature() {
        assertEquals(3.2, mda.getAirTemperature(), 0.1);
        assertEquals(26.8, mda2.getAirTemperature(), 0.1);
    }

    @Test
    public void testGetSecondaryBarometricPressure() {
        assertEquals(1.0, mda.getSecondaryBarometricPressure(), 0.1);
        assertEquals('B', mda.getSecondaryBarometricPressureUnit());
        assertEquals(1.0, mda2.getSecondaryBarometricPressure(), 0.1);
        assertEquals('B', mda2.getSecondaryBarometricPressureUnit());
    }

    @Test
    public void testGetPrimaryBarometricPressure() {
        assertEquals(99700.0, mda.getPrimaryBarometricPressure(), 0.1);
        assertEquals('P', mda.getPrimaryBarometricPressureUnit());
        assertEquals(30.0, mda2.getPrimaryBarometricPressure(), 0.1);
        assertEquals('I', mda2.getPrimaryBarometricPressureUnit());
    }

    @Test
    public void testGetRelativeHumidity() {
        assertTrue(Double.isNaN(mda.getRelativeHumidity()));
        assertEquals(64.2, mda2.getRelativeHumidity(), 0.1);
    }

    @Test
    public void testGetDewPoint() {
        assertTrue(Double.isNaN(mda.getDewPoint()));
        assertEquals(19.5, mda2.getDewPoint(), 0.1);
    }

    @Test
    public void testGetWaterTemperature() {
        assertTrue(Double.isNaN(mda.getWaterTemperature()));
        assertTrue(Double.isNaN(mda2.getWaterTemperature()));
    }

    @Test
    public void testSetMagneticWindDirection() {
        mda.setMagneticWindDirection(123.4);
        assertEquals(123.4, mda.getMagneticWindDirection(), 0.1);
    }

    @Test
    public void testSetTrueWindDirection() {
        mda.setTrueWindDirection(234.5);
        assertEquals(234.5, mda.getTrueWindDirection(), 0.1);
    }

    @Test
    public void testSetWindSpeed() {
        mda.setWindSpeed(12.3);
        assertEquals(12.3, mda.getWindSpeed(), 0.1);
    }

    @Test
    public void testSetWindSpeedKnots() {
        mda.setWindSpeedKnots(6.78);
        assertEquals(6.78, mda.getWindSpeedKnots(), 0.01);
    }

    @Test
    public void testSetAbsoluteHumidity() {
        mda.setAbsoluteHumidity(34.5);
        assertEquals(34.5, mda.getAbsoluteHumidity(), 0.1);
    }

    @Test
    public void testSetAirTemperature() {
        mda.setAirTemperature(18.9);
        assertEquals(18.9, mda.getAirTemperature(), 0.1);
    }

    @Test
    public void testSetSecondaryBarometricPressure() {
        mda.setSecondaryBarometricPressure(0.99);
        mda.setSecondaryBarometricPressureUnit('B');
        assertEquals(0.99, mda.getSecondaryBarometricPressure(), 0.01);
        assertEquals('B', mda.getSecondaryBarometricPressureUnit());
    }

    @Test
    public void testSetPrimaryBarometricPressure() {
        mda.setPrimaryBarometricPressure(29.53);
        mda.setPrimaryBarometricPressureUnit('I');
        assertEquals(29.53, mda.getPrimaryBarometricPressure(), 0.01);
        assertEquals('I', mda.getPrimaryBarometricPressureUnit());
    }

    @Test
    public void testSetRelativeHumidity() {
        mda.setRelativeHumidity(55.5);
        assertEquals(55.5, mda.getRelativeHumidity(), 0.1);
    }

    @Test
    public void testSetDewPoint() {
        mda.setDewPoint(6.7);
        assertEquals(6.7, mda.getDewPoint(), 0.1);
    }

    @Test
    public void testSetWaterTemperature() {
        mda.setWaterTemperature(8.9);
        assertEquals(8.9, mda.getWaterTemperature(), 0.1);
    }
}

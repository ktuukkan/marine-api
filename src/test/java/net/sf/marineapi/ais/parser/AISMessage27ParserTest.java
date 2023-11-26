package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage27;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AIS message 27 parser tests
 * <p>
 * According to the specification: https://www.navcen.uscg.gov/?pageName=AISMessage27
 */
public class AISMessage27ParserTest {

    // !AIVDM,1,1,,,Kk:qFP0?fhT8=7m@,0*50
    private final String payload = "Kk:qFP0?fhT8=7m@";
    private final Sixbit sixbit = new Sixbit(payload, 0);
    private final AISMessage27 message = new AisMessage27Parser(sixbit);
    
    public AISMessage27ParserTest() {
    	
    }
    @Test
    public void getRepeatIndicator() {
        assertEquals(3, message.getRepeatIndicator());
    }

    @Test
    public void getMMSI() {
        assertEquals(212752000, message.getMMSI());
    }

    @Test
    public void isAccurate() {
        assertFalse(message.isAccurate());
    }

    @Test
    public void getRaimFlag() {
        assertFalse(message.getRAIMFlag());
    }

    @Test
    public void getNavigationalStatus() {
        assertEquals(0, message.getNavigationalStatus());
    }

    @Test
    public void getLongitude() {
        assertEquals(-7.3566666666666665, message.getLongitudeInDegrees(), 0);
    }

    @Test
    public void getLatitude() {
        assertEquals(56.36333333333334, message.getLatitudeInDegrees(), 0);
    }

    @Test
    public void getSpeedOverGround() {
        assertEquals(15.0, message.getSpeedOverGround(), 0);
    }

    @Test
    public void getCourseOverGround() {
        assertEquals(340.0, message.getCourseOverGround(), 0);
    }

    @Test
    public void getPositionLatency() {
        assertEquals(0, message.getPositionLatency());
    }

}
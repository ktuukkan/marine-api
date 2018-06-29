package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage09;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for AIS message 9 parser.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
public class AISMessage09ParserTest {

    // !AIVDO,1,1,,A,95M2oQ@41Tr4L4H@eRvQ;2h20000,0*0D
    private final String payload = "95M2oQ@41Tr4L4H@eRvQ;2h20000";
    private final Sixbit sixbit = new Sixbit(payload, 0);
    private final AISMessage09 msg = new AISMessage09Parser(sixbit);

    @Test
    public void getAltitude() throws Exception {
        assertEquals(16, msg.getAltitude());
    }

    @Test
    public void getSpeedOverGround() throws Exception {
        assertEquals(100.0, msg.getSpeedOverGround(), 0.1);
    }

    @Test
    public void getPositionAccuracy() throws Exception {
        assertEquals(true, msg.getPositionAccuracy());
    }

    @Test
    public void getLongitudeInDegrees() throws Exception {
        assertEquals(-82.91646, msg.getLongitudeInDegrees(), 0.00001);
    }

    @Test
    public void getLatitudeInDegrees() throws Exception {
        assertEquals(29.20575, msg.getLatitudeInDegrees(), 0.00001);
    }

    @Test
    public void getCourseOverGround() throws Exception {
        assertEquals(30.0, msg.getCourseOverGround(), 0.1);
    }

    @Test
    public void getTimeStamp() throws Exception {
        assertEquals(11, msg.getTimeStamp());
    }

    @Test
    public void getDTEFlag() throws Exception {
        // 1 == false, "not available" (default)
        assertEquals(false, msg.getDTEFlag());
    }

    @Test
    public void getAssignedModeFlag() throws Exception {
        // 0 == Autonomous and continuous mode (default)
        assertEquals(false, msg.getAssignedModeFlag());
    }

    @Test
    public void getRAIMFlag() throws Exception {
        // 0 = RAIM not in use (default)
        assertEquals(false, msg.getRAIMFlag());
    }

    @Test
    public void getRadioStatus() throws Exception {
        assertEquals(0, msg.getRadioStatus());
    }

    @Test
    public void isLatitudeAvailable() {
        assertEquals(true, msg.isLatitudeAvailable());
    }

    @Test
    public void isLongitudeAvailable() {
        assertEquals(true, msg.isLongitudeAvailable());
    }

}
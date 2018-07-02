package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage21;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AIS message 21 tests (Aids To Navigation Report).
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
public class AISMessage21ParserTest {

    // !AIVDO,2,1,5,B,E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5,0*7B
    // !AIVDO,2,2,5,B,1CQ1A83PCAH0,0*60
    private final String payload = "E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF51CQ1A83PCAH0";
    private final Sixbit sixbit = new Sixbit(payload, 0);
    private final AISMessage21 msg = new AISMessage21Parser(sixbit);

    @Test
    public void getAidType() throws Exception {
        // Nav Type?
        assertEquals(1, msg.getAidType());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("THIS IS A TEST NAME1", msg.getName());
    }

    @Test
    public void getPositionAccuracy() throws Exception {
        assertFalse(msg.getPositionAccuracy());
    }

    @Test
    public void getLongitudeInDegrees() throws Exception {
        assertEquals(145.181, msg.getLongitudeInDegrees(), 0.001);
    }

    @Test
    public void getLatitudeInDegrees() throws Exception {
        assertEquals(-38.220167, msg.getLatitudeInDegrees(), 0.000001);
    }

    @Test
    public void getBow() throws Exception {
        assertEquals(5, msg.getBow());
    }

    @Test
    public void getStern() throws Exception {
        assertEquals(3, msg.getStern());
    }

    @Test
    public void getPort() throws Exception {
        assertEquals(3, msg.getPort());
    }

    @Test
    public void getStarboard() throws Exception {
        assertEquals(5, msg.getStarboard());
    }

    @Test
    public void getTypeOfEPFD() throws Exception {
        assertEquals(1, msg.getTypeOfEPFD());
    }

    @Test
    public void getUtcSecond() throws Exception {
        // UTC time stamp?
        assertEquals(9, msg.getUtcSecond());
    }

    @Test
    public void getOffPositionIndicator() throws Exception {
        assertEquals(true, msg.getOffPositionIndicator());
    }

    @Test
    public void getRegional() throws Exception {
        // "00001010" ?
        assertEquals(10, msg.getRegional());
    }

    @Test
    public void getRAIMFlag() throws Exception {
        assertFalse(msg.getRAIMFlag());
    }

    @Test
    public void getVirtualAidFlag() throws Exception {
        assertFalse(msg.getVirtualAidFlag());
    }

    @Test
    public void getAssignedModeFlag() throws Exception {
        assertTrue(msg.getAssignedModeFlag());
    }

    @Test
    public void getNameExtension() throws Exception {
        assertEquals("EXTENDED NAME", msg.getNameExtension());
    }

    @Test
    public void hasLatitude() {
        assertEquals(true, msg.hasLatitude());
    }

    @Test
    public void hasLongitude() {
        assertEquals(true, msg.hasLongitude());
    }
}
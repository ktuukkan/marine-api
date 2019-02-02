package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISPositionReport;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * AISPositionReportParser test, covering parsers for types 01, 02 and 03.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
public class AISPositionReportParserTest {

    // !AIVDM,1,1,,A,13u?etPv2;0n:dDPwUM1U1Cb069D,0*23
    private final String payload = "13u?etPv2;0n:dDPwUM1U1Cb069D";
    private final Sixbit sixbit = new Sixbit(payload, 0);
    private final AISPositionReport msg = new AISPositionReportParser(sixbit);

    @Test
    public void getNavigationalStatus() throws Exception {
        assertEquals(0, msg.getNavigationalStatus());
    }

    @Test
    public void getRateOfTurn() throws Exception {
        assertEquals(-2.9, msg.getRateOfTurn(), 0.1);
    }

    @Test
    public void getSpeedOverGround() throws Exception {
        assertEquals(13.9, msg.getSpeedOverGround(), 0.1);
    }

    @Test
    public void getPositionAccuracy() throws Exception {
        // 1 == high (< 10 meters)
        assertTrue(msg.isAccurate());
    }

    @Test
    public void getLongitudeInDegrees() throws Exception {
        assertEquals(11.8329767, msg.getLongitudeInDegrees(), 0.0000001);
    }

    @Test
    public void getLatitudeInDegrees() throws Exception {
        assertEquals(57.6603533, msg.getLatitudeInDegrees(), 0.0000001);
    }

    @Test
    public void getCourseOverGround() throws Exception {
        assertEquals(40.4, msg.getCourseOverGround(), 0.1);
    }

    @Test
    public void getTrueHeading() throws Exception {
        assertEquals(41, msg.getTrueHeading());
    }

    @Test
    public void getTimeStamp() throws Exception {
        assertEquals(53, msg.getTimeStamp());
    }

    @Test
    public void getManouverIndicator() throws Exception {
        assertEquals(0, msg.getManouverIndicator());
    }

    @Test
    public void hasLatitude() {
        assertEquals(true, msg.hasLatitude());
    }

    @Test
    public void hasLongitude() {
        assertEquals(true, msg.hasLongitude());
    }

    @Test
    public void hasRateOfTurn() {
        assertEquals(true, msg.hasRateOfTurn());
    }

    @Test
    public void hasCourseOverGround() {
        assertEquals(true, msg.hasCourseOverGround());
    }

    @Test
    public void hasSpeedOverGround() {
        assertEquals(true, msg.hasSpeedOverGround());
    }

    @Test
    public void hasTimeStamp() {
        assertEquals(true, msg.hasTimeStamp());
    }
}
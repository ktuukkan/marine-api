package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISPositionReportB;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AIS position report "type B" tests, covering parsers 18 and 19.
 *
 * TODO: missing getters for "Class B" flags 13 - 20.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
public class AISPositionReportBParserTest {

    // !AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58
    private final String payload = "B6CdCm0t3`tba35f@V9faHi7kP06";
    private final Sixbit sixbit = new Sixbit(payload, 0);
    private final AISPositionReportB msg = new AISPositionReportBParser(sixbit);

    @Test
    public void getSpeedOverGround() throws Exception {
        assertEquals(1.4, msg.getSpeedOverGround(), 0.1);
    }

    @Test
    public void getPositionAccuracy() throws Exception {
        assertEquals(false, msg.getPositionAccuracy());
    }

    @Test
    public void getLongitudeInDegrees() throws Exception {
        assertEquals(53.010996667, msg.getLongitudeInDegrees(), 0.000000001);
    }

    @Test
    public void getLatitudeInDegrees() throws Exception {
        assertEquals(40.005283333, msg.getLatitudeInDegrees(), 0.000000001);
    }

    @Test
    public void getCourseOverGround() throws Exception {
        assertEquals(177.0, msg.getCourseOverGround(), 0.1);
    }

    @Test
    public void getTrueHeading() throws Exception {
        assertEquals(177.0, msg.getTrueHeading(), 0.1);
    }

    @Test
    public void getTimeStamp() throws Exception {
        assertEquals(34, msg.getTimeStamp());
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
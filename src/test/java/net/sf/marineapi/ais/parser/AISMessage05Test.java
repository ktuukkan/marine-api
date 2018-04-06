package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage05;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AIS Message 05 parser test.
 *
 * Expected values according to http://www.maritec.co.za/aisvdmvdodecoding1.php
 */
public class AISMessage05Test {

    // !AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B
    // !AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D
    private final String payload = "58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0skeQ8823mDm3kP00000000000";
    private final Sixbit sixbit = new Sixbit(payload, 2);
    private final AISMessage05 msg = new AISMessage05Parser(sixbit);

    @Test
    public void getAISVersionIndicator() throws Exception {
        assertEquals(0, msg.getAISVersionIndicator());
    }

    @Test
    public void getIMONumber() throws Exception {
        assertEquals(439303422, msg.getIMONumber());
    }

    @Test
    public void getCallSign() throws Exception {
        assertEquals("ZA83R", msg.getCallSign());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("ARCO AVON", msg.getName());
    }

    @Test
    public void getTypeOfShipAndCargoType() throws Exception {
        assertEquals(69, msg.getTypeOfShipAndCargoType());
    }

    @Test
    public void getBow() throws Exception {
        assertEquals(113, msg.getBow());
    }

    @Test
    public void getStern() throws Exception {
        assertEquals(31, msg.getStern());
    }

    @Test
    public void getPort() throws Exception {
        assertEquals(17, msg.getPort());
    }

    @Test
    public void getStarboard() throws Exception {
        assertEquals(11, msg.getStarboard());
    }

    @Test
    public void getTypeOfEPFD() throws Exception {
        assertEquals(0, msg.getTypeOfEPFD());
    }

    @Test
    public void getETAMonth() throws Exception {
        assertEquals(3, msg.getETAMonth());
    }

    @Test
    public void getETADay() throws Exception {
        assertEquals(23, msg.getETADay());
    }

    @Test
    public void getETAHour() throws Exception {
        assertEquals(19, msg.getETAHour());
    }

    @Test
    public void getETAMinute() throws Exception {
        assertEquals(45, msg.getETAMinute());
    }

    @Test
    public void getMaximumDraught() throws Exception {
        assertEquals(13.2, msg.getMaximumDraught(), 0.1);
    }

    @Test
    public void getDestination() throws Exception {
        assertEquals("HOUSTON", msg.getDestination());
    }

}
package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage24;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AIS message 24 parser tests (parts A & B)
 *
 * Expected values according to http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
public class AISMessage24ParserTest {

    // !AIVDO,1,1,,B,H1c2;qA@PU>0U>060<h5=>0:1Dp,2*7D (part A)
    // !AIVDO,1,1,,B,H1c2;qDTijklmno31<<C970`43<1,0*28 (part B)
    private final String payloadA = "H1c2;qA@PU>0U>060<h5=>0:1Dp";
    private final String payloadB = "H1c2;qDTijklmno31<<C970`43<1";
    private final Sixbit sixbitA = new Sixbit(payloadA, 2);
    private final Sixbit sixbitB = new Sixbit(payloadB, 0);
    private final AISMessage24 partA = new AISMessage24Parser(sixbitA);
    private final AISMessage24 partB = new AISMessage24Parser(sixbitB);

    @Test
    public void getPartNumber() throws Exception {
        assertEquals(0, partA.getPartNumber());
        assertEquals(1, partB.getPartNumber());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("THIS IS A CLASS B UN", partA.getName());
    }

    @Test
    public void getTypeOfShipAndCargoType() throws Exception {
        assertEquals(36, partB.getTypeOfShipAndCargoType());
    }

    @Test
    public void getVendorId() throws Exception {
        // TODO correct? should be "1234567" according to http://www.maritec.co.za/tools/aisvdmvdodecoding/
        assertEquals("123", partB.getVendorId());
    }

    @Test
    public void getUnitModelCode() throws Exception {
        // TODO correct?
        assertEquals(13, partB.getUnitModelCode());
    }

    @Test
    public void getSerialNumber() throws Exception {
        // TODO correct?
        assertEquals(220599, partB.getSerialNumber());
    }

    @Test
    public void getCallSign() throws Exception {
        assertEquals("CALLSIG", partB.getCallSign());
    }

    @Test
    public void getBow() throws Exception {
        assertEquals(5, partB.getBow());
    }

    @Test
    public void getStern() throws Exception {
        assertEquals(4, partB.getStern());
    }

    @Test
    public void getPort() throws Exception {
        assertEquals(3, partB.getPort());
    }

    @Test
    public void getStarboard() throws Exception {
        assertEquals(12, partB.getStarboard());
    }

}
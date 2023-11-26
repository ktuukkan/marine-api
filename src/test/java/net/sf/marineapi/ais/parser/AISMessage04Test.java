package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage04;
import net.sf.marineapi.ais.util.Sixbit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AIS message 04 test.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
public class AISMessage04Test {
   // !AIVDM,1,1,,A,400TcdiuiT7VDR>3nIfr6>i00000,0*78
    private final String payload = "400TcdiuiT7VDR>3nIfr6>i00000";
    private final Sixbit sixbit = new Sixbit(payload, 0);
    private final AISMessage04 msg = new AISMessage04Parser(sixbit);
    
    
	public AISMessage04Test() {
		
	}
	
    @Test
    public void getUtcYear() throws Exception {
        assertEquals(2012, msg.getUtcYear());
    }

    @Test
    public void getUtcMonth() throws Exception {
        assertEquals(6, msg.getUtcMonth());
    }

    @Test
    public void getUtcDay() throws Exception {
        assertEquals(8, msg.getUtcDay());
    }

    @Test
    public void getUtcHour() throws Exception {
        assertEquals(7, msg.getUtcHour());
    }

    @Test
    public void getUtcMinute() throws Exception {
        assertEquals(38, msg.getUtcMinute());
    }

    @Test
    public void getUtcSecond() throws Exception {
        assertEquals(20, msg.getUtcSecond());
    }

    @Test
    public void getLatitudeInDegrees() throws Exception {
        assertEquals(-29.870835, msg.getLatitudeInDegrees(), 0.000001);
    }

    @Test
    public void getLongitudeInDegrees() throws Exception {
        assertEquals(31.033513, msg.getLongitudeInDegrees(), 0.000001);
    }

    @Test
    public void getTypeOfEPFD() throws Exception {
        // 1 = GPS
        assertEquals(1, msg.getTypeOfEPFD());
    }

}
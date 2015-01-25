package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.Measurement;

import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class XDRTest {

    public static final String EXAMPLE = "$IIXDR,P,1.02481,B,Barometer";

    public static final String EXAMPLE1 = "$WIXDR,C,022.5,C,,P,0.996,B";

    public static final String EXAMPLE2 = "$WIXDR,P,111.1,B,3,C,222.2,C,0,H,333.3,P,2,C,444.4,C,1";

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#XDRParser(String)}.
     */
    @Test
    public void testConstructor() {
        XDRParser xdr = new XDRParser(EXAMPLE);
        assertTrue(xdr.isValid());
        assertEquals("XDR", xdr.getSentenceId());
        assertEquals(4, xdr.getFieldCount());
        assertEquals('P', xdr.getCharValue(0));
        assertEquals(1.02481, xdr.getDoubleValue(1), 0.00001);
        assertEquals('B', xdr.getCharValue(2));
        assertEquals("Barometer", xdr.getStringValue(3));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#addMeasurement(Measurement...)
     * )}.
     */
    @Test
    public void testAddAnotherMeasurement() {

        XDRParser xdr = new XDRParser(EXAMPLE);
        Measurement value = new Measurement("C", 19.9, "C", "TempAir");

        xdr.addMeasurement(value);

        assertEquals(8, xdr.getFieldCount());
        assertTrue(xdr.toString().startsWith("$IIXDR,P,1.02481,B,Barometer,C,19.9,C,TempAir*"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#addMeasurement(Measurement...)
     * )}.
     */
    @Test
    public void testAddMeasurementToEmpty() {

        XDRParser xdr = new XDRParser(TalkerId.II);
        Measurement value = new Measurement("C", 19.9, "C", "TempAir");

        xdr.addMeasurement(value);

        assertEquals(4, xdr.getFieldCount());
        assertTrue(xdr.toString().startsWith("$IIXDR,C,19.9,C,TempAir*"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#addMeasurement(Measurement...)
     * )))}.
     */
    @Test
    public void testAddMultipleMeasurements() {

        XDRParser xdr = new XDRParser(TalkerId.II);
        Measurement m1 = new Measurement("C", 19.9, "C", "TempAir");
        Measurement m2 = new Measurement("P", 1.08, "B", "Barometer");

        xdr.addMeasurement(m1, m2);

        assertEquals(8, xdr.getFieldCount());
        assertTrue(xdr.toString().startsWith("$IIXDR,C,19.9,C,TempAir,P,1.08,B,Barometer*"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#getMeasurements()}.
     */
    @Test
    public void testGetMeasurements() {

        XDRParser xdr = new XDRParser(EXAMPLE);

        List<Measurement> values = xdr.getMeasurements();
        assertEquals(1, values.size());

        Measurement value = values.get(0);
        assertEquals("P", value.getType());
        assertEquals(1.02481, value.getValue(), 0.00001);
        assertEquals("B", value.getUnits());
        assertEquals("Barometer", value.getName());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#setMeasurement(java.util.List)}
     * .
     */
    @Test
    public void testSetMeasurement() {

        XDRParser xdr = new XDRParser(TalkerId.II);
        Measurement value = new Measurement("C", 19.9, "C", "TempAir");

        xdr.setMeasurement(value);

        assertEquals(4, xdr.getFieldCount());
        assertTrue(xdr.toString().startsWith("$IIXDR,C,19.9,C,TempAir*"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#setMeasurements(java.util.List)}
     * .
     */
    @Test
    public void testSetMeasurementAsList() {

        XDRParser xdr = new XDRParser(TalkerId.II);
        Measurement value = new Measurement("C", 19.9, "C", "TempAir");
        List<Measurement> values = new ArrayList<Measurement>();
        values.add(value);

        xdr.setMeasurements(values);

        assertEquals(4, xdr.getFieldCount());
        assertTrue(xdr.toString().startsWith("$IIXDR,C,19.9,C,TempAir*"));
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.XDRParser#setMeasurements(java.util.List)}
     * .
     */
    @Test
    public void testSetMeasurementsList() {

        XDRParser xdr = new XDRParser(TalkerId.II);
        Measurement v1 = new Measurement("C", 19.9, "C", "TempAir");
        Measurement v2 = new Measurement("P", 1.08, "B", "Barometer");
        List<Measurement> values = new ArrayList<Measurement>();
        values.add(v1);
        values.add(v2);

        xdr.setMeasurements(values);

        assertEquals(8, xdr.getFieldCount());
        assertTrue(xdr.toString().startsWith("$IIXDR,C,19.9,C,TempAir,P,1.08,B,Barometer*"));
    }

    /**
     * Test method for "incomplete" message in different formats with multiple
     * values.
     * .
     */
    @Test
    public void testMultipleValues() {
        XDRParser xdr = new XDRParser(EXAMPLE1);
        assertTrue(xdr.isValid());
        assertEquals("XDR", xdr.getSentenceId());
        assertEquals(2, xdr.getMeasurements().size());
        Measurement mesurement = xdr.getMeasurements().get(0);
        assertEquals(null, mesurement.getName());
        assertEquals("C", mesurement.getType());
        assertEquals("C", mesurement.getUnits());
        assertEquals(22.5, mesurement.getValue(), 0.00001);
        mesurement = xdr.getMeasurements().get(1);
        assertEquals(null, mesurement.getName());
        assertEquals("P", mesurement.getType());
        assertEquals("B", mesurement.getUnits());
        assertEquals(0.996, mesurement.getValue(), 0.00001);
    }

    /**
     * Test method for "incomplete" message in different formats with 4
     * values.
     * .
     */
    @Test
    public void test4MultipleValues() {
        XDRParser xdr = new XDRParser(EXAMPLE2);
        assertTrue(xdr.isValid());
        assertEquals("XDR", xdr.getSentenceId());
        assertEquals(4, xdr.getMeasurements().size());
        Measurement mesurement = xdr.getMeasurements().get(0);
        assertEquals("3", mesurement.getName());
        assertEquals("P", mesurement.getType());
        assertEquals("B", mesurement.getUnits());
        assertEquals(111.1, mesurement.getValue(), 0.00001);
        mesurement = xdr.getMeasurements().get(1);
        assertEquals("0", mesurement.getName());
        assertEquals("C", mesurement.getType());
        assertEquals("C", mesurement.getUnits());
        assertEquals(222.2, mesurement.getValue(), 0.00001);
        mesurement = xdr.getMeasurements().get(2);
        assertEquals("2", mesurement.getName());
        assertEquals("H", mesurement.getType());
        assertEquals("P", mesurement.getUnits());
        assertEquals(333.3, mesurement.getValue(), 0.00001);
        mesurement = xdr.getMeasurements().get(3);
        assertEquals("1", mesurement.getName());
        assertEquals("C", mesurement.getType());
        assertEquals("C", mesurement.getUnits());
        assertEquals(444.4, mesurement.getValue(), 0.00001);
    }
}

package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
	
	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#XDRParser(String)}.
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
	
	
	@Test
	public void testConstructorWithThreeFields() {
		
		XDRParser xdr = new XDRParser("$WIXDR,U,014.9,V,");
		
		List<Measurement> ml = xdr.getMeasurements();
		assertEquals(1, ml.size());
		
		Measurement m = ml.get(0);
		assertEquals("U", m.getType());
		assertEquals(14.9, m.getValue(), 0.1);
		assertEquals("V", m.getUnits());
		assertNull(m.getName());
	}
	
	@Test
	public void testConstructorWithMultipleMeasurements() {
		
		XDRParser xdr = new XDRParser("$WIXDR,P,111.1,B,3,C,222.2,C,0,H,333.3,P,2,C,444.4,C,1");
		
		List<Measurement> ml = xdr.getMeasurements();
		assertEquals(4, ml.size());
		
		Measurement m1 = ml.get(0);
		assertEquals("P", m1.getType());
		assertEquals(111.1, m1.getValue(), 0.1);
		assertEquals("B", m1.getUnits());
		assertEquals("3", m1.getName());
		

		Measurement m2 = ml.get(1);
		assertEquals("C", m2.getType());
		assertEquals(222.2, m2.getValue(), 0.1);
		assertEquals("C", m2.getUnits());
		assertEquals("0", m2.getName());

		Measurement m3 = ml.get(2);
		assertEquals("H", m3.getType());
		assertEquals(333.3, m3.getValue(), 0.1);
		assertEquals("P", m3.getUnits());
		assertEquals("2", m3.getName());

		Measurement m4 = ml.get(3);
		assertEquals("C", m4.getType());
		assertEquals(444.4, m4.getValue(), 0.1);
		assertEquals("C", m4.getUnits());
		assertEquals("1", m4.getName());
	}
	
	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#addMeasurement(Measurement...))}.
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
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#addMeasurement(Measurement...))}.
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
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#addMeasurement(Measurement...))))}.
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
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#getMeasurements()}.
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
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#setMeasurement(java.util.List)}.
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
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#setMeasurements(java.util.List)}.
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
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#setMeasurements(java.util.List)}.
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
}

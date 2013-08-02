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
	
	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#XDRParser(String)}.
	 */
	@Test
	public void testConstructor() {
		XDRParser xdr = new XDRParser(EXAMPLE);
		assertEquals("XDR", xdr.getSentenceId());
		assertEquals(4, xdr.getFieldCount());
	}
	
	/**
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#getMeasurements()}.
	 */
	@Test
	public void testGetValues() {
		
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
	 * Test method for {@link net.sf.marineapi.nmea.parser.XDRParser#setMeasurements(java.util.List)}.
	 */
	@Test
	public void testSetSingleValueSet() {
		
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
	public void testSetSingleValueSetAsList() {
		
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
	public void testSetMultipleValueSets() {
		
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

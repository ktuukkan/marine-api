package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.DBTSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

import org.junit.Before;
import org.junit.Test;

public class DBTTest {

	public static final String EXAMPLE = "$IIDBT,013.4,f,04.1,M,02.2,F*12";
	private DBTSentence dbt;
	private DBTSentence empty;

	@Before
	public void setUp() throws Exception {
		empty = new DBTParser(TalkerId.II);
		dbt = new DBTParser(EXAMPLE);
	}

	@Test
	public void testConstructor() {
		assertEquals("DBT", empty.getSentenceId());
		assertEquals(TalkerId.II, empty.getTalkerId());
		assertEquals(6, empty.getFieldCount());
	}

	@Test
	public void testGetFathoms() {
		assertEquals(2.2, dbt.getFathoms(), 0.01);
	}

	@Test
	public void testGetFeet() {
		assertEquals(13.4, dbt.getFeet(), 0.01);
	}

	@Test
	public void testGetMeters() {
		assertEquals(4.1, dbt.getDepth(), 0.01);
	}

	@Test
	public void testSetFathoms() {
		empty.setFathoms(7.33333);
		assertEquals(7.3, empty.getFathoms(), 0.1);
	}

	@Test
	public void testSetFeet() {
		empty.setFeet(12.33333);
		assertEquals(12.3, empty.getFeet(), 0.1);
	}

	@Test
	public void testSetMeters() {
		empty.setDepth(23.654321);
		assertEquals(23.7, empty.getDepth(), 0.1);
	}

}

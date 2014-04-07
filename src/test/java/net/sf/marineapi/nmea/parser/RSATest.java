package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.RSASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Side;

import org.junit.Before;
import org.junit.Test;

public class RSATest {

	public static final String EXAMPLE = "$IIRSA,1.2,A,2.3,V";

	RSASentence empty;
	RSASentence instance;

	@Before
	public void setUp() throws Exception {
		empty = new RSAParser(TalkerId.II);
		instance = new RSAParser(EXAMPLE);
	}

	@Test
	public void testRSAParserString() {
		assertEquals(TalkerId.II, instance.getTalkerId());
		assertEquals(SentenceId.RSA.name(), instance.getSentenceId());
	}

	@Test
	public void testRSAParserTalkerId() {
		assertEquals(TalkerId.II, empty.getTalkerId());
		assertEquals(SentenceId.RSA.name(), empty.getSentenceId());
		assertEquals(DataStatus.VOID, empty.getStatus(Side.STARBOARD));
		assertEquals(DataStatus.VOID, empty.getStatus(Side.PORT));
	}

	@Test
	public void testGetRudderAngle() {
		assertEquals(1.2, instance.getRudderAngle(Side.STARBOARD), 0.1);
		assertEquals(2.3, instance.getRudderAngle(Side.PORT), 0.1);
	}

	@Test
	public void testSetRudderAngle() {
		final double portAngle = 4.5;
		final double starboardAngle = 5.6;
		instance.setRudderAngle(Side.PORT, portAngle);
		instance.setRudderAngle(Side.STARBOARD, starboardAngle);
		assertEquals(portAngle, instance.getRudderAngle(Side.PORT), 0.1);
		assertEquals(starboardAngle, instance.getRudderAngle(Side.STARBOARD), 0.1);
	}

	@Test
	public void testGetStatus() {
		assertEquals(DataStatus.ACTIVE, instance.getStatus(Side.STARBOARD));
		assertEquals(DataStatus.VOID, instance.getStatus(Side.PORT));
	}

	@Test
	public void testSetStatusPort() {
		empty.setStatus(Side.PORT, DataStatus.ACTIVE);
		assertEquals(DataStatus.ACTIVE, empty.getStatus(Side.PORT));
	}

	@Test
	public void testSetStatusStarboard() {
		empty.setStatus(Side.STARBOARD, DataStatus.ACTIVE);
		assertEquals(DataStatus.ACTIVE, empty.getStatus(Side.STARBOARD));
	}
}

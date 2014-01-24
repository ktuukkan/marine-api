package net.sf.marineapi.nmea.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.BODSentence;
import net.sf.marineapi.nmea.sentence.Sentence;

import org.junit.Before;
import org.junit.Test;

public class AbstractSentenceListenerTest {

	private BODSentence result;
	private TestListener listener;
	private SentenceFactory factory;
	
	
	@Before
	public void setUp() throws Exception {
		result = null;
		listener = new TestListener();
		factory = SentenceFactory.getInstance();
	}

	@Test
	public void testExpectedSentenceRead() {
		Sentence bod = factory.createParser(BODTest.EXAMPLE);
		SentenceEvent evt = new SentenceEvent(this, bod);
		listener.sentenceRead(evt);
		assertNotNull(result);
		assertEquals(BODTest.EXAMPLE, result.toSentence());
	}
	
	@Test
	public void testUnexpectedSentenceRead() {
		Sentence gga = factory.createParser(GGATest.EXAMPLE);
		SentenceEvent evt = new SentenceEvent(this, gga);
		listener.sentenceRead(evt);
		assertNull(result);
	}
	
	
	private class TestListener extends AbstractSentenceListener<BODSentence> {
		@Override
		public void sentenceRead(BODSentence sentence) {
			result = sentence;						
		}
	}

}

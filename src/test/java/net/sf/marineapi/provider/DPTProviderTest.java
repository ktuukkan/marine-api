package net.sf.marineapi.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.provider.event.DBTEvent;
import net.sf.marineapi.provider.event.DBTListener;
import net.sf.marineapi.provider.event.DPTEvent;
import net.sf.marineapi.provider.event.DPTListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class DPTProviderTest implements DPTListener {

	private SentenceFactory factory;

	private DPTProvider instance;

	private DPTEvent event;
        
        private static String EXAMPLE = "$IIDPT,03.3,00.3,00.7*76";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		factory = SentenceFactory.getInstance();
		File file = new File("target/test-classes/data/sample1.txt");
		FileInputStream str = new FileInputStream(file);
		SentenceReader r = new SentenceReader(str);
		instance = new DPTProvider(r);
		instance.addListener(this);
		event = null;
	}

	@After
	public void tearDown() {
		instance.removeListener(this);
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.provider.AbstractProvider#sentenceRead(net.sf.marineapi.nmea.event.SentenceEvent)}
	 * .
	 */
	@Test
	public void testDPTSentenceRead() {

		Sentence s = factory.createParser(EXAMPLE);
		assertNull(event);

		instance.sentenceRead(new SentenceEvent(this, s));

		assertNotNull(event);
		assertEquals(3.3, event.getDeptMeters(), 0.1);
                assertEquals(00.7, event.getMaximum(), 0.1);
                assertEquals(00.3, event.getOffset(), 0.1);
	}





	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.HeadingListener#providerUpdate(net.sf
	 * .marineapi.provider.event.HeadingEvent)
	 */
	public void providerUpdate(DPTEvent evt) {
		this.event = evt;
	}

}

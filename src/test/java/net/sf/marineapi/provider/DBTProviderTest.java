package net.sf.marineapi.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.parser.HDGTest;
import net.sf.marineapi.nmea.parser.HDMTest;
import net.sf.marineapi.nmea.parser.HDTTest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.provider.event.DBTEvent;
import net.sf.marineapi.provider.event.DBTListener;
import net.sf.marineapi.provider.event.HeadingEvent;
import net.sf.marineapi.provider.event.HeadingListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class DBTProviderTest implements DBTListener {

	private SentenceFactory factory;

	private DBTProvider instance;

	private DBTEvent event;
        
        private static String EXAMPLE = "$IIDBT,10.8,f,03.3,M,01.8,F*21";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		factory = SentenceFactory.getInstance();
		File file = new File("target/test-classes/data/sample1.txt");
		FileInputStream str = new FileInputStream(file);
		SentenceReader r = new SentenceReader(str);
		instance = new DBTProvider(r);
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
	public void testDBTSentenceRead() {

		Sentence s = factory.createParser(EXAMPLE);
		assertNull(event);

		instance.sentenceRead(new SentenceEvent(this, s));

		assertNotNull(event);
		assertEquals(1.8, event.getDeptFathoms(), 0.1);
                assertEquals(10.8, event.getDeptFeet(), 0.1);
                assertEquals(3.3, event.getDeptMeters(), 0.1);
	}





	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.HeadingListener#providerUpdate(net.sf
	 * .marineapi.provider.event.HeadingEvent)
	 */
	public void providerUpdate(DBTEvent evt) {
		this.event = evt;
	}

}

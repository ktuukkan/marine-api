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
import net.sf.marineapi.provider.event.HeadingEvent;
import net.sf.marineapi.provider.event.HeadingListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kimmo Tuukkanen
 */
public class HeadingProviderTest implements HeadingListener {

	private SentenceFactory factory;

	private HeadingProvider instance;

	private HeadingEvent event;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		factory = SentenceFactory.getInstance();
		File file = new File("target/test-classes/data/sample1.txt");
		FileInputStream str = new FileInputStream(file);
		SentenceReader r = new SentenceReader(str);
		instance = new HeadingProvider(r);
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
	public void testHDMSentenceRead() {

		Sentence s = factory.createParser(HDMTest.EXAMPLE);
		assertNull(event);

		instance.sentenceRead(new SentenceEvent(this, s));

		assertNotNull(event);
		assertEquals(90.0, event.getHeading(), 0.1);
		assertFalse(event.isTrue());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.provider.AbstractProvider#sentenceRead(net.sf.marineapi.nmea.event.SentenceEvent)}
	 * .
	 */
	@Test
	public void testHDTSentenceRead() {

		Sentence s = factory.createParser(HDTTest.EXAMPLE);
		assertNull(event);

		instance.sentenceRead(new SentenceEvent(this, s));
	
		assertNotNull(event);
		assertEquals(90.1, event.getHeading(), 0.1);
		assertTrue(event.isTrue());
	}

	/**
	 * Test method for
	 * {@link net.sf.marineapi.provider.AbstractProvider#sentenceRead(net.sf.marineapi.nmea.event.SentenceEvent)}
	 * .
	 */
	@Test
	public void testHDGSentenceRead() {

		Sentence s = factory.createParser(HDGTest.EXAMPLE);
		assertNull(event);

		instance.sentenceRead(new SentenceEvent(this, s));
		
		assertNotNull(event);
		assertEquals(123.4, event.getHeading(), 0.1);
		assertFalse(event.isTrue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.HeadingListener#providerUpdate(net.sf
	 * .marineapi.provider.event.HeadingEvent)
	 */
	public void providerUpdate(HeadingEvent evt) {
		this.event = evt;
	}

}

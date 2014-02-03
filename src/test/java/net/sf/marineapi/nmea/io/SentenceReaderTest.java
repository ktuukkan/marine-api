package net.sf.marineapi.nmea.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SentenceReaderTest {

	public final static String TEST_DATA =
		"src/test/resources/data/Navibe-GM720.txt";
	
	private Sentence sentence;
	private SentenceReader reader;
	private SentenceListener dummyListener;
	private SentenceListener testListener;
	private boolean paused;
	private boolean started;
	private boolean stopped;

	@Before
	public void setUp() throws Exception {
		File file = new File(TEST_DATA);
		InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);
		
		dummyListener = new DummyListener();
		testListener = new TestListener();
		reader.addSentenceListener(dummyListener);
		reader.addSentenceListener(testListener, SentenceId.GGA);
	}

	@Test
	public void testAddSentenceListenerSentenceListenerString() {
		DummyListener dummy = new DummyListener();
		reader.addSentenceListener(dummy, "GLL");
	}

	@Test
	public void testGetPauseTimeout() {
		assertEquals(SentenceReader.DEFAULT_TIMEOUT, reader.getPauseTimeout());
	}

	@Test
	public void testRemoveSentenceListener() {
		assertFalse(started);
		reader.removeSentenceListener(testListener);
		reader.fireReadingStarted();
		assertFalse(started);
	}

	@Test
	public void testSetInputStream() throws Exception {
		File file = new File("src/test/resources/data/Garmin-GPS76.txt");
		InputStream stream = new FileInputStream(file);
		reader.setInputStream(stream);
	}

	@Test
	@Ignore
	public void testSetDatagramSocket() {
		// TODO mock socket
	}

	@Test
	public void testSetPauseTimeout() {
		final int timeout = 2500;
		reader.setPauseTimeout(timeout);
		assertEquals(timeout, reader.getPauseTimeout());
	}

	@Test
	public void testFireReadingPaused() {
		assertFalse(paused);
		reader.fireReadingPaused();
		assertTrue(paused);
	}

	@Test
	public void testFireReadingStarted() {
		assertFalse(started);
		reader.fireReadingStarted();
		assertTrue(started);
	}

	@Test
	public void testFireReadingStopped() {
		assertFalse(stopped);
		reader.fireReadingStopped();
		assertTrue(stopped);
	}

	@Test
	public void testFireSentenceEventWithExpectedType() {
		assertNull(sentence);
		SentenceFactory sf = SentenceFactory.getInstance();
		Sentence s = sf.createParser(GGATest.EXAMPLE);
		reader.fireSentenceEvent(s);
		assertEquals(s, sentence);
	}

	@Test
	public void testFireSentenceEventWithUnexpectedType() {
		assertNull(sentence);
		SentenceFactory sf = SentenceFactory.getInstance();
		Sentence s = sf.createParser(BODTest.EXAMPLE);
		reader.fireSentenceEvent(s);
		assertNull(sentence);
	}
	
	@Test
	public void testStartAndStop() {
		try {
			assertNull(sentence);
			assertFalse(started);
			assertFalse(paused);
			assertFalse(stopped);
			
			reader.start();
			Thread.sleep(500);
			
			assertNotNull(sentence);
			assertTrue(started);
			assertFalse(paused);
			assertFalse(stopped);
			
			reader.stop();
			Thread.sleep(100);
			
			assertFalse(paused);
			assertTrue(stopped);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public class DummyListener implements SentenceListener {
		public void readingPaused() {}
		public void readingStarted() {}
		public void readingStopped() {}
		public void sentenceRead(SentenceEvent event) {}
	}

	public class TestListener implements SentenceListener {
		public void readingPaused() { paused = true; }
		public void readingStarted() { started = true; }
		public void readingStopped() { stopped = true; }
		public void sentenceRead(SentenceEvent event) {
			sentence = event.getSentence();
		}
	}
	
}

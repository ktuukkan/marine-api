package net.sf.marineapi.nmea.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.sentence.SentenceId;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SentenceReaderTest {

	private SentenceReader reader;
	private SentenceListener listener;
	
	@Before
	public void setUp() throws Exception {
		File file = new File("src/test/resources/data/Navibe-GM720.txt");
		InputStream stream = new FileInputStream(file);
		reader = new SentenceReader(stream);
		listener = new DummyListener();
		reader.addSentenceListener(listener);
	}

	@Test
	public void testSentenceReaderInputStream() {

	}

	@Test
	public void testAddSentenceListenerSentenceListenerSentenceId() {
		DummyListener dummy = new DummyListener();
		reader.addSentenceListener(dummy, SentenceId.GGA);
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
		DummyListener dummy = new DummyListener();
		reader.addSentenceListener(dummy);
		reader.removeSentenceListener(dummy);
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
	public void testStart() {
		reader.start();
	}

	@Test
	public void testStop() {
		try {
			reader.start();
			Thread.sleep(1000);
			reader.stop();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void testFireReadingPaused() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testFireReadingStarted() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testFireReadingStopped() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testFireSentenceEvent() {
		fail("Not yet implemented");
	}
		
	public class DummyListener implements SentenceListener {
		public void readingPaused() {}
		public void readingStarted() {}
		public void readingStopped() {}
		public void sentenceRead(SentenceEvent event) {}
	}
}

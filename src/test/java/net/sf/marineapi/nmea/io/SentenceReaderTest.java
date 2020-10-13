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
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.parser.TXTTest;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;

import net.sf.marineapi.nmea.sentence.TXTSentence;
import net.sf.marineapi.test.util.UDPServerMock;
import org.junit.Before;
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
	private InputStream stream;

	@Before
	public void setUp() throws Exception {
		File file = new File(TEST_DATA);
		stream = new FileInputStream(file);
		reader = new SentenceReader(stream);

		dummyListener = new DummySentenceListener();
		testListener = new TestSentenceListener();
		reader.addSentenceListener(dummyListener);
		reader.addSentenceListener(testListener, SentenceId.GGA);
	}

	@Test
	public void testConstructorWithCustomReader() throws Throwable {
		SentenceReader reader = new SentenceReader(new DummyDataReader(TXTTest.EXAMPLE));
		reader.addSentenceListener(new TestSentenceListener());
		reader.start();
		Thread.sleep(200);
		reader.stop();
		assertEquals(sentence.toString(), TXTTest.EXAMPLE);
	}

	@Test
	public void testAddSentenceListenerSentenceListenerString() {
		DummySentenceListener dummy = new DummySentenceListener();
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
	public void testRemoveSentenceListenerByType() {

		reader.removeSentenceListener(testListener);
		reader.removeSentenceListener(dummyListener);
		assertEquals(0, reader.getSentenceListeners().size());

		reader.addSentenceListener(testListener, SentenceId.GLL);
		reader.addSentenceListener(dummyListener, SentenceId.GGA);
		assertEquals(2, reader.getSentenceListeners().size());

		reader.removeSentenceListener(testListener, SentenceId.GLL);
		assertEquals(1, reader.getSentenceListeners().size());

		reader.removeSentenceListener(dummyListener, SentenceId.GNS);
		assertEquals(1, reader.getSentenceListeners().size());

		reader.removeSentenceListener(dummyListener, SentenceId.GGA);
		assertEquals(0, reader.getSentenceListeners().size());
	}

	@Test
	public void testSetInputStream() throws Exception {
		File file = new File("src/test/resources/data/Garmin-GPS76.txt");
		InputStream stream = new FileInputStream(file);
		reader.setInputStream(stream);
	}

	@Test
	public void testSetDatagramSocket() throws Exception {

		UDPServerMock server = new UDPServerMock();
		List<TXTSentence> received = new ArrayList<>();

		InetAddress host = InetAddress.getLocalHost();
		DatagramSocket socket = new DatagramSocket(3810, host);
		reader.setDatagramSocket(socket);

		reader.addSentenceListener(new AbstractSentenceListener<TXTSentence>() {
			@Override
			public void sentenceRead(TXTSentence sentence) {
				received.add(sentence);
				if (received.size() == 4) {
					reader.stop();
					server.stop();
					socket.close();
				}
			}
		});

		reader.start();
		Thread.sleep(1000);

		assertFalse(received.isEmpty());
		assertEquals(server.TXT, received.get(0).toString());
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

			reader.stop();
			Thread.sleep(100);

			assertFalse(paused);
			assertTrue(stopped);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testHandleException() {

		final String ERR_MSG = "test error";

		reader.setExceptionListener(new ExceptionListener() {
			private int calls = 0;
			@Override
			public void onException(Exception e) {
				assertEquals(calls++, 0);
				assertTrue(e instanceof IllegalStateException);
				assertEquals(ERR_MSG, e.getMessage());
			}
		});

		reader.handleException("test", new IllegalStateException(ERR_MSG));
	}

	@Test
	public void testDataListener() {

		// expected non-NMEA line in TEST_DATA
		final String expected = "=~=~=~=~=~=~=~=~=~=~=~= PuTTY log 2010.02.13 13:08:23 =~=~=~=~=~=~=~=~=~=~=~=";

		DataListener listener = new DataListener() {
			@Override
			public void dataRead(String data) {
				assertEquals(expected, data);
				reader.stop();
			}
		};
		reader.setDataListener(listener);
		reader.start();
	}

	public class DummySentenceListener implements SentenceListener {
		public void readingPaused() {
		}

		public void readingStarted() {
		}

		public void readingStopped() {
		}

		public void sentenceRead(SentenceEvent event) {
		}
	}

	public class TestSentenceListener implements SentenceListener {

		public void readingPaused() {
			paused = true;
		}

		public void readingStarted() {
			started = true;
		}

		public void readingStopped() {
			stopped = true;
		}

		public void sentenceRead(SentenceEvent event) {
			sentence = event.getSentence();
		}
	}

	// Test "reader" that only repeats the given sentence
	public class DummyDataReader extends AbstractDataReader {

		private String sentence;

		public DummyDataReader(String sentence) {
			this.sentence = sentence;
		}

		@Override
		public String read() throws Exception {
			return this.sentence;
		}
	}

}

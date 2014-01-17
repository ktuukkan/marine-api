package net.sf.marineapi.nmea.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import net.sf.marineapi.nmea.sentence.Sentence;

/**
 * <p>
 * Abstract base class for typed listeners with automatic sentence type
 * resolving and casting. Extend this class to create a listener/handler for a
 * single sentence type and register it in
 * {@link net.sf.marineapi.nmea.io.SentenceReader}.</p>
 * <p>
 * Methods from {@link SentenceListener} interface implemented in this class are empty,
 * except {@link #sentenceRead(SentenceEvent)} which detects the incoming
 * sentence parsers and casts them to desired sentence interface before calling
 * {@link #sentenceRead(Sentence)} method.
 *
 * @author Kimmo Tuukkanen
 * @param <T> Sentence interface to be listened.
 * @see net.sf.marineapi.nmea.io.SentenceReader
 */
public abstract class AbstractSentenceListener<T extends Sentence>
	implements SentenceListener {

	private final Type expectedType;

	public AbstractSentenceListener() {

		ParameterizedType superClass =
			(ParameterizedType) getClass().getGenericSuperclass();

		Type[] superClassTypeArgs = superClass.getActualTypeArguments();

		this.expectedType = superClassTypeArgs[0];
	}

	/**
	 * Empty implementation.
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
	 */
	public void readingPaused() {
	}

	/**
	 * Empty implementation.
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
	 */
	public void readingStarted() {
	}

	/**
	 * Empty implementation.
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
	 */
	public void readingStopped() {
	}

	/**
	 * Invoked when sentence of type <code>T</code> has been read and parsed.
	 *
	 * @param sentence Sentence of type <code>T</code>.
	 */
	public abstract void sentenceRead(T sentence);

	/**
	 * Resolves the type of each received sentence parser and passes it to
	 * <code>sentenceRead(T sentence)</code> if the type matches listener's
	 * expected type. This method maybe overridden too, but
	 * be sure to call <code>super.sentencerRead(SentenceEvent)</code> before or
	 * after your additional event handling.</p>
	 *
	 * @see net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi.nmea.event.SentenceEvent)
	 */
	@SuppressWarnings("unchecked")
	public void sentenceRead(SentenceEvent event) {
		Sentence sentence = event.getSentence();
		Class<?>[] interfaces = sentence.getClass().getInterfaces();
		if (Arrays.asList(interfaces).contains(this.expectedType)) {
			sentenceRead((T) sentence);
		}
	}

}

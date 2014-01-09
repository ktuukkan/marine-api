package net.sf.marineapi.nmea.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import net.sf.marineapi.nmea.sentence.Sentence;

/**
 * Abstract listener with automatic sentence type inference and casting.
 * 
 * @author Kimmo Tuukkanen
 * @param <T> Sentence interface to listen
 */
public abstract class AbstractSentenceListener<T extends Sentence> implements
	SentenceListener {

	private final Type expectedType;

	public AbstractSentenceListener() {

		ParameterizedType superClass = (ParameterizedType) getClass()
			.getGenericSuperclass();

		Type[] superClassTypeArgs = superClass.getActualTypeArguments();

		this.expectedType = superClassTypeArgs[0];
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
	 */
	public void readingPaused() {
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
	 */
	public void readingStarted() {
	}

	/*
	 * (non-Javadoc)
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

	/*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi
	 * .nmea.event.SentenceEvent) Resolves the type of received sentence parser
	 * and passes it to typed sentenceRead(T sentence) method if the type
	 * matches this.type.
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

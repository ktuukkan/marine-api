/* 
 * AbstractSentenceListener.java
 * Copyright (C) 2014 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 * 
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.event;

import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.util.GenericTypeResolver;

/**
 * <p>
 * Abstract base class for typed listeners with automatic sentence type
 * resolving and casting. Extend this class to create a listener/handler for a
 * single sentence type and register it in
 * {@link net.sf.marineapi.nmea.io.SentenceReader}.</p>
 * <p>
 * Recommended usage:
 * </p>
 * <pre>
 *     class MyListener extends AbstractSentenceListener&lt;GGASentence&gt;
 * </pre>
 * </p>
 * <p>
 * Notice that more advanced use of generics may require using the
 * {@link #AbstractSentenceListener(Class)} constructor. For example, the
 * following won't work with default constructor:
 * </p>
 * <pre>
 *     class MyListener&lt;A, B extends Sentence&gt; extends AbstractSentenceListener&lt;B&gt;
 * </pre>
 * </p>
 * Methods of the {@link SentenceListener} interface implemented by this class
 * are empty, except {@link #sentenceRead(SentenceEvent)} which is final and
 * detects the incoming sentences and casts them in correct interface before
 * calling the {@link #sentenceRead(Sentence)} method.
 *
 * @author Kimmo Tuukkanen
 * @param <T> Sentence interface to be listened.
 * @see net.sf.marineapi.nmea.event.SentenceListener
 * @see net.sf.marineapi.nmea.io.SentenceReader
 */
public abstract class AbstractSentenceListener<T extends Sentence>
    implements SentenceListener {

	protected final Class<?> expectedType;

    /**
     * Default constructor. Resolves the generic type <code>T</code> in order
     * to filter desired sentences.
     *
     * @throws IllegalStateException If the generic type cannot be resolved at runtime.
     * @see #AbstractSentenceListener(Class)
     */
    public AbstractSentenceListener() {
        expectedType = (Class<?>) GenericTypeResolver
                .resolve(getClass(), AbstractSentenceListener.class);
    }

    /**
     * Constructor with generic type parameter. This constructor may be used
     * when the default constructor fails to resolve the generic type
     * <code>T</code> at runtime. Failure may be due to more advanced usage of
     * generics or inheritance, for example if the generic type information is
     * lost at compile time because of type erasure.
     *
     * @param c Sentence interface <code>T</code> to be listened.
     */
    protected AbstractSentenceListener(Class<T> c) {
        this.expectedType = c;
    }

	/**
	 * <p>Checks the type of each received sentence and invokes the
	 * <@link {@link #sentenceRead(Sentence)} if it matches the generic type
     * <code>T</code>.</p>
	 * <p>
	 * This method has been made final to ensure the correct sentence filtering.
     * For listeners that need to handle all incoming sentences, it is
     * recommended to implement the
     * {@link net.sf.marineapi.nmea.event.SentenceListener} interface.</p>
	 *
	 * @see net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi.nmea.event.SentenceEvent)
	 */
	@SuppressWarnings("unchecked")
	public final void sentenceRead(SentenceEvent event) {
		Sentence sentence = event.getSentence();
		if (expectedType.isAssignableFrom(sentence.getClass())) {
			sentenceRead((T) sentence);
		}
	}

	/**
	 * Invoked when sentence of type <code>T</code> is received.
	 *
	 * @param sentence Sentence of type <code>T</code>
	 */
	public abstract void sentenceRead(T sentence);

    /**
     * Empty implementation.
     *
     * @see SentenceListener#readingPaused()
     */
    @Override
    public void readingPaused() {
    }

    /**
     * Empty implementation.
     *
     * @see SentenceListener#readingStarted()
     */
    @Override
    public void readingStarted() {
    }

    /**
     * Empty implementation.
     *
     * @see SentenceListener#readingStopped()
     */
    @Override
    public void readingStopped() {
    }

}

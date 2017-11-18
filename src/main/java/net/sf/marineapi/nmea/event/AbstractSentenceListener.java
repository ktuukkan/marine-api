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
 * Abstract listener for NMEA-0183 sentences. Extend this class to create a
 * listener for a specific sentence type and register it in a {@link
 * net.sf.marineapi.nmea.io.SentenceReader}. For listeners that need to handle
 * all incoming sentences, it is recommended to implement the {@link
 * SentenceListener} interface.
 * </p>
 * <p>
 * Recommended usage:
 * </p>
 * <pre>
 *   class MyListener extends AbstractSentenceListener&lt;GGASentence&gt;
 * </pre>
 * </p>
 * <p>
 * Notice that more advanced use of generics and inheritance may require using
 * the {@link #AbstractSentenceListener(Class)} constructor. For example, the 
 * following example won't work because of the generic types not being available
 * at runtime:
 * </p>
 * <pre>
 *   class MyListener&lt;A, B extends Sentence&gt; extends AbstractSentenceListener&lt;B&gt;
 *   ...
 *   MyListener&lt;String, GGASentence&gt; ml = new MyListener&lt;&gt;();
 * </pre>
 * </p>
 * Methods of the {@link SentenceListener} interface implemented by this class
 * are empty, except for {@link #sentenceRead(SentenceEvent)} which is final
 * and detects the incoming sentences and casts them in correct interface
 * before calling the {@link #sentenceRead(Sentence)} method. The other methods
 * may be overridden as needed.
 *
 * @author Kimmo Tuukkanen
 * @param <T> Sentence interface to be listened.
 * @see net.sf.marineapi.nmea.io.SentenceReader
 */
public abstract class AbstractSentenceListener<T extends Sentence>
    implements SentenceListener {

    protected final Class<?> sentenceType;

    /**
     * Default constructor with automatic generic type resolving. Notice that
     * the {@link GenericTypeResolver} may not always succeed.
     *
     * @see #AbstractSentenceListener(Class)
     * @throws IllegalStateException If the generic type cannot be resolved
     *                               at runtime.
     */
    public AbstractSentenceListener() {
        sentenceType = GenericTypeResolver.resolve(
                getClass(), AbstractSentenceListener.class);
    }

    /**
     * Constructor with explicit generic type parameter. This constructor may
     * be used when the default constructor fails to resolve the generic type
     * <code>T</code> at runtime.
     *
     * @param c Sentence type <code>T</code> to be listened.
     * @see #AbstractSentenceListener()
     */
    protected AbstractSentenceListener(Class<T> c) {
        this.sentenceType = c;
    }

    /**
     * <p>
     * Invoked for all received sentences. Checks the type of each sentence
     * and invokes the {@link #sentenceRead(Sentence)} if it matches the
     * listener's generic type <code>T</code>.
     * </p>
     * <p>
     * This method has been declared <code>final</code> to ensure the correct
     * filtering of sentences.
     * </p>
     *
     * @see SentenceListener#sentenceRead(SentenceEvent)
     */
    @SuppressWarnings("unchecked")
    public final void sentenceRead(SentenceEvent event) {
        Sentence sentence = event.getSentence();
        if (sentenceType.isAssignableFrom(sentence.getClass())) {
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

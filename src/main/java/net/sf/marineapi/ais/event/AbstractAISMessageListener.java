/*
 * AbstractAISMessageListener.java
 * Copyright (C) 2015 Kimmo Tuukkanen
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
package net.sf.marineapi.ais.event;

import net.sf.marineapi.ais.parser.AISMessageFactory;
import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.sentence.AISSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.util.GenericTypeResolver;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Abstract base listener for AIS messages. Extend this class to create listener
 * for a specific AIS message type. For listening all available AIS sentences,
 * extend <code>AbstractSentenceListener&lt;AISSentence&gt;</code> or implement
 * SentenceListener interface. However, in this case you should also implement
 * AIS message concatenation to combine and parse messages that are delivered
 * over multiple sentences.
 *
 * @author Kimmo Tuukkanen
 */
public abstract class AbstractAISMessageListener<T extends AISMessage>
    implements SentenceListener {

    private final Class<?> expectedMessageType;
    private final Queue<AISSentence> queue = new LinkedList<>();
    private final AISMessageFactory factory = AISMessageFactory.getInstance();

    /**
     * Default constructor.
     *
     * @throws IllegalStateException When the generic Sentence type <code>T</code> cannot be resolved at runtime.
     */
    public AbstractAISMessageListener() {
        this.expectedMessageType = (Class<?>) GenericTypeResolver
                .resolve(getClass(), AbstractAISMessageListener.class);
    }

    /**
     * Constructor with generic type parameter. This constructor may be used
     * when the default constructor fails to resolve the generic type
     * <code>T</code> at runtime. This may be due to more advanced usage of
     * generics or inheritance, for example when generic type information is
     * lost at compile time because of Java's type erasure.
     *
     * @param c Sentence interface <code>T</code> to be listened.
     */
    public AbstractAISMessageListener(Class<T> c) {
        this.expectedMessageType = c;
    }

    @Override
    public void sentenceRead(SentenceEvent event) {
        Sentence s = event.getSentence();
        if (s.isAISSentence()) {
            handleAIS((AISSentence) s);
        }
    }

    /**
     * Concatenate and pre-parse AIS sentences.
     */
    @SuppressWarnings("unchecked")
    private void handleAIS(AISSentence sentence) {

        if (sentence.isFirstFragment()) {
            queue.clear();
        }

        queue.add(sentence);

        if (sentence.isLastFragment()) {
            AISSentence[] sentences = queue.toArray(new AISSentence[queue.size()]);
            try {
                AISMessage message = factory.create(sentences);
                if (expectedMessageType.isAssignableFrom(message.getClass())) {
                    onMessage((T) message);
                }
            } catch (IllegalArgumentException iae) {
                // nevermind unsupported message types
            }
        }
    }

    /**
     * Invoked when AIS message has been received.
     * @param msg AISMessage of type <code>T</code>
     */
    public abstract void onMessage(T msg);

    /**
     * Empty implementation.
     * @see SentenceListener#readingPaused()
     */
    @Override
    public void readingPaused() {
    }

    /**
     * Empty implementation.
     * @see SentenceListener#readingStarted()
     */
    @Override
    public void readingStarted() {
    }

    /**
     * Empty implementation.
     * @see SentenceListener#readingStopped()
     */
    @Override
    public void readingStopped() {
    }
}

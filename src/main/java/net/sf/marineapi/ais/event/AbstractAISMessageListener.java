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
import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.sentence.AISSentence;
import net.sf.marineapi.util.GenericTypeResolver;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>
 * Abstract base listener for AIS messages. Extend this class to create listener
 * for a specific AIS message type. To listen all incoming AIS sentences, extend
 * the {@link AbstractSentenceListener} using {@link AISSentence} as type, or
 * implement the {@link SentenceListener} interface. However, in these cases you
 * also need to implement the message concatenation to parse messages being
 * delivered over multiple sentences.</p>
 * <p>
 * The implementation of this class is based on {@link AbstractSentenceListener}
 * and thus it has the same recommendations and limitations regarding the usage
 * of generics and inheritance.
 * </p>
 * 
 * @author Kimmo Tuukkanen
 * @param <T> AIS message type to be listened.
 * @see AbstractSentenceListener
 * @see GenericTypeResolver
 */
public abstract class AbstractAISMessageListener<T extends AISMessage>
    extends AbstractSentenceListener<AISSentence> {

    protected final Class<?> messageType;
    private final Queue<AISSentence> queue = new LinkedList<>();
    private final AISMessageFactory factory = AISMessageFactory.getInstance();

    /**
     * Default constructor with automatic generic type resolving. Notice that
     * the {@link GenericTypeResolver} may not always succeed.
     *
     * @see #AbstractAISMessageListener(Class)
     * @throws IllegalStateException If the generic type cannot be resolved
     *                               at runtime.
     */
    public AbstractAISMessageListener() {
        this.messageType = GenericTypeResolver.resolve(
                getClass(), AbstractAISMessageListener.class);
    }

    /**
     * Constructor with explicit generic type parameter. This constructor may
     * be used when the default constructor fails to resolve the generic type
     * <code>T</code> at runtime.
     *
     * @param c Message type <code>T</code> to be listened.
     * @see #AbstractAISMessageListener()
     */
    public AbstractAISMessageListener(Class<T> c) {
        this.messageType = c;
    }

    /**
     * <p>
     * Invoked when {@link AISSentence} of any type is received. Pre-parses
     * the message to determine it's type and invokes the
     * {@link #onMessage(AISMessage)} method when the type matches the generic
     * type <code>T</code>.</p>
     * <p>
     * This method has been declared <code>final</code> to ensure the correct
     * handling of received sentences.</p>
     */
    @Override
    @SuppressWarnings("unchecked")    
    public final void sentenceRead(AISSentence sentence) {

        if (sentence.isFirstFragment()) {
            queue.clear();
        }

        queue.add(sentence);

        if (sentence.isLastFragment()) {
            AISSentence[] sentences = queue.toArray(new AISSentence[queue.size()]);
            try {
                AISMessage message = factory.create(sentences);
                if (messageType.isAssignableFrom(message.getClass())) {
                    onMessage((T) message);
                }
            } catch (IllegalArgumentException iae) {
                // never mind incorrect order or unsupported message types
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

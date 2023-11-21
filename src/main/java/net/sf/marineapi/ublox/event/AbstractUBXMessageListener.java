/*
 * Copyright (C) 2020 Gunnar Hillert
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
package net.sf.marineapi.ublox.event;

import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.sentence.UBXSentence;
import net.sf.marineapi.ublox.message.UBXMessage;
import net.sf.marineapi.ublox.parser.UBXMessageFactory;
import net.sf.marineapi.util.GenericTypeResolver;

/**
 * <p>
 * Abstract listener for UBX messages. Extend this class to create a listener
 * for a specific UBX message type and register it in a
 * {@link net.sf.marineapi.nmea.io.SentenceReader}.</p>
 * <p>
 * To listen to all incoming UBX sentences, extend the {@link
 * AbstractSentenceListener} using {@link UBXSentence} as type.</p>
 * <p>
 * This class is based on {@link AbstractSentenceListener} and thus it has the
 * same recommendations and limitations regarding the usage of generics and
 * inheritance.
 * </p>
 *
 * @author Gunnar Hillert
 * @param <T> UBX message type to be listened.
 * @see AbstractSentenceListener
 * @see GenericTypeResolver
 */
public abstract class AbstractUBXMessageListener<T extends UBXMessage>
	extends AbstractSentenceListener<UBXSentence> {

	final Class<?> messageType;
	private final UBXMessageFactory factory = UBXMessageFactory.getInstance();

	/**
	 * Default constructor with automatic generic type resolving. Notice that
	 * the {@link GenericTypeResolver} may not always succeed.
	 *
	 * @see #AbstractUBXMessageListener(Class)
	 * @throws IllegalStateException If the generic type cannot be resolved
	 *                               at runtime.
	 */
	public AbstractUBXMessageListener() {
		this.messageType = GenericTypeResolver.resolve(
				getClass(), AbstractUBXMessageListener.class);
	}

	/**
	 * Constructor with explicit generic type parameter. This constructor may
	 * be used when the default constructor fails to resolve the generic type
	 * {@code T} at runtime.
	 *
	 * @param c Message type {@code T} to be listened.
	 * @see #AbstractUBXMessageListener()
	 */
	public AbstractUBXMessageListener(Class<T> c) {
		this.messageType = c;
	}

	/**
	 * <p>
	 * Invoked when {@link UBXSentence} of any type is received. Pre-parses
	 * the message to determine it's type and invokes the
	 * {@link #onMessage(UBXMessage)} method when the type matches the generic
	 * type {@code T}.</p>
	 * <p>
	 * This method has been declared {@code final} to ensure the correct
	 * handling of received sentences.</p>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final void sentenceRead(UBXSentence sentence) {

		try {
			UBXMessage message = factory.create(sentence);
			if (messageType.isAssignableFrom(message.getClass())) {
				onMessage((T) message);
			}
		} catch (IllegalArgumentException iae) {
			// never mind incorrect order or unsupported message types
		}
	}

	/**
	 * Invoked when a UBX message has been received.
	 * @param msg UBXMessage of type {@code T}
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

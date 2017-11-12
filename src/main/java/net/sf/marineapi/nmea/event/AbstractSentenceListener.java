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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sf.marineapi.nmea.sentence.Sentence;

/**
 * <p>
 * Abstract base class for typed listeners with automatic sentence type
 * resolving and casting. Extend this class to create a listener/handler for a
 * single sentence type and register it in
 * {@link net.sf.marineapi.nmea.io.SentenceReader}.</p>
 * <p>
 * Methods of {@link SentenceListener} interface implemented by this class are
 * empty, except {@link #sentenceRead(SentenceEvent)} which detects the incoming
 * sentence parsers and casts them to correct interface before calling
 * {@link #sentenceRead(Sentence)} method.
 *
 * @author Kimmo Tuukkanen
 * @param <T> Sentence interface to be listened.
 * @see net.sf.marineapi.nmea.io.SentenceReader
 */
public abstract class AbstractSentenceListener<T extends Sentence>
	implements SentenceListener {

	final Type expectedType;

	public AbstractSentenceListener() {
		// if during super class traversal we found a parameterized type we remember the
		// concrete parameter types that were used during the type instantiation keyed by
		// the type parameters
		final Map<TypeVariable<?>, Class<?>> concreteTypeForTypeVariable = new HashMap<>();
		ParameterizedType superClass = null;
		Class<?> c = getClass();
		while (superClass == null && c != null) {
			if (c.getGenericSuperclass() instanceof ParameterizedType) {
				final ParameterizedType pt = (ParameterizedType) c.getGenericSuperclass();
				if (pt.getRawType() == AbstractSentenceListener.class) {
					superClass = pt;
				} else {
					c = (Class<?>) pt.getRawType();
					int i=0;
					for (TypeVariable<?> tv : c.getTypeParameters()) {
						concreteTypeForTypeVariable.put(tv, resolve(pt.getActualTypeArguments()[i++], concreteTypeForTypeVariable));
					}
				}
			} else {
				c = (c.getGenericSuperclass() instanceof Class<?>) ? (Class<?>) c.getGenericSuperclass() : null;
			}
		}
		assert superClass == null || superClass.getRawType() == AbstractSentenceListener.class;
		// we now assume that this class has exactly one type parameter [0] that is the expected type:
		this.expectedType = resolve(superClass.getActualTypeArguments()[0], concreteTypeForTypeVariable);
	}
	
	protected AbstractSentenceListener(Class<T> expectedType) {
		this.expectedType = expectedType;
	}

	private Class<?> resolve(Type type, Map<TypeVariable<?>, Class<?>> concreteTypeForTypeVariable) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		} else if (type instanceof TypeVariable<?>) {
			return concreteTypeForTypeVariable.get((TypeVariable<?>) type);
		} else {
			return null;
		}
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
	 * <p>Resolves the type of each received sentence parser and passes it to
	 * <code>sentenceRead(T)</code> if the type matches the expected type
	 * <code>T</code>.</p>
	 *
	 * <p>This method may be overridden, but be sure to call
	 * <code>super.sentencerRead(SentenceEvent)</code> before or after your
	 * additional event handling. However, for listeners that need to handle all
	 * incoming sentences, it's recommended to directly implement the
	 * {@link net.sf.marineapi.nmea.event.SentenceListener} interface.</p>
	 *
	 * @see net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi.nmea.event.SentenceEvent)
	 */
	@SuppressWarnings("unchecked")
	public void sentenceRead(SentenceEvent event) {
		Sentence sentence = event.getSentence();
		if (this.expectedType instanceof Class<?>) {
		        if (((Class<?>) this.expectedType).isAssignableFrom(sentence.getClass())) {
		                sentenceRead((T) sentence);
		        }
		} else {
		    Class<?>[] interfaces = sentence.getClass().getInterfaces();
		    if (Arrays.asList(interfaces).contains(this.expectedType)) {
		    	sentenceRead((T) sentence);
		    }
		}
	}

}

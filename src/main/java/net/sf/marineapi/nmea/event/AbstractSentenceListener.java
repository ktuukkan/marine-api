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
import java.util.ArrayList;
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
 * Recommended usage:
 * </p>
 * <pre>
 *     class MyListener extends AbstractSentenceListener&lt;GGASentence>&gt;
 * </pre>
 * </p>
 * <p>
 * The following example requires using the {@link #AbstractSentenceListener(Class)}
 * constructor:
 * </p>
 * <pre>
 *     class MyListener&lt;A, B extends Sentence&gt; extends AbstractSentenceListener&lt;B>&gt;
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

	protected final Type expectedType;

    /**
     * Default constructor.
     *
     * @throws IllegalStateException When the generic Sentence type <code>T</code> cannot be resolved at runtime.
     * @see #AbstractSentenceListener(Class)
     */
    public AbstractSentenceListener() {
        this.expectedType = resolve(getClass(), new HashMap<>());
        if (expectedType == null || expectedType instanceof TypeVariable) {
            throw new IllegalStateException("Cannot resolve generic type <T>, use constructor with Class<T> param.");
        }
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
    protected AbstractSentenceListener(Class<T> c) {
        this.expectedType = c;
    }

    /**
     * Resolves the generic type T.
     */
    private Type resolve(Class c, Map<TypeVariable, Type> types) {

        Type superClass = c.getGenericSuperclass();

        if (superClass instanceof ParameterizedType) {

            ParameterizedType pt = (ParameterizedType) superClass;
            Class rawType = (Class) pt.getRawType();
            TypeVariable[] typeParams = rawType.getTypeParameters();
            Type[] typeArgs = pt.getActualTypeArguments();

            for (int i = 0; i < typeParams.length; i++) {
                if (typeArgs[i] instanceof TypeVariable) {
                    TypeVariable arg = (TypeVariable) typeArgs[i];
                    types.put(typeParams[i], types.getOrDefault(arg, arg));
                } else {
                    types.put(typeParams[i], typeArgs[i]);
                }
            }

            if (rawType == AbstractSentenceListener.class) {
                return types.getOrDefault(typeParams[0], typeParams[0]);
            } else {
                return resolve(rawType, types);
            }
        }

        return resolve((Class) superClass, types);
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
	 * <p>Resolves the type of each received sentence and passes it to
	 * <@link {@link #sentenceRead(Sentence)} if it matches the expected type
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
	public final void sentenceRead(SentenceEvent event) {
		Sentence sentence = event.getSentence();
		if (((Class<?>) expectedType).isAssignableFrom(sentence.getClass())) {
		    sentenceRead((T) sentence);
        }
	}

}

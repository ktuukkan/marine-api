/*
 * GenericTypeResolver.java
 * Copyright (C) 2017 Kimmo Tuukkanen
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
package net.sf.marineapi.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for resolving the generic type of a class, mainly for
 * {@link net.sf.marineapi.nmea.event.AbstractSentenceListener} and
 * {@link net.sf.marineapi.ais.event.AbstractAISMessageListener} classes where
 * the generic type needs to be resolved at runtime to filter the incoming
 * messages.
 *
 * @author Kimmo Tuukkanen, Axel Uhl
 */
public final class GenericTypeResolver {

    private GenericTypeResolver() { }

    /**
     * Attempts to resolve the generic type of given class, with the assumption
     * of single generic type parameter. However, the resolving may not always
     * succeed, often due to more advanced or mixed usage of generics and
     * inheritance. For example, the generic type information may be lost
     * at compile-time because of the Java's <a href="https://docs.oracle.com/javase/tutorial/java/generics/nonReifiableVarargsType.html" target="_blank">
     * type erasure.</a>
     *
     * @param child The class of which parents and generic types to inspect
     * @param parent The generic class that holds the type being resolved
     * @return The generic type of <code>parent</code>
     * @throws IllegalStateException If the generic type cannot be resolved
     *                               at runtime.
     */
    public static Class<?> resolve(Class<?> child, Class<?> parent) {
        Type t = resolve(child, parent, new HashMap<>());
        if (t == null || t instanceof TypeVariable) {
            throw new IllegalStateException("Cannot resolve generic type <T>, use constructor with Class<T> param.");
        }
        return (Class<?>) t;
    }

    /**
     * Resolves the generic type of class.
     *
     * @param child The class of calling listener implementation.
     * @param parent The parent class that holds the generic type.
     * @param types Variables and types memo
     */
    private static Type resolve(Class<?> child, final Class<?> parent,
                                final Map<TypeVariable<?>, Type> types) {

        Type superClass = child.getGenericSuperclass();

        if (superClass instanceof ParameterizedType) {

            ParameterizedType pt = (ParameterizedType) superClass;
            Class<?> rawType = (Class<?>) pt.getRawType();
            TypeVariable<?>[] typeParams = rawType.getTypeParameters();
            Type[] typeArgs = pt.getActualTypeArguments();

            for (int i = 0; i < typeParams.length; i++) {
                if (typeArgs[i] instanceof TypeVariable) {
                    TypeVariable<?> arg = (TypeVariable<?>) typeArgs[i];
                    types.put(typeParams[i], types.getOrDefault(arg, arg));
                } else {
                    types.put(typeParams[i], typeArgs[i]);
                }
            }

            if (rawType == parent) {
                return types.getOrDefault(typeParams[0], typeParams[0]);
            } else {
                return resolve(rawType, parent, types);
            }
        }

        return resolve((Class<?>) superClass, parent, types);
    }
}

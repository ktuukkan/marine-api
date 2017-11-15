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
 * {@link net.sf.marineapi.ais.event.AbstractAISMessageListener} where we need
 * to know the generic type at runtime to filter the desired sentences.
 *
 * @author Kimmo Tuukkanen, Axl Uhl
 */
public final class GenericTypeResolver {

    /**
     * Resolves the generic type, assuming there is only one.
     *
     * @param c The class of which super-classes and generic types to inspect
     * @param target Target class holding the generic type to be resolved
     * @return The resolved generic Type (Class or TypeVariable)
     * @throws IllegalStateException If the generic type cannot be resolved
     */
    public static Type resolve(Class<?> c, Class<?> target) {
        Type t = resolve(c, target, new HashMap<>());
        if (t == null || t instanceof TypeVariable) {
            throw new IllegalStateException("Cannot resolve generic type <T>, use constructor with Class<T> param.");
        }
        return t;
    }

    /**
     * Resolves the generic type of class.
     *
     * @param c The class of calling listener implementation.
     * @param types Variables and types memo
     */
    private static Type resolve(Class<?> c, final Class<?> target, final Map<TypeVariable, Type> types) {

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

            if (rawType == target) {
                return types.getOrDefault(typeParams[0], typeParams[0]);
            } else {
                return resolve(rawType, target, types);
            }
        }

        return resolve((Class) superClass, target, types);
    }
}

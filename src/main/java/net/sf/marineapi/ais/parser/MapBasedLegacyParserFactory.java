/*
 * MapBasedLegacyParserFactory.java
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
package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.ais.util.Sixbit;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of AISMessageParserFactory extracted from AISMessageFactory,
 * that uses reflection to create AISMessages based on MessageType.
 */
class MapBasedLegacyParserFactory implements AISMessageParserFactory {

    private Map<Integer, Class<? extends AISMessage>> parsers;

    MapBasedLegacyParserFactory() {
        parsers = new HashMap<>(10);
        parsers.put(1, AISMessage01Parser.class);
        parsers.put(2, AISMessage02Parser.class);
        parsers.put(3, AISMessage03Parser.class);
        parsers.put(4, AISMessage04Parser.class);
        parsers.put(5, AISMessage05Parser.class);
        parsers.put(9, AISMessage09Parser.class);
        parsers.put(18, AISMessage18Parser.class);
        parsers.put(19, AISMessage19Parser.class);
        parsers.put(21, AISMessage21Parser.class);
        parsers.put(24, AISMessage24Parser.class);
    }

    @Override
    public boolean canCreate(AISMessageParser parser) {
        return parsers.containsKey(parser.getMessageType());
    }

    @Override
    public AISMessage create(AISMessageParser parser) {
        try {
            Class<? extends AISMessage> c = parsers.get(parser.getMessageType());
            Constructor<? extends AISMessage> co = c.getConstructor(Sixbit.class);
            return co.newInstance(parser.getSixbit());
        } catch (Exception e) {
            throw new IllegalStateException(e.getCause());
        }
    }
}

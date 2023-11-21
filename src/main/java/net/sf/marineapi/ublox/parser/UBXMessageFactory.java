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
package net.sf.marineapi.ublox.parser;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import net.sf.marineapi.nmea.sentence.UBXSentence;
import net.sf.marineapi.ublox.message.UBXMessage;

/**
 * Factory for creating UBX message parsers. Currently the following parsers are supported:
 *
 * <ul>
 *   <li>{@link UBXMessage00Parser}
 *   <li>{@link UBXMessage03Parser}
 * </ul>
 *
 * @author Gunnar Hillert
 *
 */
public class UBXMessageFactory {

	private static UBXMessageFactory instance;
	private Map<Integer, Class<? extends UBXMessage>> parsers;

	/**
	 * Hidden constructor.
	 */
	private UBXMessageFactory() {
		parsers = new HashMap<Integer, Class<? extends UBXMessage>>(2);
		parsers.put(0, UBXMessage00Parser.class);
		parsers.put(3, UBXMessage03Parser.class);
	}

	/**
	 * Creates a new UBX message parser based on given {@link UBXSentence}.
	 *
	 * @param sentence The UBX sentence to pass to the UBXMessageParser
	 * @throws IllegalArgumentException If given message type is not supported.
	 * @throws IllegalStateException If message parser cannot be constructed
	 *          due to illegal state, e.g. invalid or empty message.
	 * @return UBXMessage instance
	 */
	public UBXMessage create(UBXSentence sentence) {

		final UBXMessageParser parser = new UBXMessageParser(sentence);

		if (!parsers.containsKey(parser.getMessageType())) {
			String msg = String.format("no parser for message type %d", parser.getMessageType());
			throw new IllegalArgumentException(msg);
		}

		UBXMessage result;
		Class<? extends UBXMessage> c = parsers.get(parser.getMessageType());
		try {
			Constructor<? extends UBXMessage> co = c.getConstructor(UBXSentence.class);
			result = co.newInstance(sentence);
		} catch (Exception e) {
			throw new IllegalStateException(e.getCause());
		}

		return result;
	}

	/**
	 * Returns the factory singleton.
	 *
	 * @return UBXMessageFactory
	 */
	public static UBXMessageFactory getInstance() {
		if(instance == null) {
			instance = new UBXMessageFactory();
		}
		return instance;
	}

}

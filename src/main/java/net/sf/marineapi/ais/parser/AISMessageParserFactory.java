/*
 * AISMessageParserFactory.java
 * Copyright (C) 2018 Paweł Kozioł
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

/**
 * Internal interface used by {@link AISMessageFactory}
 * to find and create concrete AIS message parsers.
 *
 * @author Paweł Kozioł
 */
interface AISMessageParserFactory {

	/**
	 * Invoked before {@link #create(AISMessageParser)} to determine if factory
	 * is able to create parser from partially-parsed message.
	 *
	 * @param parser Partially-parsed message
	 * @return {@code true} when factory can create parser,
	 *         {@code false} otherwise.
	 */
	boolean canCreate(AISMessageParser parser);

	/**
	 * Creates new message parser from given partially-parsed message.
	 * Have to be called only if {@link #canCreate(AISMessageParser)} returned {@code true}.
	 *
	 * @param parser Partially-parsed message
	 * @throws IllegalStateException If message parser cannot be constructed
	 *          due to illegal state, e.g. invalid or empty message.
	 * @return Instance of AISMessageParser
	 */
	AISMessage create(AISMessageParser parser);
}

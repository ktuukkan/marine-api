/* 
 * AISMessageFactory.java
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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.nmea.sentence.AISSentence;

/**
 * Factory for creating AIS message parsers.
 * 
 * @author Kimmo Tuukkanen
 */
public class AISMessageFactory {

	private static AISMessageFactory instance;
	private Map<Integer, Class<? extends AISMessage>> parsers;
	
	/**
	 * Hidden constructor.
	 */
	private AISMessageFactory() {
		parsers = new HashMap<Integer, Class<? extends AISMessage>>(7);
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
	
	
	/**
	 * Creates a new AIS message parser based on given sentences.
	 *  
	 * @param sentences One or more AIS sentences in correct sequence order.
	 * @return AISMessage instance
	 */
	public AISMessage create(AISSentence... sentences) {

		AISMessageParser parser = new AISMessageParser();
		for (AISSentence v : sentences) {
			parser.append(v.getPayload(), v.getFragmentNumber(), v.getFillBits());
		}

		if (!parsers.containsKey(parser.getMessageType())) {
			String msg = String.format("no parser for message type %d", parser.getMessageType());
			throw new IllegalArgumentException(msg);
		}

		AISMessage result;
		Class<? extends AISMessage> clazz = parsers.get(parser.getMessageType());
		try {
			Constructor<? extends AISMessage> c = clazz.getConstructor(Sixbit.class);
			result = c.newInstance(parser.getMessageBody());
		} catch (Exception e) {
			throw new IllegalStateException(e.getCause());
		}

		return result;
	}
	
	/**
	 * Returns the factory singleton.
	 * 
	 * @return AISMessageFactory
	 */
	public static AISMessageFactory getInstance() {
		if(instance == null) {
			instance = new AISMessageFactory();
		}
		return instance;
	}
}

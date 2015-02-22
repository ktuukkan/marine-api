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

import net.sf.marineapi.ais.sentence.AISMessage;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.nmea.sentence.VDMSentence;

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
		parsers = new HashMap<Integer, Class<? extends AISMessage>>();
		parsers.put(1, AISMessage01Parser.class);
		// TODO
	}
	
	
	/**
	 * Creates a new AIS message parser based on given sentences.
	 *  
	 * @param vdm
	 * @return
	 */
	public AISMessage create(VDMSentence... vdm) {
		
		AISMessageParser p = new AISMessageParser();
		
		for(VDMSentence v : vdm) {
			p.append(v.getPayload(), v.getFragmentNumber(), v.getFillBits());
		}
		
		Class<? extends AISMessage> clazz = parsers.get(p.getMessageType());
		
		try {
			Constructor<? extends AISMessage> c = clazz.getConstructor(Sixbit.class);
			return c.newInstance(p.getMessageBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
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

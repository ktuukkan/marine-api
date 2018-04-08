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

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.nmea.sentence.AISSentence;

/**
 * Factory for creating AIS message parsers.
 * 
 * @author Kimmo Tuukkanen
 */
public class AISMessageFactory {

    private static AISMessageFactory instance;
    private List<AISMessageParserFactory> parserFactories;

    /**
     * Hidden constructor.
     */
    private AISMessageFactory() {
        parserFactories = new ArrayList<>();
        parserFactories.add(new MapBasedLegacyParserFactory());
        parserFactories.add(new AISMessage08DAC200FID10Parser.Factory());
        parserFactories.add(new AISMessage08Parser.Factory());
    }


    /**
     * Creates a new AIS message parser based on given sentences.
     *
     * @param sentences One or more AIS sentences in correct sequence order.
     * @throws IllegalArgumentException If given message type is not supported
     *          or sequence order is incorrect.
     * @throws IllegalStateException If message parser cannot be constructed
     *          due to illegal state, e.g. invalid or empty message.
     * @return AISMessage instance
     */
    public AISMessage create(AISSentence... sentences) {

        AISMessageParser parser = new AISMessageParser(sentences);

        for (AISMessageParserFactory parserFactory : parserFactories) {
            if (parserFactory.canCreate(parser)) {
                return parserFactory.create(parser);
            }
        }

        String msg = String.format("no parser for message type %d", parser.getMessageType());
        throw new IllegalArgumentException(msg);
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

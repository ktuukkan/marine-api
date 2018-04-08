/*
 * AISMessage08Parser.java
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

import net.sf.marineapi.ais.message.AISMessage08;
import net.sf.marineapi.ais.util.BitVector;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * AIS Message 8 implementation: Binary Broadcast Message.
 *
 * Length of data depends on message sub-type (DAC and FID).
 * Maximum length is 952 bits.
 *
 * <pre>
 * Field  Name                                      Bits    (from, to  )
 * -------------------------------------------------------------------------
 *  1     messageID                                    6    (   1,    6)
 *  2     repeatIndicator                              2    (   7,    8)
 *  3     userID                                      30    (   9,   38)
 *  4     spare                                        2    (  39,   40)
 *  5     designatedAreaCode                          10    (  41,   50)
 *  6     functionalID                                 6    (  51,   56)
 *  7     data                                       952    (  57, 1008)
 *                                                  ---- +
 *                                              sum 1008
 * </pre>
 *
 * @author Paweł Kozioł
 */
class AISMessage08Parser extends AISMessageParser implements AISMessage08 {

	private final static String	SEPARATOR				= "\n\t";
	private final static int	DAC		                = 0;
	private final static int	FID				        = 1;
	private final static int	DATA		            = 2;
	private final static int[]	FROM					= {40, 50, 56};
	private final static int[]	TO   					= {50, 56};

	public static class Factory implements AISMessageParserFactory {

		@Override
		public boolean canCreate(AISMessageParser parser) {
			return parser.getMessageType() == 8;
		}

		@Override
		public AISMessage08Parser create(AISMessageParser parser) {
			return new AISMessage08Parser(parser.getSixbit());
		}

		static int getDAC(Sixbit content) {
			return content.getInt(FROM[DAC], TO[DAC]);
		}

		static int getFID(Sixbit content) {
			return content.getInt(FROM[FID], TO[FID]);
		}
	}

	private int dac;
	private int fid;
	private BitVector data;

	public AISMessage08Parser(Sixbit content) {
		super(content);

		dac = Factory.getDAC(content);
		fid = Factory.getFID(content);
		data = content.get(FROM[DATA], content.length());
	}

	@Override
	public int getDAC() {
		return dac;
	}

	@Override
	public int getFID() {
		return fid;
	}

	@Override
	public BitVector getData() {
		return data;
	}

	public String toString() {
		String result =     "\tDAC:             " + getDAC();
		result += SEPARATOR + "FID:             " + getFID();
		result += SEPARATOR + "Data:            " + getData();
		return result;
	}
}

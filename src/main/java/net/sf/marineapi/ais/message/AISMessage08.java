/*
 * AISMessage08.java
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
package net.sf.marineapi.ais.message;

import net.sf.marineapi.ais.util.BitVector;

/**
 * Binary Broadcast Message.
 *
 * @author Paweł Kozioł
 */
public interface AISMessage08 extends AISMessage {

	/**
	 * Returns Designated Area Code used with FID to determine message subtype.
	 *
	 * @return Designated Area Code
	 */
	int getDAC();

	/**
	 * Returns Functional ID used with DAC to determine message subtype.
	 *
	 * @return Functional ID
	 */
	int getFID();

	/**
	 * Returns message binary data.
	 * Useful for debugging when there are no specific implementations
	 * for given combination of DAC and FID.
	 *
	 * @return Binary data with maximum 952 bits.
	 */
	BitVector getData();
}

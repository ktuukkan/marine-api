/*
 * BitVector.java
 * Copyright (C) 2015 Lázár József
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
package net.sf.marineapi.ais.util;

import java.util.BitSet;

/**
 * Class holding bit values in an array and implementing put/get
 * integer/string operations on it.
 *
 * @author Lázár József
 */
public class BitVector {

	private BitSet		fBitVector;

	/**
	 * Creates a new instance with given length.
	 *
	 * @param bits Vector length
	 */
	public BitVector(int bits) {
		fBitVector = new BitSet(bits);
	}

	/**
	 * Creates a new instance with given BitSet and length.
	 *
	 * @param vector BitSet
	 */
	public BitVector(BitSet vector) {
		fBitVector = vector;
	}

	/**
	 * Set bit at given index.
	 *
	 * @param index Index of bit to set.
	 */
	public void set(int index) {
		fBitVector.set(index);
	}

	/**
	 * Gets a vector subset.
	 *
	 * @param from Start index
	 * @param to End index
	 * @return BitVector with specified range
	 */
	public BitVector get(int from, int to) {
		to++;
		from++;
		return new BitVector(fBitVector.get(from, to));
	}

	/**
	 * Return bit as boolean from the bit vector
	 *
	 * @param index start index of bit
	 * @return {@code true} if bit is set, otherwise {@code false}.
	 */
	public boolean getBoolean(int index) {
		return fBitVector.get(index);
	}

	/**
	 * Returns the requested bits interpreted as an integer (MSB first) from the message.
	 *
	 * @param from begin index (inclusive)
	 * @param to end index (inclusive)
	 * @return unsigned int value
	 */
	public int getUInt(int from, int to) {
		int value = 0;
		for (int i = fBitVector.previousSetBit(to); i > from; i = fBitVector.previousSetBit(i - 1)) {
			value += (1 << (to - i));
		}
		return value;
	}

	/**
	 * Convert to 8-bit integer.
	 *
	 * @param from Start index
	 * @param to End index
	 * @return Integer value
	 */
	public int getAs8BitInt(int from, int to) {
		int retval = getUInt(from, to);
		if (retval >= 0x80)
	        retval -= 0x100;
		return retval;
	}

	/**
	 * Convert to 17-bit integer.
	 *
	 * @param from Start index
	 * @param to End index
	 * @return Integer value
	 */
	public int getAs17BitInt(int from, int to) {
		int retval = getUInt(from, to);
		if (retval >= 0x10000)
	        retval -= 0x20000;
		return retval;
	}

	/**
	 * Convert to 18-bit integer.
	 *
	 * @param from Start index
	 * @param to End index
	 * @return Integer value
	 */
	public int getAs18BitInt(int from, int to) {
		int retval = getUInt(from, to);
		if (retval >= 0x20000)
	        retval -= 0x40000;
		return retval;
	}

	/**
	 * Convert to 27-bit integer.
	 *
	 * @param from Start index
	 * @param to End index
	 * @return Integer value
	 */
	public int getAs27BitInt(int from, int to) {
		int retval = getUInt(from, to);
		if (retval >= 0x4000000)
	        retval -= 0x8000000;
		return retval;
	}

	/**
	 * Convert to 28-bit integer.
	 *
	 * @param from Start index
	 * @param to End index
	 * @return Integer value
	 */
	public int getAs28BitInt(int from, int to) {
		int retval = getUInt(from, to);
		if (retval >= 0x8000000)
	        retval -= 0x10000000;
		return retval;
	}
}

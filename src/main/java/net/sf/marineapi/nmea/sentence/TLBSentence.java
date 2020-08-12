/* 
 * TLBSentence.java
 * Copyright (C) 2020 Joshua Sweaney
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
package net.sf.marineapi.nmea.sentence;

/**
 * Target label data in pairs of target IDs and target labels.
 * Example:<br>
 * {@code $RATLB,3,SHIPTHREE,5,SHIPFIVE,99,SHIP99*1F}
 * 
 * @author Joshua Sweaney
 */
public interface TLBSentence extends Sentence {

    /**
	 * Get the list of target IDs listed in this sentence.
     * 
     * The nth array element returned by this method, corresponds
     * to the nth array element returned by {@link TLBSentence#getTargetLabels()}.
	 * 
	 * @return targets IDs as int array
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
    int[] getTargetIds();

    /**
	 * Get the list of target labels listed in this sentence.
     * 
     * The nth array element returned by this method, corresponds
     * to the nth array element returned by {@link TLBSentence#getTargetIds()}.
	 * 
	 * @return target labels as String array
	 * @throws net.sf.marineapi.nmea.parser.DataNotAvailableException If the data is
	 *             not available.
	 * @throws net.sf.marineapi.nmea.parser.ParseException If the field contains
	 *             unexpected or illegal value.
	 */
    String[] getTargetLabels();

    /**
     * Adds a target (id,label) pair to this sentence.
     * @param targetId int The target ID of the pair
     * @param targetLabel String The label of the pair
     */
    void addTargetLabel(int targetId, String targetLabel);

    /**
     * Sets the target (id,label) pairs in this sentence. The size of each
     * array must be identical. The nth position in each array corresponds
     * to the nth position in the other array.
     * @param targetIds int array of target IDs
     * @throws java.lang.IllegalArgumentException If array sizes are not the same
     */
    void setTargetPairs(int[] targetIds, String[] targetLabels);
    
}
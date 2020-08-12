/* 
 * TLBParser.java
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
package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TLBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * TLB sentence parser
 * 
 * @author Joshua Sweaney
 */
class TLBParser extends SentenceParser implements TLBSentence {

    // field indices
    private static final int FIRST_PAIR = 0;

    /**
	 * Creates a new instance of TLB parser
	 * 
	 * @param nmea TLB sentence string.
	 */
	public TLBParser(String nmea) {
        super(nmea, SentenceId.TLB);
        
        if ((getFieldCount() % 2) != 0) {
            throw new IllegalArgumentException("Invalid TLB sentence. Must contain pairs of target numbers and labels.");
        }
    }
    
    /**
	 * Creates TLB parser with empty sentence. The created TLB sentence contains
	 * no target id,label pairs.
	 * 
	 * @param talker TalkerId to set
	 */
	public TLBParser(TalkerId talker) {
		super(talker, SentenceId.TLB, 0);
	}
    
    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence#getTargetIds()
     */
    public int[] getTargetIds() {
        int[] ids = new int[(int) (getFieldCount() / 2)];

        for (int i = 0, j = 0; j<ids.length; i+=2, j++) {
            ids[j] = getIntValue(i);
        }

        return ids;
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence#getTargetLabels()
     */
    public String[] getTargetLabels() {
        String[] labels = new String[(int) (getFieldCount() / 2)];

        for (int i = 1, j = 0; j<labels.length; i+=2, j++) {
            try {
                labels[j] = getStringValue(i);
            } catch (DataNotAvailableException ex) {
                labels[j] = "";
            }            
        }

        return labels;
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence#addTargetLabel(int, java.lang.String)
     */
    public void addTargetLabel(int targetId, String targetLabel) {
        int[] ids = getTargetIds();
        String[] labels = getTargetLabels();

        String[] newFields = new String[(ids.length+1)*2];

        // Since the ID part of each (ID,label) pair comes first, we will consider that
        // to be authoratative about the number of pairs that should exist.
        // If the labels array is shorter, empty strings are used. If longer, only
        // pairs up to ids.length are added.
        for (int i = 0, j = 0; i<ids.length; i++, j+=2) {
            newFields[j] = String.valueOf(ids[i]);
            if (i < labels.length) {
                newFields[j+1] = labels[i];
            } else {
                newFields[j+1] = "";
            }
        }

        // Finally, add the new id,label pair
        newFields[newFields.length-2] = String.valueOf(targetId);
        newFields[newFields.length-1] = targetLabel;

        setStringValues(FIRST_PAIR, newFields);
    }

    /**
     * @see net.sf.marineapi.nmea.sentence.TLBSentence#setTargetPairs(int, java.lang.String)
     */
    public void setTargetPairs(int[] ids, String[] labels) {
        if (ids.length != labels.length) {
            throw new IllegalArgumentException("The ID and Label arrays must be the same length.");
        }

        String[] newFields = new String[ids.length*2];
        for (int i = 0, j = 0; i<ids.length; i++, j+=2) {
            newFields[j] = String.valueOf(ids[i]);
            newFields[j+1] = labels[i];
        }
        
        setStringValues(FIRST_PAIR, newFields);
    }
}
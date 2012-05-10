/* 
 * DBTSentence.java
 * Copyright (C) 2011 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://sourceforge.net/projects/marineapi/>
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
 * Water depth below transducer, in meters, feet and fathoms.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface DBTSentence extends DepthSentence {

    /**
     * Get depth in fathoms.
     * 
     * @return Depth value
     */
    double getFathoms();

    /**
     * Get depth in feet.
     * 
     * @return Depth value
     */
    double getFeet();

    /**
     * Set depth value, in fathoms.
     * 
     * @param depth Depth to set
     */
    void setFathoms(double depth);

    /**
     * Set depth value, in feet.
     * 
     * @param depth Depth to set
     */
    void setFeet(double depth);

}

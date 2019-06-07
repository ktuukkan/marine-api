/* 
 * DTASentence.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import java.util.Date;

/**
 * DTA Sentence interface.
 * 
 * <p>Boreal GasFinder data </p>
 * 
 * <p>Example GasFinder2:<br><code>$GFDTA,  7.7, 98, 600, 5527, 2011/01/27 13:29:28, HFH2O-1xxx, 1, *56 </code></p>
 * <p>Example GasFinderMC:<br><code>$GFDTA,  1, 1.5, 99, 600, 11067,  2002/03/01  00:30:28,  HF-1xxx, 1, *2F  </code></p>
 *
 * @author Bob Schwarz
 * @see <a href="https://github.com/LoadBalanced/marine-api">marina-api fork</a>
 * 
 */
public interface DTASentence extends Sentence {

	int getChannelNumber();
	
	double getGasConcentration();
	
	int getConfidenceFactorR2();
	
	double getDistance();
	
	int getLightLevel();
	
	Date getDateTime();
	
	String getSerialNumber();
	
	int getStatusCode();
	
}

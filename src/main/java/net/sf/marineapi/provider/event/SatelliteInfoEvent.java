/* 
 * SatelliteInfoEvent.java
 * Copyright (C) 2012 Kimmo Tuukkanen
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
package net.sf.marineapi.provider.event;

import java.util.List;

import net.sf.marineapi.nmea.sentence.GSASentence;
import net.sf.marineapi.nmea.util.SatelliteInfo;

/**
 * SatelliteInfoEvent contains the satellite information collected by
 * {@link net.sf.marineapi.provider.GSVProvider}.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public class SatelliteInfoEvent extends ProviderEvent {

	private static final long serialVersionUID = -5243047395130051907L;

	private GSASentence gsa;
	private List<SatelliteInfo> info;

	/**
	 * @param source
	 */
	public SatelliteInfoEvent(Object source, GSASentence gsa, List<SatelliteInfo> info) {
		super(source);
		this.gsa = gsa;
		this.info = info;
	}

	/**
	 * Returns the satellite information.
	 * 
	 * @return List of SatelliteInfo objects.
	 */
	public List<SatelliteInfo> getSatelliteInfo() {
		return this.info;
	}

	/**
	 * @return
	 */
	public double getHDOP() {
		return gsa.getHorizontalDOP();
	}
}

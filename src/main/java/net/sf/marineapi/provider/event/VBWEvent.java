/*
 * VBWEvent.java
 * Copyright (C) 2013-2014 ESRG LLC.
 * 
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
 * along with NMEA Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.provider.event;

import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;

public class VBWEvent extends ProviderEvent implements Cloneable {

    private static final long serialVersionUID = 1L;
    private double longWS;
    private double longGS;
    private double travWS;
    private double travGS;

    /**
     * Creates a new instance of TPVEvent.
     *
     * @param source Source object of event
     */
    public VBWEvent(Object source, double lw, double lg, double tw, double tg) {
        super(source);
        longWS = lw;
        longGS = lg;
        travWS = tw;
        travGS = tg;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public VBWEvent clone() {
        return new VBWEvent(getSource(), getLongWS(), getLongGS(), getTravWS(), getTravGS());
    }

    /**
     * @return the longWS
     */
    public double getLongWS() {
        return longWS;
    }

    /**
     * @param longWS the longWS to set
     */
    public void setLongWS(double longWS) {
        this.longWS = longWS;
    }

    /**
     * @return the longGS
     */
    public double getLongGS() {
        return longGS;
    }

    /**
     * @param longGS the longGS to set
     */
    public void setLongGS(double longGS) {
        this.longGS = longGS;
    }

    /**
     * @return the travWS
     */
    public double getTravWS() {
        return travWS;
    }

    /**
     * @param travWS the travWS to set
     */
    public void setTravWS(double travWS) {
        this.travWS = travWS;
    }

    /**
     * @return the travGS
     */
    public double getTravGS() {
        return travGS;
    }

    /**
     * @param travGS the travGS to set
     */
    public void setTravGS(double travGS) {
        this.travGS = travGS;
    }
}

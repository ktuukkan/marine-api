/*
 * DPTEvent.java
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.marineapi.provider.event;

/**
 *
 * @author jwilson
 *//**
 *
 * @author jwilson
 */
public class DPTEvent extends ProviderEvent implements Cloneable {
    private static final long serialVersionUID = 1L;

    private double deptMeters;
    private double offset;
    private double maximum;

    public DPTEvent(Object source, double dm, double o, double m) {
        super(source);
        deptMeters = dm;
        offset = o;
        maximum = m;
    }

    @Override
    public DPTEvent clone() {
        return new DPTEvent(getSource(), getDeptMeters(), getOffset(),getMaximum());
    }

    /**
     * @return the deptMeters
     */
    public double getDeptMeters() {
        return deptMeters;
    }

    /**
     * @param deptMeters the deptMeters to set
     */
    public void setDeptMeters(double deptMeters) {
        this.deptMeters = deptMeters;
    }

    /**
     * @return the offset
     */
    public double getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(double offset) {
        this.offset = offset;
    }

    /**
     * @return the maximum
     */
    public double getMaximum() {
        return maximum;
    }

    /**
     * @param maximum the maximum to set
     */
    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }






}

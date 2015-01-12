/*
 * DBTEvent.java
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
public class DBTEvent extends ProviderEvent implements Cloneable {
    private static final long serialVersionUID = 1L;

    private double deptFeet;
    private double deptMeters;
    private double deptFathoms;

    public DBTEvent(Object source, double df, double dm, double dfa) {
        super(source);
        deptFeet = df;
        deptMeters = dm;
        deptFathoms = dfa;


    }

    @Override
    public DBTEvent clone() {
        return new DBTEvent(getSource(), getDeptFeet(),getDeptMeters(),getDeptFathoms());
    }

    /**
     * @return the deptFeet
     */
    public double getDeptFeet() {
        return deptFeet;
    }

    /**
     * @param deptFeet the deptFeet to set
     */
    public void setDeptFeet(double deptFeet) {
        this.deptFeet = deptFeet;
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
     * @return the deptFathoms
     */
    public double getDeptFathoms() {
        return deptFathoms;
    }

    /**
     * @param deptFathoms the deptFathoms to set
     */
    public void setDeptFathoms(double deptFathoms) {
        this.deptFathoms = deptFathoms;
    }
}

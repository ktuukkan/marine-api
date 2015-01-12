/*
 * VBWSentence.java
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
package net.sf.marineapi.nmea.sentence;
 
import net.sf.marineapi.nmea.util.DataStatus;

public interface VBWSentence extends Sentence {

    double getLongWaterSpeed();

    double getLongGroundSpeed();

    double getTravWaterSpeed();

    double getTravGroundSpeed();
    
    DataStatus getWaterSpeedStatus();
    
    DataStatus getGroundSpeedStatus();
    
    double getSternWaterSpeed();
    
    DataStatus getSternWaterSpeedStatus();
    
    double getSternGroundSpeed();
    
    DataStatus getSternGroundSpeedStatus();
     
    void setLongWaterSpeed(double speed);

    void setLongGroundSpeed(double speed);

    void setTravWaterSpeed(double speed);

    void setTravGroundSpeed(double speed);
    
    void setWaterSpeedStatus(DataStatus status);
    
    void setGroundSpeedStatus(DataStatus status);
    
    void setSternWaterSpeed(double speed);
    
    void setSternWaterSpeedStatus(DataStatus status);
    
    void setSternGroundSpeed(double speed);
    
    void setSternGroundSpeedStatus(DataStatus status);

}

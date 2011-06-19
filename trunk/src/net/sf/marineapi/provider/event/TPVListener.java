/* 
 * TPVListener.java
 * Copyright (C) 2010-2011 Kimmo Tuukkanen
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

import java.util.EventListener;

import net.sf.marineapi.provider.TPVProvider;

/**
 * Listener interface for time/position/velocity report from GPS.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see TPVProvider
 * @see TPVEvent
 */
public interface TPVListener extends EventListener {

    /**
     * @param evt
     */
    void tpvUpdated(TPVEvent evt);
}

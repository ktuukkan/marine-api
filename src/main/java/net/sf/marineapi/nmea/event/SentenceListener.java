/* 
 * SentenceListener.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.event;

import java.util.EventListener;

/**
 * Base interface for listening to SentenceEvents.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 * @see net.sf.marineapi.nmea.io.SentenceReader
 * @see SentenceEvent
 */
public interface SentenceListener extends EventListener {

	/**
	 * Called when NMEA data flow has paused for some reason, e.g. when reached
	 * the end of file or device has stopped providing data, but the reader is
	 * still running and waiting for more data. Timeout for pause event to occur
	 * is 5 seconds.
	 */
	void readingPaused();

	/**
	 * Called when NMEA data is found in stream and reader starts dispatching
	 * SentenceEvents. Also, this notification occurs when events dispatching
	 * continues after <code>readingPaused()</code> has occurred.
	 */
	void readingStarted();

	/**
	 * Invoked after <code>SentenceReader</code> has stopped reading the input
	 * stream, either due to error or explicit request by calling
	 * {@link net.sf.marineapi.nmea.io.SentenceReader#stop()}.
	 */
	void readingStopped();

	/**
	 * Invoked when valid NMEA 0183 data has been read by SentenceReader.
	 * 
	 * @param event SentenceEvent containing the data.
	 */
	void sentenceRead(SentenceEvent event);

}

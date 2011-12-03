/* 
 * AbstractProvider.java
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
package net.sf.marineapi.provider;

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.PositionSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.provider.event.ProviderEvent;
import net.sf.marineapi.provider.event.ProviderListener;

/**
 * Abstract base class for providers.
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public abstract class AbstractProvider<T extends ProviderEvent> implements
        SentenceListener {

    protected SentenceReader reader;
    protected List<SentenceEvent> events = new ArrayList<SentenceEvent>();
    private List<ProviderListener<T>> listeners = new ArrayList<ProviderListener<T>>();

    /**
     * Creates a new instance of AbstractProvider.
     * 
     * @param reader Sentence reader to be used as data source
     * @param ids Types of sentences to capture for creating provider events
     */
    public AbstractProvider(SentenceReader reader, SentenceId... ids) {
        this.reader = reader;
        for (SentenceId id : ids) {
            reader.addSentenceListener(this, id);
        }
    }

    /**
     * Inserts a listener to provider.
     * 
     * @param listener Listener to add
     */
    public void addListener(ProviderListener<T> listener) {
        listeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
     */
    public void readingPaused() {
        // nothing
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
     */
    public void readingStarted() {
        reset();
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
     */
    public void readingStopped() {
        reset();
        reader.removeSentenceListener(this);
    }

    /**
     * Removes the specified listener from provider.
     * 
     * @param listener Listener to remove
     */
    public void removeListener(ProviderListener<T> listener) {
        listeners.remove(listener);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(net.sf.marineapi
     * .nmea.event.SentenceEvent)
     */
    public void sentenceRead(SentenceEvent event) {

        Sentence s = event.getSentence();

        if (s instanceof PositionSentence) {

            events.add(event);

            if (isReady()) {
                if (isValid()) {
                    fireProviderEvent(createEvent());
                }
                reset();
            }
        }
    }

    /**
     * Dispatch the TPV event to all listeners.
     * 
     * @param event TPVUpdateEvent to dispatch
     */
    private void fireProviderEvent(T event) {
        for (ProviderListener<T> listener : listeners) {
            listener.providerUpdate(event);
        }
    }

    /**
     * Clears the list of collected events.
     */
    private void reset() {
        events.clear();
    }

    /**
     * Creates a ProviderEvent of type T.
     * 
     * @return Created event, or null if failed.
     */
    abstract T createEvent();

    /**
     * Tells if provider has captured sufficient sentence events for creating a
     * new ProviderEvent.
     * 
     * @return true if ready to create ProviderEvent, otherwise false.
     */
    abstract boolean isReady();

    /**
     * Tells if the captured sentence events contain valid data to be dispatched
     * to ProviderListeners.
     * 
     * @return true if valid, otherwise false.
     */
    abstract boolean isValid();
}

/*
 * HeadingProvider.java
 * Copyright (C) 2012 Kimmo Tuukkanen
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
package net.sf.marineapi.provider;

import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.HeadingSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.provider.event.HeadingEvent;

/**
 * Heading provider reports the vessel's current heading. Data is captured from
 * HDT, HDM or HDG sentences.
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.provider.event.HeadingEvent
 * @see net.sf.marineapi.provider.event.HeadingListener
 */
public class HeadingProvider extends AbstractProvider<HeadingEvent> {

	/**
	 * Creates a new intance of HeadingProvider.
	 *
	 * @param reader Reader for capturing heading sentences.
	 */
	public HeadingProvider(SentenceReader reader) {
		super(reader, SentenceId.HDT, SentenceId.HDM, SentenceId.HDG);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#createEvent()
	 */
	@Override
	protected HeadingEvent createProviderEvent() {
		for (Sentence s : getSentences()) {
			if (s instanceof HeadingSentence) {
				return new HeadingEvent(this, (HeadingSentence) s);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isReady()
	 */
	@Override
	protected boolean isReady() {
		return hasOne("HDT", "HDM", "HDG");
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isValid()
	 */
	@Override
	protected boolean isValid() {
		return true;
	}

}

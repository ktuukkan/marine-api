/*
 * VBWProvider.java
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
package net.sf.marineapi.provider;

import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.VBWSentence;
import net.sf.marineapi.provider.event.VBWEvent;

public class VBWProvider extends AbstractProvider<VBWEvent> {


    /**
     * Creates a new instance of TPVProvider.
     *
     * @param reader SentenceReader that provides the required sentences.
     */
    public VBWProvider(SentenceReader reader) {
        super(reader, SentenceId.VBW);
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.provider.AbstractProvider#createProviderEvent()
     */
    @Override
    protected VBWEvent createProviderEvent() {
        
        double longWS = 0;
        double longGS = 0;
        double travWS = 0;
        double travGS = 0;

        for (Sentence s : getSentences()) {
            if (s instanceof VBWSentence) {
                VBWSentence v = (VBWSentence) s;
                longWS = v.getLongWaterSpeed();
                longGS = v.getLongGroundSpeed();
                travWS = v.getTravWaterSpeed();
                travGS = v.getTravGroundSpeed();
            }
        }
        return new VBWEvent(this,longWS,longGS,travWS,travGS );
    }

    /*
     * (non-Javadoc)
     * @see net.sf.marineapi.provider.AbstractProvider#isReady()
     */
    @Override
    protected boolean isReady() {
        return hasOne("VBW");
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

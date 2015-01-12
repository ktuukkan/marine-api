/*
 * DPTProvider.java
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
import net.sf.marineapi.nmea.sentence.DPTSentence;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.provider.event.DPTEvent;

/**
 *
 * @author jwilson
 */
public class DPTProvider extends AbstractProvider<DPTEvent> {

    public DPTProvider(SentenceReader reader) {
        super(reader, SentenceId.DPT);
    }

    @Override
    protected DPTEvent createProviderEvent() {

        double dm = 0;
        double o = 0;
        double m = -1;

        for (Sentence s : getSentences()) {
            if (s instanceof DPTSentence) {
                DPTSentence v = (DPTSentence) s;
                dm = v.getDepth();
                o = v.getOffset();
                m = v.getMaximum();
            }
        }
        return new DPTEvent(this, dm, o, m);
    }

    @Override
    protected boolean isReady() {
        return hasOne("DPT");
    }

    @Override
    protected boolean isValid() {

        return true;
    }
}

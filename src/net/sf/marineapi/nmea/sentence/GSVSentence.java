package net.sf.marineapi.nmea.sentence;

import java.util.List;

import net.sf.marineapi.nmea.util.SatelliteInfo;

/**
 * Interface for GSV sentence type. Detailed satellite data; satellites in view,
 * satellite elevation, azimuth and signal noise ratio (SNR). GSV sentences are
 * transmitted typically in groups of two or three sentences, depending on the
 * number of satellites in view. Each GSV sentence may contain information about
 * up to four satellites. The last sentence in sequence may contain empty
 * satellite information fields. The empty fields may also be omitted, depending
 * on the device model and manufacturer.
 * <p>
 * Example: <br>
 * <code>$GPGSV,3,2,12,15,56,182,51,17,38,163,47,18,63,058,50,21,53,329,47*73</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface GSVSentence extends Sentence {

    /**
     * Get the number of sentences in GSV sequence.
     * 
     * @return Number of sentences
     */
    int getSentenceCount();

    /**
     * Get the index of this sentence in GSV sequence.
     * 
     * @return Sentence index
     */
    int getSentenceIndex();

    /**
     * Tells if this is the first sentence in GSV sequence.
     * 
     * @return true if first, otherwise false.
     * @see #getSentenceCount()
     * @see #getSentenceIndex()
     */
    boolean isFirst();

    /**
     * Tells if this is the last sentence in GSV sequence.
     * 
     * @return true if first, otherwise false.
     * @see #getSentenceCount()
     * @see #getSentenceIndex()
     */
    boolean isLast();

    /**
     * Get the number of satellites in view.
     * 
     * @return Satellite count
     */
    int getSatelliteCount();

    /**
     * Get the satellite information of the sentence.
     * 
     * @return List of SatelliteInfo objects.
     */
    List<SatelliteInfo> getSatelliteInfo();

}

package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.nmea.util.DataStatus;

/**
 * Interface for GLL sentence type. Geographic position (latitude/longitude).
 * <p>
 * Example: <br>
 * <code>$GPGLL,6011.552,N,02501.941,E,120045,A*26</code>
 * 
 * @author Kimmo Tuukkanen
 * @version $Revision$
 */
public interface GLLSentence extends PositionSentence, TimeSentence {

    /**
     * Data status; char indicator for "valid".
     */
    public final static char STATUS_VALID = 'A';
    /**
     * Data status; char indicator for "invalid".
     */
    public final static char STATUS_INVALID = 'V';

    /**
     * Get the data quality status, valid or invalid.
     * 
     * @return DataStatus.VALID or DataStatus.INVALID
     */
    DataStatus getDataStatus();

}

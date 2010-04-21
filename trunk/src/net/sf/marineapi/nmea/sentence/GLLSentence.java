package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.nmea.parser.DataNotAvailableException;
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
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    DataStatus getDataStatus();

    /**
     * Set the data quality status, valid or invalid.
     * 
     * @param status DataStatus to set
     * @throws DataNotAvailableException If the data is not available.
     * @throws ParseException If the field contains unexpected or illegal value.
     */
    void setDataStatus(DataStatus status);

}

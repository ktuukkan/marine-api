package net.sf.marineapi.nmea.parser;

/**
 * Thrown when an unsupported sentence is encountered.
 */
public class UnsupportedSentenceException extends RuntimeException {

    private static final long serialVersionUID = 7618916517933110942L;

    /**
     * Constructor
     *
     * @param msg Exception message
     */
    public UnsupportedSentenceException(String msg) {
        super(msg);
    }
}

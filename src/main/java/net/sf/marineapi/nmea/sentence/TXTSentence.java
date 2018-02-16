package net.sf.marineapi.nmea.sentence;

/**
 * <p>
 * Text message sentence. Transmits various information on the device, such as
 * power-up screen, software version etc.</p>
 * <p>
 * Example:<br/>
 * <code>$GPTXT,01,01,TARG1,Message*35</code>
 * </p>
 */
public interface TXTSentence extends Sentence {

    /**
     * Get total number of sentences in message sequence.
     *
     * @return Number of transmission messages.
     */
    int getMessageCount();

    /**
     * Set total number of sentences in message sequence.
     *
     * @param count Number of transmission messages.
     * @throws IllegalArgumentException If given count is zero or negative
     */
    void setMessageCount(int count);

    /**
     * Returns the sentence index in message sequence.
     *
     * @return Message number of this sentence.
     * @see #getMessageIndex()
     */
    int getMessageIndex();

    /**
     * Sets the sentence index in message sequence.
     *
     * @param index Message index to set
     * @throws IllegalArgumentException If given index is negative
     */
    void setMessageIndex(int index);

    /**
     * Returns the message identifier. This field may be used for various
     * purposes depending on the device. Originally a numeric field but may
     * also contain String values. For example, should match target name in
     * <code>TLL</code> or waypoint name in <code>WPL</code>.
     *
     * @return Message identifier
     */
    String getIdentifier();

    /**
     * Sets the message identifier.
     *
     * @param id Identifier to be set
     * @see #getIdentifier()
     */
    void setIdentifier(String id);

    /**
     * Returns the message content.
     *
     * @return ASCII text content
     */
    String getMessage();

    /**
     * Sets the message content.
     *
     * @param msg ASCII text content to set.
     */
    void setMessage(String msg);

}

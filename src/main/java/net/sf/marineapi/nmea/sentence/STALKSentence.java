package net.sf.marineapi.nmea.sentence;

/**
 * <p>
 * Raymarine/Autohelm SeaTalk sentence. See
 * <a href="http://www.thomasknauf.de/seatalk.htm">SeaTalk Technical Reference</a>
 * by Thomas Knauf for more information on the protocol.
 * </p>
 *
 * <p>Example:<br/><code>$STALK,52,A1,00,00*36</code></p>
 */
public interface STALKSentence extends Sentence {

    /**
     * Add given parameter in sentence. Sentence field count is increased by one
     * field, notice that adding too many fields may result in invalid sentence.
     *
     * @param param Hex parameter to add
     */
    void addParameter(String param);

    /**
     * Returns the SeaTalk command / datagram type.
     *
     * @return Command String, "00", "01", "02" etc.
     */
    String getCommand();

    /**
     * Sets the SeaTalk command / datagram type.
     *
     * @param cmd Command String, "00", "01", "02" etc.
     */
    void setCommand(String cmd);

    /**
     * Returns the datagram payload.
     *
     * @return Parameters array containing hex values.
     */
    String[] getParameters();

    /**
     * Sets the datagram payload.
     *
     * @param params Parameters array containing hex Strings.
     */
    void setParameters(String... params);
}
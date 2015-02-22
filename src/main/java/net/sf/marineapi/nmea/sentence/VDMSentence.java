/**
 * 
 */
package net.sf.marineapi.nmea.sentence;

/**
 * AIS/VDM sentence. This is the NMEA layer of AIS that carries the actual
 * payload message as 6-bit encoded String.
 * 
 * @author Lázár József
 */
public interface VDMSentence extends Sentence {

	/**
	 * Count of fragments in the currently accumulating message.
	 * 
	 * @return number of fragments.
	 */
	public int getNumberOfFragments();

	/**
	 * Returns the fragment number of this sentence (1 based). 
	 * 
	 * @return fragment index
	 */
	public int getFragmentNumber();

	/**
	 * Returns the sequential message ID for multi-sentence messages.
	 * 
	 * @return sequential message ID
	 */
	public String getMessageId();

	/**
	 * Returns the radio channel information of the messsage.
	 * 
	 * @return radio channel id
	 */
	public String getRadioChannel();

	/**
	 * Returns the raw 6-bit decoded message.
	 * 
	 * @return message body
	 */
	public String getPayload();

	/**
	 * Returns the number of fill bits required to pad the data payload to a
	 * 6 bit boundary, ranging from 0 to 5.
	 * 
	 * Equivalently, subtracting 5 from this tells how many least significant
	 * bits of the last 6-bit nibble in the data payload should be ignored.
	 * 
	 * @return number of fill bits
	 */
	public int getFillBits();

	/**
	 * Tells if the AIS message is being delivered over multiple sentences.
	 * 
	 * @return true if this sentence is part of a sequence
	 */
	public boolean isFragmented();

	/**
	 * Tells if this is the first fragment in message sequence.
	 * 
	 * @return true if first fragment in sequence
	 */
	public boolean isFirstFragment();

	/**
	 * Tells if this is the last fragment in message sequence.
	 * 
	 * @return true if last part of a sequence
	 */
	public boolean isLastFragment();

	/**
	 * <p>Returns whether given sentence is part of message sequence.</p>
	 * 
	 * <p>Sentences are considered to belong in same sequence when:</p>
	 * <ul>
	 *   <li>Same number of fragments, higher fragment #, same channel and same
	 *       messageId</li>
	 *   <li>Same number of fragments, next fragment #, and either same channel
	 *       or same messageId</li>
	 * </ul>
	 * 
	 * @param line VDMSentence to compare with.
	 * @return true if this and given sentence belong in same sequence
	 */
	public boolean isPartOfMessage(VDMSentence line);

}

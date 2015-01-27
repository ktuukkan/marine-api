/**
 * 
 */
package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.ais.util.Sixbit;


/**
 * AIS sentence. It contains only the common part of the message.
 * 
 * @author Lázár József
 */
public interface VDMSentence extends Sentence {

	/**
	 * Count of fragments in the currently accumulating message.
	 * @return number of fragments.
	 */
	public int getNumberOfFragments();

	/**
	 * Returns the fragment number of this sentence (1 based). 
	 * @return fragment index
	 */
	public int getFragmentNumber();

	/**
	 * Returns the sequential message ID for multi-sentence messages.
	 * @return sequential message ID
	 */
	public String getMessageID();

	/**
	 * Returns the radio channel information of the messsage
	 * @return radio channel id
	 */
	public String getRadioChannel();

	/**
	 * The AIS messaeg itself.
	 * @return message body
	 */
	public String getPayload();

	/**
	 * Returns the number of fill bits requires to pad the data payload to a
	 * 6 bit boundary, ranging from 0 to 5.
	 * 
	 * Equivalently, subtracting 5 from this tells how many least significant
	 * bits of the last 6-bit nibble in the data payload should be ignored.
	 * @return fill bits
	 */
	public int getFillbits();

	/**
	 * 
	 * @return true if this line is the last part of a sequence of fragments.
	 */
	public boolean isLastFragment();

	/**
	 * 
	 * @return true if line is part if this sequence of fragments.
	 */
	public boolean isPartOfMessage(VDMSentence line);

	/**
	 * Adds the payload of line into the current message.
	 */
	public void add(VDMSentence line);

	/* ------------- Common AIS part -----------------------------------*/
	/**
	 * Returns the message type.
	 * Users of this interface should query first the message type and then
	 * instantiate the corresponding message class.
	 * For example, if the message type is 5 then the <code>AISMessage05</code>
	 * class should be created with the message body. 
	 * @return message types in the range from 1 to 27.
	 */
	public int getMessageType() throws Exception;

	/**
	 * Returns the repeat indicator which tells how many times this message
	 * has been repeated. 
	 * @return the integer repeat indicator
	 */
	public int getRepeatIndicator() throws Exception;
	
	/**
	 * Returns the  unique identifier (MMSI number) of the transmitting ship.
	 * @return the MMSI as an integer.
	 */
	public int getMMSI() throws Exception;

	/**
	 * Returns the message body as a sixbit encoded array.
	 * @return the message as a sixbit encoded array.
	 */
	public Sixbit getMessageBody() throws Exception;
}

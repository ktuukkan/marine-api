package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VDMSentence;

/**
 * AIS VDM sentence parser, contains only the NMEA layer. The actual payload
 * message is parsed by AIS message parsers.
 * 
 * @author Lázár József
 */
public class VDMParser extends SentenceParser implements VDMSentence {

	// NMEA message fields
	private static final int NUMBER_OF_FRAGMENTS	= 0;
	private static final int FRAGMENT_NUMBER		= 1;
	private static final int MESSAGE_ID				= 2;
	private static final int RADIO_CHANNEL			= 3;
	private static final int PAYLOAD				= 4;
	private static final int FILL_BITS				= 5;
 
	/**
	 * Creates a new instance of VDMParser.
	 * 
	 * @param nmea NMEA sentence String.
	 */
	public VDMParser(String nmea) {
		super(nmea, SentenceId.VDM);
	}

	/**
	 * Creates a new empty VDMParser.
	 * 
	 * @param talker TalkerId to set
	 */
	public VDMParser(TalkerId talker) {
		super('!', talker, "VDM", 6);
	}
	
	@Override
	public int getNumberOfFragments() {
		return getIntValue(NUMBER_OF_FRAGMENTS);
	}

	@Override
	public int getFragmentNumber() {
		return getIntValue(FRAGMENT_NUMBER);
	}

	@Override
	public String getMessageId() {
		return getStringValue(MESSAGE_ID);
	}

	@Override
	public String getRadioChannel() {
		return getStringValue(RADIO_CHANNEL);
	}

	@Override
	public String getPayload() {
		return getStringValue(PAYLOAD);
	}

	@Override
	public int getFillBits() {
		return getIntValue(FILL_BITS);
	}

    @Override
    public boolean isFragmented() {
        return getNumberOfFragments() > 1;
    }

    @Override
    public boolean isFirstFragment() {
        return getFragmentNumber() == 1;
    }

    @Override
	public boolean isLastFragment() {
		return getNumberOfFragments() == getFragmentNumber();
	}

    @Override
	public boolean isPartOfMessage(VDMSentence line) {
		if (getNumberOfFragments() == line.getNumberOfFragments() &&
		        getFragmentNumber() < line.getFragmentNumber()) {
			
		    if (getFragmentNumber() + 1 == line.getFragmentNumber()) {
				return (getRadioChannel().equals(line.getRadioChannel()) || 
						getMessageId().equals(line.getMessageId()));
			} else {
				return (getRadioChannel().equals(line.getRadioChannel()) &&
						getMessageId().equals(line.getMessageId()));
			}
		} else {
			return false;
		}
	}
}

package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VDMSentence;

/**
 * AIS sentence parser. For method comments please read the interface
 * definition file.
 * 
 * @author Lázár József
 */
public class VDMParser extends SentenceParser implements VDMSentence {

	// NMEA message part
	private static final int NUMBER_OF_FRAGMENTS	= 0;
	private static final int FRAGMENT_NUMBER		= 1;
	private static final int MESSAGE_ID				= 2;
	private static final int RADIO_CHANNEL			= 3;
	private static final int PAYLOAD				= 4;
	private static final int FILLBITS				= 5;

	private int				fFragmentNr;
	private String			fPayload;
	private int				fFillbits;
	private int				fLastFragmentNr;

	// Common AIS message part
	private static int		MESSAGE_TYPE		= 0;
	private static int		REPEAT_INDICATOR	= 1;
	private static int		MMSI				= 2;
	private static int[]	FROM				= {0, 6, 8};
	private static int[]	TO   				= {6, 8, 38};

	private Sixbit			fMessage;
	private int				fMessageType;
	private int				fRepeatIndicator;
	private int				fMMSI;

	/**
	 * Creates a new instance of VDMParser.
	 * 
	 * @param nmea NMEA sentence String.
	 */
	public VDMParser(String nmea) {
		super(nmea, SentenceId.VDM);
		preparse();
	}

	/**
	 * Creates a new empty VDMParser.
	 * 
	 * @param talker TalkerId to set
	 */
	public VDMParser(TalkerId talker) {
		super(talker, "VDM", 6);
	}

	private void preparse() {
		fFragmentNr = getIntValue(FRAGMENT_NUMBER);
		fLastFragmentNr = fFragmentNr;
		fPayload = getStringValue(PAYLOAD);
		fFillbits = getIntValue(FILLBITS);
	}

	@Override
	public int getNumberOfFragments() {
		return getIntValue(NUMBER_OF_FRAGMENTS);
	}

	@Override
	public int getFragmentNumber() {
		return fFragmentNr;
	}

	@Override
	public String getMessageID() {
		return getStringValue(MESSAGE_ID);
	}

	@Override
	public String getRadioChannel() {
		return getStringValue(RADIO_CHANNEL);
	}

	@Override
	public String getPayload() {
		return fPayload;
	}

	@Override
	public int getFillbits() {
		return fFillbits;
	}

	/**
	 * @return true if the last fragment has already been received
	 */
	public boolean isLastFragment() {
		return getNumberOfFragments() == fLastFragmentNr;
	}

	/**
	 * Returns whether line is part of this VDM message.
	 * They are considred the same when:
	 *  - Same number of fragments, higher fragment#, same channel and same messageId
	 *  - Same number of fragments, next fragment#, and either same channel or same messageId
	 * @param line
	 * @return boolean indicating whether line is part of VDM message
	 */
	public boolean isPartOfMessage(VDMSentence line) {
		if (getNumberOfFragments() == line.getNumberOfFragments() &&
				getFragmentNumber() < line.getFragmentNumber()) {
			if (getFragmentNumber() + 1 == line.getFragmentNumber()) {
				return (getRadioChannel().equals(line.getRadioChannel()) || 
						getMessageID().equals(line.getMessageID()));
			}
			else
				return (getRadioChannel().equals(line.getRadioChannel()) &&
						getMessageID().equals(line.getMessageID()));
		}
		else
			return false;
	}

	/**
	 * Add a fragment to this VDM Message.
	 * @param line
	 */
	public void add(VDMSentence line) {
		fLastFragmentNr = line.getFragmentNumber();
		fPayload += line.getPayload();
		fFillbits = line.getFillbits();	// we always use the last
	}

	private void parseAIS() throws Exception {
		if (fMessage == null) {
			fMessage = new Sixbit(fPayload, fFillbits);
			fMessageType = fMessage.getInt(FROM[MESSAGE_TYPE], TO[MESSAGE_TYPE]);
			fRepeatIndicator = fMessage.getInt(FROM[REPEAT_INDICATOR], TO[REPEAT_INDICATOR]);
			fMMSI = fMessage.getInt(FROM[MMSI], TO[MMSI]);
		}
	}

	public int getMessageType() throws Exception {
		parseAIS();
		return fMessageType;
	}

	public int getRepeatIndicator() throws Exception {
		parseAIS();
		return fRepeatIndicator;
	}

	public int getMMSI() throws Exception {
		parseAIS();
		return fMMSI;
	}

	public Sixbit getMessageBody() throws Exception {
		parseAIS();
		return fMessage;
	}
}

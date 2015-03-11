package net.sf.marineapi.ais.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.ais.util.Sixbit;
import net.sf.marineapi.ais.util.Violation;

/**
 * Base class for all AIS messages.
 * 
 * @author Lázár József, Kimmo Tuukkanen
 */
public class AISMessageParser implements AISMessage {

	// TODO sub-classes are not passing message/Sixbit up here
	private String message = "";
	private int fillbits;
	private int lastFragmentNr;

	private Sixbit decoder;
	private int messageType;
	private int repeatIndicator;
	private int mmsi;

	// Common AIS message part
	private static int MESSAGE_TYPE = 0;
	private static int REPEAT_INDICATOR = 1;
	private static int MMSI = 2;
	private static int[] FROM = { 0, 6, 8 };
	private static int[] TO = { 6, 8, 38 };

	protected List<Violation> fViolations = new ArrayList<Violation>();

	/**
	 * Add a new rule violation to this message
	 */
	public void addViolation(Violation v) {
		fViolations.add(v);
	}

	public int getNrOfViolations() {
		return fViolations.size();
	}

	public List<Violation> getViolations() {
		return fViolations;
	}

	public int getMessageType() {
		parseAIS();
		return messageType;
	}

	public int getRepeatIndicator() {
		parseAIS();
		return repeatIndicator;
	}

	public int getMMSI() {
		parseAIS();
		return mmsi;
	}

	public Sixbit getMessageBody() {
		parseAIS();
		return decoder;
	}

	@Override
	public void append(String fragment, int fragmentIndex, int fillBits) {
		lastFragmentNr = fragmentIndex;
		message += fragment;
		fillbits = fillBits; // we always use the last
	}

	// TODO lazy parsing
	private void parseAIS() {
		if (decoder == null) {
			decoder = new Sixbit(message, fillbits);
			messageType = decoder.getInt(FROM[MESSAGE_TYPE], TO[MESSAGE_TYPE]);
			repeatIndicator = decoder.getInt(FROM[REPEAT_INDICATOR], TO[REPEAT_INDICATOR]);
			mmsi = decoder.getInt(FROM[MMSI], TO[MMSI]);
		}
	}
}

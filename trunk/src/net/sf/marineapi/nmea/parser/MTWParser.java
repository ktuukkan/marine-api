package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.MTWSentence;

public class MTWParser extends SentenceParser implements MTWSentence {

	private static final int TEMPERATURE = 0;

	public MTWParser(String nmea) {
		super(nmea);
	}

	@Override
	public double getTemperature() {
		return getDoubleValue(TEMPERATURE);
	}

}

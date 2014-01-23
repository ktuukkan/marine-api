package net.sf.marineapi.example;

import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.MWVSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Units;

/**
 * Demonstrates the usage of sentence parsers for data output.
 * 
 * @author Kimmo Tuukkanen
 */
public class OutputExample {

	public static void main(String[] args) {
		
		// Create a fresh MWV parser
		SentenceFactory sf = SentenceFactory.getInstance();
		MWVSentence mwv = (MWVSentence) sf.createParser(TalkerId.II, "MWV");
		
		// should output "$IIMWV,,,,,V*36" 
		System.out.println(mwv.toSentence());
		
		// Be sure to set all needed values correctly. For instance, in this
		// example setAngle() and setTrue() have mutual dependency. Likewise,
		// pay attention to set units correctly.
		mwv.setAngle(43.7);
		mwv.setTrue(true);
		mwv.setSpeed(4.54);
		mwv.setSpeedUnit(Units.METER);
		mwv.setStatus(DataStatus.ACTIVE);
		
		// should output "$IIMWV,043.7,T,4.5,M,A*39"
		System.out.println(mwv.toSentence());
	}

}

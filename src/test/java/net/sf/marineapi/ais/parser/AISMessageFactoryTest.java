package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage;
import net.sf.marineapi.nmea.parser.SentenceFactory;
import net.sf.marineapi.nmea.sentence.AISSentence;

public class AISMessageFactoryTest {

    // Sentences
    private String s1 = "";
    private String s2_1 = "";
    private String s2_2 = "";

    // NMEA parsers
    SentenceFactory sf = SentenceFactory.getInstance();
    private final AISSentence single = (AISSentence) sf.createParser(s1);
    private final AISSentence multi1 = (AISSentence) sf.createParser(s2_1);
    private final AISSentence multi2 = (AISSentence) sf.createParser(s2_2);

    // AIS parsers
    AISMessageFactory amf = AISMessageFactory.getInstance();
    private AISMessage singleMsg = amf.create(single);
    private AISMessage multiMsg = amf.create(multi1, multi2);

}

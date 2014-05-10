package net.sf.marineapi.nmea.sentence;

import net.sf.marineapi.nmea.sentence.SentenceValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * © Pilotfish Networks AB
 *
 * @author Johan Riisberg-Jensen
 *         Date:   2014-05-09
 */
public class SentenceValidatorTest {

    @Test
    public void testSentenceWithChecksumValidation() {

        String nmea="$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n";
        assertTrue(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*20";
        assertTrue(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n\r\n";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*20\n\r";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*20\r";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*20\n";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*20xy";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*201";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*2";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,*";
        assertFalse(SentenceValidator.isSentence(nmea));

    }

    @Test
    public void testSentenceWithoutChecksumValidation() {

        String nmea="$GPRMC,142312.000,V,,,,,,,080514,,\r\n";
        assertTrue(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,";
        assertTrue(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,\r\n\r\n";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,\n\r";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,\r";
        assertFalse(SentenceValidator.isSentence(nmea));

        nmea="$GPRMC,142312.000,V,,,,,,,080514,,\n";
        assertFalse(SentenceValidator.isSentence(nmea));

    }

}

/* 
 * DTBTest.java
 * Copyright (C) 2019 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 * 
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import net.sf.marineapi.nmea.sentence.DTBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

/**
 * DTBTest - test class for Boreal GasFinder Channel B
 * 
 * @author Bob Schwarz
 * @see <a href="https://github.com/LoadBalanced/marine-api">marine-api fork</a>
 */
public class DTBTest {
	
	// Boreal GasFinderMC has an additional channel number
    public static final String EXAMPLE_MC = "$GFDTB,1,1.5,99,600,11067,2002/03/01 00:30:28,HF-1xxx,1*3F";	

    public static final String EXAMPLE2 = "$GFDTB,7.7,98,600,5527,2011/01/27 13:29:28,HFH2O-1xxx,1*28";

    private DTBSentence gasFinderMC;
    private DTBSentence gasFinder2;

    @Before
    public void setUp() throws Exception {
        gasFinderMC = new DTBParser(EXAMPLE_MC);
        gasFinder2 = new DTBParser(EXAMPLE2);
    }

    @Test
    public void testDTBParserTalkerId() {
        DTBParser mwdp = new DTBParser(TalkerId.GF);
        assertEquals(TalkerId.GF, mwdp.getTalkerId());
        assertEquals("DTB", mwdp.getSentenceId());
    }

    @Test
    public void testGetChannelNumber() {
        assertEquals(2, gasFinderMC.getChannelNumber());
        assertEquals(2, gasFinder2.getChannelNumber());
    }
  
    @Test
    public void testGetGasConcentration() {
        assertEquals(1.5, gasFinderMC.getGasConcentration(),0.1);
        assertEquals(7.7, gasFinder2.getGasConcentration(),0.1);
    }
    
    @Test
    public void testGetConfidenceFactorR2() {
        assertEquals(99, gasFinderMC.getConfidenceFactorR2());
        assertEquals(98, gasFinder2.getConfidenceFactorR2());
    }
    
    @Test
    public void testGetDistance() {
        assertEquals(600, gasFinderMC.getDistance(),0.1);
        assertEquals(600, gasFinder2.getDistance(),0.1);
    }
    @Test
    public void testGetLightLevel() {
        assertEquals(11067, gasFinderMC.getLightLevel());
        assertEquals(5527, gasFinder2.getLightLevel());
    }
    @Test
    public void testGetDateTime() throws ParseException {
    	DateFormat DATE_PARSER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	
        assertEquals(DATE_PARSER.parse("2002/03/01 00:30:28"), gasFinderMC.getDateTime());
        assertEquals(DATE_PARSER.parse("2011/01/27 13:29:28"), gasFinder2.getDateTime());
    }
    
    @Test
    public void testGetSerialNumber() {
        assertEquals("HF-1xxx", gasFinderMC.getSerialNumber());
        assertEquals("HFH2O-1xxx", gasFinder2.getSerialNumber());
    }
    
    @Test
    public void testGetStatusCode() {
        assertEquals(1, gasFinderMC.getStatusCode());
        assertEquals(1, gasFinder2.getStatusCode());
    }
}


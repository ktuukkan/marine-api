package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.sentence.AISMessage04;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * AIS Message 4 implementation: Base Station Report.
 *
 * Field Name                                           Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	 messageID                        		       	   6	(   1,   6)
 *  2	 repeatIndicator                         		   2	(   7,   8)
 *  3	 userID                                  		  30	(   9,  38)
 *  4	 utcYear                        		          14	(  39,  52)
 *  5	 utcMonth                            	    	   4	(  53,  56)
 *  6	 utcDay                                  		   5	(  57,  61)
 *  7	 utcHour                                 		   5	(  62,  66)
 *  8	 utcMinute                              		   6	(  67,  72)
 *  9	 utcSecond                          	    	   6	(  73,  78)
 * 10	 positionAccuracy                    	    	   1	(  79,  79)
 * 11	 longitude                               		  28	(  80, 107)
 * 12	 latitude                              		  	  27	( 108, 134)
 * 13	 typeOfElectronicPositionFixingDevice    		   4	( 135, 138)
 * 14	 transmissionControlForLongRangeBroadcastMessage   1	( 139, 139)
 * 15	 spare                                   	 	   9	( 140, 148)
 * 16	 raimFlag                              		  	   1	( 149, 149)
 * 17	 communicationState                      		  19	( 150, 168)
 *                                                      ---- +
 *                                                   sum 168
 * @author Lázár József
 */
public class AISMessage04Parser extends AISUTCParser implements AISMessage04 {

	public AISMessage04Parser(Sixbit content) throws Exception {
		super(content);
	}
}

package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage03;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * AIS Message 3 implementation: Position report.
 * 
 * <pre>
 * Field  Name                                      Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	  messageID                               	   6	(   1,   6)
 *  2	  repeatIndicator                         	   2	(   7,   8)
 *  3	  userID                                  	  30	(   9,  38)
 *  4	  navigationalStatus                      	   4	(  39,  42)
 *  5	  rateOfTurn                              	   8	(  43,  50)
 *  6	  speedOverGround                         	  10	(  51,  60)
 *  7	  positionAccuracy                        	   1	(  61,  61)
 *  8	  longitude                               	  28	(  62,  89)
 *  9	  latitude                                	  27	(  90, 116)
 * 10	  courseOverGround                        	  12	( 117, 128)
 * 11	  trueHeading                             	   9	( 129, 137)
 * 12	  timeStamp                               	   6	( 138, 143)
 * 13	  specialManoeuvre                        	   2	( 144, 145)
 * 14	  spare                                   	   3	( 146, 148)
 * 15	  raimFlag                                	   1	( 149, 149)
 * 16	  communicationState                      	  19	( 150, 168)
 *                                                   ---- +
 *                                               sum 168
 * </pre>
 * 
 * @author Lázár József
 */
class AISMessage03Parser extends AISPositionReportParser implements AISMessage03 {

	public AISMessage03Parser(Sixbit content) {
		super(content);
	}
}

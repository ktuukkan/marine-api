package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage18;
import net.sf.marineapi.ais.util.Sixbit;

/**
 * 
 * @author Lázár József
 */
public class AISMessage18Parser extends AISPositionReportBParser implements AISMessage18 {

	public AISMessage18Parser(Sixbit content) {
		super(content);
	}
}

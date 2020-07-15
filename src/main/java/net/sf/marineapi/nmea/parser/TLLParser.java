package net.sf.marineapi.nmea.parser;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TLLSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.TargetStatus;
import net.sf.marineapi.nmea.util.Time;

/**
 * Sent by the Radar (ARPA / MARPA) and handled by the AIS Decoder in the same way as an AIS target 
 * 
 * TLL - Target Latitude and Longitude
 *        0  1       2 3        4 5    6         7 8 9
 *        |  |       | |        | |    |         | | |
 * $--TLL,xx,llll.ll,a,yyyyy.yy,a,c--c,hhmmss.ss,a,a*hh)
 * 
 * Field Number:
 * 0-Target Number (0-999)
 * 1-Target Latitude
 * 2-N=north, S=south
 * 3-Target Longitude
 * 4-E=east, W=west
 * 5-Target name
 * 6-UTC of data
 * 7-Status (L=lost, Q=acquisition, T=tracking)
 * 8-R= reference target; null (,,)= otherwise
 * 9-Checksum
 * 
 * example ({@code $RATLL,01,3731.51052,N,02436.00000,E,TEST1,161617.88,T,*0C}
 * @author Epameinondas Pantzopoulos
 */
public class TLLParser extends PositionParser implements TLLSentence{

	private static final int NUMBER = 0;
	private static final int LATITUDE = 1;
	private static final int LAT_HEMISPHERE = 2;
	private static final int LONGITUDE = 3;
	private static final int LON_HEMISPHERE = 4;
	private static final int NAME = 5;
	private static final int UTC_TIME = 6;
	private static final int STATUS = 7;
	private static final int REFERENCE = 8;
	
	public TLLParser(String nmea) {
		super(nmea,SentenceId.TLL);
	}
	
	public TLLParser(TalkerId talker) {
		super(talker, SentenceId.TLL,9);
	}

	@Override
	public Position getPosition() {
		return parsePosition(LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE);
	}

	@Override
	public void setPosition(Position pos) {
		setPositionValues(pos, LATITUDE, LAT_HEMISPHERE, LONGITUDE, LON_HEMISPHERE);
	}

	@Override
	public int getNumber() {
		return getIntValue(NUMBER);
	}

	@Override
	public String getName() {
		return getStringValue(NAME);
	}

	@Override
	public TargetStatus getStatus() {
		return TargetStatus.valueOf(getCharValue(STATUS));
	}

	@Override
	public boolean getReference() {
		return getCharValue(REFERENCE) == 'R';
	}

	@Override
	public Time getTime() {
		String str = getStringValue(UTC_TIME);
		return new Time(str);
	}

	@Override
	public void setNumber(int number) {
		setIntValue(NUMBER, number, 2);
		
	}

	@Override
	public void setName(String name) {
		setStringValue(NAME, name);
		
	}

	@Override
	public void setTime(Time t) {
		String str = String.format("%02d%02d", t.getHour(), t.getMinutes());

		DecimalFormat nf = new DecimalFormat("00.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(dfs);

		str += nf.format(t.getSeconds());
		setStringValue(UTC_TIME, str);
		
		
	}

	@Override
	public void setStatus(TargetStatus status) {
		setCharValue(STATUS, status.toChar());
		
	}

	@Override
	public void setReference(boolean isReference) {
		if (isReference) {
			setCharValue(REFERENCE, 'R');
		}
		
	}
	
	
	
}

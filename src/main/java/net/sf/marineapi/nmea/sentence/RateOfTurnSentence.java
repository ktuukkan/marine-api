package net.sf.marineapi.nmea.sentence;

/**
 * Created by SJK on 22/01/14.
 */
public interface RateOfTurnSentence extends Sentence {

    /**
     * Returns the vessel's Rate Of Turn, degrees per minute, "-" means bow turns to port.
     *
     * @return Rate of Turn in degrees per minute
     */
     double getRateOfTurn();

    /**
     * Returns Status
     * @return True means data is valid
     */
    boolean getStatus();

}

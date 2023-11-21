package net.sf.marineapi.ais.message;

/**
 * Implementation of https://www.navcen.uscg.gov/?pageName=AISMessage27
 *
 * @author Krzysztof Borowski
 */
public interface AISMessage27 extends AISPositionReport {

    /**
     * Returns the RAIM flag.
     *
     * @return {@code true} if RAIM in use, otherwise {@code false}.
     */
    boolean getRAIMFlag();


    /**
     * Returns Position Latency.
     *
     * @return 0 = Reported position latency is less than 5 seconds; 1 = Reported position latency is greater than 5 seconds = default
     */
    int getPositionLatency();
}

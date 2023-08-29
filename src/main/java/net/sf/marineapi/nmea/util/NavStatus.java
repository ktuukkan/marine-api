package net.sf.marineapi.nmea.util;

/**
 * <p>FAA operating modes reported by  RMC sentences since NMEA 4.1. </p>
 * @author John
 */

public enum NavStatus {

    /** Navigation status is autonomous. */
    AUTONOMOUS('A'),

    /** Navigation status is differential. */
    DIFFERENTIAL('D'),

    /** Navigation status is Estimated. */
    ESTIMATED('E'),

    /** Navigation status is Manual. */
    MANUAL('M'),

    /** Navigation status is Not valid. */
    NOT_VALID('N'),

    /** Navigation status is Simulator. */
    SIMULATOR('S'),

    /** Navigation status is Valid. */
    VALID('V');

    private final char navStatus;

    NavStatus(char navStatusCh) {
        navStatus = navStatusCh;
    }

    /**
     * Returns the corresponding char indicator of Navigation Status.
     *
     * @return NavStatus char used in sentences.
     */
    public char toChar() {
        return navStatus;
    }

    /**
     * Returns the NavStatus enum corresponding the actual char indicator used in
     * the sentencess.
     *
     * @param ch Char mode indicator
     * @return NavStatus enum
     */
    public static NavStatus valueOf(char ch) {
        for (NavStatus gm : values()) {
            if (gm.toChar() == ch) {
                return gm;
            }
        }
        return valueOf(String.valueOf(ch));
    }
}

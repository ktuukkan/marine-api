package net.sf.marineapi.ais.parser;

import net.sf.marineapi.ais.message.AISMessage27;
import net.sf.marineapi.ais.util.*;

/**
 * AIS Message 27 implementation - LONG-RANGE AUTOMATIC IDENTIFCATION SYSTEM BROADCAST MESSAGE
 * see: https://www.navcen.uscg.gov/?pageName=AISMessage27
 * <p>
 * This message is primarily intended for long-range detection of AIS Class A and Class B “SO” equipped vessels (typically by satellite).
 * This message has a similar content to Messages 1, 2 and 3,
 * but the total number of bits has been compressed
 * to allow for increased propagation delays associated with long-range detection.
 * Note there is no time stamp in this message.
 * The receiving system is expected to provide the time stamp when this message is received.
 *
 * <pre>
 * Field  Name                                      Bits    (from, to )
 * ------------------------------------------------------------------------
 *  1	  messageID                                    6    (   1,   6) - always 27
 *  2	  repeatIndicator                              2    (   7,   8) - always 3
 *  3	  userID                                      30    (   9,  38)
 *  4	  positionAccuracy                             1    (  39,  39)
 *  5	  raimFlag                                     1    (  40,  40)
 *  6	  navigationalStatus                           4    (  41,  44)
 *  7	  longitude                                   18    (  45,  62)
 *  8	  latitude                                    17    (  63,  79)
 *  9	  speedOverGround                              6    (  80,  85)
 * 10	  courseOverGround                             9    (  86,  94)
 * 11	  positionLatency                              1    (  95,  95)
 * 12	  spare                                        1    (  96,  96) - always 0
 *                                                   ---- +
 *                                                sum 96
 * </pre>
 *
 * @author Krzysztof Borowski
 */
class AisMessage27Parser extends AISMessageParser implements AISMessage27 {

    private final static String SEPARATOR = "\n\t";
    private final static int POSITIONACCURACY = 0;
    private final static int RAIMFLAG = 1;
    private final static int NAVIGATIONALSTATUS = 2;
    private final static int LONGITUDE = 3;
    private final static int LATITUDE = 4;
    private final static int SPEEDOVERGROUND = 5;
    private final static int COURSEOVERGROUND = 6;
    private final static int POSITIONLATENCY = 7;
    @SuppressWarnings("unused")
    private final static int SPARE = 8;
    private final static int[] FROM = {
            38, 38, 49, 44, 62, 79, 85, 94, 95};
    private final static int[] TO = {
            38, 39, 44, 62, 79, 85, 94, 95, 96};


    private boolean fPositionAccuracy;
    private boolean fRaimFlag;
    private int fNavigationalStatus;
    private int fLongitude;
    private int fLatitude;
    private int fSOG;
    private int fCOG;
    private int fPositionLatency;

    // not available in this Message27 Position Report, filled in with defaults
    private int fTrueHeading = 511;
    private int fRateOfTurn = -128;
    private int fTimeStamp = 60;
    private int fManouverIndicator = 0;


    public AisMessage27Parser(Sixbit content) {
        super(content, 96, 96);
        fPositionAccuracy = content.getBoolean(TO[POSITIONACCURACY]);
        fRaimFlag = content.getBoolean(TO[RAIMFLAG]);
        fNavigationalStatus = content.getInt(FROM[NAVIGATIONALSTATUS], TO[NAVIGATIONALSTATUS]);
        if (!NavigationalStatus.isCorrect(fNavigationalStatus))
            addViolation(new AISRuleViolation("NavigationalStatus", fNavigationalStatus, NavigationalStatus.RANGE));

        fLongitude = content.getAs18BitInt(FROM[LONGITUDE], TO[LONGITUDE]);
        if (!Longitude18.isCorrect(fLongitude))
            addViolation(new AISRuleViolation("LongitudeInDegrees", fLongitude, Longitude18.RANGE));
        fLatitude = content.getAs17BitInt(FROM[LATITUDE], TO[LATITUDE]);
        if (!Latitude17.isCorrect(fLatitude))
            addViolation(new AISRuleViolation("LatitudeInDegrees", fLatitude, Latitude17.RANGE));

        fSOG = content.getInt(FROM[SPEEDOVERGROUND], TO[SPEEDOVERGROUND]);
        fCOG = content.getInt(FROM[COURSEOVERGROUND], TO[COURSEOVERGROUND]);

        if (!Angle9.isCorrect(fCOG))
            addViolation(new AISRuleViolation("CourseOverGround", fCOG, Angle9.RANGE));

        fPositionLatency = content.getInt(FROM[POSITIONLATENCY], TO[POSITIONLATENCY]);
    }

    @Override
    public boolean getRAIMFlag() {
        return fRaimFlag;
    }

    @Override
    public int getNavigationalStatus() {
        return fNavigationalStatus;
    }

    @Override
    public double getRateOfTurn() {
        return RateOfTurn.toDegreesPerMinute(fRateOfTurn);
    }

    @Override
    public double getSpeedOverGround() {
        return fSOG;
    }

    @Override
    public boolean isAccurate() {
        return fPositionAccuracy;
    }

    @Override
    public double getLongitudeInDegrees() {
        return Longitude18.toDegrees(fLongitude);
    }

    @Override
    public double getLatitudeInDegrees() {
        return Latitude17.toDegrees(fLatitude);
    }

    @Override
    public double getCourseOverGround() {
        return fCOG;
    }

    @Override
    public int getTrueHeading() {
        return fTrueHeading;
    }

    @Override
    public int getTimeStamp() {
        return fTimeStamp;
    }

    @Override
    public int getManouverIndicator() {
        return fManouverIndicator;
    }

    @Override
    public boolean hasRateOfTurn() {
        return RateOfTurn.isTurnIndicatorAvailable(fRateOfTurn);
    }

    @Override
    public boolean hasSpeedOverGround() {
        return SpeedOverGround.isAvailable(fSOG);
    }

    @Override
    public boolean hasCourseOverGround() {
        return Angle12.isAvailable(fCOG);
    }

    @Override
    public boolean hasTrueHeading() {
        return Angle9.isAvailable(fTrueHeading);
    }

    @Override
    public boolean hasTimeStamp() {
        return TimeStamp.isAvailable(fTimeStamp);
    }

    @Override
    public boolean hasLongitude() {
        return Longitude18.isAvailable(fLongitude);
    }

    @Override
    public boolean hasLatitude() {
        return Latitude17.isAvailable(fLatitude);
    }

    @Override
    public int getPositionLatency() {
        return fPositionLatency;
    }

    public String toString() {
        String result = "\tNav st:  " + NavigationalStatus.toString(fNavigationalStatus);
        result += SEPARATOR + "ROT:     " + RateOfTurn.toString(fRateOfTurn);
        result += SEPARATOR + "SOG:     " + SpeedOverGround.toString(fSOG);
        result += SEPARATOR + "Pos acc: " + (fPositionAccuracy ? "high" : "low") + " accuracy";
        result += SEPARATOR + "Lon:     " + Longitude18.toString(fLongitude);
        result += SEPARATOR + "Lat:     " + Latitude17.toString(fLatitude);
        result += SEPARATOR + "COG:     " + Angle9.toString(fCOG);
        result += SEPARATOR + "Heading: " + Angle9.getTrueHeadingString(fTrueHeading);
        result += SEPARATOR + "Time:    " + TimeStamp.toString(fTimeStamp);
        result += SEPARATOR + "Man ind: " + ManeuverIndicator.toString(fManouverIndicator);
        result += SEPARATOR + "Latency: " + (fPositionLatency == 0 ? "<5s" : ">5s");
        return result;
    }
}

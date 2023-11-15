public class PositionProvider extends AbstractProvider<PositionEvent> {

    public PositionProvider(SentenceReader reader) {
        super(reader, SentenceId.RMC, SentenceId.GGA, SentenceId.GLL, SentenceId.VTG);
    }

    @Override
    protected PositionEvent createProviderEvent() {
        Position p = null;
        Double sog = null;
        Double cog = null;
        Date d = null;
        Time t = null;
        FaaMode mode = null;
        GpsFixQuality fix = null;

        for (Sentence s : getSentences()) {
            if (s instanceof RMCSentence) {
                handleRMCSentence((RMCSentence) s, p, sog, cog, d, t, mode);
            } else if (s instanceof VTGSentence) {
                handleVTGSentence((VTGSentence) s, sog, cog);
            } else if (s instanceof GGASentence) {
                handleGGASentence((GGASentence) s, p, d, t, fix);
            } else if (s instanceof GLLSentence) {
                handleGLLSentence((GLLSentence) s, p);
            }
        }

        if (d == null) {
            d = new Date();
        }

        return new PositionEvent(this, p, sog, cog, d, t, mode, fix);
    }

    private void handleRMCSentence(RMCSentence rmc, Position p, Double sog, Double cog, Date d, Time t, FaaMode mode) {
        sog = rmc.getSpeed();
        try {
            cog = rmc.getCourse();
        } catch (DataNotAvailableException e) {
            // If we are not moving, course can be undefined. Leave null in that case.
        }
        d = rmc.getDate();
        t = rmc.getTime();
        if (p == null) {
            p = rmc.getPosition();
            if (rmc.getFieldCount() > 11) {
                mode = rmc.getMode();
            }
        }
    }

    private void handleVTGSentence(VTGSentence vtg, Double sog, Double cog) {
        sog = vtg.getSpeedKnots();
        try {
            cog = vtg.getTrueCourse();
        } catch (DataNotAvailableException e) {
            // If we are not moving, course can be undefined. Leave null in that case.
        }
    }

    private void handleGGASentence(GGASentence gga, Position p, Date d, Time t, GpsFixQuality fix) {
        p = gga.getPosition();
        fix = gga.getFixQuality();

        // Some receivers do not provide RMC message
        if (t == null) {
            t = gga.getTime();
        }
    }

    private void handleGLLSentence(GLLSentence gll, Position p) {
        p = gll.getPosition();
    }

    @Override
    protected boolean isReady() {
        return hasOne("RMC", "VTG") && hasOne("GGA", "GLL");
    }

    @Override
    protected boolean isValid() {
        for (Sentence s : getSentences()) {
            if (s instanceof RMCSentence && !isValidRMCSentence((RMCSentence) s)) {
                return false;
            } else if (s instanceof GGASentence && !isValidGGASentence((GGASentence) s)) {
                return false;
            } else if (s instanceof GLLSentence && !isValidGLLSentence((GLLSentence) s)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidRMCSentence(RMCSentence rmc) {
        DataStatus ds = rmc.getStatus();
        return !DataStatus.VOID.equals(ds) && !(rmc.getFieldCount() > 11 && FaaMode.NONE.equals(rmc.getMode()));
    }

    private boolean isValidGGASentence(GGASentence gga) {
        GpsFixQuality fq = gga.getFixQuality();
        return !GpsFixQuality.INVALID.equals(fq);
    }

    private boolean isValidGLLSentence(GLLSentence gll) {
        DataStatus ds = gll.getStatus();
        return !DataStatus.VOID.equals(ds);
    }
}

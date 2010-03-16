package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ZDA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class SentenceZDATest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPZDA,032915,07,08,2004,00,00*4D";

    private ZDASentenceImpl zda;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        try {
            zda = new ZDASentenceImpl(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getUtcTime()}.
     */
    @Test
    public void testGetUtcTime() {
        assertEquals("032915", zda.getUtcTime());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getUtcHours()}.
     */
    @Test
    public void testGetUtcHours() {
        assertTrue(3 == zda.getUtcHours());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getUtcMinutes()}.
     */
    @Test
    public void testGetUtcMinutes() {
        assertTrue(29 == zda.getUtcMinutes());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getUtcSeconds()}.
     */
    @Test
    public void testGetUtcSeconds() {
        assertTrue(15 == zda.getUtcSeconds());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getUtcDay()}.
     */
    @Test
    public void testGetUtcDay() {
        assertTrue(7 == zda.getUtcDay());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getUtcMonth()}.
     */
    @Test
    public void testGetUtcMonth() {
        assertTrue(8 == zda.getUtcMonth());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getUtcYear()}.
     */
    @Test
    public void testGetUtcYear() {
        assertTrue(2004 == zda.getUtcYear());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getLocalZoneHours()}.
     */
    @Test
    public void testGetLocalZoneHours() {
        assertTrue(0 == zda.getLocalZoneHours());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getLocalZoneMinutes()}.
     */
    @Test
    public void testGetLocalZoneMinutes() {
        assertTrue(0 == zda.getLocalZoneMinutes());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDASentenceImpl#getDate()}.
     */
    @Test
    public void testGetDate() {
        GregorianCalendar cal = new GregorianCalendar(2004, 7, 7, 3, 29, 15);
        final Date expected = cal.getTime();
        Date parsed = zda.getDate();

        assertEquals(expected, parsed);
        assertTrue(expected.getTime() == parsed.getTime());
    }

}

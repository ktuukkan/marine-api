package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.GregorianCalendar;

import net.sf.marineapi.nmea.util.Time;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ZDA sentence parser.
 * 
 * @author Kimmo Tuukkanen
 */
public class ZDATest {

    /** Example sentence */
    public static final String EXAMPLE = "$GPZDA,032915,07,08,2004,00,00*4D";

    private ZDAParser zda;

    @Before
    public void setUp() {
        try {
            zda = new ZDAParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.ZDAParser#getTime()}.
     */
    @Test
    public void testGetTime() {
        Time t = zda.getTime();
        assertNotNull(t);
        assertEquals(3, t.getHour());
        assertEquals(29, t.getMinutes());
        assertEquals(15.0, t.getSeconds(), 0.1);
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDAParser#getUtcDay()}.
     */
    @Test
    public void testGetUtcDay() {
        assertEquals(7, zda.getUtcDay());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDAParser#getUtcMonth()}.
     */
    @Test
    public void testGetUtcMonth() {
        assertEquals(8, zda.getUtcMonth());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDAParser#getUtcYear()}.
     */
    @Test
    public void testGetUtcYear() {
        assertEquals(2004, zda.getUtcYear());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDAParser#getLocalZoneHours()}.
     */
    @Test
    public void testGetLocalZoneHours() {
        assertEquals(0, zda.getLocalZoneHours());
    }

    /**
     * Test method for
     * {@link net.sf.marineapi.nmea.parser.ZDAParser#getLocalZoneMinutes()}.
     */
    @Test
    public void testGetLocalZoneMinutes() {
        assertEquals(0, zda.getLocalZoneMinutes());
    }

    /**
     * Test method for {@link net.sf.marineapi.nmea.parser.ZDAParser#getDate()}.
     */
    @Test
    public void testGetDate() {
        GregorianCalendar cal = new GregorianCalendar(2004, 7, 7, 3, 29, 15);
        final Date expected = cal.getTime();
        Date parsed = zda.getDate();
        assertEquals(expected, parsed);
        assertEquals(expected.getTime(), parsed.getTime());
    }

}

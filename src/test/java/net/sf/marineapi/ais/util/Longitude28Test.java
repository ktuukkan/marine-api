package net.sf.marineapi.ais.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Longitude28Test {
    private static final double DELTA = 0.00001;

    @Test
    public void zeroIsAvailable() {
        assertTrue(Longitude28.isAvailable(0));
    }

    @Test
    public void zeroIsCorrect() {
        assertTrue(Longitude28.isCorrect(0));
    }

    @Test
    public void minValueIsAvailable() {
        assertTrue(Longitude28.isAvailable(-180*60*10000));
    }

    @Test
    public void minValueIsCorrect() {
        assertTrue(Longitude28.isCorrect(-180*60*10000));
    }

    @Test
    public void maxValueIsAvailable() {
        assertTrue(Longitude28.isAvailable(180*60*10000));
    }

    @Test
    public void maxValueIsCorrect() {
        assertTrue(Longitude28.isCorrect(180*60*10000));
    }

    @Test
    public void defaultValueIsNotAvailable() {
        assertFalse(Longitude28.isAvailable(181*60*10000));
    }

    @Test
    public void defaultValueIsCorrect() {
        assertTrue(Longitude28.isCorrect(181*60*10000));
    }

    @Test
    public void largeNegativeValueIsNotAvailable() {
        assertFalse(Longitude28.isAvailable(-1-180*60*10000));
    }

    @Test
    public void largeNegativeValueIsNotCorrect() {
        assertFalse(Longitude28.isCorrect(-1-180*60*10000));
    }

    @Test
    public void largeValueIsNotAvailable() {
        assertFalse(Longitude28.isAvailable(1+180*60*10000));
    }

    @Test
    public void largeValueIsNotCorrect() {
        assertFalse(Longitude28.isCorrect(1+180*60*10000));
    }

    @Test
    public void conversionToKnotsWorks() {
        assertEquals(-180.0, Longitude28.toDegrees(Double.valueOf(-180.0*60*10000).intValue()), DELTA);
        assertEquals(-45.1, Longitude28.toDegrees(Double.valueOf(-45.1*60*10000).intValue()), DELTA);
        assertEquals(0.0, Longitude28.toDegrees(0), 0.00001);
        assertEquals(45.9, Longitude28.toDegrees(Double.valueOf(45.9*60*10000).intValue()), DELTA);
        assertEquals(180.0, Longitude28.toDegrees(Double.valueOf(180.0*60*10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues() {
        assertEquals(-201.1, Longitude28.toDegrees(Double.valueOf(-201.1*60*10000).intValue()), DELTA);
        assertEquals(181.1, Longitude28.toDegrees(Double.valueOf(181.1*60*10000).intValue()), DELTA);
        assertEquals(202.3, Longitude28.toDegrees(Double.valueOf(202.3*60*10000).intValue()), DELTA);
    }
}

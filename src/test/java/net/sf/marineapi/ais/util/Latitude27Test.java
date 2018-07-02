package net.sf.marineapi.ais.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Latitude27Test {
    private static final double DELTA = 0.00001;

    @Test
    public void zeroIsAvailable() {
        assertTrue(Latitude27.isAvailable(0));
    }

    @Test
    public void zeroIsCorrect() {
        assertTrue(Latitude27.isCorrect(0));
    }

    @Test
    public void minValueIsAvailable() {
        assertTrue(Latitude27.isAvailable(-90*60*10000));
    }

    @Test
    public void minValueIsCorrect() {
        assertTrue(Latitude27.isCorrect(-90*60*10000));
    }

    @Test
    public void maxValueIsAvailable() {
        assertTrue(Latitude27.isAvailable(90*60*10000));
    }

    @Test
    public void maxValueIsCorrect() {
        assertTrue(Latitude27.isCorrect(90*60*10000));
    }

    @Test
    public void defaultValueIsNotAvailable() {
        assertFalse(Latitude27.isAvailable(91*60*10000));
    }

    @Test
    public void defaultValueIsCorrect() {
        assertTrue(Latitude27.isCorrect(91*60*10000));
    }

    @Test
    public void largeNegativeValueIsNotAvailable() {
        assertFalse(Latitude27.isAvailable(-1-90*60*10000));
    }

    @Test
    public void largeNegativeValueIsNotCorrect() {
        assertFalse(Latitude27.isCorrect(-1-90*60*10000));
    }

    @Test
    public void largeValueIsNotAvailable() {
        assertFalse(Latitude27.isAvailable(1+90*60*10000));
    }

    @Test
    public void largeValueIsNotCorrect() {
        assertFalse(Latitude27.isCorrect(1+90*60*10000));
    }

    @Test
    public void conversionToKnotsWorks() {
        assertEquals(-90.0, Latitude27.toDegrees(Double.valueOf(-90.0*60*10000).intValue()), DELTA);
        assertEquals(-45.1, Latitude27.toDegrees(Double.valueOf(-45.1*60*10000).intValue()), DELTA);
        assertEquals(0.0, Latitude27.toDegrees(0), 0.00001);
        assertEquals(45.9, Latitude27.toDegrees(Double.valueOf(45.9*60*10000).intValue()), DELTA);
        assertEquals(90.0, Latitude27.toDegrees(Double.valueOf(90.0*60*10000).intValue()), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues() {
        assertEquals(-101.1, Latitude27.toDegrees(Double.valueOf(-101.1*60*10000).intValue()), DELTA);
        assertEquals(91.1, Latitude27.toDegrees(Double.valueOf(91.1*60*10000).intValue()), DELTA);
        assertEquals(102.3, Latitude27.toDegrees(Double.valueOf(102.3*60*10000).intValue()), DELTA);
    }
}

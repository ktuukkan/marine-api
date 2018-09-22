package net.sf.marineapi.ais.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpeedOverGroundTest {
    private static final double DELTA = 0.001;

    @Test
    public void minValueIsAvailable() {
        assertTrue(SpeedOverGround.isAvailable(0));
    }

    @Test
    public void minValueIsCorrect() {
        assertTrue(SpeedOverGround.isCorrect(0));
    }

    @Test
    public void maxValueIsAvailable() {
        assertTrue(SpeedOverGround.isAvailable(1022));
    }

    @Test
    public void maxValueIsCorrect() {
        assertTrue(SpeedOverGround.isCorrect(1022));
    }

    @Test
    public void defaultValueIsNotAvailable() {
        assertFalse(SpeedOverGround.isAvailable(1023));
    }

    @Test
    public void defaultValueIsCorrect() {
        assertTrue(SpeedOverGround.isCorrect(1023));
    }

    @Test
    public void negativeValueIsNotAvailable() {
        assertFalse(SpeedOverGround.isAvailable(-1));
    }

    @Test
    public void negativeValueIsNotCorrect() {
        assertFalse(SpeedOverGround.isCorrect(-1));
    }

    @Test
    public void largeValueIsNotAvailable() {
        assertFalse(SpeedOverGround.isAvailable(1100));
    }

    @Test
    public void largeValueIsNotCorrect() {
        assertFalse(SpeedOverGround.isCorrect(1100));
    }

    @Test
    public void conversionToKnotsWorks() {
        assertEquals(0.0, SpeedOverGround.toKnots(0), DELTA);
        assertEquals(0.1, SpeedOverGround.toKnots(1), DELTA);
        assertEquals(90.9, SpeedOverGround.toKnots(909), DELTA);
        assertEquals(102.2, SpeedOverGround.toKnots(1022), DELTA);
    }

    @Test
    public void conversionReturnsOnInvalidValues() {
        assertEquals(-10.1, SpeedOverGround.toKnots(-101), DELTA);
        assertEquals(102.3, SpeedOverGround.toKnots(1023), DELTA);
        assertEquals(4567.8, SpeedOverGround.toKnots(45678), DELTA);
    }
}

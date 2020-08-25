package com.raenjamio.service;

import com.raenjamio.useCase.shared.Temperature;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TemperatureTest {

    @Test
    public void shouldReturnTempCorrectlyWhenTemperatureIsNormal() {
        Long result = Temperature.calculateBeers(Double.valueOf("20.0"), Long.valueOf("10"));

        assertNotNull(result);
        assertEquals(Long.valueOf("10") , result);
    }

    @Test
    public void shouldReturnTempCorrectlyWhenTemperatureIsLow() {
        Long result = Temperature.calculateBeers(Double.valueOf("5.0"), Long.valueOf("10"));

        assertNotNull(result);
        assertEquals(Long.valueOf("8") , result);
    }

    @Test
    public void shouldReturnTempCorrectlyWhenTemperatureIsHigh() {
        Long result = Temperature.calculateBeers(Double.valueOf("35.0"), Long.valueOf("10"));

        assertNotNull(result);
        assertEquals(Long.valueOf("30") , result);
    }

    @Test
    public void shouldReturnTempCorrectlyWhenTemperatureIsLowCase2() {
        Long result = Temperature.calculateBeers(Double.valueOf("5.0"), Long.valueOf("1000"));

        assertNotNull(result);
        assertEquals(Long.valueOf("750") , result);
    }

    @Test
    public void shouldReturnTempCorrectlyWhenTemperatureIsLowCase3() {
        Long result = Temperature.calculateBeers(Double.valueOf("5.0"), Long.valueOf("5"));

        assertNotNull(result);
        assertEquals(Long.valueOf("4") , result);
    }
}

package com.charlescloud.financetracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReturnCalculatorTest {
    ReturnCalculator returnCalculator;

    @Before
    public void setUp() throws Exception {
        returnCalculator = new ReturnCalculator();
    }

    @Test
    public void earningsPercentageIsCorrect() throws Exception {
        assertEquals(0, returnCalculator.calculateTotalEarningsPercentage(0,0),0);
        assertEquals(100f, returnCalculator.calculateTotalEarningsPercentage(5,10),0);
        assertEquals(-50f, returnCalculator.calculateTotalEarningsPercentage(10,5),0);
        assertEquals(-100f, returnCalculator.calculateTotalEarningsPercentage(10,0),0);
    }
}
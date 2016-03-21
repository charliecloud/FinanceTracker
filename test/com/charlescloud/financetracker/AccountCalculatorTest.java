package com.charlescloud.financetracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountCalculatorTest {
    AccountCalculator accounCalculator;

    @Before
    public void setUp() throws Exception {
        accounCalculator = new AccountCalculator();
    }

    @Test
    public void earningsPercentageIsCorrect() throws Exception {
        assertEquals(0,accounCalculator.calculateTotalEarningsPercentage(0,0),0);
        assertEquals(100f, accounCalculator.calculateTotalEarningsPercentage(5,10),0);
        assertEquals(-50f, accounCalculator.calculateTotalEarningsPercentage(10,5),0);
        assertEquals(-100f,accounCalculator.calculateTotalEarningsPercentage(10,0),0);
    }
}
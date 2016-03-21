package com.charlescloud.financetracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountCalculatorTest {
    AccountCalculator accountCalculator;

    @Before
    public void setUp() throws Exception {
        accountCalculator = new AccountCalculator();
    }

    @Test
    public void earningsPercentageIsCorrect() throws Exception {
        assertEquals(0, accountCalculator.calculateTotalEarningsPercentage(0,0),0);
        assertEquals(100f, accountCalculator.calculateTotalEarningsPercentage(5,10),0);
        assertEquals(-50f, accountCalculator.calculateTotalEarningsPercentage(10,5),0);
        assertEquals(-100f, accountCalculator.calculateTotalEarningsPercentage(10,0),0);
    }
}
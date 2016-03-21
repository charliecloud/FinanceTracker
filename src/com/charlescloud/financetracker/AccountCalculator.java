package com.charlescloud.financetracker;

import java.io.Serializable;

public class AccountCalculator implements Serializable {

    //TODO: Write tests to confirm that it works for all numbers
    public float calculateTotalEarningsPercentage(float starting, float ending){
        //Need the current balance and the starting balance
        return (1-starting/ending)*100;
    }
}

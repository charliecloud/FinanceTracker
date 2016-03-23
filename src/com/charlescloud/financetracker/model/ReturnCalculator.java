package com.charlescloud.financetracker.model;

import java.io.Serializable;

public class ReturnCalculator implements Serializable {

    public float calculateTotalEarningsPercentage(float starting, float ending){
        if (starting == 0){
            return 0f;
        }
        float result =  (ending-starting)/starting;
        return result*100;
    }
}

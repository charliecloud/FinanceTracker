package com.charlescloud.financetracker.controller;


import com.charlescloud.financetracker.model.Account;

public class CalculatorController {

    //TODO: Write tests to confrim that it works for all numbers
    public float calculateTotalEarningsPercentage(Account account){
        //Need the current balance and the starting balance
        return (1-account.getPurchaseCost()/account.getBalance())*100;
    }
}

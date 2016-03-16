package com.charlescloud.financetracker;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        FinanceTracker financeTracker = new FinanceTracker();
        try {
            financeTracker.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

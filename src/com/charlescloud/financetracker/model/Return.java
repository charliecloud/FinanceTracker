package com.charlescloud.financetracker.model;


import java.io.Serializable;

public class Return implements Comparable, Serializable {
    private ReturnCalculator returnCalculator;
    private float starting;
    private float ending;


    public Return(ReturnCalculator returnCalculator, float starting, float ending) {
        this.returnCalculator = returnCalculator;
        this.starting = starting;
        this.ending = ending;
    }

    public Return(float starting, float ending) {
        this.starting = starting;
        this.ending = ending;
        returnCalculator = new ReturnCalculator();
    }

    public float getReturnPercentage() {
        return returnCalculator.calculateTotalEarningsPercentage(starting,ending);
    }

    public float getStarting() {
        return starting;
    }

    public void setStarting(float starting) {
        this.starting = starting;
    }

    public float getEnding() {
        return ending;
    }

    public void setEnding(float ending) {
        this.ending = ending;
    }

    @Override
    public int compareTo(Object o) {
        return Float.compare(this.getReturnPercentage(), ((Return) o).getReturnPercentage());
    }
}

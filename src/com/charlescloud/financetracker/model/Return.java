package com.charlescloud.financetracker.model;


import java.io.Serializable;

public class Return implements Comparable, Serializable {
    private float returnPercentage;

    public Return(float returnPercentage) {
        this.returnPercentage = returnPercentage;
    }

    public Return() {
    }

    public float getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(float returnPercentage) {
        this.returnPercentage = returnPercentage;
    }

    @Override
    public int compareTo(Object o) {
        return Float.compare(returnPercentage, ((Return) o).getReturnPercentage());
    }
}

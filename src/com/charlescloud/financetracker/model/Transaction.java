package com.charlescloud.financetracker.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private Date date;
    private TransactionType type;
    private Float amount;
    private boolean automatic;

    public Transaction(Date date, TransactionType type, Float amount, boolean automatic) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.automatic = automatic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
}

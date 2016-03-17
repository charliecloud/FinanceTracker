package com.charlescloud.financetracker.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Account implements Serializable {

    //TODO: Add account provider

    private String name;
    private int id;
    private Date lastUpdated;
    private boolean taxable;
    private Float purchaseCost;
    private Float balance;
    private boolean open;
    private Map<Date, Float> balanceHistory;
    private Map<Date, Transaction> transactionHistory;
    private AccountType accountType;


    public Account(String name, int id, boolean taxable, Float purchaseCost, Float balance, boolean open, AccountType accountType) {
        this.name = name;
        this.id = id;
        this.taxable = taxable;
        this.purchaseCost = purchaseCost;
        this.balance = balance;
        this.open = open;
        this.accountType = accountType;

        balanceHistory = new TreeMap<>();
        transactionHistory = new TreeMap<>();

        addTransaction(new Transaction(new Date(),TransactionType.ACCOUNT_OPEN,balance,false));
        lastUpdated = new Date();
    }

    public void addTransaction(Transaction transaction){
        Date date = transaction.getDate();
        transactionHistory.put(date, transaction);
        //TODO: Action based on transaction type
        if(transaction.getType() == TransactionType.BALANCE_UPDATE){
            addBalanceUpdate(date, transaction.getAmount());
        }
        this.lastUpdated = date;
    }

    private void addBalanceUpdate(Date date, Float balance){
        balanceHistory.put(date, balance);
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Float getPurchaseCost() {
        return purchaseCost;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Map<Date, Float> getBalanceHistory(){
        return balanceHistory;
    }

    public Map<Date, Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountType=" + accountType +
                ", open=" + open +
                ", balance=" + balance +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

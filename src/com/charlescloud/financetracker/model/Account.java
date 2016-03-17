package com.charlescloud.financetracker.model;

import com.charlescloud.financetracker.controller.AccountCalculator;

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

    private AccountCalculator accountCalculator;


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
        accountCalculator = new AccountCalculator();

        addTransaction(new Transaction(new Date(),TransactionType.ACCOUNT_OPEN,balance,false));
        lastUpdated = new Date();
    }

    //TODO: Write test case to verify correct behavior for each transaction type
    public void addTransaction(Transaction transaction){
        Date date = transaction.getDate();
        transactionHistory.put(date, transaction);
        switch(transaction.getType()){
            case ACCOUNT_OPEN:
                addBalanceUpdate(date, transaction.getAmount());
                break;
            case BALANCE_UPDATE:
                addBalanceUpdate(date, transaction.getAmount());
                break;
            case DIVIDEND_REINVEST:
                addBalanceUpdate(date, (transaction.getAmount()+this.balance));
                break;
            case INTEREST_PAYOUT:
                addBalanceUpdate(date, (transaction.getAmount()+this.balance));
                break;
            case CASH_IN:
                addBalanceUpdate(date, (transaction.getAmount()+this.balance));
                break;
            //TODO: Make sure you have enough cash to not go negative?
            case CASH_OUT:
                addBalanceUpdate(date, (this.balance-transaction.getAmount()));
                break;
        }
        //update date
        this.lastUpdated = date;
    }

    private void addBalanceUpdate(Date date, Float balance){
        balanceHistory.put(date, balance);
        this.balance = balance;
    }

    public float getTotalEarningsPercentage(){
        return accountCalculator.calculateTotalEarningsPercentage(this.purchaseCost, this.balance);
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

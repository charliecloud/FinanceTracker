package com.charlescloud.financetracker.model;

import com.charlescloud.financetracker.AccountCalculator;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Account implements Serializable {
    private String name;
    private String accountProvider;
    private int id;
    private Date lastUpdated;
    private boolean taxable;
    private Float purchaseCost;
    private Float balance;
    private boolean open;
    private Return accountReturn;
    private Map<Date, Float> balanceHistory;
    private Map<Date, Transaction> transactionHistory;
    private AccountType accountType;

    private AccountCalculator accountCalculator;


    public Account(String name, String accountProvider, int id, boolean taxable, Float purchaseCost, Float balance, boolean open, AccountType accountType) {
        this.name = name;
        this.accountProvider = accountProvider;
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

        accountReturn = new Return();
    }

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
            case INTEREST_REINVEST:
                addBalanceUpdate(date, (transaction.getAmount()+this.balance));
                break;
            case CONTRIBUTION:
                //Updating the purchase cost
                purchaseCost += transaction.getAmount();
                break;
            //TODO: Make sure you have enough cash to not go negative?
            case WITHDRAWAL:
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
        accountReturn.setReturnPercentage(accountCalculator.calculateTotalEarningsPercentage(this.purchaseCost, this.balance));
        return accountReturn.getReturnPercentage();
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

    public Return getAccountReturn() {
        accountReturn.setReturnPercentage(accountCalculator.calculateTotalEarningsPercentage(this.purchaseCost, this.balance));
        return accountReturn;
    }

    public void setAccountReturn(Return accountReturn) {
        this.accountReturn = accountReturn;
    }

    //TODO: Rewrite this to look nicer.
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

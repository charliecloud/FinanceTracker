package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.data.AccountRepository;
import com.charlescloud.financetracker.model.Account;
import com.charlescloud.financetracker.model.AccountType;
import com.charlescloud.financetracker.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccountController {

    private AccountRepository accountRepository;

    public AccountController() {
        accountRepository = new AccountRepository(new ArrayList<>());
    }

    public void createNewAccount(String name, int id, boolean taxable, Float purchaseCost, Float balance, boolean open, AccountType accountType){
        Account account = new Account(name, id, taxable, purchaseCost, balance, open, accountType);
        accountRepository.addAccount(account);
    }

    public void addTransactionForAccount(Transaction transaction, String accountName){
        getAccountByName(accountName).addTransaction(transaction);
    }

    public Map<Date, Transaction> getTransactionsForAccount(String accountName){
        return getAccountByName(accountName).getTransactionHistory();
    }

    public Map<Date, Float> getBalancesForAccount(String accountName){
        return getAccountByName(accountName).getBalanceHistory();
    }

    public Account getAccountByName(String accountName){
        for (Account a : getAllAccounts()){
            if (a.getName().equals(accountName)){
                return a;
            }
        }
        return null;
    }

    public boolean markAccountAsClosed(String accountName){
        Account account = getAccountByName(accountName);
        if (account.equals(null)){
            return false;
        }
        account.setOpen(false);
        return true;
    }

    public float calculateTotalAccountEarningPercentage(String accountName){
        return getAccountByName(accountName).getTotalEarningsPercentage();
    }

    public void saveAccounts(String fileName){
        accountRepository.saveAccountsToFilesystem(fileName);
    }

    public void loadAccounts(String fileName){
        accountRepository.loadAccountsFromFilesystem(fileName);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public int getNumberOfAccounts(){
        return accountRepository.getAllAccounts().size();
    }
}

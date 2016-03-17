package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.data.AccountRepository;
import com.charlescloud.financetracker.model.Account;
import com.charlescloud.financetracker.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AccountController {

    private AccountRepository accountRepository;

    public AccountController() {
        accountRepository = new AccountRepository(new ArrayList<>());
    }

    public void addAccount(Account account){
        accountRepository.addAccount(account);
    }

    public void addTransactionForAccount(Transaction transaction, String accountName){
        getAccountByName(accountName).addTransaction(transaction);
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

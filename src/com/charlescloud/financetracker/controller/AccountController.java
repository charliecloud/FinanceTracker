package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.data.AccountRepository;
import com.charlescloud.financetracker.model.Account;
import com.charlescloud.financetracker.model.AccountType;
import com.charlescloud.financetracker.model.Return;
import com.charlescloud.financetracker.model.Transaction;

import java.util.*;

public class AccountController {

    private AccountRepository accountRepository;

    public AccountController() {
        accountRepository = new AccountRepository(new ArrayList<>());
    }

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createNewAccount(String name, String provider, int id, boolean taxable, Float purchaseCost, Float balance, boolean open, AccountType accountType){
        Account account = new Account(name, provider, id, taxable, purchaseCost, balance, open, accountType);
        addAccountToRepository(account);
        return account;
    }

    public boolean isAccountOpen(String accountName){
        Account account = getAccountByName(accountName);
        if(account != null) {
            return getAccountByName(accountName).isOpen();
        }else{
            return false;
        }
    }

    public boolean markAccountAsClosed(String accountName){
        Account account = getAccountByName(accountName);
        if (account == null || !account.isOpen()){
            return false;
        }
        account.setOpen(false);
        return true;
    }

    public Account getAccountByName(String accountName){
        for (Account a : getAllAccounts()){
            if (a.getName().equals(accountName)){
                return a;
            }
        }
        return null;
    }

    /**
     *
     * @param accountType
     * @return a List of all accounts that are of the type accountType
     */
    public List<Account> getAccountsOfType(AccountType accountType){
        List<Account> accounts = new ArrayList<>();
        for (Account account : accountRepository.getAllAccounts()){
            if (account.getAccountType().equals(accountType)){
                accounts.add(account);
            }
        }
        return accounts;
    }

    /**
     *
     * @return
     */
    public Map<Return, Account> getAccountsSortedByReturn(){
        Map<Return, Account> returnAccountMap = new TreeMap<>();
        for (Account account : getAllAccounts()){
            returnAccountMap.put(account.getAccountReturn(), account);
        }
        return  returnAccountMap;
    }


    public Map<Date, Float> getBalancesForAccount(String accountName){
        Map<Date, Float> balances = new TreeMap<>();
        Account account = getAccountByName(accountName);
        if (account != null) {
            balances =  getAccountByName(accountName).getBalanceHistory();
        }
        return balances;
    }

    public float calculateTotalAccountEarningPercentage(String accountName){
        //TODO: Return null or 0 if not an account?
        return getAccountByName(accountName).getTotalEarningsPercentage();
    }

    public boolean addTransactionToAccount(Transaction transaction, String accountName){
        Account account = getAccountByName(accountName);
        if (account != null && account.isOpen()){
            account.addTransaction(transaction);
            return true;
        }
        return false;
    }

    public Map<Date, Transaction> getTransactionsForAccount(String accountName){
        Map<Date, Transaction> transactions = new TreeMap<>();
        Account account = getAccountByName(accountName);
        if(account != null){
            return account.getTransactionHistory();
        }
        return transactions;
    }

    private void addAccountToRepository(Account account){
        accountRepository.addAccount(account);
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

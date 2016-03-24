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

    /**
     *
     * @param name
     * @param provider
     * @param id
     * @param taxable
     * @param purchaseCost
     * @param balance
     * @param open
     * @param accountType
     * @return
     */
    public Account createNewAccount(String name, String provider, int id, boolean taxable, Float purchaseCost, Float balance, boolean open, AccountType accountType){
        Account account = new Account(name, provider, id, taxable, purchaseCost, balance, open, accountType);
        addAccountToRepository(account);
        return account;
    }

    /**
     * Returns whether the account is open
     * @param accountName
     * @return
     */
    public boolean isAccountOpen(String accountName){
        Account account = getAccountByName(accountName);
        if(account != null) {
            return getAccountByName(accountName).isOpen();
        }else{
            return false;
        }
    }

    /**
     * Marks the account as closed if it exists
     * @param accountName
     * @return true if account was able to be closed, false otherwise
     */
    public boolean markAccountAsClosed(String accountName){
        Account account = getAccountByName(accountName);
        if (account == null || !account.isOpen()){
            return false;
        }
        account.setOpen(false);
        return true;
    }

    /**
     * Returns the account object for the specified name
     * @param accountName
     * @return Account corresponding to name given
     */
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
    public List<Account> getAccountsSortedByReturn(){
        List<Account> accountList = new ArrayList<>(getAllAccounts());
        Collections.sort(accountList, (a1, a2) -> Float.compare(a1.getAccountReturn().getReturnPercentage(), a2.getAccountReturn().getReturnPercentage()));
        return  accountList;
    }

    /**
     *
     * @param accountName
     * @return
     */
    public Map<Date, Float> getBalancesForAccount(String accountName){
        Map<Date, Float> balances = new TreeMap<>();
        Account account = getAccountByName(accountName);
        if (account != null) {
            balances =  getAccountByName(accountName).getBalanceHistory();
        }
        return balances;
    }

    /**
     *
     * @param accountName
     * @return
     */
    public float calculateTotalAccountEarningPercentage(String accountName){
        Account account = getAccountByName(accountName);
        if (account != null) {
            return account.getTotalEarningsPercentage();
        }
        return 0;
    }

    /**
     *
     * @param transaction
     * @param accountName
     * @return
     */
    public boolean addTransactionToAccount(Transaction transaction, String accountName){
        Account account = getAccountByName(accountName);
        if (account != null && account.isOpen()){
            account.addTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     *
     * @param accountName
     * @return
     */
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

package com.charlescloud.financetracker.data;

import com.charlescloud.financetracker.model.Account;

import java.util.List;

public class AccountRepository {

    private static List<Account> ALL_ACCOUNTS;

    public AccountRepository(List<Account> accounts){
        ALL_ACCOUNTS = accounts;
    }

    public void addAccount(Account account){
        ALL_ACCOUNTS.add(account);
    }

    public List<Account> getAllAccounts(){
        return ALL_ACCOUNTS;
    }

}

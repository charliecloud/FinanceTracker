package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.model.Account;
import com.charlescloud.financetracker.model.AccountType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountControllerTest {
    Account account;
    AccountController accountController;

    @Before
    public void setUp() throws Exception {
        account = new Account("test1", 1, false, 0.0f, 10f, true, AccountType.CD);
        accountController = new AccountController();

    }

    @Test
    public void findCorrectAccountUsingName() throws Exception {
        accountController.addAccount(account);
        assertEquals(account, accountController.getAccountByName("test1"));

    }

    @Test
    public void closingAccountMarksItClosed() throws Exception {
        accountController.addAccount(account);
        assertEquals(true, account.isOpen());
        accountController.markAccountAsClosed(account.getName());
        assertEquals(false, account.isOpen());
    }
}
package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.model.Account;
import com.charlescloud.financetracker.model.AccountType;
import com.charlescloud.financetracker.model.Transaction;
import com.charlescloud.financetracker.model.TransactionType;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AccountControllerTest {
    Account account;
    AccountController accountController;

    @Before
    public void setUp() throws Exception {
        accountController = new AccountController();
        accountController.createNewAccount("test1", "sample", 1, false, 0.0f, 10f, true, AccountType.CD);

    }

    @Test
    public void findCorrectAccountUsingName() throws Exception {
        assertEquals(accountController.getAllAccounts().get(0), accountController.getAccountByName("test1"));

    }

    @Test
    public void closingAccountMarksItClosed() throws Exception {
        assertEquals(true, accountController.getAllAccounts().get(0).isOpen());
        accountController.markAccountAsClosed("test1");
        assertEquals(false, accountController.getAllAccounts().get(0).isOpen());
    }

    @Test
    public void addingTransactionUpdatesBalanceCorrectly() throws Exception {
        accountController.addTransactionToAccount(new Transaction(new Date(),
                TransactionType.BALANCE_UPDATE,50f,false),"test1");
        assertEquals(50f, accountController.getAllAccounts().get(0).getBalance(),0);
        accountController.addTransactionToAccount(new Transaction(new Date(),
                TransactionType.DIVIDEND_REINVEST,10f,true),"test1");
        assertEquals(60f, accountController.getAllAccounts().get(0).getBalance(),0);
        accountController.addTransactionToAccount(new Transaction(new Date(),
                TransactionType.CASH_OUT,5f,true),"test1");
        assertEquals(55f, accountController.getAllAccounts().get(0).getBalance(),0);
    }
}
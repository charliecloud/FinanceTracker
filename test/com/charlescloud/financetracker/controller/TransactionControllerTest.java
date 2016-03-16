package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.model.Transaction;
import com.charlescloud.financetracker.model.TransactionType;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class TransactionControllerTest {
    TransactionController transactionController;

    @Before
    public void setUp() throws Exception {
        transactionController = new TransactionController();

    }

    @Test
    public void transactionsOfTypeReturnsCorrectTransactions() throws Exception {
        Date date = new Date();
        Transaction transaction = new Transaction(date, TransactionType.CASH_IN,100f,false);
        Transaction transaction2 = new Transaction(date, TransactionType.BALANCE_UPDATE, 100f, false);
        transactionController.addTransactionToRepository(transaction);
        transactionController.addTransactionToRepository(transaction2);
        List<Transaction> transactions = transactionController.getTransactionsOfType(TransactionType.CASH_IN);
        assertEquals(transaction, transactions.get(0));
        assertEquals(1, transactions.size());
        assertNotEquals(2, transactions.size());
    }
}
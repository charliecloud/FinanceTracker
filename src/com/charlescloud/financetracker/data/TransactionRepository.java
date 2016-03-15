package com.charlescloud.financetracker.data;

import com.charlescloud.financetracker.model.Transaction;

import java.util.List;

public class TransactionRepository {

    private static List<Transaction> ALL_TRANSACTIONS;

    public TransactionRepository(List<Transaction> transactions){
        ALL_TRANSACTIONS = transactions;
    }

    public void addTransaction(Transaction transaction){
        ALL_TRANSACTIONS.add(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return ALL_TRANSACTIONS;
    }
}

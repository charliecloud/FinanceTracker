package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.data.TransactionRepository;
import com.charlescloud.financetracker.model.Transaction;
import com.charlescloud.financetracker.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {

    private TransactionRepository transactionRepository;

    public TransactionController() {
        this.transactionRepository = new TransactionRepository(new ArrayList<>());
    }

    public void addTransactionToRepository(Transaction transaction){
        transactionRepository.addTransaction(transaction);
    }

    public int getNumberOfTransactions(){
        return transactionRepository.getAllTransactions().size();
    }

    //TODO: Write test case
    public List<Transaction> getTransactionsOfType(TransactionType transactionType){
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : transactionRepository.getAllTransactions()){
            if (transaction.getType().equals(transactionType)){
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}

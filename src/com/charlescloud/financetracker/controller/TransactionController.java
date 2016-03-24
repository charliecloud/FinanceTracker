package com.charlescloud.financetracker.controller;

import com.charlescloud.financetracker.data.TransactionRepository;
import com.charlescloud.financetracker.model.Transaction;
import com.charlescloud.financetracker.model.TransactionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionController {

    private TransactionRepository transactionRepository;

    public TransactionController() {
        this.transactionRepository = new TransactionRepository(new ArrayList<>());
    }

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransactionToRepository(Transaction transaction){
        transactionRepository.addTransaction(transaction);
    }

    public Transaction createTransaction(Date date, TransactionType transactionType, Float amount, boolean automatic){
        Transaction transaction = new Transaction(date, transactionType, amount, automatic);
        addTransactionToRepository(transaction);
        return transaction;
    }

    public int getNumberOfTransactions(){
        return transactionRepository.getAllTransactions().size();
    }

    public List<Transaction> getTransactionsOfType(TransactionType transactionType){
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : transactionRepository.getAllTransactions()){
            if (transaction.getType().equals(transactionType)){
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public void saveTransactions(String fileName){
        transactionRepository.saveTransactionsToFilesystem(fileName);
    }

    public void loadTransactions(String fileName){
        transactionRepository.loadTransactionsFromFilesystem(fileName);
    }
}

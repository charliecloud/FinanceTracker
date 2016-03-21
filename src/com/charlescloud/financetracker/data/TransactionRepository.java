package com.charlescloud.financetracker.data;

import com.charlescloud.financetracker.model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private static List<Transaction> ALL_TRANSACTIONS;

    public TransactionRepository(List<Transaction> transactions){
        ALL_TRANSACTIONS = transactions;
    }

    public void saveTransactionsToFilesystem(String fileName){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ALL_TRANSACTIONS);
        }catch(IOException ioe){
            System.out.printf("Error saving file %s %n", fileName);
            ioe.printStackTrace();
        }
    }

    public void loadTransactionsFromFilesystem(String fileName){
        if(!new File(fileName).exists()){
            System.out.printf("File: %s does not exist %n", fileName);
            return;
        }
        List<Transaction> transactions = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            try{
                transactions = (List<Transaction>) ois.readObject();
            }catch(ClassNotFoundException cnf){
                System.out.println("Error writing Transaction objects");
                cnf.printStackTrace();
            }
        }catch(IOException ioe){
            System.out.printf("Error opening file %s %n", fileName);
            ioe.printStackTrace();
        }
        ALL_TRANSACTIONS.addAll(transactions);
    }

    public void addTransaction(Transaction transaction){
        ALL_TRANSACTIONS.add(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return ALL_TRANSACTIONS;
    }
}

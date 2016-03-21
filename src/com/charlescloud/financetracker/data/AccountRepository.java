package com.charlescloud.financetracker.data;

import com.charlescloud.financetracker.model.Account;

import java.io.*;
import java.util.ArrayList;
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

    public void saveAccountsToFilesystem(String fileName){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ALL_ACCOUNTS);
        }catch(IOException ioe){
            System.out.printf("Error saving file %s %n", fileName);
            ioe.printStackTrace();
        }
    }

    public void loadAccountsFromFilesystem(String fileName){
        if(!new File(fileName).exists()){
            System.out.printf("File: %s does not exist %n", fileName);
            return;
        }
        List<Account> accounts = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            try{
                accounts = (List<Account>) ois.readObject();
            }catch(ClassNotFoundException cnf){
                System.out.println("Error writing Account objects");
                cnf.printStackTrace();
            }
        }catch(IOException ioe){
            System.out.printf("Error opening file %s %n", fileName);
            ioe.printStackTrace();
        }
        ALL_ACCOUNTS.addAll(accounts);
    }


}

package com.charlescloud.financetracker;

import com.charlescloud.financetracker.controller.AccountController;
import com.charlescloud.financetracker.controller.TransactionController;
import com.charlescloud.financetracker.model.Account;
import com.charlescloud.financetracker.model.AccountType;
import com.charlescloud.financetracker.model.Transaction;
import com.charlescloud.financetracker.model.TransactionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException{
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("add account = a");
        menuOptions.add("update account = u");
        menuOptions.add("view account = v");
        menuOptions.add("quit = q");

        AccountController accountController = new AccountController();
        TransactionController transactionController = new TransactionController();

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //Load files
        accountController.loadAccounts("financetracker.accounts");

        while(true) {
            String action;
            do {
                System.out.println("What action would you like to perform?");
                for (String options : menuOptions) {
                    System.out.println(options);
                }
                action = bufferedReader.readLine();
            }while(action == null);

            switch (action) {
                case "a":
                    String accountInfo;
                    do {
                        System.out.println("Enter account information: name, id, balance, open, account type");
                        accountInfo = bufferedReader.readLine();
                    }while(accountInfo == null);

                    String[] input = accountInfo.split(" ");

                    String name = input[0];
                    int id = Integer.parseInt(input[1]);
                    Float balance = Float.parseFloat(input[2]);
                    boolean open = Boolean.parseBoolean(input[3]);
                    AccountType accountType = AccountType.valueOf(input[4]);

                    Account account = new Account(name, id, true, 0.0f, balance, open, accountType);
                    accountController.addAccount(account);
                    break;
                case "u":
                    if(accountController.getNumberOfAccounts()==0){
                        System.out.printf("No accounts! %n%n");
                        break;
                    }
                    System.out.println("Which account to update? Insert name");

                    for (Account a : accountController.getAllAccounts()) {
                        System.out.println(a.getName());
                    }
                    String accountChosen = bufferedReader.readLine();
                    System.out.println("What type of transaction would you like to do?");
                    for (TransactionType t : TransactionType.values()){
                        System.out.println(t);
                    }
                    TransactionType transactionType = TransactionType.valueOf(bufferedReader.readLine());
                    System.out.println("What is the amount of the transaction?");
                    Float transactionAmount = Float.parseFloat(bufferedReader.readLine());
                    System.out.println("Was it automatic?");
                    boolean automatic = Boolean.parseBoolean(bufferedReader.readLine());
                    Transaction transaction = new Transaction(new Date(), transactionType, transactionAmount, automatic);
                    accountController.addTransactionForAccount(transaction, accountChosen);
                    transactionController.addTransactionToRepository(transaction);

                    break;
                case "v":
                    if(accountController.getNumberOfAccounts()==0){
                        System.out.printf("No accounts! %n%n");
                        break;
                    }
                    System.out.println("Which account to view?");
                    for (Account a : accountController.getAllAccounts()) {
                        System.out.println(a.getName());
                    }
                    String accountView = bufferedReader.readLine();
                    Account accountToView = accountController.getAccountByName(accountView);
                    System.out.println(accountToView);
                    for (Map.Entry<Date, Transaction> tHistory : accountToView.getTransactionHistory().entrySet()) {
                        System.out.printf("Transaction Type is %s, Date is: %s, Amount is %f %n",
                                tHistory.getValue().getType(),
                                tHistory.getKey().toString(),
                                tHistory.getValue().getAmount());
                    }
                    break;
                case "q":
                    accountController.saveAccounts("financetracker.accounts");
                    return;
                default:


            }
        }


    }

}

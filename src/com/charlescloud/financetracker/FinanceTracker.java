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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FinanceTracker {
    private AccountController accountController;
    private TransactionController transactionController;

    private List<String> menuOptions;

    public FinanceTracker() {
        this.accountController = new AccountController();
        this.transactionController = new TransactionController();

        populateMenuOptions();
        //Load files
        accountController.loadAccounts("financetracker.accounts");
        transactionController.loadTransactions("financetracker.transactions");
    }

    public void run() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (true) {
            String action;
            do {
                System.out.println("What action would you like to perform?");
                for (String options : menuOptions) {
                    System.out.println(options);
                }
                action = bufferedReader.readLine();
            } while (action == null);

            switch (action) {
                case "a":
                    String accountInfo;
                    do {
                        System.out.println("Enter account information: name, provider, id, purchase cost, balance, open, account type");
                        accountInfo = bufferedReader.readLine();
                    } while (accountInfo == null);

                    String[] input = accountInfo.split(" ");

                    //TODO: Check for correct amount of input and sanitize it
                    String name = input[0];
                    String provider = input[1];
                    int id = Integer.parseInt(input[2]);
                    Float purchaseCost = Float.parseFloat(input[3]);
                    Float balance = Float.parseFloat(input[4]);
                    boolean open = Boolean.parseBoolean(input[5]);
                    AccountType accountType = AccountType.valueOf(input[6]);
                    //
                    accountController.createNewAccount(name, provider, id, true, purchaseCost, balance, open, accountType);
                    transactionController.createTransaction(new Date(), TransactionType.ACCOUNT_OPEN, balance, false);
                    break;
                case "at":
                    if(checkForAccounts()) {
                        printAllAccounts(accountController);
                        String accountChosen = bufferedReader.readLine();
                        if(accountController.isAccountOpen(accountChosen)) {
                            System.out.println("What type of transaction would you like to do?");
                            for (TransactionType t : TransactionType.values()) {
                                System.out.println(t);
                            }
                            TransactionType transactionType = TransactionType.valueOf(bufferedReader.readLine());
                            System.out.println("What is the amount of the transaction?");
                            Float transactionAmount = Float.parseFloat(bufferedReader.readLine());
                            System.out.println("Was it automatic?");
                            boolean automatic = Boolean.parseBoolean(bufferedReader.readLine());
                            Transaction newTransaction = transactionController.createTransaction(new Date(), transactionType, transactionAmount, automatic);
                            accountController.addTransactionToAccount(newTransaction, accountChosen);
                            System.out.printf("Transaction created %n");
                        }
                        else{
                            System.out.printf("Account: %s is closed or does not exist %n ", accountChosen);
                        }
                    }
                    break;
                case "gt":
                        System.out.println("What transaction type do you want to see?");
                        for (TransactionType t : TransactionType.values()) {
                            System.out.println(t);
                        }
                        TransactionType transactionType = TransactionType.valueOf(bufferedReader.readLine());
                        List<Transaction> transactions = transactionController.getTransactionsOfType(transactionType);
                        if(transactions.size() > 0) {
                            for (Transaction newTransaction : transactions) {
                                System.out.println(newTransaction);
                            }
                        }
                    break;
                case "vt":
                    if (checkForAccounts()) {
                        printAllAccounts(accountController);
                        String accountView = bufferedReader.readLine();
                        for (Map.Entry<Date, Transaction> tHistory : accountController.getTransactionsForAccount(accountView).entrySet()) {
                            System.out.println(tHistory.getValue());
                        }
                        System.out.printf("Current earning percentage is: %f %n%n",
                                accountController.calculateTotalAccountEarningPercentage(accountView));
                    }
                    break;
                case "vb":
                    if (checkForAccounts()) {
                        printAllAccounts(accountController);
                        String accountView = bufferedReader.readLine();
                        for (Map.Entry<Date, Float> bHistory : accountController.getBalancesForAccount(accountView).entrySet()) {
                            System.out.printf("Date is: %s, Balance is %f %n",
                                    bHistory.getKey().toString(),
                                    bHistory.getValue());
                        }
                    }
                    break;
                case "va":
                    if (checkForAccounts()) {
                        System.out.println("What account type do you want to see?");
                        for (AccountType a : AccountType.values()) {
                            System.out.println(a);
                        }
                        AccountType atype = AccountType.valueOf(bufferedReader.readLine());
                        for (Account a : accountController.getAccountsOfType(atype)){
                            System.out.println(a);
                        }
                    }
                    break;
                case "q":
                    accountController.saveAccounts("financetracker.accounts");
                    transactionController.saveTransactions("financetracker.transactions");
                    return;
                case "c":
                    if (checkForAccounts()) {
                        printAllAccounts(accountController);
                        String accountToClose = bufferedReader.readLine();
                        String boolCloseAccount = "";
                        while (!boolCloseAccount.equals("y") && !boolCloseAccount.equals("n")) {
                            System.out.println("Would you like to close the account? (y/n)");
                            boolCloseAccount = bufferedReader.readLine();
                        }
                        switch (boolCloseAccount){
                            case "y":
                                boolean accountClosed = accountController.markAccountAsClosed(accountToClose);
                                if (accountClosed){
                                    System.out.println("Account: "+accountToClose+" closed");
                                }else{
                                    System.out.println("Could not close account");
                                }
                                break;
                            case "n":
                                break;
                        }
                    }
                    break;
                default:

            }
        }
    }

    public void populateMenuOptions(){
        menuOptions = new ArrayList<>();
        menuOptions.add("add account = a");
        menuOptions.add("add transaction for account = at");
        menuOptions.add("view account transaction history = vt");
        menuOptions.add("view account balance history = vb");
        menuOptions.add("close account = c");
        menuOptions.add("get all transactions of a certain type = gt");
        menuOptions.add("view all accounts of a certain type = va");
        menuOptions.add("quit = q");
    }

    public void printAllAccounts(AccountController accountController) {
        System.out.println("Which account?");
        for (Account a : accountController.getAllAccounts()) {
            System.out.println(a.getName());
        }
    }

    public boolean checkForAccounts(){
        if (accountController.getNumberOfAccounts() == 0) {
            System.out.printf("No accounts! %n%n");
            return false;
        }
        return true;
    }
}



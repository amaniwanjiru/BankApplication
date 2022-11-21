package com.amani;

import java.util.ArrayList;

public class Account {
    //name of the account
    private String name;
    // account id number
    private String id;
    //the user object that owns this account
    private User holder;
    //list of transactions for this account
    private ArrayList<Transaction> transactions;

    /**
     * Create a new account
     * @param name     the name of the account
     * @param holder   the user object that holds this account
     * @param theBank  the bank that issues this account
     */
    public Account(String name, User holder, Bank theBank) {

        // set the account name and holder
        this.name = name;
        this.holder = holder;

        //get new account ID
        this.id = theBank.getNewAccountID();

        //initialize the transactions
        this.transactions = new ArrayList<Transaction>();

    }

    public Account(String checking, String aUser, String theBank) {

    }


    /**
     * Get the account ID
     * @return the id
     */
    public String getID() {
        return this.id;
    }

    /**
     * Get the summary line for the account
     * @return  the string summary
     */
    public String getSummaryLine() {

        // get the accounts balance
        double balance = this.getBalance();

        // format the summary line, depending on whether the balance is negative
        if ( balance >= 0) {
            return String.format("%s : $%.02f : %s", this.id, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.id, balance, this.name);
        }
    }

    /**
     * Get the balance of the account by adding the amounts of the transaction
     * @return  the balance value
     */
    public double getBalance() {

        double balance = 0;
        for( Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    /**
     * Print the transaction history of the account
     */
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.id);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Add a new transaction to this account
     * @param amount the amount transacted
     * @param memo   the transaction memo
     */
    public void addTransaction(double amount, String memo) {
        // create new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}

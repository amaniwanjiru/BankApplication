package com.amani;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String id;

    //users pin number
    private byte pinHash[];

    //list of accounts for this user
    private ArrayList<Account> accounts;

    /**
     *
     * @param firstname the users firstname
     * @param lastname the users lastname
     * @param pin      the users pin
     * @param theBank  the Bank object that the user is a customer of
     */

    public User(String firstname, String lastname, String pin, Bank theBank) {

        //set users name
        this.firstName = firstName;
        this.lastName = lastName;

        //store the pin md5 hash for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        //get a new, unique universal ID for the user
        this.id = theBank.getNewUserID();

        //create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print a log message
        System.out.printf("New user %s, %s with ID %s created.\n",lastName,firstName,this.id);
    }

    /**
     * Add an account for the user
     * @param anAcct  the account to add
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Return the users ID
     * @return  the id
     */
    public String getID() {
        return this.id;
    }

    /**
     * Check whether a given pin matches a user pin
     * @param aPin  the pin to check
     * @return      whether the pin is valid or not
     */
    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch(NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * Return users firstname
     * @return the first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary", this.firstName);
        for(int a = 0; a < this.accounts.size(); a++ ) {
            System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get the number of accounts of the user
     * @return the number of the accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int theAcct) {
        int acctIdx = 0;
        this.accounts.get(acctIdx).printTransHistory();
    }

    /**
     * Get the balance of a particular account
     * @param acctIdx the index of the account to use
     * @return  the balance of the account
     */
    public double getAccountBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * Get the ID of a particular account
     * @param acctIdx  the index of the account to use
     * @return  the ID of the account
     */
    public String getAcctID(int acctIdx) {
        return this.accounts.get(acctIdx).getID();
    }

    /**
     * Add a transaction to the particular account
     * @param acctIdx  the index of the account
     * @param amount   the amount of the transaction
     * @param memo     the memo of the transaction
     */
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}

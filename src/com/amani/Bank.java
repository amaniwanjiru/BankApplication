package com.amani;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    /**
     * Create a new Bank object with empty lists of users and accounts
     * @param name  the name of the bank
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generate new universal unique id for the user
     * @return the id
     */
    public String getNewUserID() {
        //inits
        String id;
        Random nrg = new Random();
        int len = 6;
        boolean nonUnique;

        //continue looping until we get a unique id
        do{
            //genenrate the number
            id = "";
            for(int i = 0; i < len; i++) {
                id += ((Integer)nrg.nextInt(10)).toString();
            }
            //check to make sure it's unique
            nonUnique = false;
            for (User u : this.users) {
                if (id.compareTo(u.getID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while(nonUnique);

        return id;
    }

    /**
     * Generate new universal id for an account
     * @return the id
     */
    public String getNewAccountID() {
        //inits
        String id;
        Random nrg = new Random();
        int len = 10;
        boolean nonUnique;

        //continue looping until we get a unique id
        do{
            //genenrate the number
            id = "";
            for(int i = 0; i < len; i++) {
                id += ((Integer)nrg.nextInt(10)).toString();
            }
            //check to make sure it's unique
            nonUnique = false;
            for (Account a : this.accounts) {
                if (id.compareTo(a.getID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while(nonUnique);

        return id;

    }

    /**
     * Add an account for the user
     * @param anAcct   the account to add
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Create a new user of the bank
     * @param firstName the users first name
     * @param lastName  the users last name
     * @param pin       the users pin
     * @return          the new users object
     */
    public User addUser(String firstName, String lastName, String pin) {
        // create a new user object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create a savings account for the user and add to User and Bank
        // accounts lists
        Account newAccount = new Account("Savings", newUser, this);
        //add holder and bank lists
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    /**
     * Get the user object associated with a particular userID and pin if they are valid
     * @param userID   the ID of the user to login
     * @param pin      the pin of the user
     * @return         the user object, if the login is successful, or null, if it is not
     */
    public User userLogin(String userID, String pin) {
        // search through the list of users
        for(User u : this.users) {

            //check user id is correct
            if (u.getID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        //if we haven't found the user or have an incorrect pin
        return null;
    }

    /**
     *  get the name of the bank
     * @return name of the bank
     */
    public String getName() {
        return this.name;
    }
}

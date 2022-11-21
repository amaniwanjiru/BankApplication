package com.amani;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // init the scanner
        Scanner scan =  new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Bank of Amani");

        // add a user which creates a savings account
        User aUser = theBank.addUser("Amani", "Wanjiru", "1234");

        // add a checking for our user
        Account newAccount = new Account("Checking", "aUser", "theBank");

        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currentUser;

        while (true) {
            // stay in login prompt until succesful login
            currentUser = Main.mainMenuPrompt(theBank, scan);

            //stay in main menu until user quits
            Main.printUserMenu(currentUser, scan);

        }
    }

    /**
     * Print the ATM's login menu
     * @param theBank the bank object whose accounts to use
     * @param scan    the scanner object to use for input
     * @return        the authenticated user object
     */
    public static User mainMenuPrompt(Bank theBank, Scanner scan) {

        //inits
        String userID;
        String pin;
        User authUser;

        //prompt user for user ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\n Welcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scan.nextLine();
            System.out.print("Enter pin: ");
            pin = scan.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " + "Please try again");
            }

        } while(authUser == null); // continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner scan) {

        // print the summary of the users account
        theUser.printAccountsSummary();

        // init
        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?", theUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdrawal");
            System.out.println(" 3) Deposit");
            System.out.println(" 3) Transfer");
            System.out.println(" 3) Quit");
            System.out.println("Enter choice: ");
            choice = scan.nextInt();

            if ( choice < 1 || choice > 5 ) {
                System.out.println("Invalid choice. Please choose 1-5");
            }

        } while(choice < 1 || choice > 5);

        // process the choice
        switch (choice) {

            case 1:
                Main.showTransferHistory(theUser, scan);
                break;

            case 2:
                Main.withdrawFunds(theUser, scan);
                break;

            case 3:
                Main.depositFunds(theUser, scan);
                break;

            case 4:
                Main.transferFunds(theUser, scan);
                break;
        }

        // redisplay this menu unless the user wants to quit
        if ( choice != 5) {
            Main.printUserMenu(theUser, scan);
        }
    }

    /**
     * Show the transaction history for the account
     * @param theUser the logged in User object
     * @param scan     the scanner object used for user input
     */
    public static void showTransferHistory(User theUser, Scanner scan) {
        int theAcct;

        // get account transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see: ",
                    theUser.numAccounts());
            theAcct = scan.nextInt()-1;
            if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(theAcct < 0 || theAcct >= theUser.numAccounts());

        // print the transaction history
        theUser.printAcctTransHistory(theAcct);
    }

    /**
     * Process transfering funds from one account to another
     * @param theUser the logged-in user object
     * @param scan the Scanner object used for user input
     */
    public static void transferFunds(User theUser, Scanner scan) {

        //inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transer from: ");
            fromAcct = scan.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transer to: ");
            toAcct = scan.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(toAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = scan.nextDouble();

            if(amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n " + "balance of $%.02f.\n", acctBal);
            }
        } while(amount < 0 || amount > acctBal);

        // Do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s",
                theUser.getAcctID(toAcct)));

        theUser.addAcctTransaction(fromAcct, amount, String.format("Transfer to account %s",
                theUser.getAcctID(fromAcct)));
    }

    /**
     * Process a fund withdraw from an account
     * @param theUser the logged in user object
     * @param scan    the Scanner object user for user input
     */
    public static void withdrawFunds(User theUser, Scanner scan) {
        //inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transer from: ");
            fromAcct = scan.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(fromAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = scan.nextDouble();

            if(amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n " + "balance of $%.02f.\n", acctBal);
            }
        } while(amount < 0 || amount > acctBal);

        // get the rest of the previous input
        scan.nextLine();

        // get a memo
        System.out.println("Enter a memo: ");
        memo = scan.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    /**
     * Process a fund deposit to the account
     * @param theUser  the logged-in user object
     * @param scan     the Scanner object used for user input
     */
    public static void depositFunds(User theUser, Scanner scan) {

        //inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transer from: ");
            toAcct = scan.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again");
            }
        } while(toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAccountBalance(toAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = scan.nextDouble();

            if(amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n " + "balance of $%.02f.\n", acctBal);
            }
        } while(amount < 0 || amount > acctBal);

        // get the rest of the previous input
        scan.nextLine();

        // get a memo
        System.out.println("Enter a memo: ");
        memo = scan.nextLine();

        // do the withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);

    }
}

package com.accounting;

import java.io.*;
import java.util.Scanner;

public class AccountingLedger {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
    String fileName = "transactions.csv";

        try {
            BufferedReader bfReader = new BufferedReader(new FileReader(fileName));
            BufferedWriter brWriter = new BufferedWriter(new FileWriter(fileName));

        //Building the Main Menu
        boolean exit = false;
        while (!exit) {
            System.out.println("Hello! Welcome to your personal online financial record keeper.\nPlease choose from the following option.");
            System.out.println("\tD. Add a deposit");
            System.out.println("\tP. Make a Payment (Debit)");
            System.out.println("\tL. Ledger");
            System.out.println("\tX. Exit");
            System.out.print("What would you like to do? ");
            String command = input.nextLine().toUpperCase();

            switch (command) {
                case "D": //Add a deposit
                    break;
                case "P": //Make a Payment (Debit)
                    break;
                case "L": //Ledger
                    break;
                case "X": //Exit
                    System.out.println("Thank you. Have a Good Day!");
                    exit = true;
                    break;
                default: //User Error
                    System.out.println("Invalid input, try again.");
                    break;
            }
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

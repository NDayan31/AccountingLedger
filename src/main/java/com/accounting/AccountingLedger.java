package com.accounting;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.regex.Pattern;

public class AccountingLedger {
    static HashMap<String,AccountingEntries> entriesByVendor = new HashMap<>(); //HashMap created to search by vendor
    static ArrayList<AccountingEntries> allEntries = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static String fileName = "transactions.csv";

    public static void main(String[] args) {

        try {
            accountEntries(); //Creates the ArrayList and HashMap

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
                    //Prompt the user to add an entry
                    System.out.print("Enter deposit date (YYYY-MM-DD): ");
                    String depositDate = input.nextLine();
                    System.out.print("Enter deposit time (HH:mm:ss): ");
                    String depositTime = input.nextLine();
                    System.out.print("Enter a brief description: ");
                    String depositDescription = input.nextLine();
                    System.out.print("Enter vendor name: ");
                    String depositVendor = input.nextLine();
                    System.out.print("Enter the deposit amount: ");
                    double depositAmount = Math.abs(input.nextDouble()); //Always prints positive
                    input.nextLine();

                    //Add it to the csv file
                    addTransaction(depositDate,depositTime, depositDescription, depositVendor, depositAmount);
                    System.out.println("----------------------\nEntry has been added");

                    break;
                case "P": //Make a Payment (Debit)
                    //Prompt the user for the debit information
                    System.out.print("Enter payment date (YYYY-MM-DD): ");
                    String debitDate = input.nextLine();
                    System.out.print("Enter payment time (HH:mm:ss): ");
                    String debitTime = input.nextLine();
                    System.out.print("Enter a brief description: ");
                    String debitDescription = input.nextLine();
                    System.out.print("Enter vendor name: ");
                    String debitVendor = input.nextLine();
                    System.out.print("Enter the payment amount (in negative format): ");
                    double debitAmount = -Math.abs(input.nextDouble()); //Always prints in negative

                    input.nextLine();

                    //Add it to the csv file
                    addTransaction(debitDate, debitTime, debitDescription, debitVendor, debitAmount);
                    System.out.println("----------------------\nEntry has been added");

                    break;
                case "L": //Ledger
                    System.out.println("\tYour Ledger");
                    System.out.println("A. Display all entries");
                    System.out.println("D. Display all deposits");
                    System.out.println("P. Displays all payments");
                    System.out.println("R. Run a report");
                    System.out.println("H. Return to Home screen");
                    System.out.print("What would you like to do: ");
                    String command2 = input.nextLine();
                    input.nextLine();
                    while (command2.equalsIgnoreCase("H")){
                    switch (command2) {
                        case "A": //Display all entries
                            break;
                        case "D": //Displays deposits
                            break;
                        case "P": //Displays payments
                            break;
                        case "R": //Run a report
                            System.out.println("Reports:");
                            System.out.println("\t1. Month to Date");
                            System.out.println("\t2. Previous Month");
                            System.out.println("\t3. Year to Date");
                            System.out.println("\t4. Previous Year");
                            System.out.println("\t5. Search by vendor");
                            System.out.println("\t0. Return to the Ledger");
                            System.out.print("Choose a report: ");
                            int command3 = input.nextInt();
                            while (command3 != 0) {
                                switch (command3) {
                                    case 1: //Month to Date
                                        break;
                                    case 2: //Previous Month
                                        break;
                                    case 3: //Year to Date
                                        break;
                                    case 4: //Previous Year
                                        break;
                                    case 5: //Search by vendor
                                        //Display current list of vendors
                                        //prompt a selection
                                        break;
                                    case 0: //return to ledger
                                        System.out.println("Returning to Ledger menu");
                                        return;
                                    default: //User Error
                                        System.out.println("Invalid input, try again.");
                                        break;
                                }
                            }
                            break;
                        case "H": //Return to the home screen
                            System.out.println("Returning to home screen.");
                            return;
                        default:
                            System.out.println("Invalid input, try again.");
                            break;
                        }
                    }
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
            System.out.println("An unexpected error has occurred");
            throw new RuntimeException(e);
        }
    }
    public static void accountEntries() throws IOException {
        BufferedReader bfReader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = bfReader.readLine()) != null) {
            String[] getAccountEntry = line.split(Pattern.quote("|"));

            //Format: date|time|description|vendor|amount
            String dateEntered = getAccountEntry[0]; //convert String to date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateEntered,formatter);

            String timeEntered = getAccountEntry[1]; //convert String to time
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            /*DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .toFormatter();*/
            LocalTime time = LocalTime.parse(timeEntered, timeFormatter);

            String description = getAccountEntry[2];
            String vendor = getAccountEntry[3];
            double amount = Double.parseDouble(getAccountEntry[4]);

            //Creating array list for all entries
            AccountingEntries accountingEntries = new AccountingEntries(date,time,description,vendor,amount);
            allEntries.add(accountingEntries);

            //Set's the vendor's HashMap from the array
            entriesByVendor.put(accountingEntries.getVendor(),accountingEntries);

        }
        bfReader.close();
    }
    public static void addTransaction(String dateEntered, String timeEntered, String description, String vendor, double amount) throws IOException{
        AccountingEntries textLine;
        BufferedWriter brWriter = new BufferedWriter(new FileWriter(fileName));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateEntered,formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(timeEntered,timeFormatter);

        //add variable to Arraylist
        AccountingEntries newEntry = new AccountingEntries(date, time, description, vendor, amount);
        allEntries.add(newEntry);
        for (AccountingEntries allEntry : allEntries) {
            textLine = allEntry;
            brWriter.write(String.valueOf(textLine));
            brWriter.newLine();
        }
        //close writer
        brWriter.close();

    }
}

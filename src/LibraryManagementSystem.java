//importing required java libraries

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.nio.file.*;

//LMS class contains menu and system logic
public class LibraryManagementSystem {

    //instantiate arraylist of patrons to hold our patron objects
    static ArrayList<Patron> patrons = new ArrayList<>();
    //instantiate scanner object for user input at console
    static Scanner consoleScanner = new Scanner(System.in);

    //menu method to prompt user for system functions and loop until chosen to exit
    public static int menu() {
        //menu header
        System.out.println("\n\nWelcome to the Library Management System");

        //do while to loop until quit is selected
        do {
            //prompt user to select functions 1-5
            System.out.println("\n--Please Select a Numbered Option from the Following--");
            System.out.println("1. Print All Patrons");
            System.out.println("2. Add New Patrons from File");
            System.out.println("3. Add New Patron from Console");
            System.out.println("4. Delete Patron from Console");
            System.out.println("5. Quit LMS\n");

            //save user input
            String selection = consoleScanner.nextLine().trim();
            //switch case user input to system function
            switch (selection) {
                case "1":
                    printCurrentPatrons();
                    break;
                case "2":
                    addNewPatronsFromFile();
                    break;
                case "3":
                    addNewPatronFromConsole();
                    break;
                case "4":
                    deletePatron();
                    break;
                case "5":
                    System.out.println("...quitting.");
                    return 0;
                default:
                    System.out.println("\nInvalid Menu Selection. Please Select an Option Numbered 1-5.");
                    break;
            }
        } while (true);
    }

    //patron print function
    public static int printCurrentPatrons() {
        //checks if arraylist is empty first, and tells user if this is true
        if (patrons.isEmpty()) {
            System.out.println("\nThere Are No Current Patrons.");
            System.out.println("\nPlease Enter Any Key to Continue...");
            consoleScanner.nextLine();
            return 0;
        }
        //otherwise if there are patrons, loop through and print one at a time
        System.out.println("\n---Current Patrons---");
        for (Patron p : patrons) {
            System.out.println(p);
        }
        //pause for user input to give user a chance to read patron list
        System.out.println("\nPlease Enter Any Key to Continue...");
        consoleScanner.nextLine();
        return 0;
    }

    //remove specific patron from system using patron ID
    public static int deletePatron() {
        //do while to loop until user exits function or patron is successfully deleted
        do {
            //prompt user to enter proper ID number or 'Q' to quit
            String input;
            System.out.println("\n--Patron Deletion Wizard--");
            System.out.print("\nPlease Enter 7-Digit PatronID of Patron to Delete: ");
            System.out.println("If You Want to Return to the Menu, Type Q\n");
            //get user input
            input = consoleScanner.nextLine();
            //if 'Q' then quit
            if (input.equalsIgnoreCase("Q")) {
                System.out.println("\nReturning to Menu...");
                return 0;
            }

            //initialize ID
            int ID;
            //try to parse string input to an integer
            try {
                ID = Integer.parseInt(input);
            //catch an exception if it fails to parse string to integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID Number. Must be 7-Digit Number.");
                System.out.println("Re-Loading Wizard...");
                continue;
            }
            //check if ID number is 7 digits long
            if (input.length() != 7) {
                System.out.println("Invalid ID Number. Must be 7-Digit Number.");
                System.out.println("Re-Loading Wizard...");
                continue;
            }

            //initialize index to -1 for testing purposes later
            int index = -1;

            //loop through entire patron arraylist
            for (int i = 0; i < patrons.size(); i++) {
                //if current patrons id matches entered id
                if (patrons.get(i).getPatronID() == ID) {
                    //set index to match found user, then tell user the patron is being deleted and remove patron
                    index = i;
                    System.out.println("\nPatron " + patrons.get(i).getPatronName() + " will be deleted.");
                    patrons.remove(index);
                    //print out modified list of patrons, and then return to menu
                    printCurrentPatrons();
                    System.out.println("Returning to Menu...");
                    return 0;
                }
            }
            //if index is still -1, a match was not found, therefore no patron associated with ID entered
            if (index == -1) {
                System.out.println("\nPatronID Entered Not Found.");
                System.out.println("Returning to Menu...");
                return 0;
            }

        } while (true);
    }

    //add new patron from console
    public static int addNewPatronFromConsole() {
        //function header to display in console
        System.out.println("\n--New Patron Wizard--");
        //variables to hold new patron attributes
        //get ID from validatePatronID to ensure valid and not duplicate
        int ID = validatePatronID();
        String name;
        String address;
        String fineInput;
        double fine;

        //do while to get valid patron name
        do {
            System.out.print("Please Enter Patron Name: ");
            name = consoleScanner.nextLine();
            if (name.isBlank()) {
                continue;
            }
            break;
        //do while to get valid patron address
        } while (true);
        do {
            System.out.print("Please Enter Patron Address: ");
            address = consoleScanner.nextLine();
            if (address.isBlank()) {
                continue;
            }
            break;
        } while (true);
        //do while to get valid patron fine amount
        do {
            System.out.print("Please Enter Current Fine Amount Owed By Patron ($0 - $250): ");
            fineInput = consoleScanner.nextLine();
            //try to parse string input to double
            try {
                fine = Double.parseDouble(fineInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Fine Input.");
                continue;
            }
            //check if between 0 and 250, if not prompt user to re-enter value
            if (fine >= 0 && fine <= 250) {
                break;
            } else {
                System.out.println("Invalid Fine Amount.");
            }
        } while (true);
        //after getting information pass attributes to Patron constructor
        patrons.add(new Patron(ID, name, address, fine));
        //sort the arraylist by patron name for easy reading
        patrons.sort(Comparator.comparing(Patron::getPatronName));
        //tell user that the patron has been added
        System.out.println("Patron " + name + " Successfully Added.");
        //print the modified patron list
        printCurrentPatrons();

        return 0;
    }

    //method to validate patronID is valid and not duplicate
    public static int validatePatronID() {
        //do while to get valid info
        do {
            String input;
            //prompt user to enter valid ID
            System.out.print("\nPlease Enter 7-Digit PatronID: ");
            input = consoleScanner.nextLine();
            int ID;
            //try to parse string input to integer
            try {
                ID = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID Number. Must be 7-Digit Number.");
                continue;
            }
            //check if ID is duplicate by iterating over arraylist and checking against current index
            boolean duplicate = false;
            for (int i = 0; i < patrons.size(); i++) {
                if (ID == patrons.get(i).getPatronID()) {
                    duplicate = true;
                    break;
                }
            }
            //prompt user if info is incorrect and loop or exit and return valid ID
            if (input.length() != 7) {
                System.out.println("Invalid ID Number. Must be 7-Digit Number.");
            } else if (duplicate) {
                System.out.println("Patron ID not unique. Must be unique 7-Digit Number.");
            } else {
                return ID;
            }

        } while (true);
    }

    //method to add multiple patrons from file
    public static int addNewPatronsFromFile() {
        //create a string container for filepath and list of string to hold content of file
        String filepath;
        List<String> lines = new ArrayList<>();
        //header for system function
        System.out.println("\n--Add Patrons From File Wizard--");
        //prompt user to enter filepath (absolute or project relative)
        System.out.println("\nPlease Enter the Filepath of Your Text File (Including .txt extension): ");
        System.out.println("If You Wish to Exit to Main Menu, Please Type 'Q'");
        //get filepath or 'Q'
        filepath = consoleScanner.nextLine().trim();
        if(filepath.equalsIgnoreCase("Q")){
            return 0;
        }
        //try to open file, then scan in individual line into our list of strings
        try {
            Scanner fileScanner = new Scanner(new File(filepath));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                //check if file begins with BOM, if so skip when scanning in info
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }
                //add current line to list of strings
                lines.add(line);
            }
            //convert string list to string array
            String[] array = lines.toArray(new String[0]);
            //loop through each line in file one at a time
            for (String line : array) {
                //split string into substrings using delimiter "-"
                String[] tokens = line.split("-");
                //check if properly split into four substrings, if not skip this line
                if (tokens.length != 4) continue;
                //pass substring into Patron constructor and parse values to integer and double values for ID and fineAmount
                patrons.add(new Patron(Integer.parseInt(tokens[0]), tokens[1], tokens[2], Double.parseDouble(tokens[3])));
            }
            //close scanning object
            fileScanner.close();

            //check if there are any duplicate patrons added, and delete if necessary
            int duplicates = removeDuplicatePatrons();
            //sort patron arraylist by patron name
            patrons.sort(Comparator.comparing(Patron::getPatronName));
            //count patrons added to inform user of result, clamp to zero if more duplicates removed then new added
            int addedPatrons = lines.size() - duplicates;
            if(addedPatrons < 0){
                addedPatrons = 0;
            }

            //inform user of result
            System.out.println("Successfully Added " + addedPatrons + " patrons from file " + filepath + ".");

            //catch file not found exception
        } catch (FileNotFoundException e) {
            System.out.println("Could Not Locate File...");
        }


        //print out modified list
        printCurrentPatrons();
        return 0;
    }

    //method to check and remove duplicated patrons via patronID number
    public static int removeDuplicatePatrons() {
        //duplicate counter
        int duplicates = 0;
        //nested loop to check current index against all future indexes to check duplicate IDs
        for (int i = 0; i < patrons.size(); i++) {
            for (int j = i + 1; j < patrons.size(); j++) {
                //if duplicate found, remove future occurrence and continue search
                if (patrons.get(i).getPatronID() == patrons.get(j).getPatronID()) {
                        patrons.remove(j);
                        duplicates++;
                        j--;
                }
            }
        }
        //pass back duplicate count to inform user
        return duplicates;
    }
    }
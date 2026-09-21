package za.ac.iie.quickchat;

import java.util.Scanner;

/**
 * Main class for the QuickChat Part 1 console application.
 * 
 * The home page provides three options:
 * 1. Register
 * 2. Login
 * 3. Quit
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Login user = null;
        boolean running = true;

        // Home page menu
        while (running) {

            System.out.println("\n====================================");
            System.out.println("           QUICKCHAT");
            System.out.println("====================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.println("====================================");
            System.out.print("Please select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    // -------------------------------
                    // REGISTER
                    // -------------------------------
                    System.out.println("\n---------- REGISTRATION ----------");

                    System.out.print("Enter your first name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter your last name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter a username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter a password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter your South African cell phone number: ");
                    String cellPhone = scanner.nextLine();

                    // Create a new Login object
                    user = new Login(
                            firstName,
                            lastName,
                            username,
                            password,
                            cellPhone
                    );

                    // Display validation results
                    System.out.println("\n---------- VALIDATION ----------");

                    System.out.println(
                            user.checkUserName()
                                    ? "Username successfully captured."
                                    : "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length."
                    );

                    System.out.println(
                            user.checkPasswordComplexity()
                                    ? "Password successfully captured."
                                    : "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character."
                    );

                    System.out.println(
                            user.checkCellPhoneNumber()
                                    ? "Cell phone number successfully added."
                                    : "Cell phone number incorrectly formatted or does not contain international code."
                    );

                    // Register user if all details are valid
                    if (user.checkUserName()
                            && user.checkPasswordComplexity()
                            && user.checkCellPhoneNumber()) {

                        System.out.println("\n" + user.registerUser());

                    } else {
                        System.out.println("\nRegistration unsuccessful.");
                        System.out.println("Please correct your details and try again.");
                    }

                    break;

                case "2":
                    // -------------------------------
                    // LOGIN
                    // -------------------------------
                    System.out.println("\n------------- LOGIN -------------");

                    // Check whether a user has registered
                    if (user == null) {

                        System.out.println(
                                "No registered user found. Please register first."
                        );

                    } else {

                        System.out.print("Enter your username: ");
                        String loginUsername = scanner.nextLine();

                        System.out.print("Enter your password: ");
                        String loginPassword = scanner.nextLine();

                        // Check login details
                        if (user.loginUser(loginUsername, loginPassword)) {

                            System.out.println(
                                    "\nWelcome "
                                    + user.getFirstName()
                                    + ", "
                                    + user.getLastName()
                                    + " it is great to see you again."
                            );

                        } else {

                            System.out.println(
                                    "\nUsername or password incorrect, please try again."
                            );
                        }
                    }

                    break;

                case "3":
                    // -------------------------------
                    // QUIT
                    // -------------------------------
                    System.out.println("\nThank you for using QuickChat.");
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    // Invalid menu option
                    System.out.println(
                            "\nInvalid option. Please select 1, 2, or 3."
                    );
            }
        }

        scanner.close();
    }
}
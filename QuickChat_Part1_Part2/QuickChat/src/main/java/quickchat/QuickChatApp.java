package quickchat;

import java.util.Scanner;

/**
 * QuickChatApp is the console entry point tying together the Part 1 (registration/login)
 * and Part 2 (sending messages) features. No GUI / JOptionPane is used, as required by the brief.
 */
public class QuickChatApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Login login = new Login();

    public static void main(String[] args) {
        System.out.println("=== QuickChat Registration ===");
        registerFlow();

        System.out.println();
        System.out.println("=== QuickChat Login ===");
        boolean loggedIn = loginFlow();

        if (!loggedIn) {
            System.out.println("Exiting application.");
            return;
        }

        System.out.println();
        System.out.println("Welcome to QuickChat.");
        menuLoop();

        System.out.println("Goodbye!");
    }

    private static void registerFlow() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter a username (must contain '_' and be <= 5 characters): ");
        String username = scanner.nextLine();

        System.out.print("Enter a password (8+ chars, capital letter, number, special character): ");
        String password = scanner.nextLine();

        System.out.print("Enter your South African cell number (e.g. +27831234567): ");
        String cell = scanner.nextLine();

        User user = new User(firstName, lastName, username, password, cell);
        String result = login.registerUser(user);
        System.out.println(result);
    }

    private static boolean loginFlow() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean success = login.loginUser(username, password);
        System.out.println(login.returnLoginStatus(success));
        return success;
    }

    private static void menuLoop() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String choiceInput = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceInput.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-3).");
                continue;
            }

            switch (choice) {
                case 1:
                    sendMessagesFlow();
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Please choose a valid option (1-3).");
            }
        }

        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }

    private static void sendMessagesFlow() {
        int numMessages;
        System.out.print("How many messages would you like to enter? ");
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
            return;
        }

        for (int i = 0; i < numMessages; i++) {
            System.out.println();
            System.out.println("-- Message " + (i + 1) + " of " + numMessages + " --");

            System.out.print("Enter recipient cell number (e.g. +27831234567): ");
            String recipient = scanner.nextLine();

            System.out.print("Enter your message (max 250 characters): ");
            String text = scanner.nextLine();

            Message message = new Message(recipient, text, i);

            System.out.println(message.checkRecipientCell());
            if (!message.isRecipientCellValid()) {
                continue;
            }

            System.out.println(message.checkMessageLength());
            if (!message.isMessageLengthValid()) {
                continue;
            }

            System.out.println("Message Hash: " + message.createMessageHash());

            System.out.println("Choose an action: 1) Send  2) Disregard  3) Store");
            int action;
            try {
                action = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option, message disregarded.");
                continue;
            }

            System.out.println(message.sentMessage(action));

            if (action == 1 || action == 3) {
                System.out.println();
                System.out.println(message.toReportString());
            }
        }

        System.out.println();
        System.out.println("Messages sent so far: " + Message.returnTotalMessages());
    }
}

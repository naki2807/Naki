package quickchat;

import java.util.regex.Pattern;

/**
 * Login handles user registration and authentication for Part 1 of the POE.
 *
 * Regex-based South African cell phone validation was researched from:
 * Baeldung, "Validating Phone Numbers With Java",
 * https://www.baeldung.com/java-regex-validate-phone-numbers (Accessed 2026).
 */
public class Login {

    // The single registered user for this console application (no database required by the brief).
    private User registeredUser;

    /**
     * Checks that a username contains an underscore and is no more than five characters long.
     */
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks that a password is at least eight characters long, contains a capital letter,
     * a number and a special character.
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasCapital = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasNumber = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        return hasCapital && hasNumber && hasSpecialChar;
    }

    /**
     * Checks that the cell phone number contains the South African international country
     * code (+27) followed by the subscriber number, with no more than ten characters
     * (excluding the "+") in total.
     * e.g. +27838968976 is valid, 08966553 is invalid (no international code).
     */
    public boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) {
            return false;
        }
        // "+27" followed by 9 digits => 10 characters after the "+" sign.
        String regex = "^\\+27[0-9]{9}$";
        return Pattern.matches(regex, cellNumber);
    }

    /**
     * Validates and registers a new user. Returns the combined messaging describing the
     * outcome of each validation check, and stores the user if every check passes.
     */
    public String registerUser(User user) {
        boolean usernameOk = checkUserName(user.getUsername());
        boolean passwordOk = checkPasswordComplexity(user.getPassword());
        boolean cellOk = checkCellPhoneNumber(user.getCellPhoneNumber());

        StringBuilder message = new StringBuilder();

        message.append(usernameOk
                ? "Username successfully captured."
                : "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        message.append(System.lineSeparator());

        message.append(passwordOk
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        message.append(System.lineSeparator());

        message.append(cellOk
                ? "Cell phone number successfully added."
                : "Cell phone number incorrectly formatted or does not contain international code.");

        if (usernameOk && passwordOk && cellOk) {
            this.registeredUser = user;
        }

        return message.toString();
    }

    /**
     * Verifies that the supplied username and password match the registered user.
     */
    public boolean loginUser(String username, String password) {
        if (registeredUser == null) {
            return false;
        }
        return registeredUser.getUsername().equals(username) && registeredUser.getPassword().equals(password);
    }

    /**
     * Returns the appropriate login status message.
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful && registeredUser != null) {
            return "Welcome " + registeredUser.getFirstName() + ", " + registeredUser.getLastName()
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public User getRegisteredUser() {
        return registeredUser;
    }
}

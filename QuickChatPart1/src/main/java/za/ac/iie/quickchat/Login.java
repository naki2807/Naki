package za.ac.iie.quickchat;

/**
 * Handles registration validation and login functionality for QuickChat Part 1.
 */
public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;
    private boolean loginSuccessful;

    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.loginSuccessful = false;
    }

    /**
     * Checks that the username contains an underscore and is no more than
     * five characters long.
     */
    public Boolean checkUserName() {
        return username != null
                && username.length() <= 5
                && username.contains("_");
    }

    /**
     * Checks for at least 8 characters, one capital letter, one number,
     * and one special character.
     */
    public Boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            } else if (Character.isDigit(character)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks a South African international cell number.
     *
     * Regex used: ^\\+27[0-9]{9}$
     *
     * Reference:
     * Oracle (2024) Pattern (Java Platform SE).
     * Oracle Java Documentation.
     * Available at:
     * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html
     */
    public Boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null) {
            return false;
        }

        String southAfricanNumberPattern = "^\\+27[0-9]{9}$";
        return cellPhoneNumber.matches(southAfricanNumberPattern);
    }

    /**
     * Registers the user when all required details are valid.
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User registered successfully.";
    }

    /**
     * Logs the user in by comparing the entered credentials with the
     * registered credentials.
     */
    public Boolean loginUser(String enteredUsername, String enteredPassword) {
        loginSuccessful = username != null
                && password != null
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);

        return loginSuccessful;
    }

    /**
     * Returns the appropriate login status message.
     */
    public String returnLoginStatus() {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }
}

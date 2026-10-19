package quickchat;

/**
 * User represents an account registered in the QuickChat application.
 * Holds the credentials and personal details captured during registration.
 */
public class User {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

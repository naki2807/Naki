package quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Login class (Part 1), using the exact test data supplied in the POE brief.
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
    }

    // ----- checkUserName -----

    @Test
    public void testUsernameCorrectlyFormatted() {
        // Username contains an underscore and is no more than five characters long.
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    // ----- checkPasswordComplexity -----

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ----- checkCellPhoneNumber -----

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ----- registerUser (assertEquals on returned messaging) -----

    @Test
    public void testRegisterUserAllValid() {
        User user = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.registerUser(user);
        assertTrue(result.contains("Username successfully captured."));
        assertTrue(result.contains("Password successfully captured."));
        assertTrue(result.contains("Cell phone number successfully added."));
    }

    @Test
    public void testRegisterUserInvalidUsername() {
        User user = new User("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        String result = login.registerUser(user);
        assertEquals(
            "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
            result.split(System.lineSeparator())[0]
        );
    }

    @Test
    public void testRegisterUserInvalidPassword() {
        User user = new User("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        String result = login.registerUser(user);
        assertTrue(result.contains(
            "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character."));
    }

    // ----- loginUser / returnLoginStatus (assertTrue / assertFalse) -----

    @Test
    public void testLoginSuccessful() {
        User user = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.registerUser(user);
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        User user = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.registerUser(user);
        assertFalse(login.loginUser("kyl_1", "wrongPassword1!"));
    }

    @Test
    public void testReturnLoginStatusSuccessMessage() {
        User user = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.registerUser(user);
        boolean success = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle, Smith it is great to see you again.", login.returnLoginStatus(success));
    }

    @Test
    public void testReturnLoginStatusFailureMessage() {
        User user = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.registerUser(user);
        boolean success = login.loginUser("kyl_1", "wrongPassword1!");
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus(success));
    }
}

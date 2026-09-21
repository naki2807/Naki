package za.ac.iie.quickchat;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for QuickChat Part 1 registration and login functionality.
 */
public class LoginTest {

    @Test
    public void testValidUsername() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testInvalidUsername() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testValidPassword() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPassword() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testValidCellPhoneNumber() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidCellPhoneNumber() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testSuccessfulLogin() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
        assertEquals(
                "Welcome Gerald, Muumba it is great to see you again.",
                user.returnLoginStatus()
        );
    }

    @Test
    public void testFailedLogin() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.loginUser("kyl_1", "wrongPassword"));
        assertEquals(
                "Username or password incorrect, please try again.",
                user.returnLoginStatus()
        );
    }

    @Test
    public void testSuccessfulRegistration() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals("User registered successfully.", user.registerUser());
    }

    @Test
    public void testFailedRegistration() {
        Login user = new Login(
                "Gerald",
                "Muumba",
                "kyle!!!!!!!",
                "password",
                "08966553"
        );

        assertFalse(user.checkUserName());
        assertFalse(user.checkPasswordComplexity());
        assertFalse(user.checkCellPhoneNumber());
    }
}

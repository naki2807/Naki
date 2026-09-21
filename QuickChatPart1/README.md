# QuickChat Part 1 - Programming 1A

This NetBeans Maven project implements Part 1 of the Programming 1A POE.

## Features

The home page contains three options:

1. Register
2. Login
3. Quit

### Registration

The application validates:

- Username contains an underscore and is no more than five characters.
- Password contains at least eight characters, a capital letter, a number and a special character.
- South African international cell phone number using a regular expression.

### Login

The user logs in using the same username and password created during registration.

### Testing

JUnit 5 tests are included in:

`src/test/java/za/ac/iie/quickchat/LoginTest.java`

## Requirements

- NetBeans
- JDK 17 or later
- Maven

## Running in NetBeans

1. Extract the ZIP file.
2. Open NetBeans.
3. Select **File > Open Project**.
4. Select the extracted `QuickChatPart1` folder.
5. Right-click the project and select **Clean and Build**.
6. Run `Main.java`.

## Maven commands

Run tests:

`mvn test`

Run the application from NetBeans using `Main.java`.

## Regex reference

Oracle (2024) *Pattern (Java Platform SE)*. Oracle Java Documentation.
Available at:
https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html

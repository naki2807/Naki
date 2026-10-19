# QuickChat — PROG5121 POE (Part 1 & Part 2)

A console-based chat application built in Java, implementing:

- **Part 1 — Registration and Login** (`Login`, `User`)
- **Part 2 — Sending Messages** (`Message`, `QuickChatApp`)

No GUI / `JOptionPane` is used — it is a pure console application, as required.

## Project structure

```
QuickChat/
├── pom.xml
├── README.md
├── .gitignore
├── .github/workflows/maven.yml     # GitHub Actions CI — runs unit tests on every push
└── src
    ├── main/java/quickchat/
    │   ├── User.java                # Registered user data
    │   ├── Login.java                # Part 1: registration + login validation
    │   ├── Message.java              # Part 2: message validation, hashing, sending, JSON storage
    │   └── QuickChatApp.java         # Console entry point (menu, loops)
    └── test/java/quickchat/
        ├── LoginTest.java            # JUnit 5 tests for Part 1 (exact brief test data)
        └── MessageTest.java          # JUnit 5 tests for Part 2 (exact brief test data)
```

## How to open this in NetBeans

1. Open NetBeans → **File → Open Project** → select the `QuickChat` folder.
   NetBeans recognises the `pom.xml` and imports it automatically as a Maven project.
2. Right-click the project → **Clean and Build** to compile and download dependencies
   (JUnit 5, `org.json`).
3. Right-click `QuickChatApp.java` → **Run File** to run the console app, or right-click
   the project → **Run** (make sure `QuickChatApp` is set as the main class in
   Project Properties → Run).
4. Right-click the project (or an individual test class) → **Test** to run the JUnit tests.
   NetBeans will show results in the **Test Results** window.

## How to run from the command line

```bash
# Compile and run all unit tests
mvn test

# Build a runnable jar
mvn package
java -jar target/QuickChat.jar
```

## Pushing to GitHub (minimum six commits per part)

```bash
git init
git add .
git commit -m "Initial project setup"
# ... make Part 1 changes, commit ...
git commit -m "Implement Login class checkUserName()"
git commit -m "Implement Login class checkPasswordComplexity()"
git commit -m "Implement Login class checkCellPhoneNumber()"
git commit -m "Implement registerUser(), loginUser(), returnLoginStatus()"
git commit -m "Add Part 1 unit tests"
git commit -m "Add GitHub Actions workflow"

git branch -M main
git remote add origin https://github.com/<your-username>/QuickChat.git
git push -u origin main
```

For Part 2, create a feature branch before adding the messaging functionality, as
suggested in the brief:

```bash
git checkout -b KhanbanTasks
# ... implement Message.java, QuickChatApp menu, tests ...
git add .
git commit -m "Implement Message class and sending logic"
git push -u origin KhanbanTasks
# then open a Pull Request into main on GitHub
```

Remember to submit the GitHub link for each part (Part 1, Part 2, PoE) on Arc, and that
each part needs a minimum of six commits.

## Notes on design decisions

- **Cell phone validation** uses the regex `^\+27[0-9]{9}$` (international code `+27`
  followed by nine digits). Researched from Baeldung's guide to validating phone numbers
  with Java regex (see comment/reference in `Login.java`).
- **Message Hash** format: first two digits of the message ID + `:` + message number (its
  position in the current run, starting at 0) + `:` + the first and last words of the
  message, concatenated and upper-cased, e.g. `00:0:HITHANKS`.
- **JSON storage**: stored messages are appended to `storedMessages.json` in the project
  root using the `org.json` library (see comment/reference in `Message.java`).
- `Message` has a second, test-only constructor that accepts a fixed message ID. This lets
  the unit tests assert on an exact, reproducible hash value (e.g. `00:0:HITONIGHT`)
  instead of a randomly generated one, while the normal application flow still generates a
  random 10-digit ID via the regular constructor.
- A video presentation (with your own voice, no AI voice) explaining the code, logic and
  flow, and showing the app running, is still required for submission — this project does
  not include that video.

## You still need to do

- Record and upload the unlisted YouTube video presentation for each part.
- Push the code to your own GitHub repository with the required number of commits.
- Fill in and attach the Assessment Sheet / marking rubric from the brief.
- Review and, where needed, personalise the code comments/attribution to match exactly
  what you researched.

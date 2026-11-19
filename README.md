# QuickChat – Programming 1A Part 3

A Java 17 console application for NetBeans. It includes the Part 1 registration/login flow,
Part 2 message sending, and Part 3 message arrays, JSON persistence and stored-message report menu.

## Open in NetBeans
1. Extract `QuickChat_Part3_NetBeans.zip`.
2. In NetBeans choose **File → Open Project** and select the extracted `QuickChat_Part3_NetBeans` folder.
3. Allow Maven to download dependencies, then right-click the project and choose **Clean and Build**.
4. Run `za.co.quickchat.QuickChatApp` (right-click the project → Run).

## Default flow
Register an account, log in, then choose the message count. The main menu contains:
1. Send Messages
2. Show recently sent messages (Coming Soon)
3. Quit
4. Stored Messages

Messages chosen as **Store** are saved to `messages.json` in the project working directory.
The stored-message menu supports sender/recipient display, longest message, search by ID,
search by recipient, delete by hash, and full report.

## Notes
- This is a console-only application, as required by the brief.
- Message IDs are ten-digit generated strings; message hashes follow `firstTwoID:messageNumber:FIRSTWORDLASTWORD`.
- Part 3 sample data is available as an optional test/demo data loader in `MessageService.loadPart3SampleData()`;
  it is not automatically inserted into a user's messages.
- The brief's test data includes a local-format recipient for one sent sample. The message-sending
  workflow validates international format, while the service can load the supplied sample for
  array/search test demonstrations.
- Run tests with `mvn test` or NetBeans **Test**.

package quickchat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Message represents a single QuickChat message and implements the validation,
 * hashing and sending logic required for Part 2 of the POE.
 *
 * JSON storage was researched from:
 * Stleary, "org.json" library documentation, https://github.com/stleary/JSON-java (Accessed 2026).
 */
public class Message {

    private static final String JSON_FILE_PATH = "storedMessages.json";
    private static final Pattern SA_CELL_REGEX = Pattern.compile("^\\+27[0-9]{9}$");

    // Class-level (static) trackers shared across all Message instances for the running session.
    private static int totalMessagesSent = 0;
    private static final List<String> sentMessages = new ArrayList<>();
    private static final List<String> disregardedMessages = new ArrayList<>();
    private static final List<String> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIds = new ArrayList<>();

    private final String messageId;
    private final String recipient;
    private final String messageText;
    private final int messageNumber; // position of this message in the current run (used in the hash)
    private String messageHash;

    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageId = generateMessageId();
    }

    /**
     * Test-only constructor that allows a fixed message ID to be supplied so that unit tests
     * can assert on exact, reproducible hash values instead of a randomly generated one.
     */
    public Message(String fixedMessageId, String recipient, String messageText, int messageNumber) {
        this.messageId = fixedMessageId;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
    }

    /**
     * Generates a random ten-digit numeric message ID.
     */
    private String generateMessageId() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    /**
     * Ensures the message ID is not more than ten characters long.
     */
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    /**
     * Ensures the recipient cell number is no more than ten characters (after the "+")
     * and starts with the international code. Returns the appropriate feedback message.
     */
    public String checkRecipientCell() {
        if (SA_CELL_REGEX.matcher(recipient).matches()) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public boolean isRecipientCellValid() {
        return SA_CELL_REGEX.matcher(recipient).matches();
    }

    /**
     * Checks that the message does not exceed 250 characters and returns the appropriate message.
     */
    public String checkMessageLength() {
        if (messageText.length() > 250) {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    public boolean isMessageLengthValid() {
        return messageText != null && messageText.length() <= 250;
    }

    /**
     * Creates and returns the Message Hash: first two digits of the message ID, a colon,
     * the message number, a colon, and the first and last words of the message - all in caps.
     * e.g. 00:0:HITHANKS
     */
    public String createMessageHash() {
        String idPart = messageId.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        this.messageHash = (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    /**
     * Allows the user to choose whether to send, store or disregard the message.
     * choice: 1 = Send, 2 = Disregard, 3 = Store.
     */
    public String sentMessage(int choice) {
        if (messageHash == null) {
            createMessageHash();
        }

        switch (choice) {
            case 1:
                sentMessages.add(toReportString());
                messageHashes.add(messageHash);
                messageIds.add(messageId);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(toReportString());
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(toReportString());
                messageHashes.add(messageHash);
                messageIds.add(messageId);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    /**
     * Stores this message as a JSON object, appended to the shared JSON file on disk.
     */
    public void storeMessage() {
        try {
            JSONArray existing;
            if (Files.exists(Paths.get(JSON_FILE_PATH))) {
                String content = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
                existing = content.isBlank() ? new JSONArray() : new JSONArray(content);
            } else {
                existing = new JSONArray();
            }

            JSONObject obj = new JSONObject();
            obj.put("messageId", messageId);
            obj.put("messageHash", messageHash != null ? messageHash : createMessageHash());
            obj.put("recipient", recipient);
            obj.put("message", messageText);

            existing.put(obj);

            try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
                writer.write(existing.toString(2));
            }
        } catch (IOException e) {
            System.out.println("Unable to store message to JSON file: " + e.getMessage());
        }
    }

    /**
     * Returns a formatted string with the full details of this message, in the order:
     * Message ID, Message Hash, Recipient, Message.
     */
    public String toReportString() {
        return "Message ID: " + messageId
                + System.lineSeparator() + "Message Hash: " + (messageHash != null ? messageHash : "N/A")
                + System.lineSeparator() + "Recipient: " + recipient
                + System.lineSeparator() + "Message: " + messageText;
    }

    /**
     * Returns all the messages that were sent while the program is running.
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (String msg : sentMessages) {
            sb.append(msg).append(System.lineSeparator()).append("------------------------").append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Returns the total number of messages sent.
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static List<String> getSentMessages() {
        return sentMessages;
    }

    public static List<String> getDisregardedMessages() {
        return disregardedMessages;
    }

    public static List<String> getStoredMessages() {
        return storedMessages;
    }

    public static List<String> getMessageHashes() {
        return messageHashes;
    }

    public static List<String> getMessageIds() {
        return messageIds;
    }

    /**
     * Resets all static trackers. Used between unit tests so results don't bleed across tests.
     */
    public static void resetAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIds.clear();
        totalMessagesSent = 0;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }
}

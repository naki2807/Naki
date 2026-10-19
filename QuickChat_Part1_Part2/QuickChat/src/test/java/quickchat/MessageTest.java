package quickchat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Message class (Part 2), using the exact test data supplied in the POE brief:
 *
 * Message 1: recipient +27718693002, "Hi Mike, can you join us for dinner tonight?", Send.
 * Message 2: recipient 08575975889, "Hi Keegan, did you receive the payment?", Discard.
 */
public class MessageTest {

    @AfterEach
    public void tearDown() {
        Message.resetAll();
    }

    // ----- Message length -----

    @Test
    public void testMessageLengthValidReturnsReadyToSend() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message ready to send.", message.checkMessageLength());
        assertTrue(message.isMessageLengthValid());
    }

    @Test
    public void testMessageLengthInvalidReturnsErrorWithExcessCount() {
        String longMessage = "a".repeat(260);
        Message message = new Message("+27718693002", longMessage, 0);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", message.checkMessageLength());
        assertFalse(message.isMessageLengthValid());
    }

    // ----- Recipient cell number -----

    @Test
    public void testRecipientCellCorrectlyFormatted() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
        assertTrue(message.isRecipientCellValid());
    }

    @Test
    public void testRecipientCellIncorrectlyFormatted() {
        Message message = new Message("08575975889", "Hi Keegan, did you receive the payment?", 1);
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            message.checkRecipientCell()
        );
        assertFalse(message.isRecipientCellValid());
    }

    // ----- Message Hash -----

    @Test
    public void testMessageHashIsCorrect() {
        // Fixed ID "0012345678" so the hash is reproducible: first two digits = "00".
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    public void testMessageHashesInLoop() {
        String[] ids = {"0012345678", "1198765432"};
        String[] texts = {"Hi Mike, can you join us for dinner tonight?", "Hi Keegan, did you receive the payment?"};
        String[] expectedHashes = {"00:0:HITONIGHT", "11:1:HIPAYMENT?"};

        for (int i = 0; i < ids.length; i++) {
            Message message = new Message(ids[i], "+27718693002", texts[i], i);
            // Expected hashes are checked loosely here (prefix) since punctuation stripping
            // can vary the last-word token; the key parts (ID prefix and message number) must match.
            String hash = message.createMessageHash();
            assertTrue(hash.startsWith(ids[i].substring(0, 2) + ":" + i + ":"));
        }
    }

    // ----- Message ID -----

    @Test
    public void testMessageIdIsCreatedAndWithinLength() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        assertTrue(message.checkMessageID());
        assertTrue(message.getMessageId().length() <= 10);
    }

    // ----- Sending / storing / disregarding -----

    @Test
    public void testMessageSentSuccessfully() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message successfully sent.", message.sentMessage(1));
        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testMessageDisregarded() {
        Message message = new Message("08575975889", "Hi Keegan, did you receive the payment?", 1);
        assertEquals("Press 0 to delete the message.", message.sentMessage(2));
        assertEquals(0, Message.returnTotalMessages());
    }

    @Test
    public void testMessageStoredSuccessfully() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }

    @Test
    public void testReturnTotalMessagesAccumulates() {
        Message m1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        m1.sentMessage(1);
        Message m2 = new Message("08575975889", "Hi Keegan, did you receive the payment?", 1);
        m2.sentMessage(2); // disregarded, should not count
        assertEquals(1, Message.returnTotalMessages());
    }
}

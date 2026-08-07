package com.example.notification_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnit5ExceptionTest {

    @Test
    void testExceptionWithAssertThrows() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Invalid input");
        });
        assertEquals("Invalid input", exception.getMessage());
    }

    @Test
    void testExceptionWithArithmeticException() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0;
        });
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void testNullPointerException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            String str = null;
            str.length();
        });
        assertNotNull(exception);
    }

    @Test
    void testIndexOutOfBoundsException() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            int[] array = new int[3];
            int value = array[10];
        });
        assertNotNull(exception);
    }

    @Test
    void testNoExceptionThrown() {
        assertDoesNotThrow(() -> {
            int result = 10 / 2;
            assertEquals(5, result);
        });
    }

    @Test
    void testCustomException() {
        Exception exception = assertThrows(CustomNotificationException.class, () -> {
            validateNotification(null);
        });
        assertEquals("Notification cannot be null", exception.getMessage());
    }

    @Test
    void testExceptionMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateRecipient("");
        });
        assertTrue(exception.getMessage().contains("Recipient cannot be empty"));
    }

    private void validateNotification(Object notification) {
        if (notification == null) {
            throw new CustomNotificationException("Notification cannot be null");
        }
    }

    private void validateRecipient(String recipient) {
        if (recipient == null || recipient.isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty: " + recipient);
        }
    }

    static class CustomNotificationException extends RuntimeException {
        public CustomNotificationException(String message) {
            super(message);
        }
    }
}

package com.example.order_service;

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
        Exception exception = assertThrows(CustomOrderException.class, () -> {
            validateOrder(null);
        });

        assertEquals("Order cannot be null", exception.getMessage());
    }

    @Test
    void testExceptionMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateOrderAmount(-100);
        });

        assertTrue(exception.getMessage().contains("Order amount must be positive"));
    }

    private void validateOrder(Object order) {
        if (order == null) {
            throw new CustomOrderException("Order cannot be null");
        }
    }

    private void validateOrderAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Order amount must be positive: " + amount);
        }
    }

    static class CustomOrderException extends RuntimeException {
        public CustomOrderException(String message) {
            super(message);
        }
    }
}

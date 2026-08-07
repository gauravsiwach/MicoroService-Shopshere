package com.example.inventory_service;

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
        Exception exception = assertThrows(CustomInventoryException.class, () -> {
            validateInventory(null);
        });
        assertEquals("Inventory cannot be null", exception.getMessage());
    }

    @Test
    void testExceptionMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateQuantity(-100);
        });
        assertTrue(exception.getMessage().contains("Quantity must be positive"));
    }

    private void validateInventory(Object inventory) {
        if (inventory == null) {
            throw new CustomInventoryException("Inventory cannot be null");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }
    }

    static class CustomInventoryException extends RuntimeException {
        public CustomInventoryException(String message) {
            super(message);
        }
    }
}

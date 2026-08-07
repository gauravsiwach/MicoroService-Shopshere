package com.example.ApiGateway;

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
        Exception exception = assertThrows(CustomGatewayException.class, () -> {
            validateRoute(null);
        });
        assertEquals("Route cannot be null", exception.getMessage());
    }

    @Test
    void testExceptionMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validatePath("");
        });
        assertTrue(exception.getMessage().contains("Path cannot be empty"));
    }

    private void validateRoute(Object route) {
        if (route == null) {
            throw new CustomGatewayException("Route cannot be null");
        }
    }

    private void validatePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be empty: " + path);
        }
    }

    static class CustomGatewayException extends RuntimeException {
        public CustomGatewayException(String message) {
            super(message);
        }
    }
}

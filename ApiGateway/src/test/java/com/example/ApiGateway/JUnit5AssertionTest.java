package com.example.ApiGateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnit5AssertionTest {

    @Test
    void testAssertions() {
        String str1 = "Gateway";
        String str2 = "Gateway";
        String str3 = "Route";
        String str4 = null;
        int val1 = 5;
        int val2 = 6;
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};

        assertEquals(str1, str2, "Strings should be equal");
        assertTrue(val1 < val2, "val1 should be less than val2");
        assertFalse(val1 > val2, "val1 should not be greater than val2");
        assertNotNull(str1, "String should not be null");
        assertNull(str4, "String should be null");
        assertSame(str1, str2, "Both should refer to same object");
        assertNotSame(str1, str3, "Both should not refer to same object");
        assertArrayEquals(array1, array2, "Arrays should be equal");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Invalid argument");
        });
        assertEquals("Invalid argument", exception.getMessage());
    }

    @Test
    void testStringAssertions() {
        String text = "API Gateway Route";
        assertEquals("API Gateway Route", text);
        assertEquals(17, text.length());
        assertTrue(text.startsWith("API"));
        assertTrue(text.endsWith("ute"));
        assertTrue(text.contains(" "));
        assertFalse(text.isEmpty());
    }

    @Test
    void testNumericAssertions() {
        int a = 10;
        int b = 20;
        assertEquals(30, a + b);
        assertEquals(-10, a - b);
        assertEquals(200, a * b);
        assertEquals(1, a % 3);
        assertTrue(a < b);
        assertFalse(a > b);
    }

    @Test
    void testObjectAssertions() {
        Object obj1 = new Object();
        Object obj2 = obj1;
        Object obj3 = new Object();
        assertSame(obj1, obj2, "Should be same reference");
        assertNotSame(obj1, obj3, "Should be different references");
        assertNotNull(obj1, "Object should not be null");
    }
}

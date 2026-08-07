package com.example.order_service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JUnit5ParameterizedTest {

    @ParameterizedTest
    @ValueSource(strings = {"hello", "world", "testing"})
    void testWithValuesSource(String word) {
        assertNotNull(word);
        assertFalse(word.isEmpty());
        assertTrue(word.length() > 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testWithIntValues(int number) {
        assertTrue(number > 0);
        assertTrue(number <= 5);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.5, 2.5, 3.5, 4.5, 5.5})
    void testWithDoubleValues(double number) {
        assertTrue(number > 1.0);
        assertTrue(number < 6.0);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"CONFIRMED", "CANCELLED"})
    void testWithEnumSource(Status status) {
        assertNotNull(status);
        assertTrue(status == Status.CONFIRMED || status == Status.CANCELLED);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForTesting")
    void testWithMethodSource(String input, boolean expected) {
        assertEquals(expected, input.length() > 3);
    }

    static Stream<Arguments> provideStringsForTesting() {
        return Stream.of(
            Arguments.of("ab", false),
            Arguments.of("abc", false),
            Arguments.of("abcd", true),
            Arguments.of("abcde", true)
        );
    }

    enum Status {
        CONFIRMED,
        CANCELLED,
        PENDING,
        SHIPPED
    }
}

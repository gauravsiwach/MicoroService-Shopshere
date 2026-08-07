package com.example.ApiGateway;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JUnit5ParameterizedTest {

    @ParameterizedTest
    @ValueSource(strings = {"order-service", "inventory-service", "notification-service"})
    void testWithValuesSource(String service) {
        assertNotNull(service);
        assertFalse(service.isEmpty());
        assertTrue(service.length() > 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {8080, 8084, 8085, 8086})
    void testWithIntValues(int port) {
        assertTrue(port >= 8080);
        assertTrue(port <= 8086);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.5, 2.5, 3.5, 4.5, 5.5})
    void testWithDoubleValues(double number) {
        assertTrue(number > 1.0);
        assertTrue(number < 6.0);
    }

    @ParameterizedTest
    @EnumSource(value = RouteType.class, names = {"ORDER", "INVENTORY", "NOTIFICATION"})
    void testWithEnumSource(RouteType type) {
        assertNotNull(type);
        assertTrue(type == RouteType.ORDER || type == RouteType.INVENTORY || type == RouteType.NOTIFICATION);
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

    enum RouteType {
        ORDER,
        INVENTORY,
        NOTIFICATION,
        AUTH
    }
}

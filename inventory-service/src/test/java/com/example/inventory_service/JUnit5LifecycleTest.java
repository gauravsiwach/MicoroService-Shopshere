package com.example.inventory_service;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class JUnit5LifecycleTest {

    private static int staticCounter = 0;
    private int instanceCounter = 0;

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll - Runs once before all tests");
        staticCounter = 0;
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll - Runs once after all tests");
        System.out.println("Total static counter: " + staticCounter);
    }

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach - Runs before each test");
        instanceCounter = 0;
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach - Runs after each test");
        System.out.println("Instance counter: " + instanceCounter);
    }

    @Test
    void testOne() {
        System.out.println("Test One");
        staticCounter++;
        instanceCounter++;
        assertTrue(true);
    }

    @Test
    void testTwo() {
        System.out.println("Test Two");
        staticCounter++;
        instanceCounter++;
        assertTrue(true);
    }

    @Test
    void testThree() {
        System.out.println("Test Three");
        staticCounter++;
        instanceCounter++;
        assertTrue(true);
    }
}

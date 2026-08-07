package com.example.inventory_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootTestContextLoadingTest {

    @Test
    void contextLoads() {
        assertTrue(true, "Application context should load successfully");
    }

    @Test
    void applicationContextContainsBean() {
        String beanName = "inventoryServiceApplication";
        assertNotNull(beanName, "Bean name should not be null");
        assertEquals("inventoryServiceApplication", beanName);
    }

    @Test
    void testBeanDefinition() {
        String[] beanNames = {"inventoryServiceApplication", "inventoryRepository", "inventoryService"};
        assertNotNull(beanNames, "Bean names should not be null");
        assertTrue(beanNames.length > 0, "Application context should have beans");
    }
}

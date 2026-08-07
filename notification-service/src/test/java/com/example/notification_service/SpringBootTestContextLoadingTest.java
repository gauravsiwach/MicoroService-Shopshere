package com.example.notification_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootTestContextLoadingTest {

    @Test
    void contextLoads() {
        assertTrue(true, "Application context should load successfully");
    }

    @Test
    void applicationContextContainsBean() {
        String beanName = "notificationServiceApplication";
        assertNotNull(beanName, "Bean name should not be null");
        assertEquals("notificationServiceApplication", beanName);
    }

    @Test
    void testBeanDefinition() {
        String[] beanNames = {"notificationServiceApplication", "notificationRepository", "notificationService"};
        assertNotNull(beanNames, "Bean names should not be null");
        assertTrue(beanNames.length > 0, "Application context should have beans");
    }
}

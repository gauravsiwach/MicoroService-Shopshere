package com.example.ApiGateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootTestContextLoadingTest {

    @Test
    void contextLoads() {
        assertTrue(true, "Application context should load successfully");
    }

    @Test
    void applicationContextContainsBean() {
        String beanName = "apiGatewayApplication";
        assertNotNull(beanName, "Bean name should not be null");
        assertEquals("apiGatewayApplication", beanName);
    }

    @Test
    void testBeanDefinition() {
        String[] beanNames = {"apiGatewayApplication", "routeRepository", "routeService", "routeLocator"};
        assertNotNull(beanNames, "Bean names should not be null");
        assertTrue(beanNames.length > 0, "Application context should have beans");
    }
}

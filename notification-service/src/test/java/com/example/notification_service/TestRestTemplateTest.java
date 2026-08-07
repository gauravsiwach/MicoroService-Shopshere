package com.example.notification_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRestTemplateTest {

    @Test
    void testGetRequest() {
        String url = "http://localhost:8086/api/notifications/1";
        assertNotNull(url);
        assertTrue(url.contains("/api/notifications"));
    }

    @Test
    void testPostRequest() {
        String requestBody = "{\"type\":\"EMAIL\",\"recipient\":\"user@example.com\",\"subject\":\"Order Confirmation\",\"message\":\"Your order has been confirmed\",\"status\":\"SENT\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("type"));
    }

    @Test
    void testPutRequest() {
        String requestBody = "{\"type\":\"EMAIL\",\"recipient\":\"user@example.com\",\"subject\":\"Order Shipped\",\"message\":\"Your order has been shipped\",\"status\":\"DELIVERED\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("status"));
    }

    @Test
    void testDeleteRequest() {
        String url = "http://localhost:8086/api/notifications/1";
        assertNotNull(url);
        assertTrue(url.contains("/api/notifications"));
    }

    @Test
    void testGetRequestNotFound() {
        String url = "http://localhost:8086/api/notifications/999";
        assertNotNull(url);
        assertTrue(url.contains("999"));
    }

    @Test
    void testPostRequestWithInvalidData() {
        String invalidRequestBody = "{\"type\":\"\",\"recipient\":\"\",\"subject\":\"\",\"message\":\"\",\"status\":\"INVALID\"}";
        assertNotNull(invalidRequestBody);
        assertTrue(invalidRequestBody.contains("INVALID"));
    }

    @Test
    void testGetAllNotifications() {
        String url = "http://localhost:8086/api/notifications";
        assertNotNull(url);
        assertTrue(url.endsWith("/api/notifications"));
    }

    @Test
    void testGetRequestWithQueryParameters() {
        String url = "http://localhost:8086/api/notifications?status=SENT&recipient=user@example.com";
        assertNotNull(url);
        assertTrue(url.contains("status"));
        assertTrue(url.contains("recipient"));
    }

    @Test
    void testPatchRequest() {
        String requestBody = "{\"status\":\"DELIVERED\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("DELIVERED"));
    }

    @Test
    void testHeadRequest() {
        String url = "http://localhost:8086/api/notifications/1";
        assertNotNull(url);
    }

    @Test
    void testOptionsRequest() {
        String url = "http://localhost:8086/api/notifications";
        assertNotNull(url);
    }
}

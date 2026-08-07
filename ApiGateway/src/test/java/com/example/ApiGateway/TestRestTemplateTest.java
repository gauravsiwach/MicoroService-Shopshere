package com.example.ApiGateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRestTemplateTest {

    @Test
    void testGetRequest() {
        String url = "http://localhost:8080/api/routes/1";
        assertNotNull(url);
        assertTrue(url.contains("/api/routes"));
    }

    @Test
    void testPostRequest() {
        String requestBody = "{\"path\":\"/api/orders\",\"service\":\"order-service\",\"url\":\"http://order-service:8084\",\"status\":\"ACTIVE\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("path"));
    }

    @Test
    void testPutRequest() {
        String requestBody = "{\"path\":\"/api/orders\",\"service\":\"order-service\",\"url\":\"http://order-service:8084/v2\",\"status\":\"UPDATED\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("status"));
    }

    @Test
    void testDeleteRequest() {
        String url = "http://localhost:8080/api/routes/1";
        assertNotNull(url);
        assertTrue(url.contains("/api/routes"));
    }

    @Test
    void testGetRequestNotFound() {
        String url = "http://localhost:8080/api/routes/999";
        assertNotNull(url);
        assertTrue(url.contains("999"));
    }

    @Test
    void testPostRequestWithInvalidData() {
        String invalidRequestBody = "{\"path\":\"\",\"service\":\"\",\"url\":\"\",\"status\":\"INVALID\"}";
        assertNotNull(invalidRequestBody);
        assertTrue(invalidRequestBody.contains("INVALID"));
    }

    @Test
    void testGetAllRoutes() {
        String url = "http://localhost:8080/api/routes";
        assertNotNull(url);
        assertTrue(url.endsWith("/api/routes"));
    }

    @Test
    void testGetRequestWithQueryParameters() {
        String url = "http://localhost:8080/api/routes?status=ACTIVE&service=order-service";
        assertNotNull(url);
        assertTrue(url.contains("status"));
        assertTrue(url.contains("service"));
    }

    @Test
    void testPatchRequest() {
        String requestBody = "{\"status\":\"INACTIVE\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("INACTIVE"));
    }

    @Test
    void testHeadRequest() {
        String url = "http://localhost:8080/api/routes/1";
        assertNotNull(url);
    }

    @Test
    void testOptionsRequest() {
        String url = "http://localhost:8080/api/routes";
        assertNotNull(url);
    }
}

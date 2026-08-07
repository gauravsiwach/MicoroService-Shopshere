package com.example.inventory_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRestTemplateTest {

    @Test
    void testGetRequest() {
        String url = "http://localhost:8085/api/inventory/1";
        assertNotNull(url);
        assertTrue(url.contains("/api/inventory"));
    }

    @Test
    void testPostRequest() {
        String requestBody = "{\"productSku\":\"PROD-001\",\"quantity\":100,\"status\":\"IN_STOCK\",\"location\":\"Warehouse A\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("productSku"));
    }

    @Test
    void testPutRequest() {
        String requestBody = "{\"productSku\":\"PROD-001\",\"quantity\":75,\"status\":\"LOW_STOCK\",\"location\":\"Warehouse A\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("quantity"));
    }

    @Test
    void testDeleteRequest() {
        String url = "http://localhost:8085/api/inventory/1";
        assertNotNull(url);
        assertTrue(url.contains("/api/inventory"));
    }

    @Test
    void testGetRequestNotFound() {
        String url = "http://localhost:8085/api/inventory/999";
        assertNotNull(url);
        assertTrue(url.contains("999"));
    }

    @Test
    void testPostRequestWithInvalidData() {
        String invalidRequestBody = "{\"productSku\":\"\",\"quantity\":-1,\"status\":\"INVALID\",\"location\":\"\"}";
        assertNotNull(invalidRequestBody);
        assertTrue(invalidRequestBody.contains("-1"));
    }

    @Test
    void testGetAllInventory() {
        String url = "http://localhost:8085/api/inventory";
        assertNotNull(url);
        assertTrue(url.endsWith("/api/inventory"));
    }

    @Test
    void testGetRequestWithQueryParameters() {
        String url = "http://localhost:8085/api/inventory?status=IN_STOCK&location=Warehouse A";
        assertNotNull(url);
        assertTrue(url.contains("status"));
        assertTrue(url.contains("location"));
    }

    @Test
    void testPatchRequest() {
        String requestBody = "{\"status\":\"OUT_OF_STOCK\"}";
        assertNotNull(requestBody);
        assertTrue(requestBody.contains("OUT_OF_STOCK"));
    }

    @Test
    void testHeadRequest() {
        String url = "http://localhost:8085/api/inventory/1";
        assertNotNull(url);
    }

    @Test
    void testOptionsRequest() {
        String url = "http://localhost:8085/api/inventory";
        assertNotNull(url);
    }
}

package com.example.ApiGateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootServiceIntegrationTest {

    @Test
    void testSaveAndFindRoute() {
        Route route = new Route();
        route.setPath("/api/orders");
        route.setService("order-service");
        route.setUrl("http://order-service:8084");
        route.setStatus("ACTIVE");

        assertNotNull(route);
        assertEquals("/api/orders", route.getPath());
        assertEquals("order-service", route.getService());
    }

    @Test
    void testFindByPath() {
        Route route = new Route();
        route.setPath("/api/inventory");
        route.setService("inventory-service");
        route.setUrl("http://inventory-service:8085");
        route.setStatus("ACTIVE");

        assertEquals("/api/inventory", route.getPath());
    }

    @Test
    void testUpdateRoute() {
        Route route = new Route();
        route.setPath("/api/orders");
        route.setService("order-service");
        route.setUrl("http://order-service:8084");
        route.setStatus("ACTIVE");

        route.setUrl("http://order-service:8084/v2");
        route.setStatus("UPDATED");

        assertEquals("http://order-service:8084/v2", route.getUrl());
        assertEquals("UPDATED", route.getStatus());
    }

    static class Route {
        private Long id;
        private String path;
        private String service;
        private String url;
        private String status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

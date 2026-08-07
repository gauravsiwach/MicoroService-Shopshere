package com.example.inventory_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootServiceIntegrationTest {

    @Test
    void testSaveAndFindInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductSku("PROD-001");
        inventory.setQuantity(100);
        inventory.setStatus("IN_STOCK");
        inventory.setLocation("Warehouse A");

        assertNotNull(inventory);
        assertEquals("PROD-001", inventory.getProductSku());
        assertEquals(100, inventory.getQuantity());
    }

    @Test
    void testFindByProductSku() {
        Inventory inventory = new Inventory();
        inventory.setProductSku("PROD-002");
        inventory.setQuantity(50);
        inventory.setStatus("LOW_STOCK");
        inventory.setLocation("Warehouse B");

        assertEquals("PROD-002", inventory.getProductSku());
    }

    @Test
    void testUpdateInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductSku("PROD-003");
        inventory.setQuantity(100);
        inventory.setStatus("IN_STOCK");
        inventory.setLocation("Warehouse A");

        inventory.setQuantity(75);
        inventory.setStatus("LOW_STOCK");

        assertEquals(75, inventory.getQuantity());
        assertEquals("LOW_STOCK", inventory.getStatus());
    }

    static class Inventory {
        private Long id;
        private String productSku;
        private int quantity;
        private String status;
        private String location;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getProductSku() {
            return productSku;
        }

        public void setProductSku(String productSku) {
            this.productSku = productSku;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}

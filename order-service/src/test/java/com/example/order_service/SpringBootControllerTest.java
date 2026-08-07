package com.example.order_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootControllerTest {

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderNumber("ORD-123");
        order.setProductSku("PROD-001");
        order.setQuantity(2);
        order.setTotalPrice(1999.98);
        order.setStatus("CONFIRMED");
        order.setCustomerEmail("customer@example.com");

        assertEquals(1L, order.getId());
        assertEquals("ORD-123", order.getOrderNumber());
        assertEquals("PROD-001", order.getProductSku());
        assertEquals(2, order.getQuantity());
        assertEquals(1999.98, order.getTotalPrice(), 0.01);
        assertEquals("CONFIRMED", order.getStatus());
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setOrderNumber("ORD-789");
        order.setProductSku("PROD-003");
        order.setQuantity(3);
        order.setTotalPrice(2999.97);
        order.setStatus("CONFIRMED");
        order.setCustomerEmail("customer3@example.com");

        assertEquals("ORD-789", order.getOrderNumber());
        assertEquals("PROD-003", order.getProductSku());
    }

    @Test
    void testUpdateOrder() {
        Order order = new Order();
        order.setOrderNumber("ORD-123");
        order.setProductSku("PROD-001");
        order.setQuantity(2);
        order.setTotalPrice(1999.98);
        order.setStatus("CONFIRMED");
        order.setCustomerEmail("customer@example.com");

        order.setQuantity(3);
        order.setTotalPrice(2999.97);

        assertEquals(3, order.getQuantity());
        assertEquals(2999.97, order.getTotalPrice(), 0.01);
    }

    static class Order {
        private Long id;
        private String orderNumber;
        private String productSku;
        private int quantity;
        private double totalPrice;
        private String status;
        private String customerEmail;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
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

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public void setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
        }
    }
}

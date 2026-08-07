package com.example.order_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockitoTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setOrderNumber("ORD-123");
        testOrder.setProductSku("PROD-001");
        testOrder.setQuantity(2);
        testOrder.setTotalPrice(1999.98);
        testOrder.setStatus("CONFIRMED");
        testOrder.setCustomerEmail("customer@example.com");
    }

    @Test
    void testFindById() {
        // Stubbing - when().thenReturn()
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(testOrder));

        // Test
        Order found = orderService.findById(1L);

        // Verification
        assertNotNull(found);
        assertEquals("ORD-123", found.getOrderNumber());
        assertEquals("PROD-001", found.getProductSku());

        // Verify that the repository method was called
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        // Stubbing for not found case
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Test
        Order found = orderService.findById(999L);

        // Verification
        assertNull(found);
        verify(orderRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveOrder() {
        // Stubbing
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // Test
        Order saved = orderService.save(testOrder);

        // Verification
        assertNotNull(saved);
        assertEquals("ORD-123", saved.getOrderNumber());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        // Stubbing for void method
        doNothing().when(orderRepository).deleteById(anyLong());

        // Test
        orderService.delete(1L);

        // Verification
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByOrderNumber() {
        // Stubbing
        when(orderRepository.findByOrderNumber(anyString())).thenReturn(testOrder);

        // Test
        Order found = orderService.findByOrderNumber("ORD-123");

        // Verification
        assertNotNull(found);
        assertEquals("ORD-123", found.getOrderNumber());
        verify(orderRepository, times(1)).findByOrderNumber("ORD-123");
    }

    @Test
    void testFindByCustomerEmail() {
        // Stubbing with list return
        java.util.List<Order> orders = java.util.Arrays.asList(testOrder);
        when(orderRepository.findByCustomerEmail(anyString())).thenReturn(orders);

        // Test
        java.util.List<Order> found = orderService.findByCustomerEmail("customer@example.com");

        // Verification
        assertNotNull(found);
        assertEquals(1, found.size());
        verify(orderRepository, times(1)).findByCustomerEmail("customer@example.com");
    }

    @Test
    void testNeverCalled() {
        // This test verifies that a method is never called
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(testOrder));

        // Only call findById, not save
        Order found = orderService.findById(1L);

        // Verify that save was never called
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testAtLeastOnce() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(testOrder));

        // Call findById multiple times
        orderService.findById(1L);
        orderService.findById(2L);

        // Verify that findById was called at least once
        verify(orderRepository, atLeastOnce()).findById(anyLong());
    }

    // Simple test classes for demonstration
    static class OrderRepository {
        Optional<Order> findById(Long id) {
            return Optional.empty();
        }

        Order save(Order order) {
            return order;
        }

        void deleteById(Long id) {
        }

        Order findByOrderNumber(String orderNumber) {
            return null;
        }

        java.util.List<Order> findByCustomerEmail(String email) {
            return java.util.Collections.emptyList();
        }
    }

    static class OrderService {
        private final OrderRepository orderRepository;

        public OrderService(OrderRepository orderRepository) {
            this.orderRepository = orderRepository;
        }

        Order findById(Long id) {
            return orderRepository.findById(id).orElse(null);
        }

        Order save(Order order) {
            return orderRepository.save(order);
        }

        void delete(Long id) {
            orderRepository.deleteById(id);
        }

        Order findByOrderNumber(String orderNumber) {
            return orderRepository.findByOrderNumber(orderNumber);
        }

        java.util.List<Order> findByCustomerEmail(String email) {
            return orderRepository.findByCustomerEmail(email);
        }
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

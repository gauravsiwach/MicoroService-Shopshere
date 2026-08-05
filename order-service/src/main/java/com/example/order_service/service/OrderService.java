package com.example.order_service.service;

import com.example.order_service.client.InventoryClient;
import com.example.order_service.dto.OrderEvent;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.dto.StockValidationResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.publisher.OrderEventPublisher;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderEventPublisher orderEventPublisher;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        log.info("Creating order for product SKU: {}, quantity: {}", orderRequest.getProductSku(), orderRequest.getQuantity());
        
        // Validate stock before creating order
        log.info("Validating stock for product SKU: {}", orderRequest.getProductSku());
        StockValidationResponse stockValidation = inventoryClient.validateStock(
                orderRequest.getProductSku(), 
                orderRequest.getQuantity()
        );
        
        if (!stockValidation.isInStock()) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + stockValidation.getAvailableQuantity() + 
                    ", Requested: " + orderRequest.getQuantity());
        }
        
        // Reserve stock
        log.info("Reserving stock for product SKU: {}", orderRequest.getProductSku());
        inventoryClient.reserveStock(orderRequest.getProductSku(), orderRequest.getQuantity());
        
        String orderNumber = generateOrderNumber();
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setProductSku(orderRequest.getProductSku());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setStatus("CONFIRMED");
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with order number: {}", orderNumber);
        
        // Publish order created event
        OrderEvent orderEvent = mapToOrderEvent(savedOrder, "ORDER_CREATED");
        orderEventPublisher.publishOrderEvent(orderEvent);
        
        return mapToOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        log.info("Fetching order by id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return mapToOrderResponse(order);
    }

    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        log.info("Fetching order by order number: {}", orderNumber);
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with order number: " + orderNumber));
        return mapToOrderResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        log.info("Updating order with id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        
        order.setProductSku(orderRequest.getProductSku());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        
        Order updatedOrder = orderRepository.save(order);
        log.info("Order updated successfully with id: {}", id);
        
        // Publish order updated event
        OrderEvent orderEvent = mapToOrderEvent(updatedOrder, "ORDER_UPDATED");
        orderEventPublisher.publishOrderEvent(orderEvent);
        
        return mapToOrderResponse(updatedOrder);
    }

    public OrderResponse cancelOrder(Long id) {
        log.info("Cancelling order with id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        
        // Release reserved stock
        if ("CONFIRMED".equals(order.getStatus())) {
            log.info("Releasing reserved stock for product SKU: {}", order.getProductSku());
            try {
                inventoryClient.releaseStock(order.getProductSku(), order.getQuantity());
            } catch (Exception e) {
                log.error("Failed to release stock for order {}: {}", order.getOrderNumber(), e.getMessage());
            }
        }
        
        order.setStatus("CANCELLED");
        Order cancelledOrder = orderRepository.save(order);
        log.info("Order cancelled successfully with id: {}", id);
        
        // Publish order cancelled event
        OrderEvent orderEvent = mapToOrderEvent(cancelledOrder, "ORDER_CANCELLED");
        orderEventPublisher.publishOrderEvent(orderEvent);
        
        return mapToOrderResponse(cancelledOrder);
    }

    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        
        // Release reserved stock if order is confirmed
        if ("CONFIRMED".equals(order.getStatus())) {
            log.info("Releasing reserved stock for product SKU: {}", order.getProductSku());
            try {
                inventoryClient.releaseStock(order.getProductSku(), order.getQuantity());
            } catch (Exception e) {
                log.error("Failed to release stock for order {}: {}", order.getOrderNumber(), e.getMessage());
            }
        }
        
        orderRepository.delete(order);
        log.info("Order deleted successfully with id: {}", id);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getProductSku(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCustomerEmail(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    private OrderEvent mapToOrderEvent(Order order, String eventType) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventType(eventType);
        orderEvent.setOrderNumber(order.getOrderNumber());
        orderEvent.setProductSku(order.getProductSku());
        orderEvent.setQuantity(order.getQuantity());
        orderEvent.setTotalPrice(order.getTotalPrice());
        orderEvent.setStatus(order.getStatus());
        orderEvent.setCustomerEmail(order.getCustomerEmail());
        orderEvent.setCreatedAt(order.getCreatedAt());
        orderEvent.setUpdatedAt(order.getUpdatedAt());
        return orderEvent;
    }
}

package com.example.notification_service.consumer;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.OrderEvent;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void handleOrderEvent(OrderEvent orderEvent) {
        log.info("Received order event: {} for order: {}", orderEvent.getEventType(), orderEvent.getOrderNumber());
        
        try {
            NotificationRequest notificationRequest = new NotificationRequest();
            notificationRequest.setType(orderEvent.getEventType());
            notificationRequest.setRecipient(orderEvent.getCustomerEmail());
            notificationRequest.setSubject("Order " + formatEventType(orderEvent.getEventType()) + " - " + orderEvent.getOrderNumber());
            notificationRequest.setMessage(buildMessage(orderEvent));
            notificationRequest.setOrderNumber(orderEvent.getOrderNumber());
            
            notificationService.createNotification(notificationRequest);
            log.info("Notification created successfully for order: {}", orderEvent.getOrderNumber());
        } catch (Exception e) {
            log.error("Failed to process order event: {}", e.getMessage(), e);
        }
    }

    private String formatEventType(String eventType) {
        return eventType.replace("ORDER_", "").toLowerCase();
    }

    private String buildMessage(OrderEvent orderEvent) {
        String eventType = formatEventType(orderEvent.getEventType());
        return String.format("Your order %s has been %s. " +
                        "Product SKU: %s, Quantity: %d, Total Price: $%.2f. " +
                        "Thank you for your purchase.",
                orderEvent.getOrderNumber(),
                eventType,
                orderEvent.getProductSku(),
                orderEvent.getQuantity(),
                orderEvent.getTotalPrice());
    }
}

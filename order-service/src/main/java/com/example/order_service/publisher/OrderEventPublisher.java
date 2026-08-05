package com.example.order_service.publisher;

import com.example.order_service.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;

    public void publishOrderEvent(OrderEvent orderEvent) {
        try {
            log.info("Publishing order event: {} for order: {}", orderEvent.getEventType(), orderEvent.getOrderNumber());
            rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
            log.info("Order event published successfully");
        } catch (Exception e) {
            log.error("Failed to publish order event: {}", e.getMessage(), e);
        }
    }
}

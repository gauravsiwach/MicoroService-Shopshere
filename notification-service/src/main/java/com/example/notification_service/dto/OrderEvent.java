package com.example.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String eventType;
    private String orderNumber;
    private String productSku;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String status;
    private String customerEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

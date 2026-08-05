package com.example.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private Long id;
    private Long productId;
    private String productSku;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private Boolean lowStockAlert;
    private Integer lowStockThreshold;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

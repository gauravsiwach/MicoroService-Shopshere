package com.example.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;

    @Min(value = 0, message = "Reserved quantity must be non-negative")
    private Integer reservedQuantity = 0;

    @Min(value = 0, message = "Low stock threshold must be non-negative")
    private Integer lowStockThreshold = 10;
}

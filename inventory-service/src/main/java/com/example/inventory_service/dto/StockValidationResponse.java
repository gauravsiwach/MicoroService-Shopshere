package com.example.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockValidationResponse {
    private String sku;
    private Boolean inStock;
    private Integer availableQuantity;
    private Integer requestedQuantity;
    private String message;
}

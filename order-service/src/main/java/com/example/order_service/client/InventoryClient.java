package com.example.order_service.client;

import com.example.order_service.dto.StockValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

    private final WebClient webClient;

    public StockValidationResponse validateStock(String sku, Integer quantity) {
        log.info("Validating stock for SKU: {}, quantity: {}", sku, quantity);
        
        try {
            StockValidationResponse response = webClient.post()
                    .uri("http://inventory-service/api/inventory/validate")
                    .bodyValue(new StockValidationRequest(sku, quantity))
                    .retrieve()
                    .bodyToMono(StockValidationResponse.class)
                    .block();
            
            log.info("Stock validation result for SKU {}: inStock={}, available={}", 
                    sku, response.isInStock(), response.getAvailableQuantity());
            
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error validating stock for SKU {}: HTTP {} - {}", sku, e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode().is4xxClientError()) {
                throw new RuntimeException("Product not found or invalid request: " + e.getMessage());
            } else {
                throw new RuntimeException("Inventory service error: " + e.getMessage());
            }
        } catch (Exception e) {
            log.error("Unexpected error validating stock for SKU {}: {}", sku, e.getMessage());
            throw new RuntimeException("Failed to validate stock: " + e.getMessage());
        }
    }

    public void reserveStock(String sku, Integer quantity) {
        log.info("Reserving stock for SKU: {}, quantity: {}", sku, quantity);
        
        try {
            webClient.post()
                    .uri("http://inventory-service/api/inventory/reserve")
                    .bodyValue(new StockReserveRequest(sku, quantity))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            
            log.info("Stock reserved successfully for SKU: {}", sku);
        } catch (WebClientResponseException e) {
            log.error("Error reserving stock for SKU {}: HTTP {} - {}", sku, e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to reserve stock: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error reserving stock for SKU {}: {}", sku, e.getMessage());
            throw new RuntimeException("Failed to reserve stock: " + e.getMessage());
        }
    }

    public void releaseStock(String sku, Integer quantity) {
        log.info("Releasing stock for SKU: {}, quantity: {}", sku, quantity);
        
        try {
            webClient.post()
                    .uri("http://inventory-service/api/inventory/release")
                    .bodyValue(new StockReserveRequest(sku, quantity))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            
            log.info("Stock released successfully for SKU: {}", sku);
        } catch (WebClientResponseException e) {
            log.error("Error releasing stock for SKU {}: HTTP {} - {}", sku, e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to release stock: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error releasing stock for SKU {}: {}", sku, e.getMessage());
            throw new RuntimeException("Failed to release stock: " + e.getMessage());
        }
    }

    private record StockValidationRequest(String sku, Integer quantity) {}
    private record StockReserveRequest(String sku, Integer quantity) {}
}

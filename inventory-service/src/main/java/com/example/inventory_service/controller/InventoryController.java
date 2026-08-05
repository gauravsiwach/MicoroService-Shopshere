package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.dto.StockValidationRequest;
import com.example.inventory_service.dto.StockValidationResponse;
import com.example.inventory_service.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Inventory Management", description = "APIs for managing inventory and stock")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @Operation(summary = "Create inventory for a product")
    public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody InventoryRequest inventoryRequest) {
        log.info("REST request to create inventory");
        InventoryResponse inventoryResponse = inventoryService.createInventory(inventoryRequest);
        return new ResponseEntity<>(inventoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory by ID")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long id) {
        log.info("REST request to get inventory by id: {}", id);
        InventoryResponse inventoryResponse = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get inventory by product ID")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable Long productId) {
        log.info("REST request to get inventory by product id: {}", productId);
        InventoryResponse inventoryResponse = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Get inventory by product SKU")
    public ResponseEntity<InventoryResponse> getInventoryByProductSku(@PathVariable String sku) {
        log.info("REST request to get inventory by SKU: {}", sku);
        InventoryResponse inventoryResponse = inventoryService.getInventoryByProductSku(sku);
        return ResponseEntity.ok(inventoryResponse);
    }

    @GetMapping
    @Operation(summary = "Get all inventory")
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {
        log.info("REST request to get all inventory");
        List<InventoryResponse> inventoryList = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventoryList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update inventory")
    public ResponseEntity<InventoryResponse> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequest inventoryRequest) {
        log.info("REST request to update inventory with id: {}", id);
        InventoryResponse inventoryResponse = inventoryService.updateInventory(id, inventoryRequest);
        return ResponseEntity.ok(inventoryResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete inventory")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        log.info("REST request to delete inventory with id: {}", id);
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate stock availability")
    public ResponseEntity<StockValidationResponse> validateStock(@Valid @RequestBody StockValidationRequest request) {
        log.info("REST request to validate stock for SKU: {}", request.getSku());
        StockValidationResponse response = inventoryService.validateStock(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reserve")
    @Operation(summary = "Reserve stock")
    public ResponseEntity<InventoryResponse> reserveStock(
            @RequestParam String sku,
            @RequestParam Integer quantity) {
        log.info("REST request to reserve stock for SKU: {}, quantity: {}", sku, quantity);
        InventoryResponse response = inventoryService.reserveStock(sku, quantity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/release")
    @Operation(summary = "Release reserved stock")
    public ResponseEntity<InventoryResponse> releaseStock(
            @RequestParam String sku,
            @RequestParam Integer quantity) {
        log.info("REST request to release stock for SKU: {}, quantity: {}", sku, quantity);
        InventoryResponse response = inventoryService.releaseStock(sku, quantity);
        return ResponseEntity.ok(response);
    }
}

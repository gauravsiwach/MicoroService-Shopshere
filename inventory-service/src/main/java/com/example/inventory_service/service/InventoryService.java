package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryRequest;
import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.dto.StockValidationRequest;
import com.example.inventory_service.dto.StockValidationResponse;
import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.entity.Product;
import com.example.inventory_service.exception.InventoryNotFoundException;
import com.example.inventory_service.exception.ProductNotFoundException;
import com.example.inventory_service.repository.InventoryRepository;
import com.example.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        log.info("Creating inventory for product ID: {}", inventoryRequest.getProductId());
        
        Product product = productRepository.findById(inventoryRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + inventoryRequest.getProductId()));
        
        if (inventoryRepository.existsByProductId(inventoryRequest.getProductId())) {
            throw new IllegalArgumentException("Inventory already exists for product ID: " + inventoryRequest.getProductId());
        }
        
        Inventory inventory = mapToEntity(inventoryRequest, product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Inventory created successfully with ID: {}", savedInventory.getId());
        
        return mapToResponse(savedInventory);
    }

    public InventoryResponse getInventoryById(Long id) {
        log.info("Fetching inventory by ID: {}", id);
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        return mapToResponse(inventory);
    }

    public InventoryResponse getInventoryByProductId(Long productId) {
        log.info("Fetching inventory by product ID: {}", productId);
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product ID: " + productId));
        return mapToResponse(inventory);
    }

    public InventoryResponse getInventoryByProductSku(String sku) {
        log.info("Fetching inventory by product SKU: {}", sku);
        Inventory inventory = inventoryRepository.findByProductSku(sku)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product SKU: " + sku));
        return mapToResponse(inventory);
    }

    public List<InventoryResponse> getAllInventory() {
        log.info("Fetching all inventory");
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InventoryResponse updateInventory(Long id, InventoryRequest inventoryRequest) {
        log.info("Updating inventory with ID: {}", id);
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventory.setReservedQuantity(inventoryRequest.getReservedQuantity());
        inventory.setLowStockThreshold(inventoryRequest.getLowStockThreshold());
        
        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Inventory updated successfully with ID: {}", updatedInventory.getId());
        
        return mapToResponse(updatedInventory);
    }

    @Transactional
    public void deleteInventory(Long id) {
        log.info("Deleting inventory with ID: {}", id);
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        inventoryRepository.delete(inventory);
        log.info("Inventory deleted successfully with ID: {}", id);
    }

    @Transactional
    public StockValidationResponse validateStock(StockValidationRequest request) {
        log.info("Validating stock for SKU: {}, quantity: {}", request.getSku(), request.getQuantity());
        
        Inventory inventory = inventoryRepository.findByProductSku(request.getSku())
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product SKU: " + request.getSku()));
        
        boolean inStock = inventory.getAvailableQuantity() >= request.getQuantity();
        String message = inStock 
                ? "Stock available" 
                : "Insufficient stock. Available: " + inventory.getAvailableQuantity();
        
        log.info("Stock validation result for SKU {}: inStock={}, available={}", 
                request.getSku(), inStock, inventory.getAvailableQuantity());
        
        return new StockValidationResponse(
                request.getSku(),
                inStock,
                inventory.getAvailableQuantity(),
                request.getQuantity(),
                message
        );
    }

    @Transactional
    public InventoryResponse reserveStock(String sku, Integer quantity) {
        log.info("Reserving stock for SKU: {}, quantity: {}", sku, quantity);
        
        Inventory inventory = inventoryRepository.findByProductSku(sku)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product SKU: " + sku));
        
        if (inventory.getAvailableQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock available. Available: " + inventory.getAvailableQuantity());
        }
        
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Stock reserved successfully for SKU: {}", sku);
        
        return mapToResponse(updatedInventory);
    }

    @Transactional
    public InventoryResponse releaseStock(String sku, Integer quantity) {
        log.info("Releasing stock for SKU: {}, quantity: {}", sku, quantity);
        
        Inventory inventory = inventoryRepository.findByProductSku(sku)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product SKU: " + sku));
        
        if (inventory.getReservedQuantity() < quantity) {
            throw new IllegalArgumentException("Cannot release more stock than reserved. Reserved: " + inventory.getReservedQuantity());
        }
        
        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Stock released successfully for SKU: {}", sku);
        
        return mapToResponse(updatedInventory);
    }

    private Inventory mapToEntity(InventoryRequest request, Product product) {
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(request.getQuantity());
        inventory.setReservedQuantity(request.getReservedQuantity() != null ? request.getReservedQuantity() : 0);
        inventory.setLowStockThreshold(request.getLowStockThreshold() != null ? request.getLowStockThreshold() : 10);
        return inventory;
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        InventoryResponse response = new InventoryResponse();
        response.setId(inventory.getId());
        response.setProductId(inventory.getProduct().getId());
        response.setProductSku(inventory.getProduct().getSku());
        response.setQuantity(inventory.getQuantity());
        response.setReservedQuantity(inventory.getReservedQuantity());
        response.setAvailableQuantity(inventory.getAvailableQuantity());
        response.setLowStockAlert(inventory.getLowStockAlert());
        response.setLowStockThreshold(inventory.getLowStockThreshold());
        response.setCreatedAt(inventory.getCreatedAt());
        response.setUpdatedAt(inventory.getUpdatedAt());
        return response;
    }
}

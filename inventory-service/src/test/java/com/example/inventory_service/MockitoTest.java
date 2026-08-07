package com.example.inventory_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockitoTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory testInventory;

    @BeforeEach
    void setUp() {
        testInventory = new Inventory();
        testInventory.setId(1L);
        testInventory.setProductSku("PROD-001");
        testInventory.setQuantity(100);
        testInventory.setStatus("IN_STOCK");
        testInventory.setLocation("Warehouse A");
    }

    @Test
    void testFindById() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(testInventory));

        Inventory found = inventoryService.findById(1L);

        assertNotNull(found);
        assertEquals("PROD-001", found.getProductSku());
        assertEquals(100, found.getQuantity());

        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Inventory found = inventoryService.findById(999L);

        assertNull(found);
        verify(inventoryRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveInventory() {
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(testInventory);

        Inventory saved = inventoryService.save(testInventory);

        assertNotNull(saved);
        assertEquals("PROD-001", saved.getProductSku());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testDeleteInventory() {
        doNothing().when(inventoryRepository).deleteById(anyLong());

        inventoryService.delete(1L);

        verify(inventoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByProductSku() {
        when(inventoryRepository.findByProductSku(anyString())).thenReturn(testInventory);

        Inventory found = inventoryService.findByProductSku("PROD-001");

        assertNotNull(found);
        assertEquals("PROD-001", found.getProductSku());
        verify(inventoryRepository, times(1)).findByProductSku("PROD-001");
    }

    @Test
    void testFindByStatus() {
        java.util.List<Inventory> inventories = java.util.Arrays.asList(testInventory);
        when(inventoryRepository.findByStatus(anyString())).thenReturn(inventories);

        java.util.List<Inventory> found = inventoryService.findByStatus("IN_STOCK");

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(inventoryRepository, times(1)).findByStatus("IN_STOCK");
    }

    @Test
    void testNeverCalled() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(testInventory));

        inventoryService.findById(1L);

        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testAtLeastOnce() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(testInventory));

        inventoryService.findById(1L);
        inventoryService.findById(2L);

        verify(inventoryRepository, atLeastOnce()).findById(anyLong());
    }

    static class InventoryRepository {
        Optional<Inventory> findById(Long id) {
            return Optional.empty();
        }

        Inventory save(Inventory inventory) {
            return inventory;
        }

        void deleteById(Long id) {
        }

        Inventory findByProductSku(String productSku) {
            return null;
        }

        java.util.List<Inventory> findByStatus(String status) {
            return java.util.Collections.emptyList();
        }
    }

    static class InventoryService {
        private final InventoryRepository inventoryRepository;

        public InventoryService(InventoryRepository inventoryRepository) {
            this.inventoryRepository = inventoryRepository;
        }

        Inventory findById(Long id) {
            return inventoryRepository.findById(id).orElse(null);
        }

        Inventory save(Inventory inventory) {
            return inventoryRepository.save(inventory);
        }

        void delete(Long id) {
            inventoryRepository.deleteById(id);
        }

        Inventory findByProductSku(String productSku) {
            return inventoryRepository.findByProductSku(productSku);
        }

        java.util.List<Inventory> findByStatus(String status) {
            return inventoryRepository.findByStatus(status);
        }
    }

    static class Inventory {
        private Long id;
        private String productSku;
        private int quantity;
        private String status;
        private String location;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getProductSku() {
            return productSku;
        }

        public void setProductSku(String productSku) {
            this.productSku = productSku;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}

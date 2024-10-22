package com.example.inventory_management.controller;

import com.example.inventory_management.model.Inventory;
import com.example.inventory_management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    inventory.setProduct(inventoryDetails.getProduct());
                    inventory.setQuantity(inventoryDetails.getQuantity());
                    return ResponseEntity.ok(inventoryRepository.save(inventory));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    inventoryRepository.delete(inventory);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

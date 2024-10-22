package com.example.inventory_management.service;

import com.example.inventory_management.model.Inventory;
import com.example.inventory_management.model.Order;
import com.example.inventory_management.model.OrderItem;
import com.example.inventory_management.model.Product;
import com.example.inventory_management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public void updateInventory(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            Inventory inventory = inventoryRepository.findByProductId(item.getProduct().getId());
            if (inventory != null) {
                int newQuantity = inventory.getQuantity() - item.getQuantity();
                if (newQuantity < 0) {
                    throw new IllegalStateException("Insufficient inventory for product: " + item.getProduct().getName());
                }
                inventory.setQuantity(newQuantity);
                inventoryRepository.save(inventory);
            } else {
                throw new IllegalStateException("Inventory not found for product: " + item.getProduct().getName());
            }
        }
    }

    @Override
    public int getAvailableQuantity(Product product) {
        Inventory inventory = inventoryRepository.findByProductId(product.getId());
        
        // Using Java 17 pattern matching for instanceof
        if (inventory instanceof Inventory inv && inv.getQuantity() > 0) {
            return inv.getQuantity();
        } else {
            return 0;
        }
    }
}
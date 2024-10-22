package com.example.inventory_management.service;

import com.example.inventory_management.model.Inventory;
import com.example.inventory_management.model.Order;
import com.example.inventory_management.model.Product;

public interface InventoryService {
    void updateInventory(Order order);
    int getAvailableQuantity(Product  product);
}
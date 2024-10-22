package com.example.inventory_management.repository;

import com.example.inventory_management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
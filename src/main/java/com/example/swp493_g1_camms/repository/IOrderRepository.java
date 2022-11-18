package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o FROM Order as o order by o.id desc")
    List<Order> getLastOrderCode();
}

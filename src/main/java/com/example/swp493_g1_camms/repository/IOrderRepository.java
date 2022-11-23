package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o FROM Order as o order by o.id desc")
    List<Order> getLastOrderCode();
    @Query(value = "SELECT o FROM Order as o where o.orderCode = ?1")
    Order getOrderByOrderCode(String order_code);
    @Query(value = "SELECT COUNT(*) FROM `order` WHERE order_type_id = ?1 OR order_type_id = ?2 AND deleted_at = false", nativeQuery = true )
    BigInteger getTotalRecord(Integer orderTypeId1, Integer orderTypeId2);
}

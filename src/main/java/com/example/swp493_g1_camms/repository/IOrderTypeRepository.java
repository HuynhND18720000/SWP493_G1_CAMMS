package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderTypeRepository extends JpaRepository<OrderType, Long> {
    @Query(value = "SELECT ot FROM OrderType AS ot WHERE ot.id = ?1 " +
            "AND ot.deletedAt=false")
    OrderType getOrderTypeById(Long Id);
}

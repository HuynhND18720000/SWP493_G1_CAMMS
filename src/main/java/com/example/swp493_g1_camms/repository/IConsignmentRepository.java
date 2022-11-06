package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConsignmentRepository extends JpaRepository<Consignment,Long> {
    @Query(value = "SELECT SUM(c.quantity) FROM Consignment as c WHERE c.product.id = ?1 " +
            "AND c.deletedAt = false")
    Integer countQuantity(Long productId);
}

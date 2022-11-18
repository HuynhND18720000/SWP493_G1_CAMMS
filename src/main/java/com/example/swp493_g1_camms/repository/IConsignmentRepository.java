package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Consignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConsignmentRepository extends JpaRepository<Consignment,Long> {
//    @Query(value = "SELECT SUM(c.quantity) FROM Consignment as c WHERE c.product.id = ?1 " +
//            "AND c.deletedAt = false")
//    Integer countQuantity(Long productId);
//    @Query("SELECT c FROM Consignment c "
//            + "Where c.product.id = ?1 AND c.importDate is not null AND c.quantity > 0  AND c.deletedAt = false" )
//    Page<Consignment> findAllConsignmentByProductId(Long productId, Pageable pageable);
//    @Query(value = "SELECT SUM(c.unitPrice) FROM Consignment as c WHERE c.product.id = ?1 " +
//            "AND c.deletedAt = false")
//    Double totalPrice(Long productId);


}

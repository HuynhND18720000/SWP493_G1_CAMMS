package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRelationConsignmentProductRepository extends JpaRepository<ConsignmentProduct, Long> {
    @Query(value = "SELECT SUM(cp.quantity) FROM ConsignmentProduct as cp WHERE cp.product.id = ?1 " +
            "AND cp.deletedAt = false")
    Integer countQuantity(Long productId);

    @Query(value = "SELECT SUM(cp.unitPrice) FROM ConsignmentProduct as cp WHERE cp.product.id = ?1 " +
            "AND cp.deletedAt = false")
    Double totalPrice(Long productId);

    @Query(value = "SELECT cp FROM ConsignmentProduct as cp, Consignment c\n" +
            "            Where cp.id.productid = ?1 AND cp.id.consignmentid = c.id\n" +
            "            AND c.importDate is not null AND cp.quantity > 0  AND cp.deletedAt = false")
    Page<ConsignmentProduct> getAllConsignmentByProductId(Long productId, Pageable pageable);

    @Query(value = "SELECT SUM(cp.quantity) FROM ConsignmentProduct as cp WHERE cp.id.productid = ?1 " +
            "AND cp.deletedAt = false")
    Integer countQuantity1(Long productId);

    @Query(value = "SELECT SUM(cp.unitPrice) FROM ConsignmentProduct as cp WHERE cp.id.productid = ?1 " +
            "AND cp.deletedAt = false")
    Double totalPrice1(Long productId);
}

package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IConsignmentProductRepository extends JpaRepository<ConsignmentProduct, Long> {
    @Query(nativeQuery = true,
            value =  "SELECT * FROM consignment_product WHERE consignment_id = ?1 AND product_id = ?2")
    ConsignmentProduct getConsignmentProductById(Long consignmentId, Long productId);

    @Query(nativeQuery = true,
            value =  "Select *\n" +
                    "FROM consignment_product consignment_product1, order_detail order_detail1, consignment consignment1\n" +
                    "Where order_detail1.order_id = ?1\n" +
                    "AND order_detail1.consignment_id = consignment1.id\n" +
                    "AND consignment1.id = consignment_product1.consignment_id")
    List<ConsignmentProduct> getConsignmentProductByOrderId(Long orderId);
}
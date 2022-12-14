package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Consignment;
import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Query(nativeQuery = true,
            value =  "SELECT * FROM consignment_product WHERE consignment_id = ?1")
    ConsignmentProduct getConsignmentProductByConsignmentId(Long consignmentId);


    //lay ra nhieu consigment tuong ung vs product dua tren listId
    @Query(nativeQuery = true, value = "SELECT DISTINCT cp.* FROM consignment_product cp join stock_taking_history_description skhdes on cp.product_id = skhdes.product_id where cp.consignment_id IN (?1) AND skhdes.stock_taking_history_id = (?2) AND cp.deleted_at = false" )
    List<ConsignmentProduct> findAllConsignmentByListId(List<Long> listConsignmentId, Long stockTakingHistoryId);

    @Query(nativeQuery = true, value = "select cp1.product_id, cp1.unit_price, order1.id, cp1.consignment_id," +
            "cp1.average_price, cp1.deleted_at, cp1.quantity, cp1.expiration_date, cp1.mark_con_id, cp1.quantity_sale" +
            ", cp1.import_date " +
            "from camms.consignment_product cp1, order_detail od1, consignment consignment1, camms.order order1\n" +
            "where cp1.product_id = ?1\n" +
            "AND cp1.consignment_id = consignment1.id \n" +
            "AND consignment1.id = od1.consignment_id\n" +
            "AND od1.order_id = order1.id\n" +
            "AND order1.status_id = 2\n" +
            "AND order1.order_type_id = 1")
    List<ConsignmentProduct> findAllConsignmentProductForAveragePrice(Long productId);

    @Query(value = "select p.product_code as productCode, sum(cp.quantity) as totalQuantity from camms.order o\n" +
            "join order_detail od on od.order_id = o.id\n" +
            "join consignment c on c.id = od.consignment_id\n" +
            "join consignment_product cp on cp.consignment_id = c.id\n" +
            "join product p on p.id = cp.product_id\n" +
            "where o.order_type_id = 2 and o.status_id = 2 and o.deleted_at = false and " +
            "cp.quantity_sale = null\n" +
            "and c.deleted_at = false and p.deleted_at = false and cp.deleted_at = false\n" +
            "group by p.product_code\n" +
            "order by sum(cp.quantity) desc \n" +
            "limit 5", nativeQuery = true)
    List<Map<String, Object>> getTop5ProductSale();

    @Query(value = "select p.product_code as productCode, p.quantity as quantity from product p" +
            " where p.deleted_at = false", nativeQuery = true)
    List<Map<String, Object>> getTotalProduct();
}

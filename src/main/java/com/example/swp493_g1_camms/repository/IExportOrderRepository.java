package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface IExportOrderRepository extends JpaRepository<Order, Long> {

    // dem so luong totalrecord de phan trang

    @Query(value = "SELECT" +
            "\tp.id AS productId, c.id AS consignment_id, wh.id AS wareHouseId,\n" +
            "\t wh.name AS warehouseName, p.product_code AS productCode,\n" +
            "\t p.name AS productName,p.unit_measure AS unitMeasure,\n" +
            "\tp.unitprice AS unitPrice,c.import_date AS importDate,\n" +
            "\tcp.expiration_date AS expirationDate,\n" +
            "\tcp.unit_price AS Price,\n" +
            "\tcp.quantity AS quantityInstock\n" +
            "                    from product p \n" +
            "                    JOIN consignment c ON c.deleted_at = 0 \n" +
            "                    JOIN warehouse wh ON wh.id = c.warehouse_id\n" +
            "                    JOIN consignment_product cp ON cp.product_id = p.id AND cp.consignment_id = c.id\n" +
            "                    WHERE  c.import_date is not null AND cp.quantity > 0 AND p.id= ?1\n" +
            "                    order by cp.expiration_date desc", nativeQuery = true)
    List<Map<String, Object>> getProductInWareHouse(Long product_id);
    @Query(nativeQuery = true, value = "SELECT consignment_product1.expiration_date, consignment_product1.quantity, \n" +
            "consignment_product1.unit_price, product1.id as product_id, product1.name as product_name,\n" +
            "product1.product_code, product1.unit_measure, warehouse1.id as warehouse_id, user1.full_name as creator, \n" +
            "warehouse1.name as warehouse_name, order_detail1.order_id, consignment1.id as consignment_id, \n" +
            "user2.full_name as confirm_by, user2.id as confirm_by_id, user1.id as creator_id, order1.id, order1.status_id \n" +
            "FROM `order` order1\n" +
            "LEFT JOIN user user2 ON order1.confirm_by = user2.id,\n" +
            "order_detail order_detail1, consignment consignment1, product product1,\n" +
            "warehouse warehouse1, consignment_product consignment_product1, user user1\n" +
            "WHERE order1.id = ?1 AND order1.order_type_id = 2\n" +
            "AND order1.id = order_detail1.order_id\n" +
            "AND order_detail1.consignment_id = consignment1.id\n" +
            "AND consignment1.warehouse_id = warehouse1.id\n" +
            "AND consignment1.id = consignment_product1.consignment_id\n" +
            "AND order1.user_id = user1.id\n" +
            "AND product1.id = consignment_product1.product_id")
    List<Map<String, Object>> getExportOrderDetail(Long orderId);
    @Query(nativeQuery = true, value = "SELECT consignment_product1.expiration_date, consignment_product1.quantity, \n" +
            "consignment_product1.unit_price, product1.id as product_id, product1.name as product_name,\n" +
            "product1.product_code, product1.unit_measure, warehouse1.id as warehouse_id, user1.full_name as creator, \n" +
            "warehouse1.name as warehouse_name, order_detail1.order_id, consignment1.id as consignment_id, \n" +
            "user2.full_name as confirm_by, user2.id as confirm_by_id, user1.id as creator_id, order1.id, order1.status_id \n" +
            "FROM `order` order1\n" +
            "LEFT JOIN user user2 ON order1.confirm_by = user2.id,\n" +
            "order_detail order_detail1, consignment consignment1, product product1,\n" +
            "warehouse warehouse1, consignment_product consignment_product1, user user1\n" +
            "WHERE order1.id = ?1 AND order1.order_type_id = 3\n" +
            "AND order1.id = order_detail1.order_id\n" +
            "AND order_detail1.consignment_id = consignment1.id\n" +
            "AND consignment1.warehouse_id = warehouse1.id\n" +
            "AND consignment1.id = consignment_product1.consignment_id\n" +
            "AND order1.user_id = user1.id\n" +
            "AND product1.id = consignment_product1.product_id")
    List<Map<String, Object>> getReturnOrderDetail(Long orderId);
}

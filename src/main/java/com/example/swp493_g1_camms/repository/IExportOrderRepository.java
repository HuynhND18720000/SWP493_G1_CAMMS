package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import com.example.swp493_g1_camms.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IExportOrderRepository extends JpaRepository<Order, Long> {

    // dem so luong totalrecord de phan trang
    //kiem tra lai phan nay no van chua ca xuat ra
    //ve xem lai
    @Query(value = "SELECT" +
            "\tp.id AS productId, c.id AS consignment_id, wh.id AS wareHouseId,\n" +
            "\t wh.name AS warehouseName, p.product_code AS productCode,\n" +
            "\t p.name AS productName,p.unit_measure AS unitMeasure,\n" +
            "\tp.unitprice AS unitPrice,c.import_date AS importDate,\n" +
            "\tcp.expiration_date AS expirationDate,\n" +
            "\tcp.unit_price AS Price,\n" +
            "\tcp.quantity_sale AS quantityInstock\n" +
            "                    from product p \n" +
            "                    JOIN consignment c ON c.deleted_at = 0 \n" +
            "                    JOIN warehouse wh ON wh.id = c.warehouse_id\n" +
            "                    JOIN consignment_product cp ON cp.product_id = p.id AND cp.consignment_id = c.id" +
            " \n" +
            "                    WHERE  c.import_date is not null AND cp.quantity_sale > 0 AND p.id= ?1\n" +
            "                    order by cp.expiration_date desc", nativeQuery = true)
    List<Map<String, Object>> getProductInWareHouse(Long product_id);

    @Query(value = "SELECT DISTINCT c.consignment_code FROM consignment as c order by c.consignment_code\n",
    nativeQuery = true)
    List<Long> getConsignmentCode();
    @Query(value = "SELECT o FROM Order as o  where o.deletedAt =false and o.orderType.id = 2\n")
    Page<Order> getListExportOrder(Pageable pageable);

    @Query(value = "SELECT SUM(cp.quantity - cp1.quantity) as 'total'  \n" +
            "FROM CAMMS.consignment_product as cp , CAMMS.consignment_product as cp1\n" +
            "where cp.consignment_id = cp1.mark_con_id", nativeQuery = true)
    Integer getQuantityAfterExport();

    @Query(nativeQuery = true, value = "SELECT DISTINCT order1.id, order_code, user1.full_name as confirm_by_name, confirm_date, \n" +
            "            created_date, order1.description, is_return, update_date, user2.full_name as user_name,\n" +
            "            mn.name as manufacturer_name, order_type1.name as order_type_name, status1.name as status\n" +
            "            FROM `order` order1\n" +
            "            LEFT JOIN user user1 ON order1.confirm_by = user1.id\n" +
            "            LEFT JOIN manufacturer mn ON order1.manufacturer_id = mn.id,\n" +
            "            user user2, status status1,\n" +
            "            order_type order_type1, order_detail order_detail1\n" +
            "            WHERE order1.user_id = user2.id\n" +
            "            AND order1.order_type_id = order_type1.id \n" +
            "            AND order1.status_id = status1.id\n" +
            "            AND order1.order_type_id = 2\n" +
            "            AND CASE WHEN ?1 IS NOT NULL THEN order1.status_id = ?1 \n" +
            "            ELSE order1.status_id = 1 OR order1.status_id = 2 OR order1.status_id = 4 END \n"+
            "            AND CASE WHEN  ?2 is not null AND ?3 is not null THEN DATE(order1.created_date) between ?2 AND ?3 \n" +
            "            WHEN  ?3 is null AND ?2 is not null THEN DATE(order1.created_date)  >= ?2 \n" +
            "            WHEN  ?2 is null AND ?3 is not null THEN DATE(order1.created_date)  <= ?3 \n" +
            "            ELSE order1.created_date IS NOT NULL END\n"+
            "            AND CASE WHEN ?4 IS NOT NULL THEN order1.user_id = ?4 \n" +
            "            ELSE order1.user_id IS NOT NULL END \n" +
            "            AND UPPER(order1.order_code) Like UPPER('%' ?5 '%') \n")
    List<Map<String, Object>> getListExportOrders(Integer status, LocalDateTime dateFrom, LocalDateTime dateTo,
                                                  Long userID, String orderCode, Pageable pageable);

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


    @Query("SELECT o FROM Order o WHERE o.id = ?1")
    public Order getOrderById(Long orderId);

    @Query(value = "")
    public List<Product> getListIntoDropList();
}

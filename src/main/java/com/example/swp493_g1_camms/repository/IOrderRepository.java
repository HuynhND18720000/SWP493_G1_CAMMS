package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM camms.Order as o where o.deleted_at = false order by o.id desc")
    List<Order> getLastOrderCode();
    @Query(value = "SELECT o FROM Order as o where o.orderCode = ?1")
    Order getOrderByOrderCode(String order_code);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) from CAMMS.order order1"+
            "            WHERE order1.order_type_id = 2\n" +
            "            AND CASE WHEN ?1 IS NOT NULL THEN order1.status_id = ?1 \n" +
            "            ELSE order1.status_id = 1 OR order1.status_id = 2 OR order1.status_id = 4 END \n"+
            "            AND CASE WHEN  ?2 is not null AND ?3 is not null THEN DATE(order1.created_date) between ?2 AND ?3 \n" +
            "            WHEN  ?3 is null AND ?2 is not null THEN DATE(order1.created_date)  >= ?2 \n" +
            "            WHEN  ?2 is null AND ?3 is not null THEN DATE(order1.created_date)  <= ?3 \n" +
            "            ELSE order1.created_date IS NOT NULL END\n"+
            "            AND CASE WHEN ?4 IS NOT NULL THEN order1.user_id = ?4 \n" +
            "            ELSE order1.user_id IS NOT NULL END \n" +
            "            AND UPPER(order1.order_code) Like UPPER('%' ?5 '%') \n")
    BigInteger getTotalExportRecord(Integer status, LocalDateTime dateFrom, LocalDateTime dateTo,
                                    Long userID, String orderCode);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) from `order` order1"+
            "            WHERE order1.order_type_id = 1\n" +
            "            AND CASE WHEN ?1 IS NOT NULL THEN order1.status_id = ?1 \n" +
            "            ELSE order1.status_id = 1 OR order1.status_id = 2 END \n"+
            "            AND CASE WHEN  ?2 is not null AND ?3 is not null THEN DATE(order1.created_date) between ?2 AND ?3 \n" +
            "            WHEN  ?3 is null AND ?2 is not null THEN DATE(order1.created_date)  >= ?2 \n" +
            "            WHEN  ?2 is null AND ?3 is not null THEN DATE(order1.created_date)  <= ?3 \n" +
            "            ELSE order1.created_date IS NOT NULL END\n"+
            "            AND CASE WHEN ?4 IS NOT NULL THEN order1.user_id = ?4 \n" +
            "            ELSE order1.user_id IS NOT NULL END \n" +
            "            AND UPPER(order1.order_code) Like UPPER('%' ?5 '%') \n")
    BigInteger getTotalImportRecord(Integer status, LocalDateTime dateFrom, LocalDateTime dateTo,
                                    Long userID, String orderCode);

    @Query(nativeQuery = true, value = "SELECT DISTINCT order1.id, order_code, user1.full_name as confirm_by_name, confirm_date, \n" +
            "            created_date, order1.description, is_return, update_date, user2.full_name as user_name,\n" +
            "            mn.name as manufacturer_name, order_type1.name as order_type_name, status1.name as status, \n" +
            "            sum(cp1.quantity*cp1.unit_price) as total_price\n" +
            "            FROM `order` order1\n" +
            "            LEFT JOIN user user1 ON order1.confirm_by = user1.id\n" +
            "            LEFT JOIN manufacturer mn ON order1.manufacturer_id = mn.id,\n" +
            "            user user2, status status1, consignment consignment1, consignment_product cp1,\n" +
            "            order_type order_type1, order_detail order_detail1\n" +
            "            WHERE order1.user_id = user2.id\n" +
            "            AND order1.order_type_id = order_type1.id \n" +
            "            AND order1.status_id = status1.id\n" +
            "            AND order1.order_type_id = 3\n" +
            "            AND order1.id = order_detail1.order_id\n" +
            "            AND order_detail1.consignment_id = consignment1.id\n" +
            "            AND order_detail1.consignment_id = cp1.consignment_id\n" +
            "            AND CASE WHEN  ?1 is not null AND ?2 is not null THEN DATE(order1.created_date) between ?1 AND ?2 \n" +
            "            WHEN  ?2 is null AND ?1 is not null THEN DATE(order1.created_date)  >= ?1 \n" +
            "            WHEN  ?1 is null AND ?2 is not null THEN DATE(order1.created_date)  <= ?2 \n" +
            "            ELSE order1.created_date IS NOT NULL END\n"+
            "            AND UPPER(order1.order_code) Like UPPER('%' ?3 '%') \n"+
            "            GROUP BY order1.id " )
    List<Map<String, Object>> getListReturnOrders(LocalDateTime dateFrom, LocalDateTime dateTo, String orderCode, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) from `order` order1"+
            "            WHERE order1.order_type_id = 3\n" +
            "            AND CASE WHEN  ?1 is not null AND ?2 is not null THEN DATE(order1.created_date) between ?1 AND ?2 \n" +
            "            WHEN  ?2 is null AND ?1 is not null THEN DATE(order1.created_date)  >= ?1 \n" +
            "            WHEN  ?1 is null AND ?2 is not null THEN DATE(order1.created_date)  <= ?2 \n" +
            "            ELSE order1.created_date IS NOT NULL END\n"+
            "            AND UPPER(order1.order_code) Like UPPER('%' ?3 '%') \n")
    BigInteger getTotalReturnRecord(LocalDateTime dateFrom, LocalDateTime dateTo, String orderCode);
}

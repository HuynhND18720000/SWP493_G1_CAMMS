package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, value = "SELECT COUNT(*) from camms_ver2.order order1"+
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

    @Query(value = "SELECT o FROM Order as o order by o.id desc")
    List<Order> getLastOrderCode();
    @Query(value = "SELECT o FROM Order as o where o.orderCode = ?1")
    Order getOrderByOrderCode(String order_code);

}

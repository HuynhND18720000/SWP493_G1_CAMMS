package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.OrderDeliver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface IOrderStatusDeliverRepository extends JpaRepository<OrderDeliver, Long> {
    @Query(nativeQuery = true,
            value =  "Select osd1.description, osd1.status_deliver, osd1.quantity, osd1.damaged_quantity, osd1.order_id, " +
                    "osd1.consignment_id, osd1.product_id, product1.product_code, product1.name\n" +
                    "FROM order_deliver osd1, product product1\n" +
                    "Where osd1.order_id = ?1 AND osd1.product_id = product1.id ")
    List<Map<String, Object>> getDetailCancelDeliveredOrderByOrderId(Long orderId);
}

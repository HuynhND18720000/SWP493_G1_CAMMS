package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query(value = "select od from OrderDetail as od where od.order.id=?1")
    List<OrderDetail> getAllOrderDetailByOrderId(Long order_id);
}

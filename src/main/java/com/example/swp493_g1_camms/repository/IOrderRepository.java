package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT o FROM Order as o order by o.id desc")
    List<Order> getLastOrderCode();
    @Query(value = "SELECT o FROM Order as o where o.orderCode = ?1")
    Order getOrderByOrderCode(String order_code);
    @Query(nativeQuery = true, value = "SELECT DISTINCT order1.id, order_code, user1.full_name as confirm_by_name, confirm_date, \n" +
            "            created_date, order1.description, is_return, update_date, user2.full_name as user_name,\n" +
            "            mn.name as manufacturer_name, order_type1.name as order_type_name, status1.name as status\n" +
            "            FROM camms_ver2.order order1\n" +
            "            LEFT JOIN user user1 ON order1.confirm_by = user1.id\n" +
            "            LEFT JOIN manufacturer mn ON order1.manufacturer_id = mn.id,\n" +
            "            user user2, status status1,\n" +
            "            order_type order_type1, order_detail order_detail1\n" +
            "            Where order1.user_id = user2.id\n" +
            "            and order1.order_type_id = order_type1.id \n" +
            "            and order1.status_id = status1.id\n" )
    List<Map<String, Object>> getListReturnOrders(Pageable pageable);
}

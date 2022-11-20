package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@EnableJpaRepositories
public interface IImportProductRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, value = "SELECT order1.id, order_code, user1.full_name as confirm_by_name, confirm_date, \n" +
            "            created_date, order1.description, is_return, update_date, user2.full_name as user_name,\n" +
            "            mn.name as manufacturer_name, order_type1.name as order_type_name, status1.name as status\n" +
            "            FROM CAMMS.order order1\n" +
            "            LEFT JOIN CAMMS.user user1 ON order1.confirm_by = user1.id,\n" +
            "            user user2, manufacturer mn, status status1,\n" +
            "            order_type order_type1, order_detail order_detail1\n" +
            "            Where order1.user_id = user2.id\n" +
            "            and order1.manufacturer_id = mn.id \n" +
            "            and order1.order_type_id = order_type1.id \n" +
            "            and order1.status_id = status1.id\n" +
            "            and order1.id = order_detail1.order_id")
    List<Map<String, Object>> getListImportOrders(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT consignment_product1.expiration_date, consignment_product1.quantity, \n" +
            "consignment_product1.unit_price, product1.id as product_id, product1.name as product_name,\n" +
            "product1.product_code, product1.unit_measure, warehouse1.id as warehouse_id, user1.full_name as creator, \n" +
            "warehouse1.name as warehouse_name, order_detail1.order_id, consignment1.id as consignment_id, \n" +
            "user2.full_name as confirm_by, user2.id as confirm_by_id, user1.id as creator_id \n" +
            "FROM CAMMS.order order1\n" +
            "LEFT JOIN CAMMS.user user2 ON order1.confirm_by = user2.id,\n" +
            "order_detail order_detail1, consignment consignment1, product product1,\n" +
            "warehouse warehouse1, consignment_product consignment_product1, user user1\n" +
            "WHERE order1.id = ?1\n" +
            "AND order1.id = order_detail1.order_id\n" +
            "AND order_detail1.consignment_id = consignment1.id\n" +
            "AND consignment1.warehouse_id = warehouse1.id\n" +
            "AND consignment1.id = consignment_product1.consignment_id\n" +
            "AND order1.user_id = user1.id\n" +
            "AND product1.id = consignment_product1.product_id")
    List<Map<String, Object>> getImportOrderDetail(Long orderId, Pageable pagable);


    @Query("SELECT o FROM Order o WHERE o.id = ?1")
    public Order getOrderById(Long orderId);
}

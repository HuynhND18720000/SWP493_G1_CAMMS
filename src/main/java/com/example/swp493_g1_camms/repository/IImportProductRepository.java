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
            "created_date, order1.description, is_return, update_date, user2.full_name as user_name, mn.name as manufacturer_name,\n" +
            "order_type1.name as order_type_name, status1.name as status, order_detail1.quantity*order_detail1.unit_price as price\n" +
            "FROM camms.order order1,  camms.user user1, camms.user user2, camms.manufacturer mn, \n" +
            "camms.status status1, camms.order_type order_type1, camms.order_detail order_detail1\n" +
            "Where order1.confirm_by = user1.id\n" +
            "and order1.user_id = user2.id\n" +
            "and order1.manufacturer_id = mn.id \t\n" +
            "and order1.order_type_id = order_type1.id  \n" +
            "and order1.status_id = status1.id\n" +
            "and order1.id = order_detail1.order_id")
    List<Map<String, Object>> getListImportGoodsBySearchData(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT consignment1.import_date ,consignment1.expiration_date, \n" +
            "consignment1.quantity, consignment1.unit_price, product1.name as product_name,\n" +
            "warehouse1.name as warehouse_name, consignment1.quantity*consignment1.unit_price as total\n" +
            "FROM order_detail order_detail1, consignment consignment1, product product1, warehouse warehouse1\n" +
            "WHERE order_id = ?1 \n" +
            "AND order_detail1.consignment_id = consignment1.id\n" +
            "AND consignment1.product_id =  product1.id \n" +
            "AND consignment1.warehouse_id = warehouse1.id")
    List<Map<String, Object>> getImportOrderDetail(Integer orderId);
}

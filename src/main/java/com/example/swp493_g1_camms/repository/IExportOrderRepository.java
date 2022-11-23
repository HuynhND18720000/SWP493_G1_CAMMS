package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface IExportOrderRepository extends JpaRepository<Order, Long> {

    // dem so luong totalrecord de phan trang
    //kiem tra lai phan nay no van chua ca xuat ra
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
            "                    JOIN consignment_product cp ON cp.product_id = p.id AND cp.consignment_id = c.id" +
            " \n" +
            "                    WHERE  c.import_date is not null AND cp.quantity > 0 AND p.id= ?1\n" +
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

}

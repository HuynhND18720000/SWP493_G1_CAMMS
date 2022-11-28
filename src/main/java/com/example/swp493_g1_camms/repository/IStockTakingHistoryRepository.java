package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Product;
import com.example.swp493_g1_camms.entities.StockTakingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface IStockTakingHistoryRepository extends JpaRepository<StockTakingHistory, Long> {

    @Query(value = "SELECT sth FROM StockTakingHistory sth "
            + "Where sth.warehouse.id = CASE WHEN ?1 IS NOT NULL THEN ?1 ELSE sth.warehouse.id END "
            + "AND sth.user.id = CASE WHEN ?2 IS NOT NULL THEN ?2 ELSE sth.user.id END "
            + "AND sth.createDate >= CASE WHEN ?3 IS NOT NULL THEN ?3 ELSE sth.createDate END "
            + "AND sth.createDate <= CASE WHEN ?4 IS NOT NULL THEN ?4 ELSE sth.createDate END "
            + "AND sth.deletedAt = false ")

    Page<StockTakingHistory> findAllStockTakingHistory(Long wareHouseId, Long userId, String startDate,
                                                                   String endDate, Pageable pageable);

    @Query("SELECT sth FROM StockTakingHistory sth Where sth.id = ?1 AND sth.deletedAt = false" )
    StockTakingHistory findStockTakingHistoryById(Long stockTakingHistoryId);

    @Query(value = "select distinct p from OrderDetail as od \n" +
            "join Order as o on od.order.id = o.id\n" +
            "join Consignment as c on c.id = od.consignment.id\n" +
            "join Warehouse as w on w.id = c.warehouse.id and w.id = ?1\n" +
            "join ConsignmentProduct as cp on cp.consignment.id = c.id\n" +
            "join Product as p on p.id = cp.product.id and p.quantity > 0\n" +
            "where o.orderType.id = 1 and o.status.id = 2 and o.deletedAt = false")
    List<Product> getProductByWarehouse(Long warehouse_id);

    @Query(value = "SELECT MAX(sth.id) FROM StockTakingHistory as sth")
    Long getLastStockId();
}

package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.StockTakingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

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

}

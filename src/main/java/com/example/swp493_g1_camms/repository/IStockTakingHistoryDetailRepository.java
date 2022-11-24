package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.StockTakingHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface IStockTakingHistoryDetailRepository extends JpaRepository<StockTakingHistoryDetail, Long> {

    @Query("SELECT sthDetail FROM StockTakingHistoryDetail sthDetail "
            + "Where sthDetail.stockTakingHistory.id = ?1 " )
    List<StockTakingHistoryDetail> findAllByStockTakingHistoryId(Long id);

}

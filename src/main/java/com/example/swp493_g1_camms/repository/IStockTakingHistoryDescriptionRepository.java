package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import com.example.swp493_g1_camms.entities.StockTakingHistoryDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface IStockTakingHistoryDescriptionRepository extends JpaRepository<StockTakingHistoryDescription, Long> {


    @Query(" SELECT sthdes FROM StockTakingHistoryDescription sthdes "
            + "Where sthdes.stockTakingHistory.id = ?1 ")
    List<StockTakingHistoryDescription> findAllDescriptionByStockTakingHistoryId(Long Id);

// cach 2
//    @Query("SELECT sthd FROM StockTakingHistoryDescription sthd " + "Where sthd.consignment.id IN (?1)" )
//    List<StockTakingHistoryDescription> findAllConsignmentInStockTakingHistoryDetail(List<Long> listConsignmentId);
}

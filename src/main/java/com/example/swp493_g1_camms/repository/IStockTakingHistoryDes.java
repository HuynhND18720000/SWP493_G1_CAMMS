package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.StockTakingHistoryDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockTakingHistoryDes extends JpaRepository<StockTakingHistoryDescription, Long> {
}

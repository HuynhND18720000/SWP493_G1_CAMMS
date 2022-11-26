package com.example.swp493_g1_camms.services.interfaceService;

import org.springframework.http.ResponseEntity;

public interface IStockTakingHistoryService {

    ResponseEntity<?> findListDetailById(Long stockTakingHistoryId);

    ResponseEntity<?> findAllStockTakingHistory(Integer pageIndex, Integer pageSize, Long wareHouseId, Long userId,
                              String startDate, String endDate, String orderBy);

    ResponseEntity<?> createStockTakingHistory();

    ResponseEntity<?> getProducFromConsignmentInWarehouse(Long warehouse_id);
}
